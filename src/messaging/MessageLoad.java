package messaging;

import types.TaskType;

public class MessageLoad extends Message {

	private String path;
	
	/**
	 * This method returns the value of the Path attribute 
	 * of the message
	 * @return The value of the Path attribute
	 */
	public String getPath() {
		return path;
	}

	/**
	 * This method sets the Path attribute of the message 
	 * to the value given as parameter
	 * @param path The value of the new Path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	public MessageLoad(TaskType taskType, String path) {
		super(taskType);
		this.path = path;
	}

}
