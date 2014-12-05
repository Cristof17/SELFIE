import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import point.Point;

import messaging.Message;
import messaging.MessageCenter;
import messaging.MessageFlash;
import messaging.MessageImage;
import messaging.MessageLoad;
import messaging.MessageSave;
import messaging.MessageZoom;
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

	
	// The values of the ArrayList are introduced in the constructor
	private ArrayList<MessageCenter> message_center_array = new ArrayList<MessageCenter>();
	
	public SimulationManager(String networkConfigFile) {
		try {
			
			this.messageCenter = buildNetwork(networkConfigFile);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
		
	public MessageCenter getNewMessageCenter(String name){
		return new MessageCenter(name) {
			
			@Override
			protected Message publishAlgorithm(Message message) {
				
				if(hasBeenHereBefore(message))
					return null;
				
				addMessage(message);
				Message received =  processMessage(message);
				
			return received ;
			}
		};
	}
	
	
	
	public MessageCenter getMessageCenterFromArray(String name){
		for(MessageCenter c : message_center_array){
			if(c.getCenterName().equals(name))
				return c; 
		}
	return null ;
	}
	
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
	 * Reads the commands from stdin and uses the messageCenter to solve all the tasks
	 */
	public void start() {
		
		sc_master = new Scanner(System.in);

		while(true){
		
		String inString = sc_master.nextLine();
			
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
			
			
			
			String preString = sc.next();
			
			StringTokenizer tokenizer = new StringTokenizer(preString, "()=,;");
			ArrayList<String> preTokens = new ArrayList<String>();
			
			while(tokenizer.hasMoreTokens()){
				preTokens.add(tokenizer.nextToken());
			}
			

			MessageZoom messageZoom = null;
			
			//search for flash value
			if(preTokens.contains("flash")){
				/*
				  create a flash object
				  after "flash" keyword follows the type of the flash
				  so we need to get the position of that value
				  
				*/
				String flashType = preTokens.get(preTokens.indexOf("flash") +1 );
				MessageFlash messageFlash = new MessageFlash(TaskType.FLASH, image.getPixels(), image.getWidth(), image.getHeight(), getFlashType(flashType));
				
				image = (MessageImage) this.messageCenter.publish(messageFlash);		
				image.generateId();
			}
			
			if(preTokens.contains("zoom")){
				int zoomPosition = preTokens.indexOf("zoom");
				
				String width_String_A = preTokens.get(zoomPosition + 1);
				String height_String_A = preTokens.get(zoomPosition + 2);
				String width_String_B = preTokens.get(zoomPosition + 3);
				String height_String_B = preTokens.get(zoomPosition + 4); 
				
				Point A = new Point(Integer.parseInt(width_String_A), Integer.parseInt(height_String_A));
				Point B = new Point(Integer.parseInt(width_String_B), Integer.parseInt(height_String_B));
				
				messageZoom = new MessageZoom(TaskType.ZOOM, image.getPixels(), A, B);
				
				image = (MessageImage) this.messageCenter.publish(messageZoom);
				image.generateId();
			}
			
			
			
			/*
			 * Parse the photo section of the command
			 * 
			 */
			String photoString = sc.next();
			tokenizer = new StringTokenizer(photoString,"()=");
			ArrayList<String> photoTokens = new ArrayList<String>();
			
			while(tokenizer.hasMoreTokens()){
				photoTokens.add(tokenizer.nextToken());
			}
			
			if(photoTokens.contains("type")){
				int typePosition = photoTokens.indexOf("type") ;
				
				image.setTaskType(getPhotoType(photoTokens.get(typePosition + 1 )));
				image = (MessageImage) this.messageCenter.publish(image);
				image.generateId();
				
//				if (getPhotoType(photoTokens.get(typePosition + 1)).equals(TaskType.RAW_PHOTO)){
//					
//					image.setTaskType(TaskType.RAW_PHOTO);
//					RawPhoto rawPhoto = new RawPhoto();
//					
//					image = (MessageImage) rawPhoto.notify(image);
//					image.generateId();
//					
//				}else if(getPhotoType(photoTokens.get(typePosition + 1 )).equals(TaskType.NORMAL_PHOTO)){
//					image.setTaskType(TaskType.RAW_PHOTO);
//					
//					RawPhoto rawPhoto = new RawPhoto() ;
//					image = (MessageImage) rawPhoto.notify(image);
//					
//					image.generateId();
//					
//					image.setTaskType(TaskType.NORMAL_PHOTO);
//					NormalPhoto normalPhoto = new NormalPhoto();
//					image = (MessageImage) normalPhoto.notify(image);
//					
//					image.generateId();
//					
//				}
			}
			
			
			
			/*
			 * Post substring from the whole line
			 */
			String postString = sc.next();
			tokenizer = new StringTokenizer(postString, "();");
			ArrayList<String> postTokens = new ArrayList<String>();
			
			while(tokenizer.hasMoreTokens()){
				postTokens.add(tokenizer.nextToken());
			}
			
			postTokens.remove("post");
			
			for(String s : postTokens){
				
				image.setTaskType(getPostType(s));
				image = (MessageImage) this.messageCenter.publish(image);
				image.generateId();
				
			}
			
			
			MessageSave messageSave = new MessageSave(TaskType.IMAGE_SAVE, image.getPixels(), image.getWidth(), image.getHeight(), second_word);
			this.messageCenter.publish(messageSave);
			/*
			 * I'm going to hardencode the values because I am wasting time
			 * figuring out the reading 
			 * 
			 */
			
//			String pre = sc.next();
//			String pre_delims = "\\(\\=\\;\\,pre";
//			String[] items = pre.split(pre_delims);
//			
//			if(sc.next().equals("exit"))
//				break;
				sc.close();
		}
		
//		MessageLoad load = new MessageLoad(TaskType.IMAGE_LOAD, "image_input.jpg");
//		MessageImage image = (MessageImage)this.messageCenter.publish(load);
//		
//		image.generateId(); //pentru ca utilizam acelasi mesaj image trebuie sa-i generam un nou id
		
//		MessageSave save = new MessageSave(TaskType.IMAGE_SAVE, 
//					image.getPixels(), image.getWidth(), image.getHeight(), 
//					"");
//		MessageSuccess success = (MessageSuccess)this.messageCenter.publish(save);
				
		
	}
	
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
		
		
//		int val =0 ;
//		int[][][] matrix = new int[5][5][3];
//		for(int i =0 ; i < 5 ; i++){
//			for(int j = 0 ; j < 5 ; j++){
//				for(int k = 0 ; k < 3 ; k++){
//					matrix[i][j][k] = val;
//				}
//				val ++ ;
//			}
//		}
//	
//		MessageZoom messageZoom = new MessageZoom(TaskType.ZOOM, matrix, new Point(2, 2), new Point(4, 4));
//		Zoom zoom = new Zoom();
//		zoom.notify(messageZoom);
		
		
//		MessageLoad load = new MessageLoad(TaskType.IMAGE_LOAD, "image_input.jpg");
//		ImageLoader loader = new  ImageLoader();
//		
//		Message imageMessage = loader.notify(load);
//		
//		Flash flash = new Flash() ;
//		MessageFlash messageFlash = new MessageFlash(TaskType.FLASH, ((MessageImage)imageMessage).getPixels(), ((MessageImage)imageMessage).getWidth(),((MessageImage)imageMessage).getHeight(), FlashType.AUTO);
//		Message afterFlash = flash.notify(messageFlash);
//		
//		RawPhoto raw = new RawPhoto();
//		Message rawMessage = raw.notify(imageMessage);
//		
//		MessageImage messageImage = (MessageImage)rawMessage;
//		MessageImage afterflashMessage = (MessageImage)afterFlash;
//		MessageSave messageSaveFlash = new MessageSave(TaskType.IMAGE_SAVE, afterflashMessage.getPixels(), afterflashMessage.getWidth(), afterflashMessage.getHeight(), "result_flash.bmp");
//		MessageSave messageSave = new MessageSave(TaskType.IMAGE_SAVE, messageImage.getPixels(), messageImage.getWidth(), messageImage.getHeight(), "result.bmp");
//		ImageSaver saver = new ImageSaver();
//		saver.notify(messageSave);
//		saver.notify(messageSaveFlash);
		
		
		
	}

}
