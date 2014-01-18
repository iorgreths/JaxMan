package io.graphics;

import java.awt.image.BufferedImage;

public class Animation {

	private final BufferedImage[] anim;
	private final int height, width;
	private final AnimationStrategy stepStrategy;
	private int currentStep;

	/**
	 * 
	 * @author Max
	 * @param anim
	 * @param rows
	 * @param columns
	 * @param strategy
	 */
	public Animation(BufferedImage anim, int rows, int columns, AnimationStrategy strategy){
		if(rows==0 || columns==0){
			throw new IllegalArgumentException("Columns and Rows must not be equal to 0!");
		}

		this.height=anim.getHeight()/rows;
		this.width=anim.getWidth()/columns;

		if(this.height*rows!=anim.getHeight() || this.width*columns!=anim.getWidth()){
			throw new IllegalArgumentException("Given spritesheet cannot be split into the given amount of rows and columns");
		}

		this.anim=new BufferedImage[rows*columns];
		for(int i=0; i<rows; i++){
			for(int j=0; j<columns; j++){
				this.anim[i*rows+j] = anim.getSubimage(j*width, i*height, (j+1)*width, (j+1)*height);
			}
		}

		this.stepStrategy=strategy;
		currentStep=0;
	}

	/**
	 * 
	 * @return
	 */
	public BufferedImage nextImage(){

		if(stepStrategy!=null){
			if(stepStrategy.isNextReady()){
				if(currentStep<anim.length){
					currentStep++;
				}else{
					currentStep=0;
				}
			}
		}else{

			if(currentStep<anim.length){
				currentStep++;
			}else{
				currentStep=0;
			}
		}
		
		return anim[currentStep];
	}
	
	/**
	 * 
	 */
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
}
