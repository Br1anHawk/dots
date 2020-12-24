<#macro chat>
<script src="../js/jquery.3.3.1.jquery.min.js" type="text/javascript"></script>	
<script src="../js/sockjs.min.js"></script>
<script src="../js/stomp.min.js"></script>
<script src="../js/gameChess/chat.js" type="text/javascript"></script>		
<link rel="stylesheet" type="text/css" href="../css/chat.css">

<div class="chatContainer" lobbyId="${lobbyId}">
	<div class="chatHeader">
		Chat
	</div>
	<div id="chatBody" class="chatBody">
		
	</div>
	<div class="chatFooter">
		<input class="textMessage" type="text" value="" name="" onkeypress="sendText(event)" />
		<input class="buttonSend" type="button" value="Send" name="" onclick="send()" />
	</div>
</div>
</#macro>