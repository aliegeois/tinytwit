<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="java.util.List" %>
<%@ page import="tinytwit.Twit" %>

<!DOCTYPE html>

<html>
	<head>
		<title>Tinytwit</title>
		<style><%@include file="./style.css"%></style>
		<meta charset="utf-8" />
	</head>

	<body>

		<div id="lerubanblanc"></div>
		
		<nav><a href="/register">deconnexion</a></nav>
		<h2><a href="/">Tinytwit ¯\_(:/)_/¯</a></h2>
		
		<div id="main">
			
			
			<form action="/" method="post" id="postTwit">
				<p>
					<textarea name="content" maxlength="70" required placeholder="Ecrivez votre super tiny twit ici ;)" onkeydown="document.getElementById('carcount').innerHTML = 
this.value.length"></textarea>
				<span id="carcount">0</span>/70
				</p>
				<p id="bottom_layer">
					<input type="text" name="username" placeholder="Your name" required id="name" />
					<input type="submit" value="Envoyer" id="submit_button" />
				</p>
			</form>
		
		
			<div id="twit_list">
				<h5>Les derniers twits :</h5>
					<%
						List<Twit> twits = (List<Twit>) request.getAttribute("twits");
						for (Twit twit : twits) {
					%>
					<p id="twit">
						<span id="author"><%= twit.getParent().getName() %></span> à <span id="date"><%= twit.getCreation() %></span> : <br />
						<span id="content"><%= twit.getContent() %></span>
					</p>

					<%
						}
					%>
			</div>
			
		</div>
		
		
	</body>
</html>