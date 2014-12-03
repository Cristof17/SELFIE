package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public class Blur extends Component{

	public Blur() {
		super(TaskType.BLUR);
	}

	@Override
	public Message notify(Message message) {
		MessageImage messageImage = (MessageImage)message ;
		return null;
	}

	
}
