package tinytwit;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class UserServlet extends HttpServlet {
	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(Twit.class);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		
		getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		
		String username = req.getParameter("username");
		System.out.println("username: <" + username + ">");
		if(username != null && !"".equals(username)) {
			Key<User> userkey = Key.create(User.class, username);
			User u = ofy().load().key(userkey).now();
			if(u == null) {
				res.sendRedirect("/register");
			} else {
				Twit t = new Twit(req.getParameter("content"), new Date(), userkey);
				ofy().save().entity(t);
				res.sendRedirect("/");
			}
		} else {
			res.sendRedirect("/register");			
		}
	}
}