<#import "parts/header.ftlh" as header>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>  
    <link rel="stylesheet" type="text/css" href="../css/homeMenu.css">  
</head>
<body>

	<@header.header />

	<div class="centerContentOnPage">
		<h2 class="centerContent">Choose the Game</h2>
		<div class="homeMenu">			
			<a href="/${hrefGameDots}">
				<img class="gameIcon" src="../img/gameIcons/dotsGameIcon.png"/>
			</a>
			<a href="/${hrefLobbyGameChess}">
				<img class="gameIcon" src="../img/gameIcons/chessGameIcon.png"/>
			</a>
			<a href="/${hrefGamePackman}">
				<img class="gameIcon" src="../img/gameIcons/packmanGameIcon.png"/>
			</a>
		</div>
	</div>
</body>
</html>