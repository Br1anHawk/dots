package spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.controllers.mappingNames.MappingURLs;
import spring.gameDotsLogic.DataPackageToClient;
import spring.gameDotsLogic.GameDots;
import spring.gameDotsLogic.Player;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class GameDotsController {

    private static ArrayList<Player> players = new ArrayList<>();
    private GameDots dots;

    public GameDotsController() {
        players.add(new Player(1, "1"));
        players.add(new Player(2, "2"));
        dots = new GameDots(10, 10, players);
    }

    @GetMapping("/" + MappingURLs.GAME_DOTS)
    public String stroke(
            @RequestParam(required = false) String x,
            @RequestParam(required = false) String y,
            Map<String, Object> model
    ) {
        try {
            dots.nextTurn(Integer.parseInt(x), Integer.parseInt(y));
        } catch (Exception e) {

        }
        model.put("M", dots.getWidth());
        model.put("N", dots.getHeight());
        return MappingURLs.GAME_DOTS;
    }

    @RequestMapping(path = MappingURLs.GET_DATA_PACKAGE_TO_CLIENT_FROM_GAME_DOTS, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataPackageToClient getDataPackageToClient(
            Model model) {
        return dots.getDataPackageToClient();
    }
}
