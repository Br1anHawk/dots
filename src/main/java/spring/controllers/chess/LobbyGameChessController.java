package spring.controllers.chess;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.controllers.mappingNames.MappingURLs;
import spring.domain.Account;
import spring.gameChessLogic.GameChess;
import spring.gameChessLogic.Player;
import spring.gameChessLogic.jsonResponses.DataPlayerReady;
import spring.gameChessLogic.jsonResponses.Lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LobbyGameChessController {

    private static final int PLAYERS_IN_LOBBY_MAX = 2;
    private static int lobbyId = 0;

    private static Map<String, Lobby> lobbies = new HashMap<>();


    public LobbyGameChessController() {
        //lobby = new Lobby(PLAYERS_IN_LOBBY_MAX);
    }

    public static void closeLobby(GameChess gameChess) {
        String lobbyId = "";
        for (Map.Entry<String, Lobby> lobbyEntry : lobbies.entrySet()) {
            if (lobbyEntry.getValue().getGameChess() == gameChess) {
                lobbyId = lobbyEntry.getKey();
                break;
            }
        }
        Lobby lobby = lobbies.get(lobbyId);
        lobby.setWinner(gameChess.getWinner());
        lobbies.remove(lobbyId);
    }

    @GetMapping("/" + MappingURLs.LIST_OF_LOBBIES_FOR_GAME_CHESS)
    public String lobbies(
            @AuthenticationPrincipal Account account,
            Map<String, Object> model
    ) {
        model.put("logout", MappingURLs.LOGOUT);
        model.put("nickname", account.getNickname());
        model.put("lobbies", lobbies);
        return MappingURLs.LIST_OF_LOBBIES_FOR_GAME_CHESS;
    }

    @RequestMapping(
            path = "/" + MappingURLs.LIST_OF_LOBBIES_FOR_GAME_CHESS,
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    public String createLobby(
            @AuthenticationPrincipal Account account
    ) {
        Lobby lobby = new Lobby(lobbyId++, PLAYERS_IN_LOBBY_MAX);
        lobby.addPlayer(new Player(lobby.getPlayers().size(), account.getNickname()));
        lobbies.put(String.valueOf(lobby.getId()), lobby);
        return "redirect:" + MappingURLs.LOBBY_FOR_GAME_CHESS;
    }

    @RequestMapping(
            path = "/" + MappingURLs.JOIN_IN_LOBBY_FOR_GAME_CHESS,
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    public String joinLobby(
            @AuthenticationPrincipal Account account,
            @RequestParam String lobbyId
    ) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            boolean isAdded = lobby.addPlayer(new Player(lobby.getPlayers().size() + 1, account.getNickname()));
            if (!isAdded) {
                return "redirect:" + MappingURLs.LIST_OF_LOBBIES_FOR_GAME_CHESS;
            }
        }
        return "redirect:" + MappingURLs.LOBBY_FOR_GAME_CHESS;
    }

    @GetMapping("/" + MappingURLs.LOBBY_FOR_GAME_CHESS)
    public String lobby(
            @AuthenticationPrincipal Account account,
            Map<String, Object> model
    ) {
        Lobby lobby = findLobbyByPlayerNickname(account.getNickname());
        if (lobby == null) {
            return "redirect:" + MappingURLs.HOME;
        }
        if (GameChessController.getGame(account.getNickname()) != null) {
            return "redirect:" + MappingURLs.HOME;
        }
        model.put("logout", MappingURLs.LOGOUT);
        model.put("nickname", account.getNickname());
        //model.put("players", lobby.getPlayers());
        for (Player player : lobby.getPlayers()) {
            if (player.getNickName().equals(account.getNickname())) {
                return MappingURLs.LOBBY_FOR_GAME_CHESS;
            }
        }

        if (lobby.getPlayers().size() == PLAYERS_IN_LOBBY_MAX) {
            return "redirect:" + MappingURLs.HOME;
        }
        //lobby.addPlayer(new Player(lobby.getPlayers().size(), account.getNickname()));
        return MappingURLs.LOBBY_FOR_GAME_CHESS;
    }

    @RequestMapping(
            path = "/" + MappingURLs.PLAYER_IS_READY_IN_LOBBY_FOR_GAME_CHESS,
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public DataPlayerReady playerIsReady(
            @AuthenticationPrincipal Account account,
            Map<String, Object> model
    ) {
        DataPlayerReady response = null;
        Lobby lobby = findLobbyByPlayerNickname(account.getNickname());
        if (lobby == null) {
            return response;
        }
        for (int i = 0; i < lobby.getPlayers().size(); i++) {
            if (lobby.getPlayers().get(i).getNickName().equals(account.getNickname())) {
                if (lobby.getPlayers().get(i).isReady()) {
                    lobby.getPlayers().get(i).unReady();
                } else {
                    lobby.getPlayers().get(i).setReady();
                }
                response = new DataPlayerReady(i, lobby.getPlayers().get(i).isReady());
                break;
            }
        }
        return response;
    }

    @RequestMapping(
            path = "/" + MappingURLs.PLAYER_IS_EXIT_FROM_LOBBY_FOR_GAME_CHESS,
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> playerIsExit(
            @AuthenticationPrincipal Account account,
            Map<String, Object> model
    ) {
        Map<String, String> response = new HashMap<>();
        Lobby lobby = findLobbyByPlayerNickname(account.getNickname());
        if (lobby == null) {
            return response;
        }
        int playerId = -1;
        for (int i = 0; i < lobby.getPlayers().size(); i++) {
            if (lobby.getPlayers().get(i).getNickName().equals(account.getNickname())) {
                playerId = i;
                break;
            }
        }
        lobby.removePlayer(playerId);
        response.put("url", MappingURLs.HOME);
        return response;
    }

    @RequestMapping(
            path = "/" + MappingURLs.GET_DATA_PACKAGE_TO_CLIENT_FROM_GAME_CHESS_LOBBY,
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public Lobby updateLobby(
            @AuthenticationPrincipal Account account,
            Map<String, Object> model
    ) {
        Lobby lobby = findLobbyByPlayerNickname(account.getNickname());
        if (lobby == null) {
            return lobby;
        }
        boolean isAllReady = true;
        for (Player player : lobby.getPlayers()) {
            if (!player.isReady()) {
                isAllReady = false;
            }
        }
        if (isAllReady && lobby.isFull() && !lobby.isGameIsStart()) {
            lobby.startGame(MappingURLs.GAME_CHESS);
            GameChessController.addGame(lobby);
        }
        return lobby;
    }

    private Lobby findLobbyByPlayerNickname(String nickname) {
        Lobby lobby = null;
        boolean isFound = false;
        for (Map.Entry<String, Lobby> tempLobby : lobbies.entrySet()) {
            ArrayList<Player> players = tempLobby.getValue().getPlayers();
            for (Player player : players) {
                if (player.getNickName().equals(nickname)) {
                    lobby = tempLobby.getValue();
                    isFound = true;
                    break;
                }
            }
            if (isFound) {
                break;
            }
        }
        return lobby;
    }
}
