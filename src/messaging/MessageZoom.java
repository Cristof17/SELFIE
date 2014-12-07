package messaging;

import types.TaskType;
import point.Point;

public class MessageZoom extends Message{
	
	private int[][][] pixels ;
	private Point A , B ;

	public MessageZoom(TaskType taskType ,int[][][] pixels , Point A ,Point B) {
		super(taskType);
		this.pixels = pixels;
		this.A = A ;
		this.B = B ;
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
	 * This method returns the starting point for a zoom message 
	 * @return The point value of the zoom message
	 */
	public Point getA() {
		return A;
	}

	/**
	 * This method sets the value of the starting point for
	 * zoom to the value given as parameter
	 * @param a The value of the point 
	 */
	public void setA(Point a) {
		A = a;
	}

	/**
	 * This method returns the ending point for a zoom message 
	 * @return The point value of the zoom message
	 */
	public Point getB() {
		return B;
	}

	/**
	 * This method sets the value of the ending point for
	 * zoom to the value given as parameter
	 * @param b The value of the point 
	 */
	public void setB(Point b) {
		B = b;
	}
	
	

}
