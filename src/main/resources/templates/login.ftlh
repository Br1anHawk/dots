<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    Login page
    <form action="/login" method="post">
        <div>
        	<label> 
        		Account Name : <input type="text" name="username"/> 
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
    <a href="/registration">Create account</a>
</body>
</html>