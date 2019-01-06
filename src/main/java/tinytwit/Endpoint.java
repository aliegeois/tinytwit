package tinytwit;

import static com.googlecode.objectify.ObjectifyService.ofy;

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
public class Endpoint {
	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(Twit.class);
		ObjectifyService.register(HashTag.class);
	}
	
	@ApiMethod(
    	path = "twit/{id}",
    	httpMethod = HttpMethod.GET
    )
    public Twit getTwit(@Named("id") int id) {
    	return ofy().load().type(Twit.class).id(id).now();
    }
    
    @ApiMethod(
    	path = "user/{username}",
    	httpMethod = HttpMethod.GET
    )
    public User getUser(@Named("username") String username) {
    	return ofy().load().type(User.class).id(username).now();
    }
    
    @ApiMethod(
    	path = "user/{username}/twits",
    	httpMethod = HttpMethod.GET
    )
    public List<Twit> getTwits(@Named("username") String username) {
    	return ofy().load().type(Twit.class).ancestor(KeyFactory.createKey("User", username)).list();
    }
    
    @ApiMethod(
    	path = "hashtag/{hashtag}/twits",
    	httpMethod = HttpMethod.GET
    )
    public List<Long> getTwitsByHashTag(@Named("hashtag") String hashtag) {
    	return ofy().load().type(HashTag.class).id(hashtag).now().getTwits();
    }
    
    @ApiMethod(
    	path = "user/{username}/twits/{quantity}",
    	httpMethod = HttpMethod.GET
    )
    public List<Twit> getTwitsByQuantity(@Named("username") String username, @Named("quantity") int quantity) {
    	return ofy().load().type(Twit.class).ancestor(KeyFactory.createKey("User", username)).order("-creation").limit(quantity).list();
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