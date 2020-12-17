package spring.gameChessLogic.chessMans;

public class Rook extends ChessMan {

    private boolean haveCastling;

    public Rook(int x, int y, int playerId) {
        super(x, y, "rook", playerId);
        haveCastling = true;
    }

    public boolean isHaveCastling() {
        return haveCastling;
    }

    public void blockCastling() {
        haveCastling = false;
    }
}
