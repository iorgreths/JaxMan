package io.graphics;

import java.util.HashMap;
import java.util.List;

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
	 * @param anim
	 * @param key
	 * @return
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
	 * @author Max
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
	 * @author Max
	 * @return True, if an Animation was found and deleted. False, if no Animation was found and therefore nothing deleted.
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
	 * @return The currently active Animation. This can be changed with the switchAnimation(...) methods.
	 */
	public Animation getCurrentAnimation(){
		return this.currentAnimation;
	}
	
	/**
	 * 
	 * @param k
	 * @return
	 */
	public Animation getAnimation(Key k){
		return this.aniMap.get(k);
	}
	
	/**
	 * Returns a reference to an Animation in this collection which equals the specified Animation.
	 * This can be used if a direct reference is needed.
	 * 
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
