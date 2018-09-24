package edu.servicebus.topic.sender;

import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.TopicClient;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;

public class TopicSender {

	public static void main(String[] args) {


		// Open the ConnectionStringBuilder of Topic
		ConnectionStringBuilder csb = new ConnectionStringBuilder("<Connection String>");
		
		// Clinet used to send Data into Azure Topics	
		TopicClient tc;

			try {
				// Initialize the Topic Client
				tc = new TopicClient(csb);
				
				// Create the Message
				Message m = new Message("Sample Data");
				
				// Send the Data Async - we can use batch for better results
				tc.sendAsync(m);
				
				// Close the Connection
				tc.closeAsync();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceBusException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				

	}
		
}
