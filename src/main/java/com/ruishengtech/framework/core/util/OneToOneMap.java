package com.ruishengtech.framework.core.util;

import java.util.HashMap;

/**
 * @author Wangyao
 *
 */
public class OneToOneMap<K,V> extends HashMap<K,V> {

	private static final long serialVersionUID = -9057522681477545928L;

	private HashMap<V,K> valueMap = new HashMap<>();
	
	public V put(K key, V value) {
		valueMap.put(value, key);
		return super.put(key, value);
	}
	
	public V removeByKey(K key) {
		V v = super.remove(key);
		this.valueMap.remove(v);
		return v;
    }
	
	public V remove(Object key) {
		V v = super.remove(key);
		this.valueMap.remove(v);
		return v;
	}
	
	public K removeByValue(V value) {
		K k = this.valueMap.remove(value);
		super.remove(k);
		return k;
    }
	
	public K getKey(V value) {
		return valueMap.get(value);
	}
	
}
