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

	public int[][][] getPixels() {
		return pixels;
	}

	public void setPixels(int[][][] pixels) {
		this.pixels = pixels;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public FlashType getFlashType() {
		return flashType;
	}

	public void setFlashType(FlashType flashType) {
		this.flashType = flashType;
	}
	
	
	
	

}
