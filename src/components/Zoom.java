package components;

import messaging.Message;
import messaging.MessageZoom;
import types.TaskType;

public class Zoom extends Component{

	public Zoom() {
		super(TaskType.ZOOM);
	}

	@Override
	public Message notify(Message message) {
		MessageZoom received_message = (MessageZoom)message;
		
		return null;
	}

	
}
