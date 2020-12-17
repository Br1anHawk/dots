const MAX_LOBBY_PLAYERS = 2;
let timeOutIntervalUpdater = 1000 * 1;
let sIUpdater = setInterval(
	function run() {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		$.ajaxSetup({
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader(header, token);
	        }
	    });

		$.ajax({
		    type: "POST",
		    url: "../lobby/update",	    
		    //data: ,
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function(data) {
		    	let playersInLobby = new Array(MAX_LOBBY_PLAYERS);
		    	for (let i = 0; i < data.players.length; i++) {
		    		let playerId = data.players[i].id;
		    		let nickName = data.players[i].nickName;
		    		let isReady = data.players[i].ready;
		    		$('#rowPlayer' + playerId + '>.nickNamePlayer').html(nickName);
		    		if (isReady) {
		    			$('#rowPlayer' + playerId + '>div>.imgPlayerReady').css('display', 'block');
		    		} else {
		    			$('#rowPlayer' + playerId + '>div>.imgPlayerReady').css('display', 'none');
		    		}
		    		playersInLobby[playerId] = true;		    		
		    	}	
		    	for (let i = 0; i < playersInLobby.length; i++) {
	    			if (!playersInLobby[i]) {
	    				$('#rowPlayer' + i + '>.nickNamePlayer').html('Empty');
	    				$('#rowPlayer' + i + '>div>.imgPlayerReady').css('display', 'none');
	    			}
	    		}
	    		if (data.gameIsStart) {
	    			window.location.replace(data.gameMappingURL);
	    		}
	    	},
		    error: function(errMsg) {
		        //alert(errMsg);
	    	}
		});
	}, 
	timeOutIntervalUpdater);

function ready() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

	$.ajax({
	    type: "POST",
	    url: "../lobby/playerIsReady",	    
	    //data: ,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data) {
	    	let playerId = data.playerId;
	    	let isReady = data.ready;
	    	if (isReady) {
		    	$('#rowPlayer' + playerId + '>div>.imgPlayerReady').css('display', 'block');
		    	$('.buttonReady').attr('value', 'Unready');
		    	$('.buttonReady').css('background-color', '#b30000');
		    } else {
		    	$('#rowPlayer' + playerId + '>div>.imgPlayerReady').css('display', 'none');
		    	$('.buttonReady').attr('value', 'Ready');
		    	$('.buttonReady').css('background-color', '#4CAF50');
		    }
    	},
	    error: function(errMsg) {
	        //alert(errMsg);
    	}
	});
}

function exit() {
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	$.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

	$.ajax({
	    type: "POST",
	    url: "../lobby/playerIsExit",	    
	    //data: ,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data) {
	    	window.location.replace(data.url);
    	},
	    error: function(errMsg) {
	        //alert(errMsg);
    	}
	});
}