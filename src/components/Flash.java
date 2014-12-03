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
					pixels[i][j][0] += 50 ;
					pixels[i][j][1] += 50 ;
					pixels[i][j][2] += 50 ;
				}
			}
		}
		
		Message resulted = new MessageImage(TaskType.FLASH, pixels, width, height);
		
		return resulted;
	}

}
