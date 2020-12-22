let socket = new SockJS('/chat-messaging');
stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {
	console.log("connected: " + frame);
	stompClient.subscribe('/chat/messages', function(response) {
		let data = JSON.parse(response.body);
		//console.log('Data from server:' + data.);
		$('.chatBody').append(data.from + ": " + data.message + "<br>");
	});
});

function send() {
	let message = $('.textMessage').val();
	console.log("Message from Send button: " + message);
	
	stompClient.send("/app/message", {}, JSON.stringify({'message': message}));



}