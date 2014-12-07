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
	/**
	 * This method gets called by the MessageCenter in order for this 
	 * component to process the given Message
	 * 
	 * This message at a minimum level should have the same
	 * functionality as RawPhoto Component
	 */
	public Message notify(Message message) {
		return raw.notify(message);
	}

	

	
}
