package tinytwit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Entity
@Index
@Cache
public class HashTag {
	@Id String name;
	@Load List<Ref<Twit>> twits = new ArrayList<>();
	
	private HashTag() {}
	
	public HashTag(String name) {
		this.name = name;
	}
	
	public void addTwit(Long twitId) {
		twits.add(Ref.create(Key.create(Twit.class, twitId)));
	}
	
	public void addTwit(Twit twit) {
		twits.add(Ref.create(twit));
	}
	
	public void removeTwit(Long twitId) {
		twits.remove(Ref.create(Key.create(Twit.class, twitId)));
	}
	
	public List<Ref<Twit>> getTwits() {
		return twits;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
}
