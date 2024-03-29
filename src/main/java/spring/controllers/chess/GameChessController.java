package spring.controllers.chess;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.controllers.mappingNames.MappingURLs;
import spring.domain.Account;
import spring.gameChessLogic.GameChess;
import spring.gameChessLogic.jsonResponses.Cell;
import spring.gameChessLogic.jsonResponses.DataAboutPawnTransformation;
import spring.gameChessLogic.jsonResponses.DataPackageToClient;
import spring.gameChessLogic.jsonResponses.Lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GameChessController {

    //private static ArrayList<GameChess> chessGames = new ArrayList<>();
    private static Map<String, GameChess> chessGamesMap = new HashMap<>();

    public GameChessController() {

    }

    public static void addGame(Lobby lobby) {
        GameChess gameChess = new GameChess(lobby.getPlayers());
        gameChess.setLobbyId(lobby.getId());
        lobby.setGameChess(gameChess);
        chessGamesMap.put(lobby.getPlayers().get(0).getNickName(), gameChess);
        chessGamesMap.put(lobby.getPlayers().get(1).getNickName(), gameChess);
    }

    public static GameChess getGame(String nickname) {
        return chessGamesMap.get(nickname);
    }

    @GetMapping("/" + MappingURLs.GAME_CHESS)
    public String chess(
            @AuthenticationPrincipal Account account,
            Map<String, Object> model
    ) {
        GameChess gameChess;
        try {
            gameChess = chessGamesMap.get(account.getNickname());
            if (gameChess.findPlayerByNickname(account.getNickname()) == null) {
                return "redirect:" + MappingURLs.HOME;
            }
        } catch (Exception e) {
            return "redirect:" + MappingURLs.HOME;
        }
        model.put("logout", MappingURLs.LOGOUT);
        model.put("nickname", account.getNickname());
        model.put("lobbyId", gameChess.getLobbyId());
        model.put("n", gameChess.getHeight());
        model.put("m", gameChess.getWidth());
        model.put("alphabet", getAlphabet());
        int rotationAngle = 0;
        int playerId = gameChess.findPlayerByNickname(account.getNickname()).getId();
        model.put("playerId", playerId);
        if (playerId == 1) {
            rotationAngle = 180;
        }
        model.put("rotationAngle", rotationAngle);
        return MappingURLs.GAME_CHESS;
    }

    @RequestMapping(
            path = "/" + MappingURLs.GET_DATA_PACKAGE_TO_CLIENT_FROM_GAME_CHESS,
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public DataPackageToClient getDataPackageToClient(
            @AuthenticationPrincipal Account account
    ) {
        return chessGamesMap.get(account.getNickname()).getDataPackageToClient();
    }

    @RequestMapping(
            path = "/" + MappingURLs.GET_AVAILABLE_TURN_CELLS_FOR_PLAYER_FROM_GAME_CHESS,
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Cell> getAvailableTurnCellsFor(
            @AuthenticationPrincipal Account account,
            @RequestParam Map<String, Object> request
    ) {
        return
                chessGamesMap
                        .get(account.getNickname())
                        .getAvailableTurnCells(
                                Integer.parseInt(request.get("x").toString()),
                                Integer.parseInt(request.get("y").toString()),
                                account.getNickname()
                        );
    }

    @RequestMapping(
            path = "/" + MappingURLs.CHESSMAN_MOVE_TO,
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public DataPackageToClient chessmanMoveTo(
            @AuthenticationPrincipal Account account,
            @RequestParam Map<String, Object> request
    ) {
        chessGamesMap
                .get(account.getNickname())
                .chessmanMoveTo(
                        Integer.parseInt(request.get("x").toString()),
                        Integer.parseInt(request.get("y").toString())
                );
        return chessGamesMap.get(account.getNickname()).getDataPackageToClient();
    }

    @RequestMapping(
            path = "/" + MappingURLs.TRANSFORMATION_PAWN_TO,
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public DataAboutPawnTransformation transformationPawnTo(
            @AuthenticationPrincipal Account account,
            @RequestParam String toType
    ) {
        chessGamesMap.get(account.getNickname()).transformationPawnTo(toType);
        return chessGamesMap.get(account.getNickname()).getDataAboutPawnTransformation();
    }

    @RequestMapping(
            path = "/" + MappingURLs.PLAYER_SURRENDER_IN_GAME_CHESS,
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public String surrender(
            @AuthenticationPrincipal Account account
    ) {
        String nickname = account.getNickname();
        GameChess gameChess = chessGamesMap.get(nickname);
        gameChess.finishByPlayerSurrender(nickname);
//        LobbyGameChessController.closeLobby(gameChess);
//        for (Player player : gameChess.getPlayers()) {
//            chessGamesMap.remove(player.getNickName());
//        }
        return MappingURLs.HOME;
    }

    @RequestMapping(
            path = "/" + MappingURLs.UPDATE_DATA_FOR_GAME_CHESS,
            produces = "application/json; charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public DataPackageToClient update(
            @AuthenticationPrincipal Account account
    ) {
        DataPackageToClient dataPackageToClient = chessGamesMap.get(account.getNickname()).getDataPackageToClient();
        GameChess gameChess = chessGamesMap.get(account.getNickname());
//        LobbyGameChessController.closeLobby(gameChess);
//        for (Player player : gameChess.getPlayers()) {
//            chessGamesMap.remove(player.getNickName());
//        }
        return dataPackageToClient;
    }

    private ArrayList<Character> getAlphabet() {
        ArrayList<Character> alphabet = new ArrayList<>(26);
        for (char letter = 'a'; letter <= 'z'; letter++) {
            alphabet.add(letter);
        }
        return alphabet;
    }

}
