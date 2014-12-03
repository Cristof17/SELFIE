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

	public int[][][] getPixels() {
		return pixels;
	}

	public void setPixels(int[][][] pixels) {
		this.pixels = pixels;
	}

	public Point getA() {
		return A;
	}

	public void setA(Point a) {
		A = a;
	}

	public Point getB() {
		return B;
	}

	public void setB(Point b) {
		B = b;
	}
	
	

}
