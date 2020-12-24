package spring.controllers.chess;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import spring.gameChessLogic.jsonResponses.Message;

@Controller
public class ChatController {

    @MessageMapping("/{lobbyId}/message")
    @SendTo("/chat/{lobbyId}/message")
    public Message getMessages(
            @Payload Message message,
            @PathVariable String lobbyId
    ) {
        message.setPlayerId(
                LobbyGameChessController
                        .getLobbyById(message.getLobbyId())
                        .getPlayerIdByNickname(message.getFromPlayerNickname())
        );
        //System.out.println(lobbyId);
        return message;
    }
}
