package spring.gameChessLogic.jsonResponses;

public class Cell {
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        Cell cell = (Cell) obj;
        if (x == cell.getX() && y == cell.getY()) {
            return true;
        } else {
            return false;
        }
    }
}
