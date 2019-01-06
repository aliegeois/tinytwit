package tinytwit;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class HashTagServlet extends HttpServlet {
	static {
		ObjectifyService.register(HashTag.class);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		
		String path = req.getRequestURI().substring(1);
		String[] parts = path.split("/");
		String hashtag = parts[parts.length-1];
		
		req.setAttribute("hashtag", hashtag);
		
		getServletContext().getRequestDispatcher("/WEB-INF/hashtag.jsp").forward(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User newUser = new User(username);
		ofy().save().entity(newUser);
		
		res.sendRedirect("/");
	}
}