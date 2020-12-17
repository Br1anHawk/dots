package spring.gameDotsLogic;

public class Dot {
    private int x;
    private int y;
    private boolean isCaptured;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
        isCaptured = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCaptured() {
        return isCaptured;
    }
}
