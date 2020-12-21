<#import "parts/header.ftlh" as header>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Lobbies</title>
    <script src="../js/jquery.3.3.1.jquery.min.js" type="text/javascript"></script>	
	<script src="../js/gameChess/lobbies.js" type="text/javascript"></script>		
	<link rel="stylesheet" type="text/css" href="../css/lobbies.css">
</head>
<body>
	<@header.header />
	<div class="pageContainer">
		<div class="list">
			<div class="listHead">
				List of Lobbies (Game Chess)
			</div>
			<div class="listBody">
				<#list lobbies?keys as key>
					<div class="lobbyInfoLineContainer">
						<div class="lobbyId">
							${key}
						</div>
						<div class="players">
							<#list lobbies[key].players as player>
								<div class="player">
									${player.nickName}
								</div>
							</#list>
						</div> 
						<div class="">
							<form action="/joinLobby" method="post">
								<input type="submit" value="Join">
								<input type="hidden" name="lobbyId" value="${key}">
								<input type="hidden" name="_csrf" value="${_csrf.token}" />
							</form>							
						</div>
					</div>
				<#else>
					<div>
						Empty
					</div>
				</#list>
			</div>
			<div class="listFooter">
				<form action="/lobbies" method="post">
					<input class="buttonCreateLobby" type="submit" value="+">
					<input type="hidden" name="_csrf" value="${_csrf.token}" />
				</form>
			</div>
		</div>
	</div>
</body>
</html>