function mainJS() {
	//$('.field').html(initSvgField());	
	fillTheMainFieldWithDots();
}

function fillTheMainFieldWithDots() {
	let dataJSON;
	let colors = [
		'#900',
		'#090'
	];
	$.getJSON(
		'../dots/getDataPackageToClient',
		function(data) {
			//dataJSON = data;
			for (let c = 0; c < data.players.length; c++) {
				data.players[c].color = colors[c];
			}
			for (let k = 0; k < data.players.length; k++) {
				let dots = data.players[k].dots;
				for (let i = 0; i < dots.length; i++) {		
					$('#dot' + dots[i].x + dots[i].y).attr('fill', data.players[k].color);
					$('#dot' + dots[i].x + dots[i].y).attr('opacity', '1');		
					$('#dot' + dots[i].x + dots[i].y).attr('onclick', '');
				}
			}
		});		
	
}

function initSvgField() {
	let width = 400;
	let height = 400;
	let lineStroke = "#59baff";
	let lineStrokeWidth = 3;
	let widthSideOfCell = 40;
	let dotRadius = widthSideOfCell / 10;
	
	let codeHTML = "";
	codeHTML += "<svg width=" + width + " height=" + height + ">";
	let linePos = widthSideOfCell / 2;
	while (linePos < width) {
		codeHTML += "<line x1='" + linePos + "' y1='" + 0 + "' x2='" + linePos + "' y2='" + height + "'" +
		" stroke='" + lineStroke + "' stroke-width='" + lineStrokeWidth + "'/>";
		linePos += widthSideOfCell;
	}
	linePos = widthSideOfCell / 2;
	while (linePos < height) {
		codeHTML += "<line x1='" + 0 + "' y1='" + linePos + "' x2='" + width + "' y2='" + linePos + "'" + 
		" stroke='" + lineStroke + "' stroke-width='" + lineStrokeWidth + "'/>";
		linePos += widthSideOfCell;
	}
	let dotX;
	let dotY = widthSideOfCell / 2;
	let i = 0, j;
	while (dotY < height) {
		dotX = widthSideOfCell / 2;
		j = 0;
		while (dotX < width) {
			codeHTML += "<circle id='dot" + i + j + "' cx='" + dotX + "' cy='" + dotY + "' r='" + dotRadius + "'" +
			" stroke='' stroke-width='' fill='' opacity='0.0' onclick='clickedDot(" + i + "," + j + ")' />";
			dotX += widthSideOfCell;
			j++;
		}
		dotY += widthSideOfCell;
		i++;
	}
	codeHTML += "</svg>";
	return codeHTML;	
}

function clickedDot(x, y) {
	//alert(x + " " + y);
	window.location.replace("/dots/?x=" + x + "&y=" + y);
	/*
	let xhr = new XMLHttpRequest();
    let params = 'x=' + encodeURIComponent(x) +
      '&y=' + encodeURIComponent(y);
    xhr.open("GET", '/?' + params, true);
    //xhr.onreadystatechange = ...;
    xhr.send();
    */
}