package emse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;

public class TurnOn {
	public static void TurnOn(Ec2Client ec2, String instance_id) {
		StartInstancesRequest request = StartInstancesRequest.builder()
                .instanceIds(instance_id)
                .build();

        ec2.startInstances(request);
        System.out.println("Successfully started instance" + instance_id);
		Selection.main();

	}
	
	public static String Instance_id() {
			String id = null;
			Scanner input = new Scanner(System.in);  // Create a Scanner object
	        System.out.println("Enter instance id");
	        id = input.nextLine();  // Read user input
	        input.close();
	        return id;
	}
	
	public static List<String> DisplayOff() {
		Ec2Client ec2 = Ec2Client.builder().build();
		boolean done = false;
	    String nextToken = null;
	    List<String> offInstances = new ArrayList<String>();
	    try {

	        do {
	            DescribeInstancesRequest request = DescribeInstancesRequest.builder().maxResults(6).nextToken(nextToken).build();
	            DescribeInstancesResponse response = ec2.describeInstances(request);

	            for (Reservation reservation : response.reservations()) {
	                for (Instance instance : reservation.instances()) {
	                    
	                    Integer code = instance.state().code();
	                    if (code != 16){
		                    String instance_id = instance.instanceId();
		                    offInstances.add(instance_id);
		                    System.out.println(instance_id);
	                    }

	            }
	        }
	            
	            nextToken = response.nextToken();
	        } while (nextToken != null);
	        

	    } catch (Ec2Exception e) {
	        System.err.println(e.awsErrorDetails().errorMessage());
	        System.exit(1);
	    	}
		return offInstances;

	}
	
	public static void main(String[] args)
	{
	
	}
	

}
