package spring.gameChessLogic.chessMans;

import spring.gameChessLogic.Player;
import spring.gameChessLogic.jsonResponses.Cell;
import spring.gameChessLogic.jsonResponses.Stroke;

import java.util.ArrayList;

public class King extends ChessMan {

    private ChessMan attacker;
    private boolean haveCastling;
    private boolean wasCastling;
    private Stroke strokeForRookAfterCastling;

    private boolean isCheck;
    private boolean isCheckmate;

    public King(int x, int y, int playerId) {
        super(x, y, "king", playerId);
        attacker = null;
        haveCastling = true;
        wasCastling = false;
        isCheck = false;
        isCheckmate = false;
    }

    public boolean isHaveCastling() {
        return haveCastling;
    }

    public void blockCastling() {
        haveCastling = false;
    }

    public boolean isWasCastling() {
        return wasCastling;
    }

    public Stroke getStrokeForRookAfterCastling() {
        wasCastling = false;
        return strokeForRookAfterCastling;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public boolean isCheckmate() {
        return isCheckmate;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public void setCheckmate(boolean checkmate) {
        isCheckmate = checkmate;
    }

    @Override
    public boolean move(int x, int y, ChessMan[][] field, ArrayList<Player> players, Player currentPlayerStroke, boolean isForTest) {
        int dy = y - this.getY();
        boolean isMoved = super.move(x, y, field, players, currentPlayerStroke, isForTest);
        if (isMoved && (dy == 2 || dy == -2) && !isForTest) {
            int tempY = y + dy / 2;
            Rook rook;
            while (true) {
                if (field[x][tempY] == null) {
                    tempY += dy / 2;
                    continue;
                } else {
                    rook = (Rook) field[x][tempY];
                    break;
                }
            }
            field[x][y] = null;
            this.knockedDown();
            rook.move(x, y - dy / 2, field, players, currentPlayerStroke, false);
            this.returnOnBoard();
            field[x][y] = this;

            strokeForRookAfterCastling = new Stroke(new Cell(x, tempY), new Cell(x, y - dy / 2));
            wasCastling = true;
        }
        return isMoved;
    }
}
