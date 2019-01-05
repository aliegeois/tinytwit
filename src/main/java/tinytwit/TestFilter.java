package tinytwit;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * Servlet Filter implementation class TestFilter
 */
@WebFilter("/TestFilter")
public class TestFilter implements Filter {
	@Override
	public void init(FilterConfig fConfig) throws ServletException {}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse result, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)result;
		System.out.println(req.getRequestURI());
		/*String path = req.getRequestURI().substring(1);
		int slashes = StringUtils.countMatches(path, "/");
		
		if(path.indexOf("/") != -1) { // Il y a un slash
			String[] parts = path.split("/");
			String username = parts[0];
			String fct = parts[1];
			System.out.println("username: " + username + " - fonction: " + fct);
			
			chain.doFilter(req, res);
		} else { // Il n'y en a pas
			String username = path;
			if("".equals(username)) {
				chain.doFilter(req, res);
			} else {
				System.out.println("username: " + username);
				res.sendRedirect("/user/" + username);
			}
		}*/
		
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
						chain.doFilter(req, res);
						return;
					}
				}
			}
		} else {
			// redirect to 404
		}
		
	}
	
	@Override
	public void destroy() {}
}
