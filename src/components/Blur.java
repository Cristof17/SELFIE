package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public class Blur extends Component{

	public Blur() {
		super(TaskType.BLUR);
	}

	@Override
	public Message notify(Message message) {
		MessageImage messageImage = (MessageImage)message ;
		int width = messageImage.getWidth();
		int height = messageImage.getHeight();
		int [][][]pixels = messageImage.getPixels();
		
		for(int i = 0 ; i < height ; i++){
			for(int j = 0 ; j < width ; j++){
				
				if(i == 0 && j == 0){ //upper-left corner ;
					
					float summ_red = 0 ;
					float summ_green = 0;
					float summ_blue = 0;
					try{
					summ_red = pixels[i+1][j][0] + pixels[i][j+1][0] + pixels[i+1][j+1][0];
					summ_green = pixels[i+1][j][1] + pixels[i][j+1][1] + pixels[i+1][j+1][1];
					summ_blue = pixels[i+1][j][2] + pixels[i][j+1][2] + pixels[i+1][j+1][2];
					
					pixels[i][j][0] = filterOverflow(Math.round((float)summ_red / 3));
					pixels[i][j][1] = filterOverflow(Math.round((float)summ_green / 3));
					pixels[i][j][2] = filterOverflow(Math.round((float)summ_blue / 3));
					}catch(ArrayIndexOutOfBoundsException e){
						System.out.println("Error");
					}
				}else if(i == 0 && j == width -1 ){ //upper-right corner
					
					float summ_red = 0 ;
					float summ_green = 0;
					float summ_blue = 0;
					
					summ_red = pixels[i+1][j][0] + pixels[i][j-1][0] + pixels[i+1][j-1][0];
					summ_green = pixels[i+1][j][1] + pixels[i][j-1][1] + pixels[i+1][j-1][1];
					summ_blue = pixels[i+1][j][2] + pixels[i][j-1][2] + pixels[i+1][j-1][2];
					
					pixels[i][j][0] = filterOverflow(Math.round((float)summ_red / 3));
					pixels[i][j][1] = filterOverflow(Math.round((float)summ_green / 3));
					pixels[i][j][2] = filterOverflow(Math.round((float)summ_blue / 3));
				
				}else if(i == height - 1 && j == 0){ //lower left corner
					
					float summ_red = 0 ;
					float summ_green = 0;
					float summ_blue = 0;
					
					summ_red = pixels[i-1][j][0] + pixels[i-1][j+1][0] + pixels[i][j+1][0];
					summ_green = pixels[i-1][j][1] + pixels[i-1][j+1][1] + pixels[i][j+1][1];
					summ_blue = pixels[i-1][j][2] + pixels[i-1][j+1][2] + pixels[i][j+1][2];
					
					pixels[i][j][0] = filterOverflow(Math.round((float)summ_red / 3));
					pixels[i][j][1] = filterOverflow(Math.round((float)summ_green / 3));
					pixels[i][j][2] = filterOverflow(Math.round((float)summ_blue / 3));
					
				}else if(i == height -1 && j == width -1 ){ //lower right corner
					
					float summ_red = 0 ;
					float summ_green = 0;
					float summ_blue = 0;
					
					summ_red = pixels[i][j-1][0] + pixels[i-1][j-1][0] + pixels[i-1][j][0];
					summ_green = pixels[i][j-1][1] + pixels[i-1][j-1][1] + pixels[i-1][j][1];
					summ_blue = pixels[i][j-1][2] + pixels[i-1][j-1][2] + pixels[i-1][j][2];
					
					pixels[i][j][0] = filterOverflow(Math.round((float)summ_red / 3));
					pixels[i][j][1] = filterOverflow(Math.round((float)summ_green / 3));
					pixels[i][j][2] = filterOverflow(Math.round((float)summ_blue / 3));
					
				}else if (i == 0 && j >=1 ){ //upper margin
					
					float summ_red = 0 ;
					float summ_green = 0;
					float summ_blue = 0;
					
					summ_red = pixels[i][j-1][0] + pixels[i+1][j-1][0] + pixels[i+1][j][0] + pixels[i+1][j+1][0] + pixels[i][j+1][0];
					summ_green = pixels[i][j-1][1] + pixels[i+1][j-1][1] + pixels[i+1][j][1] + pixels[i+1][j+1][1] + pixels[i][j+1][1];
					summ_red = pixels[i][j-1][0] + pixels[i+1][j-1][2] + pixels[i+1][j][2] + pixels[i+1][j+1][2] + pixels[i][j+1][2];
					
					pixels[i][j][0] = filterOverflow(Math.round((float)summ_red / 5));
					pixels[i][j][1] = filterOverflow(Math.round((float)summ_green / 5));
					pixels[i][j][2] = filterOverflow(Math.round((float)summ_blue / 5));
					
				}else if(i >= 1 && j==0){ //left margin
					
					float summ_red = 0 ;
					float summ_green = 0;
					float summ_blue = 0;
					
					summ_red = pixels[i-1][j][0] + pixels[i-1][j+1][0] + pixels[i][j+1][0] + pixels[i+1][j+1][0] + pixels[i+1][j][0];
					summ_green = pixels[i-1][j][1] + pixels[i-1][j+1][1] + pixels[i][j+1][1] + pixels[i+1][j+1][1] + pixels[i+1][j][1];
					summ_blue = pixels[i-1][j][2] + pixels[i-1][j+1][2] + pixels[i][j+1][2]+pixels[i+1][j+1][2] + pixels[i+1][j][2];
					
					pixels[i][j][0] = filterOverflow(Math.round((float)summ_red / 5));
					pixels[i][j][1] = filterOverflow(Math.round((float)summ_green / 5));
					pixels[i][j][2] = filterOverflow(Math.round((float)summ_blue / 5));
					
				}else if(i == height-1 && j >= 1){ //lower margin
					
					
					float summ_red = 0 ;
					float summ_green = 0;
					float summ_blue = 0;
					
					summ_red = pixels[i][j-1][0] + pixels[i-1][j-1][0] + pixels[i-1][j][0] + pixels[i-1][j+1][0] + pixels[i][j+1][0];
					summ_green = pixels[i][j-1][1] + pixels[i-1][j-1][1] + pixels[i-1][j][1] + pixels[i-1][j+1][1] + pixels[i][j+1][1];
					summ_blue = pixels[i][j-1][2] + pixels[i-1][j-1][2] + pixels[i-1][j][2] + pixels[i-1][j+1][2] + pixels[i][j+1][2];
					
					pixels[i][j][0] = filterOverflow(Math.round((float)summ_red / 5));
					pixels[i][j][1] = filterOverflow(Math.round((float)summ_green / 5));
					pixels[i][j][2] = filterOverflow(Math.round((float)summ_blue / 5));
					
				}else if(i >= 1 && j == width-1){ //right marging
					
					float summ_red = 0 ;
					float summ_green = 0;
					float summ_blue = 0;
					
					summ_red = pixels[i-1][j][0] + pixels[i-1][j-1][0] + pixels[i][j-1][0] + pixels[i+1][j-1][0] + pixels[i+1][j][0];
					summ_green = pixels[i-1][j][1] + pixels[i-1][j-1][1] + pixels[i][j-1][1] + pixels[i+1][j-1][1] + pixels[i+1][j][1];
					summ_blue = pixels[i-1][j][2] + pixels[i-1][j-1][2] + pixels[i][j-1][2] + pixels[i+1][j-1][2] + pixels[i+1][j][2];
					
					pixels[i][j][0] = filterOverflow(Math.round((float)summ_red / 5));
					pixels[i][j][1] = filterOverflow(Math.round((float)summ_green / 5));
					pixels[i][j][2] = filterOverflow(Math.round((float)summ_blue / 5));
					
				}else{ //point in the middle
					
					float summ_red = 0 ;
					float summ_green = 0;
					float summ_blue = 0;
					
					summ_red = pixels[i-1][j-1][0] + pixels[i-1][j][0] + pixels[i-1][j+1][0] + pixels[i][j+1][0] + pixels[i+1][j+1][0] + pixels[i+1][j][0] + pixels[i+1][j-1][0] + pixels[i][j-1][0];
					summ_green = pixels[i-1][j-1][1] + pixels[i-1][j][1] + pixels[i-1][j+1][1] + pixels[i][j+1][1] + pixels[i+1][j+1][1] + pixels[i+1][j][1] + pixels[i+1][j-1][1] + pixels[i][j-1][1];
					summ_red = pixels[i-1][j-1][2] + pixels[i-1][j][2] + pixels[i-1][j+1][2] + pixels[i][j+1][2] + pixels[i+1][j+1][2] + pixels[i+1][j][2] + pixels[i+1][j-1][2] + pixels[i][j-1][2];
					
					pixels[i][j][0] = filterOverflow(Math.round((float)summ_red / 8));
					pixels[i][j][1] = filterOverflow(Math.round((float)summ_green / 8));
					pixels[i][j][2] = filterOverflow(Math.round((float)summ_blue / 8));
					
				}
			} //end j 
		} // end i
		
		MessageImage resulted = new MessageImage(TaskType.IMAGE_SAVE, pixels, width, height);
		return resulted;
	}
	
	/**Method which replaces the value of a color if that value
	 * is greater than 255 
	 * 
	 * @param value The value to be checked if is greater than 255
	 * @return The value itself if it is not greater than 255 , and 255 
	 * if the value is greater than 255
	 */
	public int filterOverflow(int value ){
		if (value > 255){
			return 255;
		}else{
			return value ;
		}
	}
	
}
