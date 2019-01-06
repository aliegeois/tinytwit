<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="tinytwit.User" %>
<%@ page import="tinytwit.Twit" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<h1>Liste des twits de <%= ((User)request.getAttribute("user")).getName() %></h1>
		<table border="1">
			<tr>
				<th>Contenu</th>
				<th>Date</th>
			</tr>
			<%
				List<Twit> twits = (List<Twit>)request.getAttribute("twits");
				for(Twit twit : twits) {
			%>
			<tr>
				<td><%= twit.getContent() %></td>
				<td><%= twit.getCreation() %></td>
			</tr>
			<%
				}
			%>
		</table>
	</body>
</html>