package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public class BlackWhite extends Component{

	
	// ? Superficially implemented 
	public BlackWhite() {
		super(TaskType.BLACK_WHITE);
	}

	@Override
	/**
	 * This method gets called by the MessageCenter in order for this 
	 * component to process the given Message
	 */
	public Message notify(Message message) {
		MessageImage messageImage = (MessageImage)message ;
		
		int width = messageImage.getWidth();
		int height = messageImage.getHeight();
		int[][][] pixels = messageImage.getPixels();
		
		for(int i = 0 ; i < height ; i++){
			for(int j = 0 ; j < width ; j++){
		
				/*
				 * Get the initial values of the pixel color
				 * so that we could refer to them when we apply
				 * black and white filter
				 */
				int red_init = pixels[i][j][0] ;
				int green_init = pixels[i][j][1];
				int blue_init = pixels[i][j][2];
				
				pixels[i][j][0] = (int) Math.round((red_init*0.3) + (green_init * 0.59) + (blue_init * 0.11));
				pixels[i][j][1] = (int) Math.round((red_init*0.3) + (green_init * 0.59) + (blue_init * 0.11));
				pixels[i][j][2] = (int) Math.round((red_init*0.3) + (green_init * 0.59) + (blue_init * 0.11));
			}
		}
		
		return messageImage ;
	}

	
	
}
