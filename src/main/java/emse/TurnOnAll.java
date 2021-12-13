package emse;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.InstanceType;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Tag;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;

public class TurnOnAll {
	public static void TurnOn() {
	Ec2Client ec2 = Ec2Client.builder().build();
	boolean done = false;
    String nextToken = null;

    try {

        do {
            DescribeInstancesRequest request = DescribeInstancesRequest.builder().maxResults(6).nextToken(nextToken).build();
            DescribeInstancesResponse response = ec2.describeInstances(request);

            for (Reservation reservation : response.reservations()) {
                for (Instance instance : reservation.instances()) {
                    
                    Integer code = instance.state().code();
                    if (code != 80){
	                    String instance_id = instance.instanceId();
	                    StopInstancesRequest request1 = StopInstancesRequest.builder()
	                            .instanceIds(instance_id)
	                            .build();

	                    ec2.stopInstances(request1);
	                    System.out.println("Successfully started instance " + instance_id);
                    }

            }
        }
            System.out.println("All instances are started ");
            nextToken = response.nextToken();
        } while (nextToken != null);

    } catch (Ec2Exception e) {
        System.err.println(e.awsErrorDetails().errorMessage());
        System.exit(1);
    	}
    Selection.main();

    }

	public static void main(String[] args)
	{
		TurnOn();
	}
}