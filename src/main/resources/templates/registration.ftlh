<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    Registation
    <#if errorMessage??>
        ${errorMessage}
    </#if>
    <form action="/registration" method="post">
        <div>
        	<label> 
        		Account Name : <input type="text" name="nickname"/> 
        	</label>
        </div>
        <div>
        	<label> 
        		Password: <input type="password" name="password"/> 
        	</label>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div>
        	<input type="submit" value="Sign In"/>
        </div>
    </form>
    
</body>
</html>