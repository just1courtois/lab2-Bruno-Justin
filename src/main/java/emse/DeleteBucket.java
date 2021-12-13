package emse;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.Scanner;

public class DeleteBucket {
    public static void deleteBucket(S3Client s3, String bucketName) {

        try {
            DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build();
            s3.deleteBucket(deleteBucketRequest);
            s3.close();
        }
        catch(S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

    }

    public static void main (String[] args) {
        System.out.println("Please ensure your bucket is empty to delete it.");
        Region region = Region.US_WEST_1;
        S3Client s3 = S3Client.builder().region(region).build();
        String bucket = null;
        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter a valid bucket name");
        bucket = input.nextLine();  // Read user input
        input.close();
        deleteBucket(s3, bucket);
        Selection.main();

    }

}
