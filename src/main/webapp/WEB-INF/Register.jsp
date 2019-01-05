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
		
		<div>
			<h3>Register</h3>
			<form action="/register" method="post">
				<p>
					<label for="username">Name</label>
					<input type="text" name="username" />
				</p>
				<p>
					<input type="submit" value="S'incrire sur le meilleur site du monde !" />
				</p>
			</form>
		</div>
	</body>
</html>