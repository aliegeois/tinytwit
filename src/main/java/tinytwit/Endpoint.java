package tinytwit;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
    
    @ApiMethod( //Get all the twits from username
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
    public Collection<Twit> getTwitsByHashTag(@Named("hashtag") String hashtag) {
    	//ofy().load().type(Twit.class).ids(ofy().load().type(HashTag.class).id(hashtag).now().getTwits());
    	HashTag h = ofy().load().type(HashTag.class).id(hashtag).now();
    	if(h != null) {
    		Collection<Twit> values = ofy().load().refs(h.getTwits()).values();
    		return values;
    	}
    	return null;
    	//return ofy().load().refs(ofy().load().type(HashTag.class).id(hashtag).now().getTwits()).values();
    	//return ofy().load().type(HashTag.class).id(hashtag).now().getTwits();
    }
    
    @ApiMethod( //Get the quantity first twits of username
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
    	//System.out.println("flag 1");
    	return ofy().load().type(User.class).id(username).now().getSubscriptions();
    }
    
    @ApiMethod( // Get the list of users username is subscribed to
    	path = "user/{username}/subscribers",
    	httpMethod = HttpMethod.GET
    )
    public Set<String> getSubscribers(@Named("username") String username) {
    	return ofy().load().type(User.class).id(username).now().getSubscribers();
    }
    
    @ApiMethod( //Get the twits of the persons username is subscribed to
    		path = "user/{username}/twistSubscribed/{quantity}",
        	httpMethod = HttpMethod.GET
    )
    public List<Twit> getSubscribedTwit(@Named("username") String username, @Named("quantity") int quantity) {
    	System.out.println("flag 0");
    	Set<String> subscriptions = this.getSubscriptions(username);
    	List<Twit> twits = new ArrayList<Twit>();
    	for(String subscib : subscriptions) {
    		twits.addAll(this.getTwitsByQuantity(username, quantity));
    	}
    	
    	twits.sort(new Comparator<Twit>() {
    	    @Override
    	    public int compare(Twit t1, Twit t2) {
    	        return t1.creation.compareTo(t2.creation);
    	    }
    	});
    	
    	//System.out.println("Taille : " + twits.size());
    	return twits;
    	
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