package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public class Sepia extends Component{

	// ?? implemented superficially 
	public Sepia() {
		super(TaskType.SEPIA);
	}

	@Override
	public Message notify(Message message) {
		MessageImage messageImage = (MessageImage)message ;
		
		int width = messageImage.getWidth();
		int height = messageImage.getHeight();
		int pixels[][][] = messageImage.getPixels();
		
		for(int i = 0 ; i < height ; i++){
			for(int j = 0 ; j < width ; j++){
				int red_init = pixels[i][j][0];
				int green_init = pixels[i][j][1];
				int blue_init = pixels[i][j][2];
				
				pixels[i][j][0] = filterOverflow(0.393f, 0.769f, 0.189f, red_init, blue_init, green_init);
				pixels[i][j][1] = filterOverflow(0.349f, 0.686f, 0.168f, red_init, blue_init, green_init);
				pixels[i][j][2] = filterOverflow(0.272f, 0.534f, 0.131f, red_init, blue_init, green_init);
				
			}
		}
		
		MessageImage result = new MessageImage(TaskType.IMAGE_SAVE, pixels, width, height);
		
		return result;
	}

	public int filterOverflow(float index1 ,float index2 ,float index3 ,int red_init , int blue_init , int green_init){
		if((int)Math.round((red_init * index1 )+ (green_init * index2) + (blue_init * index3)) > 255)
			return 255;
		else return (int)Math.round((red_init * index1 )+ (green_init * index2) + (blue_init * index3));
	}
	
}
