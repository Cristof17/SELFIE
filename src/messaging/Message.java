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
	
	public void generateId() {
		this.messageId = Generator.generateInt();
	}
	
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public int getId() {
		return messageId;
	}
	
	
}
