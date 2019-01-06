<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="tinytwit.User" %>
<%@ page import="tinytwit.Twit" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<h1>Liste des twits de <%= ((User)request.getAttribute("user")).getName() %></h1>
		<div id="twits"></div>
		<script>
		fetch('/_ah/api/tinytwit/v1/user/<%= ((User)request.getAttribute("user")).getName() %>/twits').then(response => {
			return response.json();
		}).then(twits => {
			let d_twits = document.getElementById('twits');
			for(let twit of twits.items) {
				let d_twit = document.createElement('div');
				d_twit.innerHTML = twit.content;
				d_twits.appendChild(d_twit);
			}
		});
		</script>
	</body>
</html>