<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="java.util.Set" %>
<%@ page import="tinytwit.Twit" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<h1>Liste des twits de <%=  %>:</h1>
		<table border="1">
			<tr>
				<th>Nom</th>
				<th>Twit</th>
				<th>Date</th>
			</tr>
			<%
				Set<Twit> twits = (Set<Twit>) request.getAttribute("twits");
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