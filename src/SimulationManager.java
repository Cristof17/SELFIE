import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import components.Component;

import messaging.Message;
import messaging.MessageCenter;


public class SimulationManager {
	private MessageCenter messageCenter;

	
	// The values of the ArrayList are introduced in the constructor
	private ArrayList<MessageCenter> message_center_array = new ArrayList<MessageCenter>();
	private MessageCenter current_message_center ;
	
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
		
		
		
		for(int i = 0 ; i < n ; i++){
			String linie = buff_read.readLine();
			sc = new Scanner(linie);
			MessageCenter center = getNewMessageCenter(sc.next());
			
			while(sc.hasNext()){
				center.stringFeatures.add(sc.next()); 
			}
			
			message_center_array.add(center);
			
		}

		return null;
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
	
	
	/**
	 * Reads the commands from stdin and uses the messageCenter to solve all the tasks
	 */
	public void start() {
		
		/* 
		 * Example of usage when the MessageCenter will be implemented *
		*/ 
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
		SimulationManager simulationManager = new SimulationManager(args[0]);
		simulationManager.start();
	}

}
