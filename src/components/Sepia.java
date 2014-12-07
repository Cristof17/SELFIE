package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public class Sepia extends Component{

	public Sepia() {
		super(TaskType.SEPIA);
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
		int pixels[][][] = messageImage.getPixels();
		
		for(int i = 0 ; i < height ; i++){
			for(int j = 0 ; j < width ; j++){
				
				/*
				 * Initial color values held in a pixel
				 */
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

	/**This method checks if the resulting value from the formula is greater 
	 * than 255 (white color) so that there will be no need to hold greater values
	 * 
	 * @param index1 specific coefficient for red color
	 * @param index2 specific coefficient for green color
	 * @param index3 specific coefficient for blue color
	 * @param red_init the initial value of the red color before processing
	 * @param blue_init the initial value of the green color before processing
	 * @param green_init the initial value of the blue color before processing
	 * 
	 * @return The value that a color of the pixel needs to have after the Sepia filter
	 * 
	 */
	public int filterOverflow(double index1 ,double index2 ,double index3 ,int red_init , int blue_init , int green_init){
		if((int)Math.round((red_init * index1 )+ (green_init * index2) + (blue_init * index3)) > 255)
			return 255;
		else return (int)Math.round((red_init * index1 )+ (green_init * index2) + (blue_init * index3));
	}
	
}
