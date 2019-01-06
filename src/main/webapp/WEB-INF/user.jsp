<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="tinytwit.User" %>
<%@ page import="tinytwit.Twit" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-81">
		<style><%@include file="./style.css"%></style>
		<title>TinyTwit : <%= ((User)request.getAttribute("user")).getName() %></title>
	</head>
	<body>
		<h2><a href="/">Tinytwit ¯\_(ツ)_/¯</a></h2>
		
		<nav><a href="/register">deconnexion</a></nav>
		
		
		<div id="twit_list">
				<h4><%= ((User)request.getAttribute("user")).getName() %></h4>
				<h3>Ses derniers twits :</h5>
					<%
						List<Twit> twits = (List<Twit>)request.getAttribute("twits");
						for(Twit twit : twits) {
					%>
					<p id="twit">
						Le <span id="date"><%= twit.getCreation() %></span> : <br />
						<span id="content"><%= twit.getContent() %></span>
					</p>

					<%
						}
					%>
			</div>
	</body>
</html>