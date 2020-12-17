package spring.gameChessLogic.chessMans;

import spring.gameChessLogic.Player;

import java.util.ArrayList;

public class Pawn extends ChessMan {

    private boolean readyForTransformation = false;

    public Pawn(int x, int y, int playerId) {
        super(x, y, "pawn", playerId);
    }

    @Override
    public boolean move(int x, int y, ChessMan[][] field, ArrayList<Player> players, Player currentPlayerStroke, boolean isForTest) {
        boolean isMoved = super.move(x, y, field, players, currentPlayerStroke, isForTest);
        if (isMoved) {

        }
        return isMoved;
    }
}
