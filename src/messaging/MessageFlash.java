package messaging;

import types.FlashType;
import types.TaskType;

public class MessageFlash extends Message {

	private int[][][] pixels ;
	private int width ;
	private int height; 
	private FlashType flashType ;
	
	public MessageFlash(TaskType taskType , int[][][] pixels , int width , int height ,FlashType flashType) {
		super(taskType);
		this.pixels = pixels ;
		this.width = width;
		this.height = height;
		this.flashType = flashType;
	}

	/**
	 * This method return the pixel matrix 
	 * @return Returns the pixel matrix
	 */
	public int[][][] getPixels() {
		return pixels;
	}

	/**
	 * This method sets the pixel matrix of a message to
	 * the values given as parameter
	 * @param pixels The pixel matrix values to be changed
	 */
	public void setPixels(int[][][] pixels) {
		this.pixels = pixels;
	}

	/**
	 * This method returns the Width of the pixel matrix
	 * @return Returns the Width value
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * This method sets the Width of the
	 * pixel matrix to the one given as parameter
	 * @param width The value of the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * This method returns the Height of pixel matrix
	 * @return The height of the pixel matrix
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * This method sets the height of the pixel matrix 
	 * to the one given as parameter
	 * @param height The value of the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * This method returns the FlashType of the Message
	 * @return The FlashType of the message
	 */
	public FlashType getFlashType() {
		return flashType;
	}

	/**
	 * This method sets the FlashType value of the 
	 * Message to the one given as parameter
	 * @param flashType The FlashType value of the
	 * new FlashType
	 */
	public void setFlashType(FlashType flashType) {
		this.flashType = flashType;
	}
	
	
	
	

}
