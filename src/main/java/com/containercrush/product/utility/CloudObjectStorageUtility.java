package com.containercrush.product.utility;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.SDKGlobalConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.auth.BasicAWSCredentials;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;
import com.ibm.cloud.objectstorage.services.s3.model.ListBucketsRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ListObjectsRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectListing;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;

public class CloudObjectStorageUtility {

	private static AmazonS3 _cosClient;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SDKGlobalConfiguration.IAM_ENDPOINT = "https://iam.cloud.ibm.com/identity/token";

		String bucketName = "gamification-cos-standard-tkq"; // eg my-unique-bucket-name
		String newBucketName = "<NEW_BUCKET_NAME>"; // eg my-other-unique-bucket-name
		String api_key = "_bAzHuCAN1yPz4Rcg5CZY1Tbp0UOpshuMhpoNkIvJAa3"; // eg
																			// "W00YiRnLW4k3fTjMB-oiB-2ySfTrFBIQQWanc--P3byk"
		String service_instance_id = "crn:v1:bluemix:public:iam-identity::a/693fe8ead49b44b192004113d21b15c2::serviceid:ServiceId-f6d85b01-d45a-4d92-831d-3e3efa44bb3c"; // eg
																																											// "crn:v1:bluemix:public:cloud-object-storage:global:a/3bf0d9003abfb5d29761c3e97696b71c:d6f04d83-6c4f-4a62-a165-696756d63903::"
		String resource_instance_id = "crn:v1:bluemix:public:cloud-object-storage:global:a/693fe8ead49b44b192004113d21b15c2:fce26086-5b77-42cc-b1aa-d388aa2853d7::";
		String endpoint_url =		 "https://s3.us-south.cloud-object-storage.appdomain.cloud"; 
		//String endpoint_url = "https://control.cloud-object-storage.cloud.ibm.com/v2/endpoints";
		String storageClass = "us-south-standard";
		String location = "us";

		System.out.println("Current time: " + new Timestamp(System.currentTimeMillis()).toString());
		//_cosClient = createClient(api_key, service_instance_id, endpoint_url, location);
		
		String key = "1004.jpg";
		getObject(key);
		//getObject ( bucketName, _cosClient, key) ;
		//listObjects(bucketName, _cosClient); 
		//createBucket(newBucketName, _cosClient,	storageClass);
		
		//listBuckets(_cosClient);
	}

	/**
	 * @param api_key
	 * @param service_instance_id
	 * @param endpoint_url
	 * @param location
	 * @return AmazonS3
	 */
	public static AmazonS3 createClient() {
		
		String bucketName = "gamification-cos-standard-tkq"; // eg my-unique-bucket-name
		String api_key = "_bAzHuCAN1yPz4Rcg5CZY1Tbp0UOpshuMhpoNkIvJAa3"; // eg
																			// "W00YiRnLW4k3fTjMB-oiB-2ySfTrFBIQQWanc--P3byk"
		String service_instance_id = "crn:v1:bluemix:public:iam-identity::a/693fe8ead49b44b192004113d21b15c2::serviceid:ServiceId-f6d85b01-d45a-4d92-831d-3e3efa44bb3c"; // eg
																																											// "crn:v1:bluemix:public:cloud-object-storage:global:a/3bf0d9003abfb5d29761c3e97696b71c:d6f04d83-6c4f-4a62-a165-696756d63903::"
		String endpoint_url =		 "https://s3.us-south.cloud-object-storage.appdomain.cloud"; 
		String storageClass = "us-south-standard";
		String location = "us";

		
		
		AWSCredentials credentials;
        credentials = new BasicIBMOAuthCredentials(api_key, service_instance_id);

        ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(5000);
        clientConfig.setUseTcpKeepAlive(true);

        AmazonS3 cosClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new EndpointConfiguration(endpoint_url, location)).withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfig).build();
        return cosClient;
	}

	/**
	 * @param bucketName
	 * @param cosClient
	 */
	public static void listObjects(String bucketName, AmazonS3 cosClient) {
		System.out.println("Listing objects in bucket " + bucketName);
        ObjectListing objectListing = cosClient.listObjects(new ListObjectsRequest().withBucketName(bucketName));
        
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
        }
        
        System.out.println(objectListing.getObjectSummaries().size());
	}
	
	
	public static void getObject (String key) {
		AmazonS3 cosClient = createClient();
		String bucketName = "gamification-cos-standard-tkq"; 
		InputStream in = cosClient.getObject(bucketName, key).getObjectContent();
		//byte [] image = (byte[]) cosClient.getObject(bucketName, key).getObjectContent();
		System.out.println("URL----="+cosClient.getUrl(bucketName, key));
		//System.out.println("---Image Object ---="+cosClient.getObject(bucketName, key));
	}

	/**
	 * @param bucketName
	 * @param cosClient
	 * @param storageClass
	 */
	public static void createBucket(String bucketName, AmazonS3 cosClient, String storageClass) {
		cosClient.createBucket(bucketName, storageClass);
	}

	/**
	 * @param cosClient
	 */
	public static void listBuckets(AmazonS3 cosClient) {
		System.out.println("Listing buckets");
		ListBucketsRequest request = new ListBucketsRequest();

		final List<Bucket> bucketList = cosClient.listBuckets();
		for (final Bucket bucket : bucketList) {
			System.out.println(bucket.getName());
		}

		System.out.println();
	}
}
