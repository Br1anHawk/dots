package spring.gameDotsLogic;

import java.util.ArrayList;

public class Player {
    private int id;
    private String nickname;
    private ArrayList<Dot> dots;
    private double square;

    public Player(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        dots = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void addDot(Dot dot) {
        dots.add(dot);
    }

    public ArrayList<Dot> getDots() {
        return dots;
    }
}
