<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<!--<link rel="stylesheet" href="./register.css" />-->
		<style><%@include file="./style.css"%></style>
		<title>TinyTwitt, le twitter du futur passé</title>
	</head>
	<body>
		<h1><a href="/register">Tinytwit ¯\_(ツ)_/¯</a></h1>
		
		<div id="registering">
			<h3><a href="/register">Register</a></h3>
			<form action="/register" method="post">
				<p id="name">
					<label for="username">Name</label><br />
					<input type="text" name="username" required />
				</p>
				<p id="submit">
					<input type="submit" value="S'incrire sur le meilleur site du monde !" />
				</p>
			</form>
		</div>
	</body>
</html>
