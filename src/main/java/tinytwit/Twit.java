package tinytwit;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
@Index
@Cache
public class Twit {
	@Id Long id;
	@Parent Key<User> parent;
	String content;
	Date creation;
	
	private Twit() {}
	
	public Twit(String content, Date creation, Key<User> parent) {
		this.content = content;
		this.creation = creation;
		this.parent = parent;
	}
	
	public void setContent(String c) {
		content = c;
	}
	
	public void setCreation(Date c) {
		creation = c;
	}
	
	public String getContent() {
		return content;
	}
	
	public Date getCreation() {
		return creation;
	}
}
