<#macro header>
<link rel="stylesheet" type="text/css" href="../css/header.css">
<div class="header">
	<form action="/${logout}" method="post">		
		<div class="nickname" value="${nickname}">
			${nickname}
		</div>
        <input class="buttonSubmit" type="submit" value="Sign Out"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</div>
</#macro>