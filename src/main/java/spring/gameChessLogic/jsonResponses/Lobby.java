package spring.gameChessLogic.jsonResponses;

import spring.gameChessLogic.Player;

import java.util.ArrayList;

public class Lobby {
    private int MAX_PLAYERS_IN_LOBBY;
    private ArrayList<Player> players;
    private boolean gameIsStart = false;
    private String gameMappingURL;
    private boolean isFull = false;
    private boolean gameIsFinish = false;
    private Player winner;

    public Lobby(int maxPlayersInLobby) {
        players = new ArrayList<>(maxPlayersInLobby);
        MAX_PLAYERS_IN_LOBBY = maxPlayersInLobby;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean isGameIsStart() {
        return gameIsStart;
    }

    public String getGameMappingURL() {
        return gameMappingURL;
    }

    public boolean isFull() {
        return isFull;
    }

    public boolean isGameIsFinish() {
        return gameIsFinish;
    }

    public Player getWinner() {
        return winner;
    }

    public void addPlayer(Player player) {
        players.add(player);
        if (players.size() == MAX_PLAYERS_IN_LOBBY) {
            isFull = true;
        }
    }

    public void removePlayer(int playerId) {
        players.remove(playerId);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setId(i);
        }
        isFull = false;
    }

    public void startGame(String url) {
        gameIsStart = true;
        gameMappingURL = url;
    }
}
