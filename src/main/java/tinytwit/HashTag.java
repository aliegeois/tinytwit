package tinytwit;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
@Cache
public class HashTag {
	@Id String name;
	List<Long> twits = new ArrayList<>();
	
	private HashTag() {}
	
	public HashTag(String name) {
		this.name = name;
	}
	
	public void addTwit(Long twitId) {
		twits.add(twitId);
	}
	
	public void removeTwit(Long twitId) {
		twits.remove(twitId);
	}
	
	public List<Long> getTwits() {
		return twits;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
}
