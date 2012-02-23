package somebody.is.mad;

import java.util.List;

public class IPMap {
	
	public List<String> usernames;
	public String ipaddr;
 	
	public IPMap(String ipaddress, String username) {
		usernames.add(username);
		ipaddr = ipaddress;
	}
	
	public void addusername(String user) {
		usernames.add(user);
	}
	
	public boolean overmax() {
		return usernames.size() > 3;
	}

}
