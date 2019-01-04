package tinytwit;

import java.util.Date;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
@Cache
public class Twit {
	@Id Long id;
	//@Parent Key<User> parent;
	String content;
	Date creation;
	
	public Twit() {}
	
	public Twit(String content, Date creation) {
		this.content = content;
		this.creation = creation;
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
