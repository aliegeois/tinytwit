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
        <h1>Liste des twits de <%= request.getAttribute("username") %></h1>
        <div id="twits"></div>
        <h2>Abonnements</h2>
        <div id="subscriptions"></div>
        <h2>Abonnés</h2>
        <div id="subscribers"></div>
        <h2>S'abonner à <%= request.getAttribute("username") %></h2>
        Votre nom: <input id="your_name" type="text">
        <button id="to_sub">S'abonner !</button>
        <script>
        fetch('/_ah/api/tinytwit/v1/user/<%= request.getAttribute("username") %>/twits').then(response => {
            return response.json();
        }).then(twits => {
            let d_twits = document.getElementById('twits');
            for(let twit of twits.items) {
                let d_twit = document.createElement('div'),
                    parts = twit.content.split(' ');
                for(let i = 0; i < parts.length; i++) {
                	if(parts[i].charAt(0) === '#') {
                		let a_tag = document.createElement('a'),
                		    txt = parts[i].substring(1);
                		a_tag.href = '/hashtag/' + txt;
                		a_tag.innerHTML = txt;
                		d_twit.appendChild(a_tag);
                	} else {
                		d_twit.innerHTML += part;
                	}
                	if(i == parts.length - 2)
                		d_twit.innerHTML += ' ';
                }
                d_twit.innerHTML = twit.content;
                d_twits.appendChild(d_twit);
            }
        });
        fetch('/_ah/api/tinytwit/v1/user/<%= request.getAttribute("username") %>/subscriptions').then(response => {
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
        fetch('/_ah/api/tinytwit/v1/user/<%= request.getAttribute("username") %>/subscribers').then(response => {
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
        	fetch('/_ah/api/tinytwit/v1/subscribe/' + name + '/<%= request.getAttribute("username") %>').then(() => {
        		alert('Vous êtes abonné à <%= request.getAttribute("username") %> !');;
        	});
        });
        </script>
    </body>
</html>