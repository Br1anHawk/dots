package spring.gameChessLogic.jsonResponses;

import spring.gameChessLogic.GameChess;
import spring.gameChessLogic.Player;

import java.util.ArrayList;

public class Lobby {
    private int id;
    private int MAX_PLAYERS_IN_LOBBY;
    private ArrayList<Player> players;
    private boolean gameIsStart = false;
    private String gameMappingURL;
    private boolean isFull = false;
    private GameChess gameChess;
    private boolean gameIsFinish = false;
    private Player winner;
    private boolean isEmpty = true;

    public Lobby(int id, int maxPlayersInLobby) {
        this.id = id;
        players = new ArrayList<>(maxPlayersInLobby);
        MAX_PLAYERS_IN_LOBBY = maxPlayersInLobby;
    }

    public int getId() {
        return id;
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

    public GameChess getGameChess() {
        return gameChess;
    }

    public void setGameChess(GameChess gameChess) {
        this.gameChess = gameChess;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
        gameIsFinish = true;
    }


    public boolean isGameIsFinish() {
        return gameIsFinish;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean addPlayer(Player player) {
        if (isFull) {
            return false;
        }
        players.add(player);
        isEmpty = false;
        if (players.size() == MAX_PLAYERS_IN_LOBBY) {
            isFull = true;
        }
        return true;
    }

    public void removePlayer(int playerId) {
        players.remove(playerId);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setId(i);
        }
        isFull = false;
        if (players.size() == 0) {
            isEmpty = true;
        }
    }

    public void startGame(String url) {
        gameIsStart = true;
        gameMappingURL = url;
    }

    public int getPlayerIdByNickname(String nickname) {
        for (Player player : players) {
            if (player.getNickName().equals(nickname)) {
                return player.getId();
            }
        }
        return -1;
    }
}
