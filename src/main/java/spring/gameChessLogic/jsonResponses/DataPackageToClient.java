package spring.gameChessLogic.jsonResponses;

import spring.gameChessLogic.Player;

import java.util.ArrayList;

public class DataPackageToClient {
    private ArrayList<Player> players;
    private ArrayList<Stroke> lastStrokes;

    public DataPackageToClient(ArrayList<Player> players, ArrayList<Stroke> lastStrokes) {
        this.players = players;
        this.lastStrokes = lastStrokes;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Stroke> getLastStrokes() {
        return lastStrokes;
    }
}
