package messaging;

import types.TaskType;

public class MessageSave extends MessageImage {
	private String path;
	
	public MessageSave(TaskType taskType, int messageId) {
		super(taskType);
	}

	public MessageSave(TaskType taskType, int[][][] pixels,
			int width, int height, String path) {
		super(taskType, pixels, width, height);
		this.path = path;
	}

	/**
	 * This method gets the value of the Path variable of the message
	 * @return The path of the image which will hold the pixel information
	 */
	public String getPath() {
		return path;
	}

	/**
	 * This method sets the value of the Path attribute to the
	 * one given as parameter
	 * @param path The value of the Path attribute
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
