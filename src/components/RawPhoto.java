package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public class RawPhoto extends Component{

	public RawPhoto() {
		super(TaskType.RAW_PHOTO);
	}

	@Override
	/**
	 * This method gets called by the MessageCenter in order for this 
	 * component to process the given Message
	 */
	public Message notify(Message message) {
		MessageImage messageImage = (MessageImage)message ;
		
		int[][][] pixels = messageImage.getPixels();
		int width = messageImage.getWidth();
		int height = messageImage.getHeight();
		
		/*
		 * To rotate the image in O(1) memory complexity 
		 * we need to go to iterate from the top left corner of 
		 * the image down to the middle of the image in order to
		 * swap the corresponding pixels from the top half and the 
		 * low half
		 */
		for(int i = 0 ; i < height/2 ; i++){
			for(int j= 0 ; j < width ; j++){
				for(int k = 0 ; k < 3 ; k++){
					int aux = pixels[i][j][k];
					pixels[i][j][k] = pixels[height-i-1][j][k];
					pixels[height-i-1][j][k] = aux ;
				}
			}
		}
		
		return messageImage;
	}

}
