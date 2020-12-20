package spring.controllers.mappingNames;

public class MappingURLs {
    public static final String HOME = "/";
    public static final String INDEX = "index"; //template
    public static final String LOGIN = "login"; //template
    public static final String LOGOUT = "logout";
    public static final String REGISTRATION = "registration"; //template

    public static final String LOBBY_FOR_GAME_CHESS = "lobby"; //template
    public static final String LIST_OF_LOBBIES_FOR_GAME_CHESS = "lobbies";
    public static final String JOIN_IN_LOBBY_FOR_GAME_CHESS = "joinLobby";
    public static final String PLAYER_IS_READY_IN_LOBBY_FOR_GAME_CHESS = LOBBY_FOR_GAME_CHESS + "/playerIsReady";
    public static final String PLAYER_IS_EXIT_FROM_LOBBY_FOR_GAME_CHESS = LOBBY_FOR_GAME_CHESS + "/playerIsExit";
    public static final String GET_DATA_PACKAGE_TO_CLIENT_FROM_GAME_CHESS_LOBBY = LOBBY_FOR_GAME_CHESS + "/update";

    public static final String GAME_CHESS = "chess"; //template
    public static final String GET_DATA_PACKAGE_TO_CLIENT_FROM_GAME_CHESS = GAME_CHESS + "/getDataPackageToClient";
    public static final String GET_AVAILABLE_TURN_CELLS_FOR_PLAYER_FROM_GAME_CHESS = GAME_CHESS + "/getAvailableTurnCellsFor";
    public static final String CHESSMAN_MOVE_TO = GAME_CHESS + "/chessmanMoveTo";
    public static final String PLAYER_SURRENDER_IN_GAME_CHESS = GAME_CHESS + "/surrender";
    public static final String UPDATE_DATA_FOR_GAME_CHESS = GAME_CHESS + "/update";

    public static final String GAME_DOTS = "dots"; //template
    public static final String GET_DATA_PACKAGE_TO_CLIENT_FROM_GAME_DOTS = GAME_DOTS + "/getDataPackageToClient";

    public static final String GAME_PACKMAN = "packman"; //template


}
