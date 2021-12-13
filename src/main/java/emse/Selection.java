package emse;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.*;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.sqs.SqsClient;

public class Selection extends JFrame {
	
	Selection() {
        //... Create the buttons.
        JRadioButton CreateButton   = new JRadioButton("Create an instance", true);
        JRadioButton offAllButton    = new JRadioButton("Turn Off all instances");
        JRadioButton offButton = new JRadioButton("Turn Off an instance");
        JRadioButton onAllButton = new JRadioButton("Turn On all instances");
        JRadioButton onButton = new JRadioButton("Turn On an instance");
        JRadioButton sendFile = new JRadioButton("Send File");
        JRadioButton sendMessageQueue = new JRadioButton("Send Message Queue");
        JRadioButton retrieve = new JRadioButton("Retrieve Message Queue");
        JRadioButton retrieveBuckets = new JRadioButton("Retrieve Buckets");
        JRadioButton deleteBucket = new JRadioButton("Delete Bucket");
        JRadioButton createBucket = new JRadioButton("Create Bucket");
        JRadioButton deleteFile = new JRadioButton("Delete File");
        JRadioButton downloadFile = new JRadioButton("Download File");
        JRadioButton deleteQueue = new JRadioButton("Delete Queue");
        JRadioButton cleanQueue = new JRadioButton("Clean Queue");

        JPanel pnlPreview = new JPanel();
        JButton okButton = new JButton("ok");

        //... Create a button group and add the buttons.
        ButtonGroup bgroup = new ButtonGroup();
        bgroup.add(CreateButton);
        bgroup.add(offAllButton);
        bgroup.add(offButton);
        bgroup.add(onAllButton);
        bgroup.add(onButton);
        bgroup.add(sendFile);
        bgroup.add(sendMessageQueue);
        bgroup.add(retrieve);
        bgroup.add(retrieveBuckets);
        bgroup.add(createBucket);
        bgroup.add(deleteBucket);
        bgroup.add(deleteFile);
        bgroup.add(downloadFile);
        bgroup.add(deleteQueue);
        bgroup.add(cleanQueue);
        bgroup.add(okButton);
        
        
        //... Arrange buttons vertically in a panel
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(7, 3));
        radioPanel.add(CreateButton);
        CreateButton.setActionCommand("Create");
        radioPanel.add(offAllButton);
        offAllButton.setActionCommand("Stopping all");
        radioPanel.add(offButton);
        offButton.setActionCommand("Stopping one");
        radioPanel.add(onAllButton);
        onAllButton.setActionCommand("Starting all");
        radioPanel.add(onButton);
        onButton.setActionCommand("Starting one");
        radioPanel.add(sendFile);
        sendFile.setActionCommand("Send File");
        radioPanel.add(sendMessageQueue);
        sendMessageQueue.setActionCommand("Send Message Queue");
        radioPanel.add(retrieve);
        retrieve.setActionCommand("Retrieve Message Queue");
        radioPanel.add(retrieveBuckets);
        retrieveBuckets.setActionCommand("Retrieve Buckets");
        radioPanel.add(deleteBucket);
        deleteBucket.setActionCommand("Delete Bucket");
        radioPanel.add(createBucket);
        createBucket.setActionCommand("Create Bucket");
        radioPanel.add(deleteFile);
        deleteFile.setActionCommand("Delete File");
        radioPanel.add(downloadFile);
        downloadFile.setActionCommand("Download File");
        radioPanel.add(deleteQueue);
        deleteQueue.setActionCommand("Delete Queue");
        radioPanel.add(cleanQueue);
        cleanQueue.setActionCommand("Clean Queue");

        okButton.setVisible(true);
        radioPanel.add(okButton);
        okButton.addActionListener(e ->
        {
        	String selection = bgroup.getSelection().getActionCommand();
        	System.out.println(bgroup.getSelection().getActionCommand());
        	dispose();

        	
        	switch(selection) {
        	case "Create":
        		CreateInstance.main(null);
        		break;
        	
        	case "Stopping all":
        		TurnOffAll.TurnOff();
        		break;
        		
        	case "Stopping one":
        		String id = TurnOff.Instance_id();
        		Ec2Client ec2 = Ec2Client.builder().build();
        		TurnOff.TurnOn(ec2, id);
        		break;
        	
        	case "Starting all":
        		TurnOnAll.TurnOn();
        		break;
        	
        	case "Starting one":
        		String idOn = TurnOn.Instance_id();
        		Ec2Client ec2On = Ec2Client.builder().build();
        		TurnOn.TurnOn(ec2On, idOn);
        		break;

        	case "Send File":
                String path = SendFile.getPath();
                Region region = Region.US_WEST_1;
                S3Client s3 = S3Client.builder().region(region).build();
                String name = CreateBucket.nameYourBucket();

                SendFile.sendFileS3(s3, name, path);
        		break;
            case "Send Message Queue":
                String name_msg = SendMessageQueue.nameYourMessage();
                //String name = RetrieveQueue.QueueName();
                SqsClient sqsClient = SqsClient.builder()
                        .region(Region.US_WEST_1)
                        .build();
                SendMessageQueue.sendMessageQueue(sqsClient, name_msg);
                break;

            case "Retrieve Message Queue":
                SqsClient sqsClientQueue = SqsClient.builder()
                        .region(Region.US_WEST_1)
                        .build();
                String queue_name = RetrieveQueue.QueueName();
                RetrieveQueue.RetrieveQueueURL(sqsClientQueue, queue_name);
                break;
            case "Retrieve Buckets":
                S3Client s3_retrieve = S3Client.builder().build(); //.region(region)

                ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder()
                        .build();
                ListBucketsResponse listBucketResponse = s3_retrieve.listBuckets(listBucketsRequest);
                listBucketResponse.buckets().stream()
                        .forEach(x -> System.out.println(x.name()));
                Selection.main();
                break;

            case "Create Bucket":
              Region regionS3 = Region.US_WEST_1;
              S3Client s3_create_bucket = S3Client.builder().region(regionS3).build();
              String name_bucket = CreateBucket.nameYourBucket();
              CreateBucket.CreateBucket(s3_create_bucket, name_bucket);
              break;

            case "Delete Bucket":
              String delete_bucket = null;
              Scanner inputDelete = new Scanner(System.in);  // Create a Scanner object
              System.out.println("Enter a valid bucket name to delete");
              delete_bucket = inputDelete.nextLine();  // Read user input
              inputDelete.close();
              Region regionS3delete = Region.US_WEST_1;

            S3Client s3_delete_bucket = S3Client.builder().region(regionS3delete).build();
              DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder()
                      .bucket(delete_bucket)
                      .build();
              s3_delete_bucket.deleteBucket(deleteBucketRequest);
              Selection.main();
            break;
            case "Delete File":

                      DeleteFile.main();
                      break;
            case "Download File":

                      DownloadFile.main();
                      break;
            case "Delete Queue":

                      DeleteQueue.main();
                      break;
            case "Clean Queue":

                      CleanQueue.main();
                      break;
            default:
                System.out.println("Error");
                break;
        	}
        	
        });

     
        
        
        //... Add a titled border to the button panel.
        radioPanel.setBorder(BorderFactory.createTitledBorder(
                   BorderFactory.createEtchedBorder(), "Action?"));

        //... Set window attributes.
        setContentPane(radioPanel);  // Button panel is only content.
        pack();                      // Layout window.
        
        
    }
	
	public static void main (String[]... args) {
		JFrame window = new Selection();
        window.setTitle("Lab2 : Task1 + Task2");  // But window is too small to show it!
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);  // Center window.
        window.setVisible(true);
        
	}
}
