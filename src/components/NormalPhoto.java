package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public class NormalPhoto extends Component {

	private RawPhoto raw = new RawPhoto();
	
	public NormalPhoto() {
		super(TaskType.NORMAL_PHOTO);
	}

	@Override
	public Message notify(Message message) {
		
		return raw.notify(message);
	}

	

	
}
