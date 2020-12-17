<#import "parts/header.ftlh" as header>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Chess</title>     		
	<script src="../js/jquery.3.3.1.jquery.min.js" type="text/javascript"></script>	
	<script src="../js/gameChess/logic.js" type="text/javascript"></script>		
	<link rel="stylesheet" type="text/css" href="../css/gameChess.css">
</head>
<body>
	<@header.header />
	<div class="fieldChess">
		<#assign width = 400>
		<#assign height = 400>
		<#assign cellSideWidth = width / m>
		<#assign circleAlailableTurnMarker = cellSideWidth / 5>
		<#assign lineStrokeWidth = 1>
		<#assign lineStroke = "#000">
		<#assign borderLineStroke = 5>
		<svg width="${width}" height="${height}">			
			<g id="rotateBoard" transform="rotate(${rotationAngle} ${width / 2} ${height / 2})">
				<#assign linePos = 0>
				<#list 0..n as i>
					<line x1='0' y1='${linePos}' x2='${width}' y2='${linePos}' stroke='${lineStroke}' stroke-width='${lineStrokeWidth}'/>
					<#assign linePos += cellSideWidth>
				</#list>
				<#assign linePos = 0>
				<#list 0..m as j>
					<line x1='${linePos}' y1='0' x2='${linePos}' y2='${height}' stroke='${lineStroke}' stroke-width='${lineStrokeWidth}'/>
					<#assign linePos += cellSideWidth>
				</#list>

				<#assign posY = 0>
				<#list 0..m - 1 as i>
					<#assign posX = 0> 
					<#list 0..n - 1 as j>	
						<rect id="rect${i}${j}" x="${posX}" y="${posY}" width="${cellSideWidth}" height="${cellSideWidth}" fill="" />	
						<rect id="rectMarker${i}${j}" x="${posX}" y="${posY}" width="${cellSideWidth}" height="${cellSideWidth}" fill="" opacity="0.0" />
						<g transform="rotate(${rotationAngle} ${posX + cellSideWidth / 2} ${posY + cellSideWidth / 2})">	
							<image id="image${i}${j}" x="${posX}" y="${posY}" width="${cellSideWidth}" height="${cellSideWidth}" xlink:href="" opacity="1" />
						</g>
						<#assign cx = posX + cellSideWidth / 2>
						<#assign cy = posY + cellSideWidth / 2>
						<circle id="cMarker${i}${j}" cx="${cx}" cy="${cy}" r="${circleAlailableTurnMarker?abs}" fill="" opacity="0.0"/>
						<rect id="rectClicker${i}${j}" x="${posX}" y="${posY}" width="${cellSideWidth}" height="${cellSideWidth}" fill="" opacity="0.0" onclick="clickedOnCell(${i}, ${j})"/>
						<#assign posX += cellSideWidth> 
					</#list>
					<#assign posY += cellSideWidth> 
				</#list>
			</g>
		</svg>	
		<div class="containerMenuOfPawnTransformation">
			<div class="menuOfPawnTransformation">
				<img class="" src="../img/chessmen/menuPawnTransformation/queen0.png" style="width: ${cellSideWidth}px; border: 0px">
				<img class="" src="../img/chessmen/menuPawnTransformation/rook0.png" style="width: ${cellSideWidth}px">
				<img class="" src="../img/chessmen/menuPawnTransformation/horse0.png" style="width: ${cellSideWidth}px">
				<img class="" src="../img/chessmen/menuPawnTransformation/elephant0.png" style="width: ${cellSideWidth}px">
			</div>
		</div>	
	</div>
	<div class="boardFooter">
		<input class="buttonSurrender" type="button" value="Surrender" onclick="surrender()">
	</div>



	<script type="text/javascript">		
		javascript:mainJS();
	</script>
</body>
</html>