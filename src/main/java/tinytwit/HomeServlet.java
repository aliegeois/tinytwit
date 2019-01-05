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

public class HomeServlet extends HttpServlet {
	static {
		ObjectifyService.register(Twit.class);
		ObjectifyService.register(User.class);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		
		List<Twit> twits = ofy().load().type(Twit.class).order("-creation").list();
		req.setAttribute("twits", twits);
		getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		
		String username = req.getParameter("username");
		Key<User> userkey = Key.create(User.class, username);
		Twit t = new Twit(req.getParameter("content"), new Date(), userkey);
		ofy().save().entity(t);
		
		res.sendRedirect("/");
	}
}