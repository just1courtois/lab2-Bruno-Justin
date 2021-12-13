package emse;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.util.Scanner;

public class DeleteFile {
    public static void deleteFile(S3Client s3, String bucketName, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3.deleteObject(deleteObjectRequest);
    }

    public static void main (String[]... args) {
        Region region = Region.US_WEST_1;
        S3Client s3 = S3Client.builder().region(region).build();
        String key = null;
        String bucket = null;
        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter a valid file name");
        key = input.nextLine();  // Read user input
        System.out.println("Enter a valid bucket name");
        bucket = input.nextLine();  // Read user input
        input.close();
        deleteFile(s3, bucket, key);
        Selection.main();

    }

}
