package spring.gameChessLogic.chessMans;

import spring.gameChessLogic.Player;
import spring.gameChessLogic.chessMans.checkAvailableTurn.Checker;
import spring.gameChessLogic.jsonResponses.Cell;

import java.util.ArrayList;

abstract public class ChessMan {
    private int x;
    private int y;
    private String type;
    private int playerId;
    private boolean isAlive;

    public ChessMan(int x, int y, String type, int playerId) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.playerId = playerId;
        this.isAlive = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void knockedDown() {
        this.isAlive = false;
    }

    public void returnOnBoard() {
        this.isAlive = true;
    }

    public void moveBackTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean move(int x, int y, ChessMan[][] field, ArrayList<Player> players, Player currentPlayerStroke, boolean isForTest) {
        ArrayList<Cell> cells = Checker.getAvailableTurnCells(this, field, players, currentPlayerStroke, false);
        for (Cell cell : cells) {
            if (cell.getX() == x && cell.getY() == y) {
                int saveX = this.x;
                int saveY = this.y;
                ChessMan cheessmanOnUnderAttack = field[x][y];

                field[this.x][this.y] = null;
                this.x = x;
                this.y = y;
                field[x][y] = this;
                if (cheessmanOnUnderAttack != null) {
                    cheessmanOnUnderAttack.knockedDown();
                }

                Player opponentPlayer = players.get(0);
                if (players.get(0).getId() == currentPlayerStroke.getId()) {
                    opponentPlayer = players.get(1);
                }
                if (Checker.checkCellForKingOnUnderAttack(new Cell(currentPlayerStroke.getKing().getX(), currentPlayerStroke.getKing().getY()), field, players, currentPlayerStroke, opponentPlayer.getChessmans())) {
                    field[saveX][saveY] = this;
                    this.x = saveX;
                    this.y = saveY;
                    field[x][y] = cheessmanOnUnderAttack;
                    if (cheessmanOnUnderAttack != null) {
                        cheessmanOnUnderAttack.returnOnBoard();
                    }
                    return false;
                }
                if (this instanceof King && !isForTest) {
                    King king = (King) this;
                    if (king.isHaveCastling()) {
                        king.blockCastling();
                    }
                }
                if (this instanceof Rook && !isForTest) {
                    Rook rook = (Rook) this;
                    if (rook.isHaveCastling()) {
                        rook.blockCastling();
                    }
                }
                return true;
            }
        }
        return false;
    }
}
