package messaging;

import java.util.ArrayList;

import components.Component;

public abstract class MessageCenter {
	
	private String centerName;
	private ArrayList<Integer> publishedMessages = new ArrayList<Integer>();
	private ArrayList<Component> components = new ArrayList<Component>() ;
	private ArrayList<MessageCenter> neighbors ; 
		
	public MessageCenter(String centerName) {
		super();
		this.centerName = centerName;
	}

	/**Method called from another class in order for the message 
	 * transmitted as a parameter to be processed
	 * 
	 * @param message The message to be processed
	 * @return The message processed
	 */
	public Message publish(Message message)	{
		System.out.print(centerName+"\r\n");
		return publishAlgorithm(message);
	}
	
	/**
	 * This method returns the center name of this Message Center
	 * @return Returns the name of the Message Center
	 */
	public String getCenterName(){
		return this.centerName;
	}
	
	/**
	 * This method adds a Component to this MessageCenter
	 * @param component The Component to be added
	 */
	public void subscribe(Component component){
		this.components.add(component);
	}
	
	/**
	 * This method removes a Component from the MessageCenter
	 * @param component The Component to be removed
	 */
	public void unsubscribe(Component component){
		if(this.components.contains(component))
			components.remove(component);
	}
	
	
	/**This method adds the Message given as parameter 
	 * to the neighbors list
	 * 
	 * @param center The MessageCenter to be added to the neighbors list
	 */
	public void addNeighbor(MessageCenter center ){
		if(this.neighbors == null)
			this.neighbors = new ArrayList<MessageCenter>();
		this.neighbors.add(center);
	}
	
	
	
	/**Method which checks if the message has been here before
	 * to eliminate the possibility of Loops  
	 * 
	 * @param messageID The message to be checked
	 * @return Returns true if the message has been here before
	 */
	public boolean hasBeenHereBefore(Integer messageID){
		if(this.publishedMessages.contains(messageID))
			return true ;
		else
			return false; 
	}
	
	/**This method adds the ID of a Message to the 
	 * array of the Messages that got through this 
	 * MessageCenter
	 * 
	 * @param value The ID of the Message that needs to 
	 * go through this MessageCenter
	 */
	public void addMessage(Integer value){
		this.publishedMessages.add(value);
	}
	
	
	
	/**Method to see if a Message can be processed by this MessageCenter
	 * 
	 * @param message The message to be checked
	 * @return Returns true if this MessageCenter can handle the message meaning if any component contained int this class can handle the message
	 */
	public boolean isFitForMe(Message message){
		for(Component c : components ){
			if(c.getTaskType().equals(message.getTaskType()))
				return true ;
		}
		return false;
	}
	
	/**
	 * Method which finds the right Component to handle the Message
	 * @param message The message to be processed
	 * @return Returns the component that can handle the Message received as parameter
	 */
	public Component getTheComponentFitForTheMessage(Message message){
		for(Component c : components){
			if(c.getTaskType().equals(message.getTaskType())){
				return c ;
			}
		}
	return null;
	}
	
	/**
	 * Method which processes the message received and returning the message processed by 
	 * the right Component
	 * @param message The message to be processed
	 * @return Returns the message that has been processed or null if there was no component
	 * that could have processed the message received as argument
	 */
	public Message processMessage(Message message){
		
		if(isFitForMe(message)){ //if the components in this class can handle the message
			Component component = getTheComponentFitForTheMessage(message); //get the one that can handle the message
			Message received = component.notify(message); //get the Message returned by calling the notify method for that component
			return  received; 
		}else{
			if(neighbors == null)
				return null ;
			
			for(MessageCenter m : neighbors){  //send the message to all neighbors
				Message received = m.publish(message); // get the message from the neighbor 
				if(received == null) // this neighbor could not help me
					continue; // move to the next neighbor
				else
					return received ; //return the message thet a neighbor could provide
			}
		}
	return null ;
	}
	
	
	protected abstract Message publishAlgorithm(Message message);
}
