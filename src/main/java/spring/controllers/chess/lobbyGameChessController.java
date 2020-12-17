package spring.controllers.chess;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.controllers.mappingNames.MappingURLs;
import spring.domain.Account;
import spring.gameChessLogic.Player;
import spring.gameChessLogic.jsonResponses.DataPlayerReady;
import spring.gameChessLogic.jsonResponses.Lobby;

import java.util.HashMap;
import java.util.Map;

@Controller
public class lobbyGameChessController {

    private Lobby lobby;

    private static final int PLAYERS_IN_LOBBY_MAX = 2;

    public lobbyGameChessController() {
        lobby = new Lobby(PLAYERS_IN_LOBBY_MAX);
    }

    @GetMapping("/" + MappingURLs.LOBBY_FOR_GAME_CHESS)
    public String lobby(
            @AuthenticationPrincipal Account account,
            Map<String, Object> model
    ) {
        if (GameChessController.getGame(account.getNickname()) != null) {
            return "redirect:" + MappingURLs.HOME;
        }
        model.put("logout", MappingURLs.LOGOUT);
        model.put("nickname", account.getNickname());
        model.put("players", lobby.getPlayers());
        for (Player player : lobby.getPlayers()) {
            if (player.getNickName().equals(account.getNickname())) {
                return MappingURLs.LOBBY_FOR_GAME_CHESS;
            }
        }

        if (lobby.getPlayers().size() == PLAYERS_IN_LOBBY_MAX) {
            return "redirect:" + MappingURLs.HOME;
        }
        lobby.addPlayer(new Player(lobby.getPlayers().size(), account.getNickname()));
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
        boolean isAllReady = true;
        for (Player player : lobby.getPlayers()) {
            if (!player.isReady()) {
                isAllReady = false;
            }
        }
        if (isAllReady && lobby.isFull() && !lobby.isGameIsStart()) {
            lobby.startGame(MappingURLs.GAME_CHESS);
            GameChessController.addGame(lobby.getPlayers());
        }
        return lobby;
    }
}
