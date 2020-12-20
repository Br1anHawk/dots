package spring.gameChessLogic;

import spring.gameChessLogic.chessMans.*;
import spring.gameChessLogic.chessMans.checkAvailableTurn.Checker;
import spring.gameChessLogic.jsonResponses.Cell;
import spring.gameChessLogic.jsonResponses.DataPackageToClient;
import spring.gameChessLogic.jsonResponses.Stroke;

import java.util.*;

public class GameChess {
    private static final int width = 8;
    private static final int height = 8;
    private ChessMan[][] field;
    private ArrayList<Player> players;
    private Queue<Player> playersQueue;
    private Player currentPlayerStroke;
    private ChessMan selectedChessman;
    private boolean isFinish = false;
    private Player winner;

    private ArrayList<Stroke> lastStrokes = new ArrayList<>(2);

    public GameChess(ArrayList<Player> players) {
        this.players = players;
        field = new ChessMan[height][width];
        this.playersQueue = new LinkedList<>();
        this.playersQueue.addAll(players);
        lastStrokes.add(new Stroke());
        initChessmans();
        initField();
    }

    private void initChessmans() {
        nextPlayerStroke();
        Player firstPlayer = currentPlayerStroke;
        nextPlayerStroke();
        Player secondPlayer = currentPlayerStroke;
        nextPlayerStroke();
        for (int j = 0; j < field[0].length; j++) {
            firstPlayer.addChessman(new Pawn(height - 1 - 1, j, firstPlayer.getId()));
            secondPlayer.addChessman(new Pawn(1, j, secondPlayer.getId()));
        }
        firstPlayer.addChessman(new Rook(height - 1, 0, firstPlayer.getId()));
        firstPlayer.addChessman(new Rook(height - 1, width - 1 - 0, firstPlayer.getId()));
        firstPlayer.addChessman(new Horse(height - 1, 1, firstPlayer.getId()));
        firstPlayer.addChessman(new Horse(height - 1, width - 1 - 1, firstPlayer.getId()));
        firstPlayer.addChessman(new Elephant(height - 1, 2, firstPlayer.getId()));
        firstPlayer.addChessman(new Elephant(height - 1, width - 1 - 2, firstPlayer.getId()));
        firstPlayer.addChessman(new Queen(height - 1, 3, firstPlayer.getId()));
        King king = new King(height - 1, 4, firstPlayer.getId());
        firstPlayer.addChessman(king);
        firstPlayer.setKing(king);

        secondPlayer.addChessman(new Rook(0, 0, secondPlayer.getId()));
        secondPlayer.addChessman(new Rook(0, width - 1 - 0, secondPlayer.getId()));
        secondPlayer.addChessman(new Horse(0, 1, secondPlayer.getId()));
        secondPlayer.addChessman(new Horse(0, width - 1 - 1, secondPlayer.getId()));
        secondPlayer.addChessman(new Elephant(0, 2, secondPlayer.getId()));
        secondPlayer.addChessman(new Elephant(0, width - 1 - 2, secondPlayer.getId()));
        secondPlayer.addChessman(new Queen(0, 3, secondPlayer.getId()));
        king = new King(0, 4, secondPlayer.getId());
        secondPlayer.addChessman(king);
        secondPlayer.setKing(king);
    }

