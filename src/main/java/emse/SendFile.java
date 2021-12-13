package emse;

import java.io.File;

import javax.swing.JFileChooser;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;


public class SendFile {
	public static void sendFileS3(S3Client s3, String bucketName, String path) {
		File file = new File(path);
		String key = file.getName();
		PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName).key(key)
                .build();

        s3.putObject(objectRequest, RequestBody.fromFile(file));
       // s3.putObject(bucketName,"Desktop/tests3.txt", new File("/Users/bruno/Desktop/tests3.txt");
        System.out.println("fim");
	
	}
	
	public static String getPath() {
		JFileChooser file = new JFileChooser();
	      file.setMultiSelectionEnabled(true);
	      file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	      file.setFileHidingEnabled(false);
	      if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	         java.io.File f = file.getSelectedFile();
	         System.err.println(f.getPath());
	      }
	      java.io.File f = file.getSelectedFile();
		return (f.getPath());
	}
	
	public static void main(String[] args) {
		String path = getPath();
		Region region = Region.US_WEST_1;
		S3Client s3 = S3Client.builder().region(region).build();
		String name = CreateBucket.nameYourBucket();
		
		sendFileS3(s3, name, path);
		Selection.main();
		
	}
}
