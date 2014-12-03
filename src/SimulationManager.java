import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;

import messaging.Message;
import messaging.MessageCenter;
import messaging.MessageImage;
import messaging.MessageLoad;
import types.TaskType;


public class SimulationManager {
	private MessageCenter messageCenter;
	
	//ArrayList used to check if a string read from the configuration file
	//referes to a MessageCenter or a Component by calling contains() method
	// of the ArrayList object
	
	// The values of the ArrayList are introduced in the constructor
	private ArrayList<String> features = new ArrayList<String>();
	private ArrayList<MessageCenter> message_center_array = new ArrayList<MessageCenter>();
	private MessageCenter current_message_center ;
	
	public SimulationManager(String networkConfigFile) {
		try {
			
			
			features.add("Zoom");
			features.add("Flash");
			features.add("RawPhoto");
			features.add("NormalPhoto");
			features.add("Blur");
			features.add("BlackWhite");
			features.add("Sepia");
			features.add("ImageLoader");
			features.add("ImageSaver");
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
		//tokenizer allows me to read word by word 
		StreamTokenizer tokenizer = new StreamTokenizer(buff_read);
		
		
		int current = tokenizer.nextToken();
		//read the number of centers
		int n = (int)tokenizer.nval;
		
		tokenizer.nextToken();
		
		//read the first message center after
		// the number of message centers 
		
		current_message_center = new MessageCenter(tokenizer.sval) {
			
			@Override
			protected Message publishAlgorithm(Message message) {
				
				if(hasBeenHereBefore(message))
					return null;
				
				addMessage(message);
				Message received =  processMessage(message);
				
			return received ;
			}
		};
		
		for(int i = 0 ; i <= n ; ){
			//the String value that the tokenizer takes out from the current line
			String value = tokenizer.sval;
			
			if(features.contains(value)){
				System.out.println("Feature "+value);
				tokenizer.nextToken();
			}else{
				if(i == n)
					break;
				System.out.println("Center " + value);
				
				tokenizer.nextToken();
				i++;
			}
		}
		
		System.out.println("Current value "+ tokenizer.sval);
		
		
		
		return null;
	}
	
	
	/**
	 * Reads the commands from stdin and uses the messageCenter to solve all the tasks
	 */
	public void start() {
		
		/* 
		 * Example of usage when the MessageCenter will be implemented *
		*/ 
		MessageLoad load = new MessageLoad(TaskType.IMAGE_LOAD, "image_input.jpg");
		MessageImage image = (MessageImage)this.messageCenter.publish(load);
		
		image.generateId(); //pentru ca utilizam acelasi mesaj image trebuie sa-i generam un nou id
		
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
		SimulationManager simulationManager = new SimulationManager(args[0]);
		simulationManager.start();
	}

}
