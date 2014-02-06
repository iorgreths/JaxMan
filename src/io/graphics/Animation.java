package io.graphics;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Maxmanski
 *
 */
public class Animation {

	private final BufferedImage[] anim;
	private final int height, width;
	private final AnimationStrategy stepStrategy;
	private int currentStep;
	private boolean hasEnded;

	/**
	 * Creates a new Animation from the specified source BuffererdImage, where it is split into
	 * several subpictures (how many subpictures is specified by rows and columns, which determine
	 * how many rows of subpictures should be used (and how many columns per row respectively)).<br>
	 * The specified AnimationStrategy will be used to determine whether the next animation step should be loaded
	 * after calling the nextImage method.<br>
	 * If the specified AnimationStrategy is a NULL reference, every call of the nextImage method will return the next
	 * animation step.
	 * <br>
	 * Note that the amount of rows and columns must at least be 1 (1 row and 1 column means that the entire
	 * specified BufferedImage will be used as a single animation step).<br>
	 * Also, the specified BufferedImage cannot be a NULL reference.<br>
	 * <br>
	 * The amount of specified rows multiplied by the amount of specified columns determines into how many
	 * animation steps (subimages) the source image should be divided.<br>
	 * Note that every of these subimages has to be used and none can be left out.
	 * 
	 * @param anim The source Image from which the animation steps should be extracted
	 * @param rows Determines into how many rows of subimages the source image should be divided.
	 * @param columns Determines into how many columns of subimages the source image should be divided.
	 * @param strategy Specifies the AnimationStrategy used to determine when the next animation step should be returned by the nextImage method
	 *					If this reference is a Nullpointer, every call of the nextImage method will return the next animation step.
	 */
	public Animation(BufferedImage anim, int rows, int columns, AnimationStrategy strategy){
		if(rows<=0 || columns<=0){
			throw new IllegalArgumentException("Columns and Rows must not be <= 0!");
		}
		if(anim==null){
			throw new IllegalArgumentException("The specified source image cannot be null!");
		}

		this.height=anim.getHeight()/rows;
		this.width=anim.getWidth()/columns;

		if(this.height*rows!=anim.getHeight() || this.width*columns!=anim.getWidth()){
			throw new IllegalArgumentException("Given spritesheet cannot be split into the given amount of rows and columns");
		}

		this.anim=new BufferedImage[rows*columns];
		for(int i=0; i<rows; i++){
			for(int j=0; j<columns; j++){
				this.anim[i*rows+j] = anim.getSubimage(j*width, i*height, width, height);
			}
		}

		this.hasEnded=false;
		this.stepStrategy=strategy;
		currentStep=0;
		
	}
	
	/**
	 * Creates a new Animation from the specified source BufferedImage, where the source image will be divided
	 * in a series of subimages (which will be used as animation steps).
	 * This constructor works the same way as the constructor Animation(BufferedImage anim, int rows, int columns, AnimationStrategy strategy)
	 * with the parameter animationSteps being equivalent to columns and having 1 as the parameter rows.
	 * <br>
	 * Note that the amount of animationSteps has to be at least 1<br>
	 * The AnimationStrategy is handled the same way as in the constructor Animation(BufferedImage anim, int rows, int columns, AnimationStrategy strategy).
	 *
	 * @param anim The source image
	 * @param animationSteps The amount of subimages in the source image, which should be used as animation steps
	 * @param strategy Specifies the AnimationStrategy used to determine when the next animation step should be returned by the nextImage method
	 *					If this reference is a Nullpointer, every call of the nextImage method will return the next animation step.
	 */
	public Animation(BufferedImage anim, int animationSteps, AnimationStrategy strategy){
		this(anim, 1, animationSteps, strategy);
	}
	
	/**
	 * This will create a new Animation with only 1 animation step, effectively making it a BufferedImage.
	 * 
	 * @param image The non-animated Image to be used.
	 */
	public Animation(BufferedImage image){
		this(image, 1, 1, null);
	}

	/**
	 * Creates a deep copy of the specified Animation.
	 * 
	 * @param toCopy
	 */
	public Animation(Animation toCopy){
		this.height=toCopy.height;
		this.width=toCopy.width;
		this.stepStrategy=toCopy.stepStrategy.clone();
		this.anim=new BufferedImage[toCopy.anim.length];
		BufferedImage tmp;
		for(int i=0; i<toCopy.anim.length; i++){
			tmp=toCopy.anim[i];
			this.anim[i] = tmp.getSubimage(0, 0, tmp.getWidth(), tmp.getHeight());
		}
		this.hasEnded=false;
	}

	/**
	 * Lets the AnimationStrategy check if the next animation step is ready to be returned.<br>
	 * If so, the next animation step will be returned. Else, the last animation step will be returned.<br>
	 * If this Animation has no AnimationStrategy, this method will always return the next animation step.<br>
	 * <br>
	 * If the animation reaches its last animation step and this method is called again, the first animation
	 * step will be returned and the Animation will start anew. Check the method hasEnded if you want to
	 * prevent the cycling of the Animation.
	 * 
	 * @return The next animation step
	 */
	public BufferedImage nextImage(){

		if(stepStrategy!=null){
			if(stepStrategy.isNextReady()){
				currentStep++;
				if(currentStep>=anim.length){
					currentStep=0;
				}
			}
		}else{
			
			currentStep++;
			if(currentStep>=anim.length){
				currentStep=0;
			}
		}
		
		if(currentStep==anim.length-1){
			hasEnded=true;
		}
		
		return anim[currentStep];
	}
	
	public boolean equals(Object other){
		if(other==null || other.getClass()!=this.getClass()){
			return false;
		}

		Animation otherAnim = (Animation)other;

		for(int i=0; i<anim.length; i++){
			if(!this.anim[i].equals(otherAnim.anim[i])){
				return false;
			}
		}

		return this.stepStrategy.equals(otherAnim.stepStrategy) && this.height==otherAnim.height
				&& this.width==otherAnim.height;

	}
	
	/**
	 * Checks if the Animation has reached its final animation step at least.
	 * 
	 * @return TRUE, if this Animation has reached its end at least once.
	 * 			FALSE otherwise.
	 */
	public boolean hasEnded(){
		return this.hasEnded;
	}
	
	/**
	 * Resets the Animation and its hasEnded property.
	 */
	public void reset(){
		this.currentStep=0;
		this.hasEnded=false;
	}
}
