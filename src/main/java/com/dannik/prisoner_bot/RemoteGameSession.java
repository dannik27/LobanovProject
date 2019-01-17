package com.dannik.prisoner_bot;


import com.dannik.prisoner_bot.prisoners.adv.AdvancedPlayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class RemoteGameSession {

    private String uri;
    private Map<Integer, Player> tables = new HashMap<>();
    private Class<? extends Player> playerClass;
    private GameSocket websocket;
    private String prefix;
    private boolean debug = true;

    private ObjectMapper mapper = new ObjectMapper();

    public void start() {

        websocket = new GameSocket(uri, new GameSocket.GameSocketListener() {
            @Override
            public void onSend(String message) {
                System.out.printf("%s Send: %s \n", prefix, message);
            }

            @Override
            public void onMessage(String message) {
                System.out.printf("%s Receive: %s \n", prefix, message);
            }

            @Override
            public void onConnect(String name, String version) {
                System.out.printf("%s Connected to server. Game: %s; Version: %s \n", prefix, name, version);

                String login = System.getProperty("game.login");
                String password = System.getProperty("game.password");

                ObjectNode request = mapper.createObjectNode();
                request.put("state", "login");
                request.put("debug", debug);

                if (StringUtils.isNotBlank(login)) {
                    request.put("login", login);
                }
                if (StringUtils.isNotBlank(password)) {
                    request.put("password", password);
                }

                websocket.sendMessage(request);
            }

            @Override
            public void onAuthorize(String name) {

                System.out.printf("%s Authorized as %s \n", prefix, name);
            }

            @Override
            public void onStart(int gameId, double terminationProbability, int hand, Matrix matrix) {

                try {
                    Player player = playerClass.getConstructor(String.class).newInstance("kekes");

                    if (player instanceof AdvancedPlayer) {
                        ((AdvancedPlayer) player).setTerminationProbability(terminationProbability);
                    }

                    player.setMatrix(matrix);
                    player.setEnemy(null);
                    player.setHand(hand);
                    tables.put(gameId, player);

                    makeMove(gameId);
                } catch (InstantiationException | IllegalAccessException
                        | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onEnemyMove(int gameId, int[] strategies) {
                Player player = tables.get(gameId);
                player.addGameResult(null, strategies[player.getHand() == 1 ? 0 : 1]);

                makeMove(gameId);
            }

            @Override
            public void onGameOver(int gameId, double[] scores) {
                Player player = tables.get(gameId);
                System.out.printf(">>>> The game %d is over \n", gameId);
                System.out.printf(">>>> Player: %s hand: %d \n", player.getStrategyName(), player.getHand());
                System.out.printf(">>>> Scores: %f -- %f", scores[0], scores[1]);
                System.out.printf(">>>> Matrix is: \n");
                MatrixUtils.printMatrix(player.getMatrix());
            }

            @Override
            public void onError(String message) {
                System.out.printf("%s Error received: %s \n", prefix, message);
                websocket.disconnect();
            }


        });
        websocket.connect();

    }

    private void makeMove(int gameId) {
        Player player = tables.get(gameId);
        int choise = player.getHand() == 0
                ? player.ask()
                : invert(player.ask());

        ObjectNode request = mapper.createObjectNode();
        request.put("state", "move");
        request.put("strategy", choise);
        request.put("game", gameId);

        websocket.sendMessage(request);
    }

    private int invert(int input) {
        return input == 1 ? 0 : 1;
    }

    public void stop() {
        websocket.disconnect();
    }


    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setPlayer(Class<? extends Player> player) {
        this.playerClass = player;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
