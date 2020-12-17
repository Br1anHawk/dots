<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<title>Dots</title>
		<meta content="text/html; charset=utf-8" />    		
		<script src="../js/jquery.3.3.1.jquery.min.js" type="text/javascript"></script>
		<script src="../js/gameDots/logic.js" type="text/javascript"></script>		
		<link rel="stylesheet" type="text/css" href="../css/gameDots.css"> 			
	</head>

	<body>		
		<div class="fieldDots">
		    <#assign lineStroke = "#59baff">
		    <#assign lineStrokeWidth = 3>
		    <#assign widthSideOfCell = 40>
		    <#assign dotRadius = widthSideOfCell / 10>
		    <#assign width = N * widthSideOfCell>
            <#assign height = M * widthSideOfCell>           
           	

            <svg width="${width}" height="${height}">
                <#assign linePos = widthSideOfCell / 2>
                <#list 1..N as i>
                    <line x1='${linePos}' y1='0' x2='${linePos}' y2='${height}' stroke='${lineStroke}' stroke-width='${lineStrokeWidth}'/>
                    <#assign linePos += widthSideOfCell>
                </#list>
                <#assign linePos = widthSideOfCell / 2>
                <#list 1..M as j>
                    <line x1='0' y1='${linePos}' x2='${width}' y2='${linePos}' stroke='${lineStroke}' stroke-width='${lineStrokeWidth}'/>
                    <#assign linePos += widthSideOfCell>
                </#list>

                <#assign dotY = widthSideOfCell / 2>                
                <#list 0..N - 1 as i>
                	<#assign dotX = widthSideOfCell / 2>
                	<#list 0..M - 1 as j>
                		<circle id='dot${i + "" + j}' cx='${dotX}' cy='${dotY}' r='${dotRadius}' stroke='' stroke-width='' fill='' opacity='0.0' onclick='clickedDot(${i + "," + j})' />
                        <#assign dotX += widthSideOfCell>
                	</#list>
                    <#assign dotY += widthSideOfCell>
                </#list>




            </svg>
		</div>

    
		<script>
			javascript:mainJS();
		</script>
	</body>
</html>