package generator;

/**class which contains a static method for generating a 
 * unique ID for a Messages
 * 
 * @author cristof
 *
 */
public class Generator {

	private static int value =0;
	/**This method returns a unique Integer value for
	 * a message
	 * 
	 * @return The integer ID
	 */
	public static int generateInt(){
		return value++ ;
	}
	
}
