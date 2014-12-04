package components;

import messaging.Message;
import messaging.MessageFlash;
import messaging.MessageImage;
import types.FlashType;
import types.TaskType;


/*
 * superficially implemented class 
 */
public class Flash extends Component{

	public Flash() {
		super(TaskType.FLASH);
	}

	@Override
	public Message notify(Message message) {
		MessageFlash flashMessage = (MessageFlash)message ;
		int[][][] pixels = flashMessage.getPixels();
		int width = flashMessage.getWidth();
		int height = flashMessage.getHeight();
		
		
		int summ = 0 ;
		
		if(flashMessage.getFlashType().equals(FlashType.AUTO)){
			for(int i = 0 ; i < height ; i++){
				for(int j = 0 ; j < width ; j++){
					summ += Math.round(0.2126* pixels[i][j][0] + 0.7152* pixels[i][j][1] + 0.0722 * pixels[i][j][2] );
				}
			}
			
		}
		
		int averege =  summ / (width * height);
		if(averege < 60){
			for(int i = 0 ; i < height ; i ++ ){
				for(int j = 0 ; j < width ; j++){
					pixels[i][j][0] = checkPixelMax(pixels[i][j][0]) ;
					pixels[i][j][1] = checkPixelMax(pixels[i][j][1]) ;
					pixels[i][j][2] = checkPixelMax(pixels[i][j][2]) ;
				}
			}
		}
		
		Message resulted = new MessageImage(TaskType.FLASH, pixels, width, height);
		
		return resulted;
	}
	
	/**Helper method for verifying if the value of a pixel incremented
	 * exceeds 255 .
	 * 
	 * @param value The value of the unincremented pixel that needs to be verified
	 * @return Returns the value of the pixel incrememnted or 255 if the size of the pixel is
	 * greater than 255
	 */
	private int checkPixelMax(int value){
		if(value + 50  > 255)
			return 255;
	return value + 50 ;
	}

}
