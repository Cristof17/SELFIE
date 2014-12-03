package messaging;

import java.util.ArrayList;

import components.Component;

public abstract class MessageCenter {
	
	private String centerName;
	private ArrayList<Message> publishedMessages = new ArrayList<Message>();
	private ArrayList<Component> components ;
	private ArrayList<MessageCenter> neighbors ; 
		
	public MessageCenter(String centerName) {
		super();
		this.centerName = centerName;
	}

	public Message publish(Message message)	{
		System.out.println(centerName);
		return publishAlgorithm(message);
	}
	
	public void addComponent(Component component){
		this.components.add(component);
	}
	
	
	
	public void addNeighbor(MessageCenter center ){
		if(this.neighbors == null)
			this.neighbors = new ArrayList<MessageCenter>();
		this.neighbors.add(center);
	}
	
	
	
	/**Method which checks if the message has been here before
	 * to eliminate the possibility of Loops  
	 * 
	 * @param message The message to be checked
	 * @return Returns true if the message has been here before
	 */
	public boolean hasBeenHereBefore(Message message){
		if(this.publishedMessages.contains(message))
			return true ;
		else
			return false; 
	}
	
	public void addMessage(Message message){
		this.publishedMessages.add(message);
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
