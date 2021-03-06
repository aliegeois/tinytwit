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

public class LoginServlet extends HttpServlet {
	static {
		ObjectifyService.register(User.class);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		
		Cookie ck[]=req.getCookies();  
	    for(int i=0;i<ck.length;i++){  
	     res.getWriter().print("<br>"+ck[i].getName()+" "+ck[i].getValue());//printing name and value of cookie  
	    }
		getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(req, res);
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