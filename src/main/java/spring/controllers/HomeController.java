package spring.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import spring.controllers.mappingNames.MappingURLs;
import spring.domain.Account;

import java.util.Map;

@Controller
public class HomeController {

    public HomeController() {

    }

    @GetMapping(MappingURLs.HOME)
    public String index(
            @AuthenticationPrincipal Account account,
            Map<String, Object> model) {
        model.put("logout", MappingURLs.LOGOUT);
        model.put("nickname", account.getNickname());
        model.put("hrefGameDots", MappingURLs.GAME_DOTS);
        model.put("hrefLobbies", MappingURLs.LIST_OF_LOBBIES_FOR_GAME_CHESS);
        model.put("hrefGamePackman", MappingURLs.GAME_PACKMAN);
        return MappingURLs.INDEX;
    }


}
