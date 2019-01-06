<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>TinyTwitt, le twitter du futur passé</title>
	</head>
	<body>
		<h1>Tinytwit (v2)</h1>
		
		<div>
			<h3>Connexion</h3>
			<form action="/login" method="post">
				<p>
					<label for="username">Name</label>
					<input type="text" name="username" />
				</p>
				<p>
					<label for="password">Password</label>
					<input type="password" name="password" />
				<p>
					<input type="submit" value="Connexion" />
				</p>
			</form>
		</div>
	</body>
</html>