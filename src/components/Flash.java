package components;

import messaging.Message;
import messaging.MessageFlash;
import messaging.MessageImage;
import types.FlashType;
import types.TaskType;


public class Flash extends Component{

	public Flash() {
		super(TaskType.FLASH);
	}

	@Override
	/**
	 * This method gets called by the MessageCenter in order for this 
	 * component to process the given Message
	 */
	public Message notify(Message message) {
		
		MessageFlash flashMessage = (MessageFlash)message ;
		int[][][] pixels = flashMessage.getPixels();
		int width = flashMessage.getWidth();
		int height = flashMessage.getHeight();
		
		//summ of all the pixels' values
		int summ = 0 ;
		
		if(flashMessage.getFlashType().equals(FlashType.AUTO)){
			for(int i = 0 ; i < height ; i++){
				for(int j = 0 ; j < width ; j++){
					summ +=(int) Math.round(0.2126* pixels[i][j][0] + 0.7152* pixels[i][j][1] + 0.0722 * pixels[i][j][2] );
				}
			}
		
			// averege value to see if there is a need for flash 
			double averege = summ / (width * height);
			if (averege < 60) {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						pixels[i][j][0] = checkPixelMax(pixels[i][j][0], 50);
						pixels[i][j][1] = checkPixelMax(pixels[i][j][1], 50);
						pixels[i][j][2] = checkPixelMax(pixels[i][j][2], 50);
					}
				}
			} 
			
		}else if(flashMessage.getFlashType().equals(FlashType.ON)){
			for(int i = 0 ; i < height ; i++){
				for(int j = 0 ; j < width ; j++){
					pixels[i][j][0] = checkPixelMax(pixels[i][j][0], 50);
					pixels[i][j][1] = checkPixelMax(pixels[i][j][1], 50);
					pixels[i][j][2] = checkPixelMax(pixels[i][j][2], 50);
				}
			}
			
		}else if(flashMessage.getFlashType().equals(FlashType.OFF)){
			//do nothing 
		}
		//return the image using a MessageImage object so that other filters can use it in the
		//post capture phase
		MessageImage result  = new MessageImage(TaskType.IMAGE_SAVE,pixels ,width ,height);
		return result;
	}
	
	/**Helper method for verifying if the value of a pixel incremented
	 * exceeds 255 .
	 * 
	 * @param init The value to be added to
	 * @param value The value to be added
 	 * @return The returned value is 255 if init + value > 255 
	 */
	private int checkPixelMax(int init , int value ){
		if(init + value > 255)
			return 255;
	return init + value ;
	}

}
