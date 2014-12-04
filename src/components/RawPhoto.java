package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public class RawPhoto extends Component{

	public RawPhoto() {
		super(TaskType.RAW_PHOTO);
	}

	@Override
	public Message notify(Message message) {
		MessageImage messageImage = (MessageImage)message ;
		
		int[][][] pixels = messageImage.getPixels();
		int width = messageImage.getWidth();
		int height = messageImage.getHeight();
		
		int[][][] rotatedPixels = new int[height][width][3];
		for(int i = 0 ; i < height ; i ++ ){
			for(int j = 0 ; j < width ; j ++){
				for(int k= 0 ; k < 3 ; k ++ ){
					rotatedPixels[height-i-1][width -j -1][k] = pixels[i][j][k];
				}
			}
		}
		
		Message resulted = new MessageImage(TaskType.IMAGE_SAVE,rotatedPixels , width ,height);
		return resulted;
	}

}
