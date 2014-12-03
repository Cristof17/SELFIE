package components;

import messaging.Message;
import messaging.MessageFlash;
import types.TaskType;

public class Flash extends Component{

	public Flash() {
		super(TaskType.FLASH);
	}

	@Override
	public Message notify(Message message) {
		MessageFlash flashMessage = (MessageFlash)message ;
		return null;
	}

}
