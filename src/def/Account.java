package def;

import java.util.List;

public class Account {
	private String username;
	
	private String password;
	
	private List<String> dresses;
	
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		dresses = null;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void updateDresses(String string) {
		dresses.add(string);
	}
}
