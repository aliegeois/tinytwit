package tinytwit;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
@Cache
public class User {
	@Id String name;
	Set<String> subscriptions = new HashSet<>();
	Set<String> subscribers = new HashSet<>();
	
	private User() {}
	
	public User(String name) {
		this.name = name;
	}
	
	public void addSubscription(String username) {
		subscriptions.add(username);
	}
	
	public void removeSubscription(String username) {
		for(Iterator<String> iter = subscriptions.iterator(); iter.hasNext();) {
			String name = iter.next();
			if(name.equals(username))
				iter.remove();
		}
	}
	
	public void addSubscriber(String username) {
		subscribers.add(username);
	}
	
	public Set<String> getSubscriptions() {
		return subscriptions;
	}
	
	public void removeSubscriber(String username) {
		for(Iterator<String> iter = subscribers.iterator(); iter.hasNext();) {
			String name = iter.next();
			if(name.equals(username))
				iter.remove();
		}
	}
	
	public Set<String> getSubscribers() {
		return subscribers;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
}
