package tinytwit;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class GlobalServlet extends HttpServlet {
	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(Twit.class);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		
		String path = req.getRequestURI().substring(1);
		int slashes = StringUtils.countMatches(path, "/");
		String[] parts = path.split("/");
		String username = parts[0];
		if(slashes >= 1) {
			String method = parts[1];
			if(slashes == 2) {
				if("status".equals(method)) {
					String id = parts[2];
					User u = ofy().load().type(User.class).id(username).now();
					if(u != null) {
						Twit t = ofy().load().type(Twit.class).parent(u).id(id).now();
						req.setAttribute("user", u);
						req.setAttribute("twit", t);
						getServletContext().getRequestDispatcher("/WEB-INF/twit.jsp").forward(req, res);
						return;
					}
				}
			}
		}
		getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req, res);
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