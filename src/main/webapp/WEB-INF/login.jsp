<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>TinyTwitt, le twitter du futur passé</title>
	</head>
	<body>
		<h1>Tinytwit (v2)</h1>
		<form action="/login" method="post">
			<p>
				<label for="username">Name</label>
				<input type="text" name="username" />
			</p>
			<p>
				<label for="password">Password</label>
				<input type="text" name="password" />
			<p>
				<input type="submit" value="Connexion" />
			</p>
		</form>
	</body>
</html>