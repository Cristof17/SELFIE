package messaging;

import generator.Generator;
import types.TaskType;

public abstract class Message {
	
	private TaskType taskType;
	private int messageId;
	
	
	public Message(TaskType taskType) {
		super();
		this.taskType = taskType;
		generateId();
	}
	/**
	 * This method generates a new ID for the message
	 */
	public void generateId() {
		this.messageId = Generator.generateInt();
	}
	
	/**
	 * Set the TaskType attribute the value given as parameter
	 * @param taskType The TaskType value
	 */
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	/**
	 * This method returns the value of the TaskType attribute
	 * in a Message derived object
	 * @return
	 */
	public TaskType getTaskType() {
		return taskType;
	}

	/**
	 * This method returns the ID of a given Message
	 * @return The ID of the Message
	 */
	public int getId() {
		return messageId;
	}
	
	
}
