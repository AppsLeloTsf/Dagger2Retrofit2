package com.molitics.molitician.ui.dashboard.voice.createVoice;

import android.content.Context;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.molitics.molitician.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AwsFileUploadUtils {

    public static TransferUtility getAwsConfig(Context context) {
        // KEY and SECRET
        BasicAWSCredentials credentials = new BasicAWSCredentials(context.getString(R.string.key), context.getString(R.string.access));
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setMaxErrorRetry(3);
        configuration.setConnectionTimeout(501000);
        configuration.setSocketTimeout(501000);
        configuration.setProtocol(Protocol.HTTP);
        AmazonS3Client s3Client = new AmazonS3Client(credentials);
        return TransferUtility.builder()
                .context(getApplicationContext())
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .s3Client(s3Client)
                .build();
    }
}
