package emse;
import java.util.Scanner;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;

public class TurnOff {
	public static void TurnOn(Ec2Client ec2, String instance_id) {
		StopInstancesRequest request = StopInstancesRequest.builder()
                .instanceIds(instance_id)
                .build();

        ec2.stopInstances(request);
        System.out.println("Successfully stoped instance" + instance_id);
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

}
