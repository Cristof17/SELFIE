package components;

import messaging.Message;
import messaging.MessageImage;
import types.TaskType;

public class Blur extends Component{

	public Blur() {
		super(TaskType.BLUR);
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
		int [][][]pixels = messageImage.getPixels();
		
		/*
		 * If the number of pixels of the image is 1 then 
		 * the averege of the pixel values of the neighbors is 0
		 * (because the pixel has no neighboring pixels
		 */
		if(width == 1 && height == 1){
			pixels[0][0][0] = 0;
			pixels[0][0][1] = 0;
			pixels[0][0][2] = 0;
			
			MessageImage resulted = new MessageImage(TaskType.IMAGE_SAVE, pixels, width, height);
			return resulted;
		}
		//this auxiliary pixel matrix is needed because we need to modify the pixel values
		//and then transfer them to the original pixel matrix. 
		//Otherwise the value of some pixels will be calculated depending 
		//on the newly calculated values of the neighbors ; so we don't want that
		int[][][] aux = new int[messageImage.getHeight()][messageImage.getWidth()][3];
		
		//the blur effect will be applied 10 times
		for(int nr = 0 ; nr < 10  ; nr++){
		
		for(int i = 0 ; i < height ; i++){
			for(int j = 0 ; j < width ; j++){
				
				
				int summ_red = 0;
				int summ_green = 0;
				int summ_blue = 0;
				int vecini = 0;
				
				/*
				 * With these iteration blocks we go through 
				 * the values of the neighboring pixels of the 
				 * pixel situated at line i , and column j 
				 * in the pixels matrix .
				 * The neighbors are situated at one pixel distance
				 * from the current pixel so this why I use i-1 ; i+1; j-1 ; j+1
				 */
				for(int m = i-1 ; m<= i+1 ; m++){
					for(int n = j-1 ; n <= j+1 ;n++ ){
						//if the neighboring pixel exceeds the limits of the image
						//we should not take it into account
						if(m < 0 || m > height-1 )
							continue;
						if(n < 0 || n > width -1)
							continue;
						
						//a neighboring pixel cannot be the pixel itself
						if(m == i && n == j)
							continue;
						/*
						 * add the value of the red,green,blue values of a valid neighboring pixel
						 */
						summ_red += pixels[m][n][0];
						summ_green += pixels[m][n][1];
						summ_blue += pixels[m][n][2];
						vecini ++ ; //increase the number of neighboring pixels
						
						/*
						 * change the values of the three colors of a pixel according to 
						 * the sum of the neighbors calculated before
						 */
						aux[i][j][0] =filterOverflow( Math.round(((double)summ_red/(double)vecini)));
						aux[i][j][1] =filterOverflow(Math.round(((double)summ_green/(double)vecini)));
						aux[i][j][2] =filterOverflow(Math.round(((double)summ_blue/(double)vecini)));
						
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
		
		return messageImage;
	}
	
	/**Method which replaces the value of a color if that value
	 * is greater than 255 
	 * 
	 * @param value The value to be checked if is greater than 255
	 * @return The value itself if it is not greater than 255 , and 255 
	 * if the value is greater than 255
	 */
	public int filterOverflow(double value){
		if (value > 255){
			return 255;
		}else{
			return (int)value ;
		}
	}
	
}
