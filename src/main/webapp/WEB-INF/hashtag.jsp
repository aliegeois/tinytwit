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
        <div id="tps"></div>
        <script>
        let t1 = new Date().getTime(),
            tps = document.getElementById('tps');
        fetch('/_ah/api/tinytwit/v1/hashtag/<%= request.getAttribute("hashtag") %>/twits').then(response => {
        	let t2 = new Date().getTime();
        	let t = document.createElement('div');
        	t.innerHTML = 'Temps pour récupérer les twits : ' + (t2 - t1) + 'ms';
        	tps.appendChild(t);
            return response.json();
        }).then(twits => {
            let d_twits = document.getElementById('twits');
            for(let twit of twits.items) {
                let d_twit = document.createElement('div'),
                    parts = twit.content.split(' ');
                for(let i = 0; i < parts.length; i++) {
                	if(parts[i].charAt(0) === '#') {
                		let a_tag = document.createElement('a');
                		a_tag.href = '/hashtag/' + parts[i].substring(1);
                		a_tag.innerHTML = parts[i];
                		d_twit.appendChild(a_tag);
                	} else {
                		d_twit.innerHTML += parts[i];
                	}
                	if(i != parts.length - 1)
                		d_twit.innerHTML += ' ';
                }
                d_twits.appendChild(d_twit);
            }
        });
        </script>
    </body>
</html>