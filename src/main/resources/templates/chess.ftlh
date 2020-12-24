<#import "parts/header.ftlh" as header>
<#import "parts/chat.ftlh" as chat>

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
	<div class="page">
		<div class="fieldChess">
			<#assign borderForTextStroke = 30>
			<#assign indentTextStroke = borderForTextStroke / 2>
			<#assign textFont = "">
			<#assign textFontSize = 16>
			<#assign deltaCorrectionForLetterX = textFontSize / 4>

			<#assign borderLineStrokeWidth = 6>
			<#assign borderLineStroke = "#000">
			<#assign borderLineRadius = borderLineStrokeWidth * 5>
			<#assign boardFillColor = "#fff">

			<#assign width = 400>
			<#assign height = 400>
			<#assign cellSideWidth = width / m>
			<#assign circleAlailableTurnMarker = cellSideWidth / 5>
			<#assign lineStrokeWidth = 1>
			<#assign lineStroke = "#000">
			
			<#assign svgWidth = width + 2 * borderForTextStroke>
			<#assign svgHeight = height + 2 * borderForTextStroke>
			<svg width="${svgWidth}" height="${svgHeight}">			
				<g id="rotateBoard" transform="rotate(${rotationAngle} ${svgWidth / 2} ${svgHeight / 2})">

					<rect x="${borderLineStrokeWidth / 2}" y="${borderLineStrokeWidth / 2}" rx="${borderLineRadius}" ry="${borderLineRadius}" width="${svgWidth - borderLineStrokeWidth}" height="${svgHeight - borderLineStrokeWidth}" style="stroke: ${borderLineStroke}; stroke-width: ${borderLineStrokeWidth}; fill: ${boardFillColor}" />

					<#assign textNumberPosX = indentTextStroke>
					<#assign textNumberPosY = svgHeight - borderForTextStroke - cellSideWidth / 2 + borderLineStrokeWidth>
					<#list 1..n as i>
						<g transform="rotate(${rotationAngle} ${textNumberPosX + borderLineStrokeWidth - deltaCorrectionForLetterX / 2} ${textNumberPosY - borderLineStrokeWidth})">
							<text x="${textNumberPosX}" y="${textNumberPosY}" style="font-size: ${textFontSize}px">${i}</text>
						</g>
						<#assign textNumberPosY -= cellSideWidth>
					</#list>
					<#assign textLetterPosY = svgHeight - indentTextStroke + borderLineStrokeWidth / 2>
					<#assign textLetterPosX = borderForTextStroke + cellSideWidth / 2 - deltaCorrectionForLetterX>
					<#list 0..m - 1 as j>
						<g transform="rotate(${rotationAngle} ${textLetterPosX + borderLineStrokeWidth - deltaCorrectionForLetterX / 2} ${textLetterPosY - borderLineStrokeWidth})">
							<text x="${textLetterPosX}" y="${textLetterPosY}" style="font-size: ${textFontSize}px"s>${alphabet[j]}</text>
						</g>
						<#assign textLetterPosX += cellSideWidth>
					</#list>
					
					<#assign linePos = borderForTextStroke>
					<#list 0..n as i>
						<line x1='${borderForTextStroke}' y1='${linePos}' x2='${width + borderForTextStroke}' y2='${linePos}' stroke='${lineStroke}' stroke-width='${lineStrokeWidth}'/>
						<#assign linePos += cellSideWidth>
					</#list>
					<#assign linePos = borderForTextStroke>
					<#list 0..m as j>
						<line x1='${linePos}' y1='${borderForTextStroke}' x2='${linePos}' y2='${height + borderForTextStroke}' stroke='${lineStroke}' stroke-width='${lineStrokeWidth}'/>
						<#assign linePos += cellSideWidth>
					</#list>

					<#assign posY = borderForTextStroke>
					<#list 0..m - 1 as i>
						<#assign posX = borderForTextStroke> 
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

		<div>
			<@chat.chat />
		</div>

		<div class="boardFooter">
			<input class="buttonSurrender" type="button" value="Surrender" onclick="surrender()">
		</div>		
	</div>


	<script type="text/javascript">		
		javascript:mainJS();
	</script>
</body>
</html>