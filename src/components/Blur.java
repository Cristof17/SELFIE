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
		
		if(width == 1 && height == 1){
			pixels[0][0][0] = 0;
			pixels[0][0][1] = 0;
			pixels[0][0][2] = 0;
			
			MessageImage resulted = new MessageImage(TaskType.IMAGE_SAVE, pixels, width, height);
			return resulted;
		}
		
		int[][][] aux = new int[messageImage.getHeight()][messageImage.getWidth()][3];
		
		for(int nr = 0 ; nr < 10  ; nr++){
		
		for(int i = 0 ; i < height ; i++){
			for(int j = 0 ; j < width ; j++){
				
				
				int summ_red = 0;
				int summ_green = 0;
				int summ_blue = 0;
				int vecini = 0;
				
				for(int m = i-1 ; m<= i+1 ; m++){
					for(int n = j-1 ; n <= j+1 ;n++ ){
						if(m < 0 || m > height-1 )
							continue;
						if(n < 0 || n > width -1)
							continue;
						if(m == i && n == j)
							continue;
						
						summ_red += pixels[m][n][0];
						summ_green += pixels[m][n][1];
						summ_blue += pixels[m][n][2];
						vecini ++ ;
						
						aux[i][j][0] =(int) Math.round(((double)summ_red/(double)vecini));
						aux[i][j][1] =(int) Math.round(((double)summ_green/(double)vecini));
						aux[i][j][2] =(int) Math.round(((double)summ_blue/(double)vecini));
						
					}	
				}
			} //end j 
		} // end i
		//copy the matrix
			for(int i = 0 ; i < height ; i++){
				for(int j = 0 ; j < width ; j++){
					for(int k = 0 ; k < 3 ; k++)
						pixels[i][j][k] = aux[i][j][k];
				}
			}
		}//end for 10
		
		MessageImage resulted = new MessageImage(TaskType.IMAGE_SAVE, aux, width, height);
		return resulted;
	}
	
	/**Method which replaces the value of a color if that value
	 * is greater than 255 
	 * 
	 * @param value The value to be checked if is greater than 255
	 * @return The value itself if it is not greater than 255 , and 255 
	 * if the value is greater than 255
	 */
	public int filterOverflow(float value){
		if (value > 255){
			return 255;
		}else{
			return (int)value ;
		}
	}
	
}
