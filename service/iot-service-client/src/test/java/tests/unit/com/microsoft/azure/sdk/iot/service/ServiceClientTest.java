/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package tests.unit.com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.service.*;
import com.microsoft.azure.sdk.iot.service.auth.IotHubServiceSasToken;
import com.microsoft.azure.sdk.iot.service.transport.amqps.AmqpSend;
import mockit.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ServiceClientTest
{
    @Mocked
    AmqpSend amqpSend;
    @Mocked
    IotHubServiceSasToken iotHubServiceSasToken;
    @Mocked
    FeedbackReceiver feedbackReceiver;

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_001: [The constructor shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createFromConnectionString_input_null() throws Exception
    {
        // Arrange
        String connectionString = null;
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        // Act
        ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_001: [The constructor shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createFromConnectionString_input_empty() throws Exception
    {
        // Arrange
        String connectionString = "";
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        // Act
        ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_002: [The constructor shall create IotHubConnectionString object using the IotHubConnectionStringBuilder]
    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_003: [The constructor shall create a new instance of ServiceClient using the created IotHubConnectionString object and the given iotHubServiceClientProtocol return with it]
    @Test
    public void createFromConnectionString_check_call_flow() throws Exception
    {
        // Arrange
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        new Expectations()
        {
            {
                IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createConnectionString(connectionString);
                Deencapsulation.newInstance(ServiceClient.class, iotHubConnectionString, iotHubServiceClientProtocol);
            }
        };
        // Act
        ServiceClient iotHubServiceClient = (ServiceClient) ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
        // Assert
        assertNotEquals(null, iotHubServiceClient);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_004: [The constructor shall throw IllegalArgumentException if the input object is null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void constructor_input_null() throws Exception
    {
        // Arrange
        IotHubConnectionString iotHubConnectionString = null;
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        // Act
        ServiceClient serviceClient = Deencapsulation.newInstance(ServiceClient.class, iotHubConnectionString, iotHubServiceClientProtocol); 
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_013: [The function shall call send() on the member AMQP sender object with the given parameters]
    @Test
    public void send_call_sender_close() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String hostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + "." + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createConnectionString(connectionString);
        String deviceId = "XXX";
        String content = "HELLO";
        Message iotMessage = new Message(content);
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        ServiceClient serviceClient = ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
        // Assert
        new Expectations()
        {
            {
                amqpSend.send(deviceId, null, iotMessage);
            }
        };
        serviceClient.open();

        // Act
        serviceClient.send(deviceId, iotMessage);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_28_002: [The function shall call send() on the member AMQP sender object with the given parameters]
    @Test
    public void send_module_call_sender_close() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String hostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + "." + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createConnectionString(connectionString);
        String deviceId = "XXX";
        String moduleId = "XXX";
        String content = "HELLO";
        Message iotMessage = new Message(content);
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        ServiceClient serviceClient = ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
        // Assert
        new Expectations()
        {
            {
                amqpSend.send(deviceId, moduleId, iotMessage);
            }
        };

        serviceClient.open();

        // Act
        serviceClient.send(deviceId, moduleId, iotMessage);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_014: [The function shall create an async wrapper around the open() function call, handle the return value or delegate exception]
    @Test
    public void open_async_future_return_ok() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String hostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + "." + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createConnectionString(connectionString);
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        ServiceClient serviceClient = ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
        // Assert
        new Expectations()
        {
            {
                serviceClient.open();
            }
        };
        // Act
        CompletableFuture<Void> completableFuture = serviceClient.openAsync();
        completableFuture.get();
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_014: [The function shall create an async wrapper around the open() function call, handle the return value or delegate exception]
    // Assert
    @Test (expected = Exception.class)
    public void open_async_future_throw() throws Exception
    {
        // Arrange
        new MockUp<ServiceClient>()
        {
            @Mock
            public void open() throws IOException
            {
                throw new IOException();
            }
        };
        String iotHubName = "IOTHUBNAME";
        String hostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + "." + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createConnectionString(connectionString);
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        ServiceClient serviceClient = ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
        // Act
        CompletableFuture<Void> completableFuture = serviceClient.openAsync();
        completableFuture.get();
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_015: [The function shall create an async wrapper around the close() function call, handle the return value or delegate exception]
    @Test
    public void close_async_future_return_ok() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String hostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + "." + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createConnectionString(connectionString);
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        ServiceClient serviceClient = ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
        serviceClient.open();

        // Assert
        new Expectations()
        {
            {
                serviceClient.close();
            }
        };

        // Act
        CompletableFuture<Void> completableFuture = serviceClient.closeAsync();
        completableFuture.get();
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_015: [The function shall create an async wrapper around the close() function call, handle the return value or delegate exception]
    // Assert
    @Test (expected = Exception.class)
    public void close_async_future_throw() throws Exception
    {
        // Arrange
        new MockUp<ServiceClient>()
        {
            @Mock
            public void close() throws IOException
            {
                throw new IOException();
            }
        };
        String iotHubName = "IOTHUBNAME";
        String hostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + "." + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        ServiceClient serviceClient = ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
        // Act
        CompletableFuture<Void> completableFuture = serviceClient.closeAsync();
        completableFuture.get();
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_016: [The function shall create an async wrapper around the send() function call, handle the return value or delegate exception]
    @Test
    public void send_async_future_return_ok() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String hostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + "." + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String deviceId = "XXX";
        String content = "HELLO";
        Message iotMessage = new Message(content);
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        ServiceClient serviceClient = ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
        serviceClient.open();

        // Assert
        new Expectations()
        {
            {
                amqpSend.send(deviceId, null, iotMessage);
                serviceClient.send(deviceId, iotMessage);
            }
        };

        // Act
        CompletableFuture<Void> completableFuture = serviceClient.sendAsync(deviceId, iotMessage);
        completableFuture.get();
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_016: [The function shall create an async wrapper around the send() function call, handle the return value or delegate exception]
    // Assert
    @Test (expected = Exception.class)
    public void send_async_future_throw() throws Exception
    {
        // Arrange
        new MockUp<ServiceClient>()
        {
            @Mock
            public void send(String deviceId, String message) throws IOException
            {
                throw new IOException();
            }
        };
        String iotHubName = "IOTHUBNAME";
        String hostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + "." + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String deviceId = "XXX";
        String content = "HELLO";
        Message iotMessage = new Message(content);
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        ServiceClient serviceClient = ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
        // Act
        CompletableFuture<Void> completableFuture = serviceClient.sendAsync(deviceId, iotMessage);
        completableFuture.get();
    }

    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_017: [The function shall create a FeedbackReceiver object and returns with it]
    @Test
    public void getFeedbackReceiver_good_case() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String hostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + "." + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String deviceId = "XXX";
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createConnectionString(connectionString);
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        ServiceClient serviceClient = ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
        // Assert
        new Expectations()
        {
            {
                feedbackReceiver = new FeedbackReceiver(anyString, anyString, anyString, iotHubServiceClientProtocol, deviceId);
            }
        };
        // Act
        FeedbackReceiver feedbackReceiver = serviceClient.getFeedbackReceiver(deviceId);
        // Assert
        assertNotEquals(null, feedbackReceiver);
    }
    
    // Tests_SRS_SERVICE_SDK_JAVA_SERVICECLIENT_12_017: [The function shall create a FeedbackReceiver object and returns with it]
    @Test
    public void getFeedbackReceiver_good_case_without_deviceid() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String hostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + "." + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubServiceClientProtocol iotHubServiceClientProtocol = IotHubServiceClientProtocol.AMQPS;
        ServiceClient serviceClient = ServiceClient.createFromConnectionString(connectionString, iotHubServiceClientProtocol);
        // Assert
        new Expectations()
        {
            {
                feedbackReceiver = new FeedbackReceiver(anyString, anyString, anyString, iotHubServiceClientProtocol);
            }
        };
        // Act
        FeedbackReceiver feedbackReceiver = serviceClient.getFeedbackReceiver();
        // Assert
        assertNotEquals(null, feedbackReceiver);
    }
}
