package emse;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.QueueNameExistsException;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SendMessageQueue extends JFrame {
	public static void sendMessageQueue(SqsClient sqs, String queueName) {
		
		String message;
		String bucket = nameYourBucket();
		String path = getPath();
		File file = new File(path);
		String key = file.getName();
		
		message = bucket + " "+ key;
		CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(queueName)
                .build();
        CreateQueueResponse createResult = sqs.createQueue(createQueueRequest);
        GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
            .queueName(queueName)
            .build();

        String queueUrl = sqs.getQueueUrl(getQueueRequest).queueUrl();

        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody(message)
            .delaySeconds(5)
            .build();
        sqs.sendMessage(sendMsgRequest);
        System.exit(0);
	}
	
	public static String nameYourBucket() {
		String bucket = null;
		Scanner inputBucket = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter a valid bucket name");
        bucket = inputBucket.nextLine();  // Read user input
        inputBucket.close();
        
        return bucket;
	}
	
	public static String nameYourMessage() {
		String name = null;
		Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter a message");
        name = input.nextLine();  // Read user input
        
        return name;
	}
	
	public static String getPath() {
		  JFileChooser file = new JFileChooser("Select a file");
		  JFrame frame = new JFrame("Select a file"); 
		  frame.add(file);
		  frame.pack();
		  frame.setAlwaysOnTop(true);
	      frame.setVisible(false);
	      file.setAcceptAllFileFilterUsed(false);
	      FileNameExtensionFilter extFilter = new FileNameExtensionFilter("CSV Files", "csv", "xls");
	      file.addChoosableFileFilter(extFilter);
	      file.setMultiSelectionEnabled(true);
	      file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	      file.setFileHidingEnabled(false);
	      if (file.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
	         java.io.File f = file.getSelectedFile();
	         System.err.println(f.getPath());
	      }
	      java.io.File f = file.getSelectedFile();
		return (f.getPath());
	}
	
	public static void main (String[] args){
		
		String name = nameYourMessage();
		//String name = RetrieveQueue.QueueName();
		SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_WEST_1)
                .build();
		sendMessageQueue(sqsClient, name);
		Selection.main();

	}
}
