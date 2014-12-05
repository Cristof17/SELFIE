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
	public Message notify(Message message) {
		MessageImage messageImage = (MessageImage)message ;
		
		int width = messageImage.getWidth();
		int height = messageImage.getHeight();
		int[][][] pixels = messageImage.getPixels();
		
		for(int i = 0 ; i < height ; i++){
			for(int j = 0 ; j < width ; j++){
				
				int red_init = pixels[i][j][0] ;
				int green_init = pixels[i][j][1];
				int blue_init = pixels[i][j][2];
				
				pixels[i][j][0] = (int) Math.round((red_init*0.3) + (green_init * 0.59) + (blue_init * 0.11));
				pixels[i][j][1] = (int) Math.round((red_init*0.3) + (green_init * 0.59) + (blue_init * 0.11));
				pixels[i][j][2] = (int) Math.round((red_init*0.3) + (green_init * 0.59) + (blue_init * 0.11));
			}
		}
		
		MessageImage resulted = new MessageImage(TaskType.IMAGE_SAVE , pixels , width ,height);
		
		return resulted ;
	}

	
	
}
