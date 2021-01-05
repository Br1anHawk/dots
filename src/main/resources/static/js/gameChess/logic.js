let N = 8;
let M = 8;

function mainJS() {
	initField(N, M);
	drawChessmans();
}

function initField(n, m) {
	let colors = [
		"#fff",
		"#98623C"
	];
	let colorSwitcher = true;
	for (let i = 0; i < n; i++) {
		for (let j = 0; j < m; j++) {
			let color;
			if (colorSwitcher) {
				color = colors[0];
			} else {
				color = colors[1];
			}
			$("#rect" + i + j).attr("fill", color);
			colorSwitcher = !colorSwitcher;
		}
		colorSwitcher = !colorSwitcher;
	}
}


let colorBackgroundCellForLastStroke = '#990';
let opacityBackgroundCellForLastStroke = '0.5';

let colorBackgroundCellForCheckKing = '#900';
let opacityBackgroundCellForCheckKing = '0.7';

let imageSourcePrefix = "../img/chessmen/board/";
let imageSourceFileName = {
	pawn : "pawn",
	rook : "rook",
	horse : "horse",
	elephant : "elephant",
	queen : "queen",
	king : "king",
};
let imageSourceSuffix = ".png";

function drawChessmans() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

	$.ajax({
	    type: "POST",
	    url: "../chess/getDataPackageToClient",	    
	    //data: ,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",	
		success: function(data) {	
			for (let playerNumber = 0; playerNumber < data.players.length; playerNumber++) {
				let chessmans = data.players[playerNumber].chessmans;
				for (let i = 0; i < chessmans.length; i++) {
					let x = chessmans[i].x;
					let y = chessmans[i].y;
					let type = chessmans[i].type;
					let isAlive = chessmans[i].alive;
					if (isAlive) {
						let imageSource = imageSourcePrefix + imageSourceFileName[type] + playerNumber + imageSourceSuffix;						
						$("#image" + x + y).attr('xlink:href', imageSource);
					}
				}
			}
			if (data.lastStrokes.length > 0) {	
				let fromCell = data.lastStrokes[0].fromCell;
				let toCell = data.lastStrokes[0].toCell;
				if (fromCell != null && toCell != null) {
					$("#rectMarker" + fromCell.x + fromCell.y).attr('fill', colorBackgroundCellForLastStroke);
					$("#rectMarker" + fromCell.x + fromCell.y).attr('opacity', opacityBackgroundCellForLastStroke);
					$("#rectMarker" + toCell.x + toCell.y).attr('fill', colorBackgroundCellForLastStroke);
					$("#rectMarker" + toCell.x + toCell.y).attr('opacity', opacityBackgroundCellForLastStroke);
				}
			}
		},
		error: function(errMsg) {

		}
	});
}

let isChessmanSelected = false;
function clickedOnCell(x, y) {	
	if (isChessmanSelected) {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		$.ajaxSetup({
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader(header, token);
	        }
	    });
		let request = {
			"x" : x,
			"y" : y
		}
		$.ajax({
		    type: "POST",
		    url: "../chess/chessmanMoveTo",	    
		    data: request,
		    dataType: "json",	
			success: function(data) {
				//initField(N, M);
				clearCirclesAndRectAvailableTurnMarker(N, M);				
				//window.location.replace("../chess");
				let fromCell = data.lastStrokes[0].fromCell;
				let toCell = data.lastStrokes[0].toCell;
				let lastStrokes = data.lastStrokes;		
				for (let i = 0; i < lastStrokes.length; i++) {
					if (lastStrokes[i].moved) {		
						let imgSrc = $("#image" + lastStrokes[i].fromCell.x + lastStrokes[i].fromCell.y).attr('xlink:href');						
						$("#image" + lastStrokes[i].fromCell.x + lastStrokes[i].fromCell.y).attr('xlink:href', '');
						$("#image" + lastStrokes[i].toCell.x + lastStrokes[i].toCell.y).attr('xlink:href', imgSrc);
					}	
				}
				$("#rectMarker" + fromCell.x + fromCell.y).attr('fill', colorBackgroundCellForLastStroke);
				$("#rectMarker" + fromCell.x + fromCell.y).attr('opacity', opacityBackgroundCellForLastStroke);
				$("#rectMarker" + toCell.x + toCell.y).attr('fill', colorBackgroundCellForLastStroke);
				$("#rectMarker" + toCell.x + toCell.y).attr('opacity', opacityBackgroundCellForLastStroke);	
				for (let playerNumber = 0; playerNumber < data.players.length; playerNumber++) {
					let king = data.players[playerNumber].king;
					if (king.check) {
						$("#rectMarker" + king.x + king.y).attr('fill', colorBackgroundCellForCheckKing);
						$("#rectMarker" + king.x + king.y).attr('opacity', opacityBackgroundCellForCheckKing);
						/*
						if (king.checkmate) {
							if (playerNumber == 0) {
								alert("Black Win!");
							} else {
								alert("White Win!");
							}	
							break;
						}*/
					}
				}
				if (data.lastStrokes[0].pawnTransformation)	{
					let leftMargin = $('.containerMenuOfPawnTransformation').css('left');
					leftMargin += y * $('#rect' + x + y).attr('width');
					$('.containerMenuOfPawnTransformation').css('left', leftMargin);
					$('.containerMenuOfPawnTransformation').css('display', 'block');
				}
			},
			error: function(errMsg) {

			}						
		});
		isChessmanSelected = false;
	} else {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		$.ajaxSetup({
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader(header, token);
	        }
	    });
		let request = {
			"x" : x,
			"y" : y
		};
		$.ajax({
		    type: "POST",
		    url: "../chess/getAvailableTurnCellsFor",	    
		    data: request,
		    dataType: "json",	
			success: function(data) {	
				if (data.length == 0) {					
					return 0;
				}
				let colorCircleAvailableTurnMarker = "#444141";
				let opacityCircleAvailableTurnMarker = "0.4";				
				for (let i = 0; i < data.length; i++) {
					let x = data[i].x;
					let y = data[i].y;
					$("#cMarker" + x + y).attr('opacity', opacityCircleAvailableTurnMarker);
					$("#cMarker" + x + y).attr('fill', colorCircleAvailableTurnMarker);					
				}
				$("#rectMarker" + x + y).attr('fill', colorBackgroundCellForLastStroke);	
				$("#rectMarker" + x + y).attr('opacity', opacityBackgroundCellForLastStroke);	
				isChessmanSelected = true;
			},
			error: function(errMsg) {

			}
		});
	}
}

