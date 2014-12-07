import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import messaging.Message;
import messaging.MessageCenter;
import messaging.MessageFlash;
import messaging.MessageImage;
import messaging.MessageLoad;
import messaging.MessageSave;
import messaging.MessageZoom;
import point.Point;
import types.FlashType;
import types.TaskType;

import components.BlackWhite;
import components.Blur;
import components.Component;
import components.Flash;
import components.ImageLoader;
import components.ImageSaver;
import components.NormalPhoto;
import components.RawPhoto;
import components.Sepia;
import components.Zoom;


public class SimulationManager {
	private Scanner sc_master;
	private MessageCenter messageCenter;

	
	private ArrayList<MessageCenter> message_center_array = new ArrayList<MessageCenter>();
	
	public SimulationManager(String networkConfigFile) {
		try {
			
			this.messageCenter = buildNetwork(networkConfigFile);
			
		} catch (FileNotFoundException e) {
			System.err.println("File not found ");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Builds the network of message centers.
	 * @param networkConfigFile configuration file
	 * @return the first message center from the config file
	 * @throws IOException 
	 */
	private MessageCenter buildNetwork(String networkConfigFile) throws IOException {
		
		FileReader reader = new  FileReader(new File(networkConfigFile));
		BufferedReader buff_read = new BufferedReader(reader);
	
		
		String line = buff_read.readLine();
		Scanner sc = new Scanner(line);
		int n = sc.nextInt();
		
		
		/*
		 * Create the centers and put them into the arrayList
		 */
		for(int i = 0 ; i < n ; i++){
			String linie = buff_read.readLine();
			sc = new Scanner(linie);
			MessageCenter center = getNewMessageCenter(sc.next());
			
			while(sc.hasNext()){
				center.subscribe(getSpecificComponent(sc.next())); 
			}
			
			message_center_array.add(center);
			
		}

		/*
		 * Read the neighbors list and add them to the 
		 * right MessageCenters
		 */
		for(int i = 0 ; i < n ; i++){
			
			String linie = buff_read.readLine();
			sc.close();
			sc = new Scanner(linie);
			
			MessageCenter center = getMessageCenterFromArray(sc.next());
			
			while(sc.hasNext()){
				center.addNeighbor(getMessageCenterFromArray(sc.next()));
			}
		}
		
		reader.close();
		buff_read.close();
		sc.close();
		return message_center_array.get(0);
	}
	
	
	/**This method creates a new MessageCenter and returns it 
	 * with the right name .
	 * 
	 * 
	 * @param name The name of the MessageCenter
	 * @return A new MessageCenter . The object is created by calling "new" on 
	 * an anonymous class .
	 */
	public MessageCenter getNewMessageCenter(String name){
		return new MessageCenter(name) {
			
			@Override
			protected Message publishAlgorithm(Message message) {
				
				if(hasBeenHereBefore(message.getId()))
					return null;
				
				addMessage(message.getId());
				Message received =  processMessage(message);
				
			return received ;
			}
		};
	}
	
	
	/**This method returns a MessageCenter from the array
	 * based on its name
	 * 
	 * @param name The name of the MessageCenter to be searched through 
	 * the MessageCenters array 
	 * @return The MessageCenter that has the name given as a parameter
	 */
	public MessageCenter getMessageCenterFromArray(String name){
		for(MessageCenter c : message_center_array){
			if(c.getCenterName().equals(name))
				return c; 
		}
	return null ;
	}
	
	
	/**This method returns a new Component depending on the type of the component
	 * 
	 * @param type The type of the component needed ; This is given as a string from
	 * the input file
	 * @return The specific component based on its String type
	 */
	public Component getSpecificComponent(String type){
		
		switch (type) {
		case "Zoom":
			return new Zoom();
		case "Blur":
			return new Blur();
		case "BlackWhite":
			return new BlackWhite();
		case "Sepia":
			return new Sepia();
		case "RawPhoto":
			return new RawPhoto();
		case "Flash":
			return new Flash();
		case "ImageLoader":
			return new ImageLoader();
		case "ImageSaver":
			return new ImageSaver();
		case "NormalPhoto":
			return new NormalPhoto();
		default:
			return null;
		}
	}
	
	
	/**
	 * 
	 * Reads the commands from stdin and uses the messageCenter to solve all the tasks
	 */
	public void start() {
		
		//Scanner to go through the whole file
		sc_master = new Scanner(System.in);

		while(true){
		
		String inString = sc_master.nextLine();
		
		//Scanner to go through a line of the file . The line is given as a String by
		// the master scanner
		Scanner sc ;
		sc = new Scanner(inString);
		
			String first_word = sc.next();
			
			if(first_word.equals("exit")){
				sc_master.close();
				break;
			}
				
			
			String second_word = sc.next();
			
			/*
			 * Build MessageLoad using the input_file's name 
			 */
			Message messageLoad = new MessageLoad(TaskType.IMAGE_LOAD, first_word);
			MessageImage image =  (MessageImage) this.messageCenter.publish(messageLoad);
			
			image.generateId();
			
			/*
			 * String which holds the precapture substring of the line 
			 */
			String preString = sc.next(); 
			
			StringTokenizer tokenizer = new StringTokenizer(preString, "()=,;");
			ArrayList<String> preTokens = new ArrayList<String>(); //split the substring into its components
			
			while(tokenizer.hasMoreTokens()){
				preTokens.add(tokenizer.nextToken()); //generate components based of the string values
			}
			

			MessageZoom messageZoom = null;
			
			//search for flash value
			if(preTokens.contains("flash")){
				/*
				  create a flash object ;
				  after "flash" keyword follows the type of the flash
				  so we need to get the position of that value
				  
				*/
				String flashType = preTokens.get(preTokens.indexOf("flash") +1 ); //flash type
				
				//Create the message and send it 
				MessageFlash messageFlash = new MessageFlash(TaskType.FLASH, image.getPixels(), image.getWidth(), image.getHeight(), getFlashType(flashType));
				image = (MessageImage) this.messageCenter.publish(messageFlash);
				
				image.generateId();
			}
			
			
			/*
			 *Check to see if the substring from the precapture substring contains
			 *the "zoom" keyword in order to create a Zoom Component after 
			 */
			if(preTokens.contains("zoom")){
				/*
				 * if the "zoom" keyword exists 
				 * get its position in the tokens array so that 
				 * we can create Point Objects containing the coordinates
				 */
				int zoomPosition = preTokens.indexOf("zoom");
				
				String width_String_A = preTokens.get(zoomPosition + 1);
				String height_String_A = preTokens.get(zoomPosition + 2);
				String width_String_B = preTokens.get(zoomPosition + 3);
				String height_String_B = preTokens.get(zoomPosition + 4); 
				
				/*
				 * Two objects that contain the values of the two corners
				 * A - upper - left corner
				 * B - lower - right corner
				 */
				Point A = new Point(Integer.parseInt(width_String_A), Integer.parseInt(height_String_A));
				Point B = new Point(Integer.parseInt(width_String_B), Integer.parseInt(height_String_B));
				
				//Create the message and send it 
				messageZoom = new MessageZoom(TaskType.ZOOM, image.getPixels(), A, B);
				image = (MessageImage) this.messageCenter.publish(messageZoom);
				image.generateId();
				
			}
			
			
			
			/*
			 * Parse the Capture Phase substring of the line .
			 * 
			 */
			String photoString = sc.next();
			tokenizer = new StringTokenizer(photoString,"()=");
			//ArrayList holding the tokens of the substring
			ArrayList<String> photoTokens = new ArrayList<String>();
			
			while(tokenizer.hasMoreTokens()){
				//add the tokens retrieved in the ArrayList
				photoTokens.add(tokenizer.nextToken());
			}
			
			if(photoTokens.contains("type")){
				/*get the position in the array of type word
				 * in order to get the next token which can be "raw" or "normal" 
				 */
				int typePosition = photoTokens.indexOf("type") ;
				
				TaskType task = getPhotoType(photoTokens.get(typePosition + 1 ));
				
				if(task.equals(TaskType.RAW_PHOTO)){
					
					//send the message to the MessageCenter
					image.setTaskType(TaskType.RAW_PHOTO);
					image = (MessageImage) this.messageCenter.publish(image);
					
					image.generateId();
				}else if(task.equals(TaskType.NORMAL_PHOTO)){
					
					/*
					 * If the photo needs to be normal we first need to 
					 * apply rotate on it in order to get to the normal position
					 */
					image.setTaskType(TaskType.RAW_PHOTO);
					image = (MessageImage) this.messageCenter.publish(image);
					
					image.generateId();
					image.setTaskType(TaskType.NORMAL_PHOTO);
					
					image = (MessageImage) this.messageCenter.publish(image);
					
					image.generateId();
				}
	
			}
			
			
			
			/*
			 * Parse the PostCapture Phase substring of the line
			 */
			String postString = sc.next();
			tokenizer = new StringTokenizer(postString, "();");
			/*
			 * ArrayList which holds the tokens of the substring 
			 */
			ArrayList<String> postTokens = new ArrayList<String>();
			
			while(tokenizer.hasMoreTokens()){
				//add the tokens to the array
				postTokens.add(tokenizer.nextToken());
			}
			
			//remove the "post" keyword from the tokens array
			//because there is no TaskType available for this keyword
			postTokens.remove("post");
			
			for(String s : postTokens){
				
				//set the MessageImages specific TaskType
				//and send the message to the MessageCenter
				image.setTaskType(getPostType(s));
				image = (MessageImage) this.messageCenter.publish(image);
				
				image.generateId();
				
			}
			
			/*
			 * Create a MessageSave object in order for the image to be saved
			 */
			MessageSave messageSave = new MessageSave(TaskType.IMAGE_SAVE, image.getPixels(), image.getWidth(), image.getHeight(), second_word);
			this.messageCenter.publish(messageSave);
			
			sc.close();
		}		
	}
	
	
	/**This method creates a TaskType which messages will use in their
	 * constructor depending on the String value given as parameter
	 * 
	 * @param value The String value of the TaskType
	 * @return Returns the TaskType object specific for the given String value
	 */
	public TaskType getPostType(String value){
		switch (value) {
		case "sepia":
			return TaskType.SEPIA;
		case "black_white":
			return TaskType.BLACK_WHITE;
		case "blur":
			return TaskType.BLUR;
		default:
			return null;
		}
	}
	
	/**This message returns the photo type that is need 
	 * in order to process the image .This can be RAW or NORMAL
	 * 
	 * @param value	The String name of the photo type 
	 * @return The TaskType object specific for the given String type
	 */
	public TaskType getPhotoType(String value){
		switch (value) {
		case "normal":
			return TaskType.NORMAL_PHOTO; 
			
		case "raw":
			return TaskType.RAW_PHOTO;
			
		default:
			return null ;
		}
	}
	
	/**This method returns the FlashType depending on the input type
	 * 
	 * @param stringValue The String name of the FlashType that is needed
	 * @return Returns the FlashType object depending on the given name
	 */
	public FlashType getFlashType(String stringValue){
		switch (stringValue) {
		case "on":
			return FlashType.ON;
		case "off":
			return FlashType.OFF;
		case "auto":
			return FlashType.AUTO;

		default:
			return null; 
		}
	}
	
	/**
	 * Main method
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Usage: java SimulationManager <network_config_file>");
			return;
		}
		
		
		SimulationManager simulationManager = new SimulationManager(args[0]);
		simulationManager.start();
		
	}

}
