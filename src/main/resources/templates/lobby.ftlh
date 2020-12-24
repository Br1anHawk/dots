<#import "parts/header.ftlh" as header>
<#import "parts/chat.ftlh" as chat>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Lobby</title>
    <script src="../js/jquery.3.3.1.jquery.min.js" type="text/javascript"></script>	
	<script src="../js/gameChess/lobby.js" type="text/javascript"></script>		
	<link rel="stylesheet" type="text/css" href="../css/lobby.css">
</head>
<body>
	<@header.header />
	<div class="page">
		<div class="lobbyBoard">
			<div class="lobbyBoardHead">
				Lobby #${lobbyId} <br>
				Game Chess
			</div>
			<div class="lobbyBoardBody">
				<div class="rowForPlayerInfo">
					<div></div>
					<div>Nickname</div>
					<div>Ready</div>
				</div>
				<div id="rowPlayer0" class="rowForPlayerInfo">
					<div>
						<img class="imgPlayerColor" src="../img/chessmen/lobby/pawn0.png" >
					</div>
					<div class="nickNamePlayer">Empty</div>
					<div>
						<img class="imgPlayerReady" src="../img/chessmen/lobby/ready.png" >
					</div>
				</div>
				<div id="rowPlayer1" class="rowForPlayerInfo">
					<div>
						<img class="imgPlayerColor" src="../img/chessmen/lobby/pawn1.png" >
					</div>
					<div class="nickNamePlayer">Empty</div>
					<div>
						<img class="imgPlayerReady" src="../img/chessmen/lobby/ready.png" >
					</div>
				</div>
		    </div>   
	    	<div class="lobbyBoardFooter">
	    		<input class="buttonReady" type="button" value="Ready" onclick="ready()" />
	    		<input class="buttonExit" type="button" value="Exit" onclick="exit()" />
	    	</div>
	    </div>

	    <div>
			<@chat.chat />  
		</div>
	</div>
</body>
</html>