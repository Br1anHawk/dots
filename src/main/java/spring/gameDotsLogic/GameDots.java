package spring.gameDotsLogic;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameDots {

    private int[][] field;
    private Queue<Player> queueOfPlayers;
    private ArrayList<Player> players;

    private Player isTurnPlayer;

    public GameDots(int width, int height, ArrayList<Player> players) {
        field = new int[width][height];
        this.players = players;
        this.queueOfPlayers = new LinkedList<>();
        this.queueOfPlayers.addAll(players);
        isTurnPlayer = this.queueOfPlayers.poll();
        this.queueOfPlayers.add(isTurnPlayer);
    }

    public int getWidth() {
        return field.length;
    }

    public int getHeight() {
        return field[0].length;
    }

    public void nextTurn(int x, int y) {
        Dot dot = new Dot(x, y);
        isTurnPlayer.addDot(dot);
        field[x][y] = isTurnPlayer.getId();
        capturePoints();

        selectNextPlayerTurn();
    }

    private void capturePoints() {
        for (Player player : players) {
            for (Dot dot : player.getDots()) {
                if (!checkDotFreedom(dot)) {

                }
            }
        }
    }

    private boolean checkDotFreedom(Dot dot) {
        boolean isFreedom = true;


        return isFreedom;
    }

    private boolean checkDirection(Dot dot, Direction direction) {
        boolean isPossible = false;
        int x = dot.getX();
        int y = dot.getY();
        return isPossible;
    }

    private void selectNextPlayerTurn() {
        Player player = queueOfPlayers.poll();
        isTurnPlayer = player;
        queueOfPlayers.add(player);
    }

    public DataPackageToClient getDataPackageToClient() {
        return new DataPackageToClient(players);
    }
}
