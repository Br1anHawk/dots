package spring.gameDotsLogic;

import java.util.ArrayList;

public class DataPackageToClient {
    private ArrayList<Player> players;

    public DataPackageToClient(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
