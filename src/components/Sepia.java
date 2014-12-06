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
				
				pixels[i][j][0] = filterOverflow(0.393, 0.769, 0.189, red_init, blue_init, green_init);
				pixels[i][j][1] = filterOverflow(0.349, 0.686, 0.168, red_init, blue_init, green_init);
				pixels[i][j][2] = filterOverflow(0.272, 0.534, 0.131, red_init, blue_init, green_init);
				
			}
		}
		
		return messageImage;
	}

	public int filterOverflow(double index1 ,double index2 ,double index3 ,int red_init , int blue_init , int green_init){
		if((int)Math.round((red_init * index1 )+ (green_init * index2) + (blue_init * index3)) > 255)
			return 255;
		else return (int)Math.round((red_init * index1 )+ (green_init * index2) + (blue_init * index3));
	}
	
}
