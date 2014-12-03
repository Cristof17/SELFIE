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
	
	public void processMessage(Message){
		if(isFitForMe(message)){
			
		}
	}
	
	
	
	
	protected abstract Message publishAlgorithm(Message message);
}
