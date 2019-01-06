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
        let t1 = new Date().getTime();
        fetch('/_ah/api/tinytwit/v1/hashtag/<%= request.getAttribute("hashtag") %>/twits').then(response => {
        	let t2 = new Date().getTime();
        	console.log(`${t2 - t1}ms`);
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