<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="tinytwit.Twit" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>HashTag</title>
    </head>
    <body>
        <h1>Liste des twits contenant #<%= request.getAttribute("hashtag") %></h1>
        <div id="twits"></div>
        <script>
        fetch('/_ah/api/tinytwit/v1/hashtag/<%= request.getAttribute("hashtag") %>/twits').then(response => {
            return response.json();
        }).then(twitsId => {
            let d_twits = document.getElementById('twits');
            for(let twitId of twitsId.items) {
                let d_twit = document.createElement('div');
                fetch('/_ah/api/tinytwit/v1/twit/' + twitId).then(response => {
                	return reponse.json();
                }).then(twit => {
                	d_twit.innerHTML = twit.content;
                });
                d_twits.appendChild(d_twit);
            }
        });
        </script>
    </body>
</html>