package messaging;

import types.TaskType;

public class MessageImage extends Message {

	private int[][][] pixels;
	private int width, height;
	
	public MessageImage(TaskType taskType, int[][][] pixels,
			int width, int height) {
		super(taskType);
		this.pixels = pixels;
		this.width = width;
		this.height = height;
	}
	
	public MessageImage(TaskType taskType) {
		super(taskType);
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
	 * This method returns the Height of pixel matrix
	 * @return The height of the pixel matrix
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * This method sets the Height of the 
	 * pixels matrix to the one given as parameter
	 * @param height The height value
	 */
	public void setHeight(int height) {
		this.height = height;
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
}
