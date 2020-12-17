package spring.gameChessLogic.jsonResponses;

public class Stroke {
    private boolean isMoved;
    private Cell fromCell;
    private Cell toCell;

    public Stroke() {
        isMoved = false;
        fromCell = toCell = null;
    }

    public Stroke(Cell fromCell, Cell toCell) {
        isMoved = true;
        this.fromCell = fromCell;
        this.toCell = toCell;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public Cell getFromCell() {
        return fromCell;
    }

    public Cell getToCell() {
        return toCell;
    }

    public void setMoved(boolean moved) {
        isMoved = moved;
    }
}
