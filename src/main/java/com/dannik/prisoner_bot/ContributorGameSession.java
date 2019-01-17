package com.dannik.prisoner_bot;


import com.dannik.prisoner_bot.contributors.Contributor;
import com.dannik.prisoner_bot.prisoners.adv.AdvancedPlayer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ContributorGameSession {

    private String uri;
    private WebSocket websocket;
    private String prefix;
    private boolean debug = true;

    private Map<Integer, Contributor> tables = new HashMap<>();
    private Class<? extends Contributor> playerClass;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void start() {

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

                        onMessage(message);

                        switch (response.get("state").asText()) {
                            case "info":
                                onConnect(response.get("name").asText(), response.get("version").asText());
                                break;
                            case "access":
                                onAuthorize(response.get("user").asText());
                                break;
                            case "start":
                                int gameid = response.get("game").asInt();
                                double termProb = response.get("parameters").get("termination_probability").asDouble();
                                double contribution = response.get("parameters").get("contribution").asDouble();
                                int hand = response.get("hand").asInt();
                                double[] income = objectMapper.convertValue(response.get("parameters").get("income"), double[].class);

                                onStart(gameid, termProb, contribution, income, hand);
                                break;
                            case "turnover":
                                int[] moves = objectMapper.convertValue(response.get("moves"), int[].class);

                                onEnemyMove(response.get("game").asInt(), moves);
                                break;
                            case "gameover":
                                double[] scores = objectMapper.convertValue(response.get("scores"), double[].class);

                                onGameOver(response.get("game").asInt(), scores);
                                break;
                            case "error":
                                handleError(response.get("error").asText());
                                break;
                        }

                    }
                });

            websocket.connect();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (WebSocketException e) {
            e.printStackTrace();
        }

    }


    public void onSend(String message) {
        System.out.printf("%s Send: %s \n", prefix, message);
    }

    public void onMessage(String message) {
        System.out.printf("%s Receive: %s \n", prefix, message);
    }

    public void onConnect(String name, String version) {
        System.out.printf("%s Connected to server. Game: %s; Version: %s \n", prefix, name, version);

        String login = System.getProperty("game.login");
        String password = System.getProperty("game.password");

        ObjectNode request = objectMapper.createObjectNode();
        request.put("state", "login");
        request.put("debug", debug);

        if (StringUtils.isNotBlank(login)) {
            request.put("login", login);
        }
        if (StringUtils.isNotBlank(password)) {
            request.put("password", password);
        }

        sendMessage(request);
    }

    public void onAuthorize(String name) {

        System.out.printf("%s Authorized as %s \n", prefix, name);
    }

    public void onStart(int gameId, double termProb, double contribution, double[] income, int hand) {

        try {
            Contributor player = playerClass.getConstructor(String.class).newInstance("kekes");
            player.gameStarted(contribution, income, termProb, hand);
            tables.put(gameId, player);

            makeMove(gameId);

        } catch (InstantiationException | IllegalAccessException
            | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }


    public void onEnemyMove(int gameId, int[] result) {
        Contributor player = tables.get(gameId);
        player.roundEnd(result);

        makeMove(gameId);
    }

    public void onGameOver(int gameId, double[] scores) {
        Contributor player = tables.get(gameId);
        System.out.printf(">>>> The game %d is over \n", gameId);
        //todo: add metrics
    }


    public void handleError(String message) {
        System.out.printf("%s Error received: %s \n", prefix, message);
        websocket.disconnect();
    }

    private void makeMove(int gameId) {
        Contributor player = tables.get(gameId);

        ObjectNode request = objectMapper.createObjectNode();
        request.put("state", "move");
        request.put("strategy", player.ask());
        request.put("game", gameId);

        sendMessage(request);
    }

    public void sendMessage(JsonNode request){
        String message = request.toString();
        websocket.sendText(message);
        onSend(message);
    }


    public void stop() {
        websocket.disconnect();
    }


    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setPlayer(Class<? extends Contributor> player) {
        this.playerClass = player;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
