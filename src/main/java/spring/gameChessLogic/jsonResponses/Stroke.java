package spring.gameChessLogic.jsonResponses;

public class Stroke {
    private boolean isMoved;
    private Cell fromCell;
    private Cell toCell;
    private boolean isPawnTransformation = false;
    private String pawnTransformationToType;
    private int playerIdForPawnTransformation;

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

    public boolean isPawnTransformation() {
        return isPawnTransformation;
    }

    public void setPawnTransformationToType(String pawnTransformationToType) {
        this.isPawnTransformation = true;
        this.pawnTransformationToType = pawnTransformationToType;
    }

    public void setPawnTransformation(boolean pawnTransformation) {
        isPawnTransformation = pawnTransformation;
    }

    public String getPawnTransformationToType() {
        return pawnTransformationToType;
    }

    public int getPlayerIdForPawnTransformation() {
        return playerIdForPawnTransformation;
    }

    public void setPlayerIdForPawnTransformation(int playerIdForPawnTransformation) {
        this.playerIdForPawnTransformation = playerIdForPawnTransformation;
    }
}
