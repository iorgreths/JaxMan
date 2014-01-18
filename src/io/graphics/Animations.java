package io.graphics;

import java.util.HashMap;

public class Animations<Key> {

	private HashMap<Key, Animation> aniMap;
	private Animation currentAnimation;
	
	/**
	 * 
	 * @param animations
	 * @param keySet
	 */
	public Animations(Animation[] animations, Key[] keySet){
		if(animations.length!=keySet.length){
			throw new IllegalArgumentException("");
		}
		
		aniMap=new HashMap<>();
		for(int i=0; i<animations.length; i++){
			aniMap.put(keySet[i],animations[i]);
		}
	}
	
	
}
