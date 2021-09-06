package shared;

import java.io.Serializable;
import java.util.Map;

public class ChatList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map <String,Integer> map;
	
	private String protocol;

	public ChatList(Map<String, Integer> map, String protocol) {
		this.map = map;
		this.protocol = protocol;
	}
	
	public Map<String, Integer> getMap() {
		return map;
	}
	
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	

}