    private void initField() {
        Player firstPlayer = currentPlayerStroke;
        nextPlayerStroke();
        Player secondPlayer = currentPlayerStroke;
        nextPlayerStroke();
        for (ChessMan chessMan : firstPlayer.getChessmans()) {
            field[chessMan.getX()][chessMan.getY()] = chessMan;
        }
        for (ChessMan chessMan : secondPlayer.getChessmans()) {
            field[chessMan.getX()][chessMan.getY()] = chessMan;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Stroke> getLastStrokes() {
        return lastStrokes;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public Player getWinner() {
        return winner;
    }

    public DataPackageToClient getDataPackageToClient() {
        DataPackageToClient dataPackageToClient = new DataPackageToClient(players, lastStrokes);
        return dataPackageToClient;
    }

    private void nextPlayerStroke() {
        currentPlayerStroke = this.playersQueue.peek();
        this.playersQueue.add(this.playersQueue.poll());
    }

    public ArrayList<Cell> getAvailableTurnCells(int x, int y, String nickname) {
        if (!currentPlayerStroke.getNickName().equals(nickname)) {
            return null;
        }
        selectedChessman = field[x][y];
        ArrayList<Cell> cells = Checker.getAvailableTurnCells(selectedChessman, field, players, currentPlayerStroke, true);
        //printFieldInConsole("check");
        return cells;
    }

    public ArrayList<Stroke> chessmanMoveTo(int x, int y) {
        int selectedChessmanX = selectedChessman.getX();
        int selectedChessmanY = selectedChessman.getY();
        boolean isMoved = selectedChessman.move(x, y, field, players, currentPlayerStroke, false);
        for (Stroke stroke : lastStrokes) {
            stroke.setMoved(false);
        }
        if (isMoved) {
            nextPlayerStroke();
            checkupKingUnderCheck();


            Stroke stroke = new Stroke(new Cell(selectedChessmanX, selectedChessmanY), new Cell(x, y));
            lastStrokes.clear();
            lastStrokes.add(stroke);
        }
        if (selectedChessman instanceof King) {
            King king = (King) selectedChessman;
            if (king.isWasCastling()) {
                lastStrokes.add(king.getStrokeForRookAfterCastling());
            }
        }
        //printFieldInConsole("move");
        return lastStrokes;
    }

    private void checkupKingUnderCheck() {
        King king = null;
        King checkKing = players.get(0).getKing();
        checkKing.setCheck(false);
        Cell checkCell = new Cell(checkKing.getX(), checkKing.getY());
        if (Checker.checkCellForKingOnUnderAttack(checkCell, field, players, players.get(0), players.get(1).getChessmans())) {
            king = checkKing;
        }
        checkKing = players.get(1).getKing();
        checkKing.setCheck(false);
        checkCell = new Cell(checkKing.getX(), checkKing.getY());
        if (king == null) {
            if (Checker.checkCellForKingOnUnderAttack(checkCell, field, players, players.get(1), players.get(0).getChessmans())) {
                king = checkKing;
            }
        }
        if (king != null) {
            king.setCheck(true);
            checkupCheckmateKing(king);
        }
    }

    private void checkupCheckmateKing(King king) {
        ArrayList<Cell> cells = new ArrayList<>();
        int pId = king.getPlayerId();
        ArrayList<ChessMan> chessmen = players.get(pId).getChessmans();
        for (ChessMan chessMan : chessmen) {
            if (!chessMan.isAlive()) {
                continue;
            }
            cells.addAll(Checker.getAvailableTurnCells(chessMan, field, players, currentPlayerStroke, true));
        }
        if (cells.size() == 0) {
            king.setCheckmate(true);
        }
    }

    public Player findPlayerByNickname(String nickname) {
        for (Player player : players) {
            if (player.getNickName().equals(nickname)) {
                return player;
            }
        }
        return null;
    }

    public void finishByPlayerSurrender(String nickname) {
        isFinish = true;
        Player playerSurrender = findPlayerByNickname(nickname);
        for (Player player : players) {
            if (playerSurrender != player) {
                winner = player;
                break;
            }
        }
    }

    private void printFieldInConsole(String operation) {
        Map<String, String> map = new HashMap<>();
        map.put("pawn", "P");
        map.put("rook", "R");
        map.put("horse", "H");
        map.put("elephant", "E");
        map.put("queen", "Q");
        map.put("king", "K");
        System.out.println("________________________________________");
        System.out.println("Operation - " + operation);
        String tab = "          ";
        //System.out.println("Player " + "0" + tab + "        " + "Player " + "1");
        for (int i = 0; i < field.length; i++) {
            System.out.print("|");
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == null) {
                    System.out.print(" *|");
                } else {
                    System.out.print(map.get(field[i][j].getType()) + field[i][j].getPlayerId() + "|");
                }
            }
            System.out.println();
            System.out.println("|-----------------------|");
        }
    }
}
