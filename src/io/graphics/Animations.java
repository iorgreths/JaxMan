package io.graphics;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author Maxmanski
 *
 * @param <Key>
 */
public class Animations<Key> {

	private HashMap<Key, Animation> aniMap;
	private Animation currentAnimation;
	
	/**
	 * Creates a new collection of Animations, which is a container where objects of the type Animation can be
	 * stored and identified by specific sets of Key elements, much like a HashMap.<br>
	 * Both arrays have to be equal in size and must contain at least one element.<br>
	 * The first contained Animation object will be selected as the active Animation initially.
	 * 
	 * @param animations The Animation objects to be stored initially
	 * @param keySet The Key elements which should identify the initial set of Animations. Note that the
	 * 				positioning in the animations and keySet arrays determine which Key should be mapped to which
	 * 				Animation.
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
	 * Creates a new collection of Animations, which is a container where objects of the type Animation can be
	 * stored and identified by specific sets of Key elements, much like a HashMap.<br>
	 * Both lists have to be equal in size and must contain at least one element.<br>
	 * The first contained Animation object will be selected as the active Animation initially.
	 * 
	 * @param animations The Animation objects to be stored initially
	 * @param keySet The Key elements which should identify the initial set of Animations. Note that the
	 * 				positioning in the animations and keySet lists determine which Key should be mapped to which
	 * 				Animation.
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
	 * Creates a deep copy of the specified Animations collection.<br>
	 * The specified Animations object cannot be a NULL reference.
	 * 
	 * @param toCopy The Animations collection of which a deep copy should be created
	 */
	public Animations(Animations<Key> toCopy){
		if(toCopy==null){
			throw new IllegalArgumentException("The Animations object to be copied cannot be null");
		}
		aniMap=new HashMap<>();
		for(Key k: toCopy.aniMap.keySet()){
			this.aniMap.put(k, new Animation(toCopy.aniMap.get(k)));
		}
		this.currentAnimation=toCopy.currentAnimation;
	}
	
	/**
	 * Switches the currently active Animation to the first Animation object contained by this
	 * Animations object which equals the specified Animation.<br>
	 * The selected Animation will be reset.<br>
	 * If no equal element can be found, an Exception will be thrown.
	 * 
	 * @param anim The Animation of which an equal instance should be used as currently active Animation
	 * @throws InvalidAnimationException Thrown, if either anim is a NULL reference or no equal Animation can be found
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
			tmp.reset();
			this.currentAnimation=tmp;
		}
	}
	
	/**
	 * Switches the currently active Animation to an Animation in this collection which is identified
	 * by the specified key.<br>
	 * If there is no Animation contained by the specified key, an InvalidAnimationException will be thrown.
	 * 
	 * @param key The key that identifies the new to-be currently active Animation
	 * @throws InvalidAnimationException Will be thrown if the specified key does not identify any of the contained Animation objects.
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
	 * Adds anim to the collection and sets its identifier to key.<br>
	 * If there is already an Animation in the collection being identified by key, the old Animation will be overridden.
	 * 
	 * @param anim The Animation to be added to the collection.
	 * @param key The Key element to be used for identifying the specified Animation.
	 * @return TRUE, if the call of this method has defined a new Animation. FALSE, if an Animation has been overridden.
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
	 * @return The currently active Animation. This can be changed with the switchAnimation methods.
	 */
	public Animation getCurrentAnimation(){
		return this.currentAnimation;
	}
	
	/**
	 * Returns the Animation for the specified Key element.
	 * 
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
