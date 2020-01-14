package com.containercrush.product.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;

@Service
public class CloudObjectServiceImpl implements CloudObjectService{

	/**
	 * @param api_key
	 * @param service_instance_id
	 * @param endpoint_url
	 * @param location
	 * @return AmazonS3
	 */
	private  AmazonS3 createClient() {
		
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
	public InputStream getObject (String key) {
		AmazonS3 cosClient = createClient();
		String bucketName = "gamification-cos-standard-tkq"; 
		InputStream in = cosClient.getObject(bucketName, key).getObjectContent();
		//byte [] image = (byte[]) cosClient.getObject(bucketName, key).getObjectContent();
		//System.out.println("URL----="+cosClient.getUrl(bucketName, key));
		//System.out.println("---Image Object ---="+cosClient.getObject(bucketName, key));
		return in;
	}

}
