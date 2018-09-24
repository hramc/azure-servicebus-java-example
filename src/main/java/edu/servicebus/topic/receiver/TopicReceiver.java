package edu.servicebus.topic.receiver;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.microsoft.azure.servicebus.ExceptionPhase;
import com.microsoft.azure.servicebus.IMessage;
import com.microsoft.azure.servicebus.IMessageHandler;
import com.microsoft.azure.servicebus.MessageHandlerOptions;
import com.microsoft.azure.servicebus.ReceiveMode;
import com.microsoft.azure.servicebus.SubscriptionClient;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;

public class TopicReceiver {
	
	public static void main(String[] args) throws InterruptedException, ServiceBusException {
		
		// Open the ConnectionStringBuilder of Topic
		ConnectionStringBuilder csbSub = new ConnectionStringBuilder("<Connection String>"
				,"<Name of the Subscription>");
		// eg <Topic name>/Subscriptions/<Subscripton Name>
		
		// Initialize Subscription Client to read the Data
		SubscriptionClient sc = new SubscriptionClient(csbSub, ReceiveMode.RECEIVEANDDELETE);

		// Custom Method to read the data
		receiveSubscriptionMessage(sc);

	}
	
	// Method to register the listener to read the data from the subscription
	private static void receiveSubscriptionMessage(SubscriptionClient sc) throws ServiceBusException, InterruptedException {
        //Better Results
		sc.setPrefetchCount(100);
		
		// Register the listener to read data
        sc.registerMessageHandler(
                new IMessageHandler() {
                	
                	 public CompletableFuture<Void> onMessageAsync(IMessage message) {
                       	 
                	        System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
                	        return CompletableFuture.completedFuture(null);
                	    }

                	    public void notifyException(Throwable exception, ExceptionPhase phase) {
                	        System.out.println(phase + " encountered exception:" + exception.getMessage());
                	    }
                },
                // 1 concurrent call, messages are auto-completed, auto-renew duration
                new MessageHandlerOptions(1, false, Duration.ofSeconds(30),Duration.ofSeconds(30)));
		
        // Sleep to wait for reading to complete.
        TimeUnit.SECONDS.sleep(60);
        
        sc.close();
    }

}
