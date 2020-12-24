openWebSocket();

function openWebSocket() {
	let colorsForPlayerNickname = [
		"#800000",
		"#145214"
	];

	let socket = new SockJS('/chat-messaging');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log("connected: " + frame);
		let lobbyId = $('.chatContainer').attr('lobbyId');
		stompClient.subscribe('/chat/' + lobbyId + '/message', function(response) {
			let chatBodyContainer = document.getElementById("chatBody");
			
			let data = JSON.parse(response.body);
			let nicknameColor = colorsForPlayerNickname[data.playerId % colorsForPlayerNickname.length];				
			let textMessageHTML = 
				"<div class='messageLine'>" +
					"<a class='nickname' style='color:" + nicknameColor + "'>" +
						data.fromPlayerNickname + 
					"</a>" + 				
					": " + data.message	+				
				"</div>";
			
			$('.chatBody').append(textMessageHTML);		
			chatBodyContainer.scrollTop = chatBodyContainer.scrollHeight;		
		});
	});
}



function sendText(event) {
	if (event.keyCode == 13) {
		send();
	}
}

function send() {
	let message = {};
	message.lobbyId = $('.chatContainer').attr('lobbyId');
	message.fromPlayerNickname = $('.nickname').attr('value');
	message.message = $('.textMessage').val();
	stompClient.send("/app/" + message.lobbyId + "/message", {}, JSON.stringify(message));
	$('.textMessage').val("");
}