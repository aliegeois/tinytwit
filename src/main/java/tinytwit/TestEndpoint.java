package tinytwit;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;


@Api(name = "monapi")
public class TestEndpoint {
	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(Twit.class);
	}
    
    @ApiMethod(
    	path = "{username}",
    	httpMethod = HttpMethod.GET
    )
    public User getMembre(@Named("username") String username) {
    	//User u = new User(username);
    	User membre = ofy().load().type(User.class).id(username).now();
    	
       // User membre = new User(username);
        return membre;
    }
    
    @ApiMethod(
    	path = "{username}/twits",
    	httpMethod = HttpMethod.GET
    )
    public List<Twit> getTwits(@Named("username") String username) {
    	return ofy().load().type(Twit.class).ancestor(KeyFactory.createKey("User", username)).list();
    }
    
    /*private class Infos {
    	public String username;
    	public List<Twit> twits;
    	
    	public Infos(String username, List<Twit> twits) {
    		this.username = username;
    		this.twits = twits;
    	}
    }*/
}