package tinytwit;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;


@Api(name = "tinytwit")
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
    
    @ApiMethod(
    	path = "subscribe/{user1}/{user2}",
    	httpMethod = HttpMethod.PUT
    )
    public void subscribe(@Named("user1") String user1, @Named("user2") String user2) {
    	User u1 = ofy().load().type(User.class).id(user1).now();
    	User u2 = ofy().load().type(User.class).id(user2).now();
    	if(u1 != null && u2 != null) {
    		u1.addSubscription(user2);
    		u2.addSubscriber(user1);
    		ofy().transact(new VoidWork() {
    			public void vrun() {
    				ofy().save().entities(u1, u2);
    			}
    		});
    	}
    }
    
    @ApiMethod(
    	path = "unsubscribe/{user1}/{user2}",
    	httpMethod = HttpMethod.PUT
    )
    public void unsubscribe(@Named("user1") String user1, @Named("user2") String user2) {
    	User u1 = ofy().load().type(User.class).id(user1).now();
    	User u2 = ofy().load().type(User.class).id(user2).now();
    	if(u1 != null && u2 != null) {
    		u1.removeSubscription(user2);
    		u2.removeSubscriber(user1);
    		ofy().transact(new VoidWork() {
    			public void vrun() {
    				ofy().save().entities(u1, u2);
    			}
    		});
    	}
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