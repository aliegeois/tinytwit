package tinytwit;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

public class HomeServlet extends HttpServlet {
	static {
		ObjectifyService.register(Twit.class);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		
		try {
			List<Twit> twits = ofy().load().type(Twit.class).order("-creation").list();
			req.setAttribute("twits", twits);
			getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req, res);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		
		Twit t = new Twit(req.getParameter("content"), new Date());
		ofy().save().entity(t);
		
		res.sendRedirect("/");
	}
}