function transformatePawnTo(toType) {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });	
	$.ajax({
	    type: "POST",
	    url: "../chess/transformationPawnTo",	    
	    data: toType,
	    dataType: "json",	
		success: function(data) {
			let imageSource = imageSourcePrefix + imageSourceFileName[type] + data.playerId + imageSourceSuffix;						
			$("#image" + data.x + data.y).attr('xlink:href', imageSource);
			$('.containerMenuOfPawnTransformation').css('display', 'none');
		},
		error: function(errMsg) {

		}						
	});
}

function clearCirclesAndRectAvailableTurnMarker(n, m) {
	for (let i = 0; i < n; i++) {
		for (let j = 0; j < m; j++) {
			$("#cMarker" + i + j).attr('opacity', '0.0');
			$("#rectMarker" + i + j).attr('opacity', '0.0');
		}
	}
}

function surrender() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });
	$.ajax({
	    type: "POST",
	    url: "../chess/surrender",	    
		success: function(data) {	
			window.location.replace(data);
		},
		error: function(errMsg) {

		}
	});
}










let timeOutIntervalRedraw = 1000 * 1;
let st = setInterval(function run() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

	$.ajax({
	    type: "POST",
	    url: "../chess/update",	    
	    //data: ,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",	
		success: function(data) {
			//initField(N, M);
			//clearCirclesAndRectAvailableTurnMarker(N, M);	
			if (data) {
				//window.location.replace("../chess");
				let fromCell = data.lastStrokes[0].fromCell;
				let toCell = data.lastStrokes[0].toCell;
				if (fromCell != null && toCell != null) {					
					for (let i = 0; i < data.lastStrokes.length; i++) {	
						if (data.lastStrokes[i].moved) {		
							let imgSrc = $("#image" + data.lastStrokes[i].fromCell.x + data.lastStrokes[i].fromCell.y).attr('xlink:href');										
							if (imgSrc != "") {
								$("#image" + data.lastStrokes[i].fromCell.x + data.lastStrokes[i].fromCell.y).attr('xlink:href', '');
								$("#image" + data.lastStrokes[i].toCell.x + data.lastStrokes[i].toCell.y).attr('xlink:href', imgSrc);
								clearCirclesAndRectAvailableTurnMarker(N, M);
							}
						}	
					}
					$("#rectMarker" + fromCell.x + fromCell.y).attr('fill', colorBackgroundCellForLastStroke);
					$("#rectMarker" + fromCell.x + fromCell.y).attr('opacity', opacityBackgroundCellForLastStroke);
					$("#rectMarker" + toCell.x + toCell.y).attr('fill', colorBackgroundCellForLastStroke);
					$("#rectMarker" + toCell.x + toCell.y).attr('opacity', opacityBackgroundCellForLastStroke);
					
					if (data.lastStrokes[0].pawnTransformation && data.lastStrokes[0].pawnTransformationToType != "") {
						let imageSource = imageSourcePrefix + imageSourceFileName[data.lastStrokes[0].pawnTransformationToType] + data.lastStrokes[0].playerIdForPawnTransformation + imageSourceSuffix;						
						$("#image" + toCell.x + toCell.y).attr('xlink:href', imageSource);
					}

					for (let playerNumber = 0; playerNumber < data.players.length; playerNumber++) {
						let king = data.players[playerNumber].king;
						if (king.check) {
							$("#rectMarker" + king.x + king.y).attr('fill', colorBackgroundCellForCheckKing);
							$("#rectMarker" + king.x + king.y).attr('opacity', opacityBackgroundCellForCheckKing);
							if (king.checkmate) {
								if (playerNumber == 0) {
									alert("Black Win!");
								} else {
									alert("White Win!");
								}
								clearInterval(st);
							}
						}
					}
				}
			}
		},		
	    error: function(errMsg) {
	        //alert(errMsg);
	    }
	});
}, timeOutIntervalRedraw);