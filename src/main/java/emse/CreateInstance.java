package emse;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.InstanceType;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Tag;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;
import java.util.Scanner;

/**
 * Creates an EC2 instance
 */
public class CreateInstance
{
	public static String CreateInstanceEC2(Ec2Client ec2, String name, String ami_id) {
		RunInstancesRequest runRequest = RunInstancesRequest.builder()
                .imageId(ami_id)
                .instanceType(InstanceType.T2_MICRO)
                .maxCount(1)
                .minCount(1)
                .build();

        RunInstancesResponse response = ec2.runInstances(runRequest);
        String instanceId = response.instances().get(0).instanceId();

        Tag tag = Tag.builder()
                .key("Name")
                .value(name)
                .build();

        CreateTagsRequest tagRequest = CreateTagsRequest.builder()
                .resources(instanceId)
                .tags(tag)
                .build();

        try {
            ec2.createTags(tagRequest);
            System.out.printf(
                    "Successfully started EC2 Instance %s based on AMI %s",
                    instanceId, ami_id);

          return instanceId;

        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        return "";
	}
	
	public static String[] UserInput() {
		String[] inputs = new String[2];
		Scanner nameInput = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter name");
        inputs[0] = nameInput.nextLine();  // Read user input
        Scanner amiInput = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter ami_id");
        inputs[1] = amiInput.nextLine();  // Read user input
        System.out.println("name is: " + inputs[0]);
        System.out.println("ami_id is: " + inputs[1]); 
        nameInput.close();// Output user input
        amiInput.close();
        return inputs;
	}
	
	public static void startInstance(Ec2Client ec2, String instanceId) {

        StartInstancesRequest request = StartInstancesRequest.builder()
                .instanceIds(instanceId)
                .build();

        ec2.startInstances(request);
        System.out.printf("Successfully started instance %s", instanceId);
    }
	
	
	
    public static void main(String[] args)
    {
    	String[] inputs = UserInput();
    	
    	Ec2Client ec2 = Ec2Client.builder().build();
        CreateInstanceEC2(ec2, inputs[0], inputs[1]);
        Selection.main();
    }
}

// name : new instance
// ami_id : ami-0ed9277fb7eb570c9
