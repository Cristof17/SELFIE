package components;

import messaging.Message;
import messaging.MessageImage;
import messaging.MessageZoom;
import point.Point;
import types.TaskType;

public class Zoom extends Component{
		
	public Zoom() {
		super(TaskType.ZOOM);
	}

	@Override
	/**
	 * This method gets called by the MessageCenter in order for this 
	 * component to process the given Message
	 */
	public Message notify(Message message) {
		MessageZoom zoomMessage = (MessageZoom)message;
		int[][][] pixels = zoomMessage.getPixels();
		
		
		Point A = zoomMessage.getA(); //upper-left corner
		Point B = zoomMessage.getB(); //lower-right corner
		
		int delta_height = B.getHeight()-A.getHeight();//difference in height between the zoomed image and the original one
		int delta_width = B.getWidth() - A.getWidth(); //difference in width between the zoomed image and the original one
		
		int[][][] pixels_returned = new int[delta_height + 1][delta_width + 1][3];
		for(int i = 0 ; i < delta_height + 1; i++){
			for(int j =0 ; j < delta_width + 1 ; j++){
				for(int k = 0 ; k < 3 ;k++)
					pixels_returned[i][j][k] = pixels[A.getHeight()+i][A.getWidth() + j][k];
			}
		}
		//create a MessageImage object to be returned to the MessageCenter that order the zoom on the image transmitted through message
		Message resulted = new MessageImage(TaskType.IMAGE_SAVE, pixels_returned, delta_width + 1 , delta_height + 1);
		return resulted;
	}

	
}
