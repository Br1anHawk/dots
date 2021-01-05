package spring.gameChessLogic.jsonResponses;

public class DataAboutPawnTransformation {
    private int x;
    private int y;
    private int playerId;

    public DataAboutPawnTransformation(int x, int y, int playerId) {
        this.x = x;
        this.y = y;
        this.playerId = playerId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlayerId() {
        return playerId;
    }
}
