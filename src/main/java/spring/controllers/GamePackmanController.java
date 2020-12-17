package spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import spring.controllers.mappingNames.MappingURLs;

import java.util.Map;

@Controller
public class GamePackmanController {


    @GetMapping("/" + MappingURLs.GAME_PACKMAN)
    public String packman(
            Map<String, Object> model
    ) {
        return MappingURLs.GAME_PACKMAN;
    }

}
