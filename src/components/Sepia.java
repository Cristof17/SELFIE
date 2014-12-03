package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public class Sepia extends Component{

	public Sepia() {
		super(TaskType.SEPIA);
	}

	@Override
	public Message notify(Message message) {
		MessageImage messageImage = (MessageImage)message ;
		return null;
	}

	
}
