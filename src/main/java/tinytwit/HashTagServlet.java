package tinytwit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}