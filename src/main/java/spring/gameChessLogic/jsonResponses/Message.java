package spring.gameChessLogic.jsonResponses;

public class Message {
    private int playerId;
    private String lobbyId;
    private String fromPlayerNickname;
    private String message;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public String getFromPlayerNickname() {
        return fromPlayerNickname;
    }

    public void setFromPlayerNickname(String fromPlayerNickname) {
        this.fromPlayerNickname = fromPlayerNickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "playerId=" + playerId +
                ", lobbyId='" + lobbyId + '\'' +
                ", fromPlayerNickname='" + fromPlayerNickname + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
