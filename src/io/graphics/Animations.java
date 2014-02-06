package io.graphics;

import java.util.HashMap;
import java.util.List;

public class Animations<Key> {

	private HashMap<Key, Animation> aniMap;
	private Animation currentAnimation;
	
	/**
	 * 
	 * 
	 * @author Maxmanski
	 * @param animations
	 * @param keySet
	 */
	public Animations(Animation[] animations, Key[] keySet){
		if(animations.length!=keySet.length){
			throw new IllegalArgumentException("The Animation array and Keyset must be of the same length!");
		}else if(animations.length<=0){
			throw new IllegalArgumentException("The arrays must contain at least one element!");
		}
		
		aniMap=new HashMap<>();
		for(int i=0; i<animations.length; i++){
			aniMap.put(keySet[i],animations[i]);
		}
		this.currentAnimation=aniMap.get(keySet[0]);
	}
	
	/**
	 * 
	 * @author Maxmanski
	 * @param animations
	 * @param keySet
	 */
	public Animations(List<Animation> animations, List<Key> keySet){
		if(animations.size()!=keySet.size()){
			throw new IllegalArgumentException("The Animation List and Keyset must be of the same length!");
		}else if(animations.isEmpty()){
			throw new IllegalArgumentException("The Lists must contain at least one element!");
		}
		
		aniMap=new HashMap<>();
		for(int i=0; i<animations.size(); i++){
			aniMap.put(keySet.get(i),animations.get(i));
		}
		
		this.currentAnimation=aniMap.get(keySet.get(0));
	}
	
	/**
	 * 
	 * @author Maxmanski
	 * @param toCopy
	 */
	public Animations(Animations<Key> toCopy){
		aniMap=new HashMap<>();
		for(Key k: toCopy.aniMap.keySet()){
			this.aniMap.put(k, new Animation(toCopy.aniMap.get(k)));
		}
		this.currentAnimation=toCopy.currentAnimation;
	}
	
	/**
	 * 
	 * @author Maxmanski
	 * @param anim
	 * @throws InvalidAnimationException
	 */
	public void switchAnimation(Animation anim)throws InvalidAnimationException{
		if(anim==null){
			throw new IllegalArgumentException("Target Animation cannot be null");
		}
		Animation tmp = null;
		
		for(Key k: this.aniMap.keySet()){
			if(anim.equals(this.aniMap.get(k))){
				tmp=this.aniMap.get(k);
			}
		}
		
		if(tmp==null){
			throw new InvalidAnimationException("The specified Animation could not be found");
		}else{
			this.currentAnimation=tmp;
		}
	}
	
	/**
	 * 
	 * @author Maxmanski
	 * @param key
	 * @throws InvalidAnimationException
	 */
	public void switchAnimation(Key key) throws InvalidAnimationException{
		Animation tmp = null;
		
		tmp = this.aniMap.get(key);
		
		if(tmp==null){
			throw new InvalidAnimationException("The specified Animation could not be found");
		}else{
			this.currentAnimation=tmp;
		}
	}
	
	/**
	 * 
	 * 
	 * @author Maxmanski
	 * @param anim The Animation to be added to the collection.
	 * @param key The Key element to be used for identifying the specified Animation. If this Key element already identifies another Animation, the old Animation will be overridden.
	 * @return TRUE, if the call of this method has defined a new Animation. FALSE, if the old Animation for the specified Key has been overridden.
	 */
	public boolean putAnimation(Animation anim, Key key){
		boolean newKey=true;
		
		if(this.aniMap.containsKey(key)){
			newKey=false;
		}
		
		this.aniMap.put(key, anim);
		
		return newKey;
	}
	
	/**
	 * Deletes the specified Animation mapped to the given Key from this collection.
	 * Returns a boolean value depending on if something was deleted or not.
	 * 
	 * @author Maxmanski
	 * @return True, if the Animation was found and deleted. False, if no Animation was found and therefore nothing deleted.
	 */
	public boolean removeAnimation(Animation anim){
		boolean contained=false;

		for(Key k: this.aniMap.keySet()){
			if(this.aniMap.get(k).equals(anim)){
				contained=true;
				this.aniMap.remove(k);
			}
		}
		
		return contained;
	}
	
	/**
	 * Deletes the Animation mapped to the specified Key from this collection.
	 * Returns a boolean value depending on if something was deleted or not.
	 * 
	 * @author Maxmanski
	 * @return TRUE, if an Animation was found and deleted. FALSE, if no Animation was found and therefore nothing deleted.
	 */
	public boolean removeAnimation(Key key){
		boolean contained=false;
		
		if(this.aniMap.containsKey(key)){
			contained=true;
		}
		
		this.aniMap.remove(key);
		
		return contained;
	}
	
	/**
	 * Returns the currently active Animation.
	 * 
	 * @author Maxmanski
	 * @return The currently active Animation. This can be changed with the switchAnimation methods.
	 */
	public Animation getCurrentAnimation(){
		return this.currentAnimation;
	}
	
	/**
	 * Returns the Animation for the specified Key element.
	 * 
	 * @author Maxmanski
	 * @param k The Key element, for which the corresponding Animation should be returned
	 * @return The Animation corresponding to the specified Key element or NULL, if no corresponding Animation exists.
	 */
	public Animation getAnimation(Key k){
		return this.aniMap.get(k);
	}
	
	/**
	 * Returns a reference to an Animation in this collection which equals the specified Animation.
	 * This can be used if a direct reference is needed.
	 * 
	 * @author Maxmanski
	 * @return The Animation in this collection which equals the specified Animation or null, if no such Animation could be found.
	 */
	public Animation getAnimation(Animation anim){
		Animation tmp = null;
		
		for(Key k: this.aniMap.keySet()){
			if(this.aniMap.get(k).equals(anim)){
				tmp=this.aniMap.get(k);
				break;
			}
		}
		
		return tmp;
	}
}
