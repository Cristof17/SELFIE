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
		int delta_width = B.getWidth() - A.getWidth();
		
		int[][][] pixels_returned = new int[delta_height + 1][delta_width + 1][3];
		for(int i = 0 ; i < delta_height + 1; i++){
			for(int j =0 ; j < delta_width + 1 ; j++){
				pixels_returned[i][j] = pixels[A.getHeight()+i][A.getWidth() + j];
			}
		}
		
		Message resulted = new MessageImage(TaskType.IMAGE_SAVE, pixels_returned, delta_width + 1 , delta_height + 1);
		return resulted;
	}

	
}
