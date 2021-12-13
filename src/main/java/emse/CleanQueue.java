package emse;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.PurgeQueueRequest;

public class CleanQueue {
    public static void cleanQueue(SqsClient sqs, String queueUrl) {

        // Commenting so we ue url directely
        //GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder().queueName(queueName).build();

        PurgeQueueRequest queueRequest = PurgeQueueRequest.builder()
                .queueUrl(queueUrl)
                .build();

        sqs.purgeQueue(queueRequest);
    }

    public static void main (String[] args) {
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_WEST_1)
                .build();
        String name = RetrieveQueue.QueueName();
        cleanQueue(sqsClient, name);
        Selection.main();

    }
}
