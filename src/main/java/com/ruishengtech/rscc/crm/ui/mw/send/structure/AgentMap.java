package com.ruishengtech.rscc.crm.ui.mw.send.structure;

import java.util.HashMap;

/**
 * @author Wangyao
 *
 */
public class AgentMap extends HashMap<String,String>{


	private static final long serialVersionUID = 3810870880826396982L;
	
	private HashMap<String,String> map = new HashMap<>();
	
	public String put(String key, String value) {
		map.put(value, key);
		return super.put(key, value);
	}
	
	public String remove(String key) {
		String v = super.remove(key);
		this.map.remove(v);
		return v;
    }
	
	public String removeValue(String value) {
		String k = this.map.remove(value);
		super.remove(k);
		return k;
    }
	
	public String getKey(String value) {
		return map.get(value);
	}
	
}
