package spring.gameChessLogic;

import spring.gameChessLogic.chessMans.ChessMan;
import spring.gameChessLogic.chessMans.King;

import java.util.ArrayList;

public class Player {
    private int id;
    private String nickName;
    private King king;
    private ArrayList<ChessMan> chessmans;

    private boolean isReady = false;

    public Player(int id, String nickName) {
        this.id = id;
        this.nickName = nickName;
        chessmans = new ArrayList<>(16);
    }

    public int getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public King getKing() {
        return king;
    }

    public ArrayList<ChessMan> getChessmans() {
        return chessmans;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public void addChessman(ChessMan chessMan) {
        chessmans.add(chessMan);
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady() {
        isReady = true;
    }

    public void unReady() {
        isReady = false;
    }

}
