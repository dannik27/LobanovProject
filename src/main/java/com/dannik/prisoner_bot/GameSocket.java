package com.dannik.prisoner_bot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;

public class GameSocket {

    private WebSocket websocket;
    private GameSocketListener listener;
    private ObjectMapper objectMapper = new ObjectMapper();

    public GameSocket(String uri, GameSocketListener listener) {
        this.listener = listener;
        try {
            websocket = new WebSocketFactory()
                    .createSocket(uri)
                    .addListener(new WebSocketAdapter() {
                        @Override
                        public void onTextMessage(WebSocket ws, String message) {

                            JsonNode response = null;
                            try {
                                 response = new ObjectMapper().readTree(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            listener.onMessage(message);

                            switch (response.get("state").asText()){
                                case "info" :
                                    listener.onConnect(response.get("name").asText(), response.get("version").asText());
                                    break;
                                case "access" :
                                    listener.onAuthorize(response.get("user").asText());
                                    break;
                                case "start" :

                                    int gameid = response.get("game").asInt();
                                    double termProb = response.get("parameters").get("termination_probability").asDouble();
                                    int[] matrixSize = objectMapper.convertValue(response.get("parameters").get("number_of_strategies"), int[].class);
                                    double[][][] matrixValues = objectMapper.convertValue(response.get("parameters").get("payoff"), double[][][].class);
                                    int hand = response.get("hand").asInt();

                                    listener.onStart(gameid, termProb, hand,
                                            MatrixUtils.matrixFromMessage(matrixSize, matrixValues, hand));
                                    break;
                                case "turnover" :
                                    int[] strategies = objectMapper.convertValue(response.get("moves"), int[].class);

                                    listener.onEnemyMove(response.get("game").asInt(), strategies);
                                    break;
                                case "gameover" :
                                    double[] scores = objectMapper.convertValue(response.get("scores"), double[].class);

                                    listener.onGameOver(response.get("game").asInt(), scores);
                                    break;
                            }

                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            websocket.connect();
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        websocket.disconnect();
    }

    public void sendMessage(JsonNode request){
        String message = request.toString();
        websocket.sendText(message);
        listener.onSend(message);
    }

    interface GameSocketListener {

        void onSend(String message);
        void onMessage(String message);

        void onConnect(String name, String version);
        void onAuthorize(String name);
        void onStart(int gameId, double terminationProbability, int hand, Matrix matrix);
        void onEnemyMove(int gameId, int[] strategies);
        void onGameOver(int gameId, double[] scores);

    }

}
