package spring.gameChessLogic.chessMans.checkAvailableTurn;

import spring.gameChessLogic.Player;
import spring.gameChessLogic.Vector;
import spring.gameChessLogic.chessMans.*;
import spring.gameChessLogic.jsonResponses.Cell;

import java.util.ArrayList;
import java.util.Iterator;

public class Checker {
    public static ArrayList<Cell> getAvailableTurnCells(ChessMan chessMan, ChessMan[][] field, ArrayList<Player> players, Player currentPlayerStroke, boolean checkMovable) {
        ArrayList<Cell> cells = new ArrayList<>();
        ArrayList<Vector> vectors = new ArrayList<>();

        if (chessMan == null) {
            return cells;
        }
        if (chessMan.getPlayerId() != currentPlayerStroke.getId()) {
            return cells;
        }

        if (chessMan instanceof Pawn) {
            int x = chessMan.getX();
            int y = chessMan.getY();
            switch (chessMan.getPlayerId()) {
                case 0:
                    if (field[x - 1][y] == null) {
                        cells.add(new Cell(x - 1, y));
                    }
                    try {
                        if (field[x - 1][y - 1].getPlayerId() != chessMan.getPlayerId() && field[x - 1][y - 1] != null) {
                            cells.add(new Cell(x - 1, y - 1));
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (field[x - 1][y + 1].getPlayerId() != chessMan.getPlayerId() && field[x - 1][y + 1] != null) {
                            cells.add(new Cell(x - 1, y + 1));
                        }
                    } catch (Exception e) {

                    }
                    if (x == field.length - 1 - 1) {
                        if (field[x - 2][y] == null && field[x - 1][y] == null) {
                            cells.add(new Cell(x - 2, y));
                        }
                    }
                    break;
                case 1:
                    if (field[x + 1][y] == null) {
                        cells.add(new Cell(x + 1, y));
                    }
                    try {
                        if (field[x + 1][y - 1].getPlayerId() != chessMan.getPlayerId() && field[x + 1][y - 1] != null) {
                            cells.add(new Cell(x + 1, y - 1));
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (field[x + 1][y + 1].getPlayerId() != chessMan.getPlayerId() && field[x + 1][y + 1] != null) {
                            cells.add(new Cell(x + 1, y + 1));
                        }
                    } catch (Exception e) {

                    }
                    if (x == 0 + 1) {
                        if (field[x + 2][y] == null && field[x + 1][y] == null) {
                            cells.add(new Cell(x + 2, y));
                        }
                    }
                    break;
            }
        } else if (chessMan instanceof Horse) {
            int x = chessMan.getX();
            int y = chessMan.getY();
            vectors.add(new Vector(2, 1));
            vectors.add(new Vector(1, 2));
            vectors.add(new Vector(-1, 2));
            vectors.add(new Vector(-2, 1));
            for (Vector vector : vectors) {
                int vx = vector.getVx();
                int vy = vector.getVy();
                boolean conditionForContinueFirst = false;
                boolean conditionForContinueSecond = false;
                try {
                    if (field[x + vx][y + vy] == null) {
                        cells.add(new Cell(x + vx, y + vy));
                        conditionForContinueFirst = true;
                    }
                } catch (Exception e) {

                }
                try {
                    if (field[x - vx][y - vy] == null) {
                        cells.add(new Cell(x - vx, y - vy));
                        conditionForContinueSecond = true;
                    }
                } catch (Exception e) {

                }
                if (conditionForContinueFirst && conditionForContinueSecond) {
                    continue;
                }
                try {
                    if (field[x + vx][y + vy].getPlayerId() != chessMan.getPlayerId()) {
                        cells.add(new Cell(x + vx, y + vy));
                    }
                } catch (Exception e) {

                }
                try {
                    if (field[x - vx][y - vy].getPlayerId() != chessMan.getPlayerId()) {
                        cells.add(new Cell(x - vx, y - vy));
                    }
                } catch (Exception e) {

                }
            }
        } else if (chessMan instanceof King) {
            int x = chessMan.getX();
            int y = chessMan.getY();
            vectors.add(new Vector(1, 0));
            vectors.add(new Vector(1, 1));
            vectors.add(new Vector(0, 1));
            vectors.add(new Vector(-1, 1));
            vectors.add(new Vector(-1, 0));
            vectors.add(new Vector(-1, -1));
            vectors.add(new Vector(0, -1));
            vectors.add(new Vector(1, -1));
            for (Vector vector : vectors) {
                int vx = vector.getVx();
                int vy = vector.getVy();
                try {
                    if (field[x + vx][y + vy] == null) {
                        cells.add(new Cell(x + vx, y + vy));
                        continue;
                    }
                } catch (Exception e) {

                }
                try {
                    if (field[x + vx][y + vy].getPlayerId() != chessMan.getPlayerId()) {
                        cells.add(new Cell(x + vx, y + vy));
                    }
                } catch (Exception e) {

                }
            }
            King king = (King) chessMan;
            if (king.isHaveCastling()) {
                ArrayList<Integer> vectorsOfY = new ArrayList<>(2);
                vectorsOfY.add(1);
                vectorsOfY.add(-1);
                for (int dy : vectorsOfY) {
                    x = king.getX();
                    y = king.getY();
                    try {
                        while (true) {
                            y += dy;
                            ChessMan cm = field[x][y];
                            if (cm != null) {
                                if (cm instanceof Rook) {
                                    Rook rook = (Rook) cm;
                                    if (rook.isHaveCastling()) {
                                        cells.add(new Cell(x, king.getY() + 2 * dy));
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            }
        } else {

            if (chessMan instanceof Rook) {
                vectors.add(new Vector(1, 0));
                vectors.add(new Vector(0, 1));
                vectors.add(new Vector(-1, 0));
                vectors.add(new Vector(0, -1));
            }
            if (chessMan instanceof Elephant) {
                vectors.add(new Vector(1, 1));
                vectors.add(new Vector(-1, 1));
                vectors.add(new Vector(-1, -1));
                vectors.add(new Vector(1, -1));
            }
            if (chessMan instanceof Queen) {
                vectors.add(new Vector(1, 0));
                vectors.add(new Vector(0, 1));
                vectors.add(new Vector(-1, 0));
                vectors.add(new Vector(0, -1));

                vectors.add(new Vector(1, 1));
                vectors.add(new Vector(-1, 1));
                vectors.add(new Vector(-1, -1));
                vectors.add(new Vector(1, -1));
            }
            for (Vector vector : vectors) {
                int x = chessMan.getX();
                int y = chessMan.getY();
                try {
                    while (true) {
                        x += vector.getVx();
                        y += vector.getVy();
                        if (field[x][y] == null) {
                            cells.add(new Cell(x, y));
                            continue;
                        }
                        if (field[x][y].getPlayerId() != chessMan.getPlayerId()) {
                            cells.add(new Cell(x, y));
                        }
                        break;
                    }
                } catch (Exception e) {

                }
            }
        }
        if (checkMovable) {
            Iterator<Cell> iterator = cells.iterator();
            int x = chessMan.getX();
            int y = chessMan.getY();
            while (iterator.hasNext()) {
                Cell cell = iterator.next();
                ChessMan cheessmanOnUnderAttack = field[cell.getX()][cell.getY()];
                if (!chessMan.move(cell.getX(), cell.getY(), field, players, currentPlayerStroke, true)) {
                    iterator.remove();
                    continue;
                }
                chessMan.moveBackTo(x, y);
                field[x][y] = chessMan;
                field[cell.getX()][cell.getY()] = cheessmanOnUnderAttack;
                if (cheessmanOnUnderAttack != null) {
                    cheessmanOnUnderAttack.returnOnBoard();
                }
            }
            if (chessMan instanceof King) {
                King king = (King) chessMan;

                Player opponentPlayer = players.get(0);
                if (players.get(0).getId() == currentPlayerStroke.getId()) {
                    opponentPlayer = players.get(1);
                }
                boolean isKingUnderAttack = Checker.checkCellForKingOnUnderAttack(new Cell(king.getX(), king.getY()), field, players, currentPlayerStroke, opponentPlayer.getChessmans());

                int kingY = king.getY();
                int dy = 2;
                ArrayList<TempCellForCastling> tempCellsForCastling = new ArrayList<>(2);
                if (king.isHaveCastling()) {
                    for (int i = 0; i < cells.size(); i++) {
                        if (cells.get(i).getY() - kingY == dy) {
                            tempCellsForCastling.add(new TempCellForCastling(i, 1));
                        }
                        if (kingY - cells.get(i).getY() == dy) {
                            tempCellsForCastling.add(new TempCellForCastling(i, -1));
                        }
                    }

                    for (TempCellForCastling tc : tempCellsForCastling) {
                        boolean removeCastlingMark = true;
                        if (!isKingUnderAttack) {
                            for (Cell cell : cells) {
                                if (cell.getX() == king.getX() && cell.getY() == kingY + tc.getDirectionY()) {
                                    removeCastlingMark = false;
                                    break;
                                }
                            }
                        }
                        if (removeCastlingMark) {
                            cells.remove(tc.getId());
                        }
                    }
                }
            }
        }
        return cells;
    }

    private static class TempCellForCastling {
        private int id;
        private int directionY;
        private boolean markForRemove;

        public TempCellForCastling(int id, int directionY) {
            this.id = id;
            this.directionY = directionY;
            this.markForRemove = false;
        }

        public int getId() {
            return id;
        }

        public int getDirectionY() {
            return directionY;
        }

        public boolean isMarkForRemove() {
            return markForRemove;
        }

        public void remove() {
            this.markForRemove = true;
        }
    }

    public static boolean checkCellForKingOnUnderAttack(Cell cell, ChessMan[][] field, ArrayList<Player> players, Player currentPlayerStroke, ArrayList<ChessMan> chessmansOpponentPlayer) {
        Player opponentPlayer = players.get(0);
        if (players.get(0).getId() == currentPlayerStroke.getId()) {
            opponentPlayer = players.get(1);
        }
        for (ChessMan chessMan : chessmansOpponentPlayer) {
            if (!chessMan.isAlive()) {
                continue;
            }
            ArrayList<Cell> cells = Checker.getAvailableTurnCells(chessMan, field, players, opponentPlayer, false);
            for (Cell tempCell : cells) {
                if (tempCell.equals(cell)) {
                    return true;
                }
            }
        }
        return false;
    }
}
