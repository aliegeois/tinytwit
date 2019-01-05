<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<form action="/user" method="post">
			<p>
				<label>Nouvel utilisateur<br>
				<input type="text" name="username" /></label>
			</p>
			<p>
				<input type="submit" value="Envoyer" />
			</p>
		</form>
	</body>
</html>