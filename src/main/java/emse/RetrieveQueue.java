package emse;

import  software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RetrieveQueue {
	public static String RetrieveQueueURL(SqsClient sqsClient, String queueName) {
		
		        GetQueueUrlResponse getQueueUrlResponse =
		                sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build());

		        String queueUrl = getQueueUrlResponse.queueUrl();
		        return queueUrl;


	}


	
	public static String QueueName() {
		String name = null;
		Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter a valid queue name");
        name = input.nextLine();  // Read user input
        input.close();
        return name;
	}
	
	public static void main (String[] args) {
		SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_WEST_1)
                .build();
		String name = QueueName();
		System.out.println(RetrieveQueueURL(sqsClient, name));
		Selection.main();

	}
	
	
}
