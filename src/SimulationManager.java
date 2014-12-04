import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import messaging.Message;
import messaging.MessageCenter;
import messaging.MessageFlash;
import messaging.MessageImage;
import messaging.MessageLoad;
import messaging.MessageSave;
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
	private MessageCenter messageCenter;

	private String systemInString = "party.jpg selfie.jpg pre(flash=auto;zoom=100,100,300,400) photo(type=normal) post(black_white;blur)"+System.getProperty("line.separator")+ "exit";
	
	
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
		
		/* 
		 * Example of usage when the MessageCenter will be implemented *
		*/ 
		Scanner sc ;
		
		sc = new Scanner(systemInString);
		
		while(sc.hasNext()){
			
			String input_file = sc.next();
			String output_file = sc.next();
			
			/*
			 * Build MessageLoad using the input_file's name 
			 */
			Message messageLoad = new MessageLoad(TaskType.IMAGE_LOAD, "image_input.jpg");
			
			MessageImage image =  (MessageImage) this.messageCenter.publish(messageLoad);
			
			image.generateId();
			
			Message messageFlash = new MessageFlash(TaskType.FLASH, image.getPixels(), image.getWidth(), image.getHeight(), FlashType.AUTO);
			
			MessageImage messageImage = (MessageImage)this.messageCenter.publish(messageFlash);
			
			
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
				break;
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
	
	
	/**
	 * Main method
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Usage: java SimulationManager <network_config_file>");
			return;
		}
//		SimulationManager simulationManager = new SimulationManager(args[0]);
//		simulationManager.start();
		
		MessageLoad load = new MessageLoad(TaskType.IMAGE_LOAD, "image_input.jpg");
		ImageLoader loader = new  ImageLoader();
		
		Message imageMessage = loader.notify(load);
		
		Flash flash = new Flash() ;
		MessageFlash messageFlash = new MessageFlash(TaskType.FLASH, ((MessageImage)imageMessage).getPixels(), ((MessageImage)imageMessage).getWidth(),((MessageImage)imageMessage).getHeight(), FlashType.AUTO);
		Message afterFlash = flash.notify(messageFlash);
		
		RawPhoto raw = new RawPhoto();
		Message rawMessage = raw.notify(imageMessage);
		
		MessageImage messageImage = (MessageImage)rawMessage;
		MessageImage afterflashMessage = (MessageImage)afterFlash;
		MessageSave messageSaveFlash = new MessageSave(TaskType.IMAGE_SAVE, afterflashMessage.getPixels(), afterflashMessage.getWidth(), afterflashMessage.getHeight(), "result_flash.bmp");
		MessageSave messageSave = new MessageSave(TaskType.IMAGE_SAVE, messageImage.getPixels(), messageImage.getWidth(), messageImage.getHeight(), "result.bmp");
		ImageSaver saver = new ImageSaver();
		saver.notify(messageSave);
		saver.notify(messageSaveFlash);
		
		
		
	}

}
