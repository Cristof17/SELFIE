package components;

import messaging.Message;
import messaging.MessageImage;
import messaging.MessageZoom;
import point.Point;
import types.TaskType;

public class Zoom extends Component{
	
	// ? ce TaskType este mesajul returnat ?
	// ? este bine matricea de B.heigh() - A.height() + 1 si B.width() - A.width + 1 ; ?
	
	public Zoom() {
		super(TaskType.ZOOM);
	}

	@Override
	public Message notify(Message message) {
		MessageZoom zoomMessage = (MessageZoom)message;
		int[][][] pixels = zoomMessage.getPixels();
		
		
		Point A = zoomMessage.getA();
		Point B = zoomMessage.getB();
		
		
		int delta_height = B.getHeight()-A.getHeight();
		int delta_width = B.getHeight() - A.getWidth();
		
		int[][][] pixels_returned = new int[delta_height + 1][delta_width + 1][3];
		for(int i = A.getHeight() ; i <= B.getHeight(); i++){
			for(int j = A.getWidth() ; j <= B.getWidth() ; j++){
				pixels_returned[i][j] = pixels[i][j];
			}
		}
		
		Message resulted = new MessageImage(TaskType.IMAGE_SAVE, pixels_returned, delta_width + 1 , delta_height + 1);
		return resulted;
	}

	
}
