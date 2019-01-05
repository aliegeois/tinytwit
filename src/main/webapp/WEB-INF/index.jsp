<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="java.util.List" %>
<%@ page import="tinytwit.Twit" %>

<!DOCTYPE html>

<html>
	<head>
		<title>Tinytwit</title>
		<meta charset="utf-8" />
	</head>

	<body>
		<h1>Tinytwit</h1>
		<form action="/" method="post">
			<p>
				<label>Contenu du twit :<br>
				<textarea name="content" style="width: 200px; height: 100px;" onkeyup="document.getElementById('carcount').innerHTML = this.value.length"></textarea></label>
				<span id="carcount">0</span>/70
			</p>
			<p>
				<label>Name :<br>
				<input type="text" name="username" /></label>
				
			</p>
			<p>
				<input type="submit" value="Envoyer" />
			</p>
		</form>
	
		<h1>Liste des twits:</h1>
		<table border="1">
			<tr>
				<th>Nom</th>
				<th>Twit</th>
				<th>Date</th>
			</tr>
			<%
				List<Twit> twits = (List<Twit>) request.getAttribute("twits");
				for (Twit twit : twits) {
			%>
			<tr>
				<td><a><%= twit.getParent().getName() %></a></td>
				<td><%= twit.getContent() %></td>
				<td><%= twit.getCreation() %></td>
			</tr>
			<%
				}
			%>
		</table>
	</body>
</html>