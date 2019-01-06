<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="tinytwit.User" %>
<%@ page import="tinytwit.Twit" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <style><%@include file="./style.css"%></style>
        <title>TinyTwit : <%= request.getAttribute("username") %></title>
    </head>
    <body>
    
    	<nav><a href="/register">deconnexion</a></nav>
		<h2><a href="/">Tinytwit ¯\_(ツ)_/¯</a></h2>
		
    	<h4 id="username"><%= request.getAttribute("username") %></h4>
    	
    	<div id="div_twits">
	        <h6>Les derniers twits de <%= request.getAttribute("username") %></h6>
	        <div id="twits"></div>
        </div>
        
        <div id="twits_recommandes">
	        <h6>Les twits recommandés pour vous</h6>
	        <div id="subscribed_twits"></div>
	    </div>
	    
        <div id="div_abonnements">
	        <h6>Abonnements</h6>
	        <div id="subscriptions"></div>
        </div>
        
        <div id="div_abonnes">
	        <h6>Abonnés</h6>
	        <div id="subscribers"></div>
        </div>
        
        <div id="tps"></div>
        
        
        
        <h2>S'abonner à <%= request.getAttribute("username") %></h2>
        <label>Votre nom: </label><input id="your_name" type="text" required />
        <button id="to_sub">S'abonner !</button>
        
        
        <script>
        let t1 = new Date().getTime(),
    	    tps = document.getElementById('tps');
        fetch('/_ah/api/tinytwit/v1/user/<%= request.getAttribute("username") %>/twits').then(response => {
        	let t2 = new Date().getTime();
        	let t = document.createElement('div');
        	t.innerHTML = 'Temps pour récupérer les twits : ' + (t2 - t1);
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
        
        fetch('/_ah/api/tinytwit/v1/user/<%= request.getAttribute("username") %>/subscriptions').then(response => {
        	let t2 = new Date().getTime();
        	let t = document.createElement('div');
        	t.innerHTML = 'Temps pour récupérer les subscriptions : ' + (t2 - t1);
        	tps.appendChild(t);
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
        	let t2 = new Date().getTime();
        	let t = document.createElement('div');
        	t.innerHTML = 'Temps pour récupérer les subscribers : ' + (t2 - t1);
        	tps.appendChild(t);
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
        
        fetch('/_ah/api/tinytwit/v1/user/<%= request.getAttribute("username") %>/twistSubscribed/20').then(response => {
        	let t2 = new Date().getTime();
        	let t = document.createElement('div');
        	t.innerHTML = 'Temps pour récupérer les twistSubscribed : ' + (t2 - t1);
        	tps.appendChild(t);
        	return response.json();
        }).then(twits => {
            let d_twits = document.getElementById('subscribed_twits');
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
                		d_twit.innerHTML += parts[i];
                	}
                	if(i == parts.length - 2)
                		d_twit.innerHTML += ' ';
                }
                d_twit.innerHTML = twit.parent.name + " : " + twit.content;
                d_twits.appendChild(d_twit);
            }
        });
        
        document.getElementById('to_sub').addEventListener('click', () => {
        	let name = document.getElementById('your_name').value;
        	console.log(name);
        	if(name != null && name != "") {
	        	fetch('/_ah/api/tinytwit/v1/subscribe/' + name + '/<%= request.getAttribute("username") %>').then(() => {
	        		alert('Vous êtes abonné à <%= request.getAttribute("username") %> !');
	        	});
        	} else {
        		alert('Vous devez mettre un nom, sinon ça marchera beaucoup moins bien');
        	}
        });
        </script>
    </body>
</html>