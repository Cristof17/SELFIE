package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public class NormalPhoto extends Component {

	public NormalPhoto() {
		super(TaskType.NORMAL_PHOTO);
	}

	@Override
	public Message notify(Message message) {
		MessageImage messageImage = (MessageImage)message ;
		return null;
	}

	

	
}
