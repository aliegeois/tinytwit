package tinytwit;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.googlecode.objectify.ObjectifyService;
import com.google.api.server.spi.config.Named;


@Api(name = "monapi")
public class TestEndpoint {
	static {
		ObjectifyService.register(User.class);
	}
    
    @ApiMethod(
    	path = "{username}",
    	httpMethod = HttpMethod.GET
    )
    public User getMembre(@Named("username") String username) {
    	User u = new User(username);
    	//User membre = ofy().load().type(User.class).id(username).now();
        User membre = new User(username);
        return membre;
    }
}