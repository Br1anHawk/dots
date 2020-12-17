package spring.gameChessLogic.jsonResponses;

public class DataPlayerReady {
    int playerId;
    boolean isReady;

    public DataPlayerReady(int playerId, boolean isReady) {
        this.playerId = playerId;
        this.isReady = isReady;
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean isReady() {
        return isReady;
    }
}

