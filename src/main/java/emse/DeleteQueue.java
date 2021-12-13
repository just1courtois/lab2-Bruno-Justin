package emse;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;
import java.util.Scanner;

public class DeleteQueue {


    public static void deleteQueue(SqsClient sqs, String queueName){

        try {

            GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                    .queueName(queueName)
                    .build();

            String queueUrl = sqs.getQueueUrl(getQueueRequest).queueUrl();

            DeleteQueueRequest deleteQueueRequest = DeleteQueueRequest.builder()
                    .queueUrl(queueUrl)
                    .build();

            sqs.deleteQueue(deleteQueueRequest);

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static void main (String[]... args) {
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_WEST_1)
                .build();
        String name = null;
        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter a valid queue name");
        name = input.nextLine();  // Read user input
        input.close();
        deleteQueue(sqsClient, name);
        Selection.main();
    }
}
