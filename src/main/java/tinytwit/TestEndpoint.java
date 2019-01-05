package tinytwit;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    	path = "user/{username}",
    	httpMethod = HttpMethod.GET
    )
    public Object getUser(@Named("username") String username) {
    	User u = ofy().load().type(User.class).id(username).now();
    	return new Object() {
    		public String bite = u.getName();
    		public List<String> subscriptions = new ArrayList<>(u.getSubscriptions());
    		public List<String> subscribers = new ArrayList<>(u.getSubscribers());
    	};
    	//return ofy().load().type(User.class).id(username).now();
    }
    
    @ApiMethod(
    	path = "user/{username}/twits",
    	httpMethod = HttpMethod.GET
    )
    public List<Twit> getTwits(@Named("username") String username) {
    	return ofy().load().type(Twit.class).ancestor(KeyFactory.createKey("User", username)).list();
    }
    
    @ApiMethod(
    	path = "user/{username}/subscriptions",
    	httpMethod = HttpMethod.GET
    )
    public Set<String> getSubscriptions(@Named("username") String username) {
    	return ofy().load().type(User.class).id(username).now().getSubscriptions();
    }
    
    @ApiMethod(
    	path = "user/{username}/subscribers",
    	httpMethod = HttpMethod.GET
    )
    public Set<String> getSubscribers(@Named("username") String username) {
    	return ofy().load().type(User.class).id(username).now().getSubscribers();
    }
    
    @ApiMethod(
    	path = "subscribe/{user1}/{user2}",
    	httpMethod = HttpMethod.GET
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
    	httpMethod = HttpMethod.GET
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