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
		ObjectifyService.register(User.class);
		ObjectifyService.register(Twit.class);
		ObjectifyService.register(HashTag.class);
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
		if(username != null && !"".equals(username)) {
			Key<User> userkey = Key.create(User.class, username);
			User u = ofy().load().key(userkey).now();
			if(u == null) {
				res.sendRedirect("/register");
			} else {
				String content = req.getParameter("content");
				
				Twit t = new Twit(content, new Date(), userkey);
				ofy().save().entity(t).now();
				
				String[] parts = content.split(" ");
				System.out.println(parts.length + " mots");
				for(String part : parts) {
					System.out.println("mot : " + part);
					if(part.charAt(0) == '#') {
						String tag = part.substring(1);
						System.out.println("tag : " + tag);
						HashTag h = ofy().load().type(HashTag.class).id(tag).now();
						if(h == null) {
							System.out.println("new tag");
							h = new HashTag(tag);
							System.out.println("twit : " + t);
						}
						h.addTwit(t);
						ofy().save().entity(h).now();
					}
				}
				
				res.sendRedirect("/");
			}
		} else {
			res.sendRedirect("/register");			
		}
	}
}