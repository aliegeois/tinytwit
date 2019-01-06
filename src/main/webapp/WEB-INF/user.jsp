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
        <h2>Abonnements</h2>
        <div id="subscriptions"></div>
        <h2>Abonnés</h2>
        <div id="subscribers"></div>
        <h2>S'abonner à <%= ((User)request.getAttribute("user")).getName() %></h2>
        Votre nom: <input id="your_name" type="text">
        <button id="to_sub">S'abonner !</button>
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
        fetch('/_ah/api/tinytwit/v1/user/<%= ((User)request.getAttribute("user")).getName() %>/subscriptions').then(response => {
            return response.json();
        }).then(subs => {
            let d_subs = document.getElementById('subscriptions');
            for(let sub of subs.items) {
                let d_sub = document.createElement('div');
                let a_sub = document.createElement('a');
                a_sub.href = "/user/" + sub;
                a_sub.innerHTML = sub;
                d_sub.appendChild(a_sub);
                d_subs.appendChild(d_sub);
            }
        });
        fetch('/_ah/api/tinytwit/v1/user/<%= ((User)request.getAttribute("user")).getName() %>/subscribers').then(response => {
            return response.json();
        }).then(subs => {
            let d_subs = document.getElementById('subscribers');
            for(let sub of subs.items) {
                let d_sub = document.createElement('div');
                let a_sub = document.createElement('a');
                a_sub.href = "/user/" + sub;
                a_sub.innerHTML = sub;
                d_sub.appendChild(a_sub);
                d_subs.appendChild(d_sub);
            }
        });
        document.getElementById('to_sub').addEventListener('click', () => {
        	let name = document.getElementById('your_name').value;
        	fetch('/_ah/api/tinytwit/v1/subscribe/' + name + '/' + <%= ((User)request.getAttribute("user")).getName() %>).then(() => {
        		alert('Vous êtes abonné à <%= ((User)request.getAttribute("user")).getName() %> !');;
        	});
        });
        </script>
    </body>
</html>