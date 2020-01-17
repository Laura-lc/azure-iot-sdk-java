/*
 *  Copyright (c) Microsoft. All rights reserved.
 *  Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package tests.integration.com.microsoft.azure.sdk.iot.iothub.serviceclient;

import com.microsoft.azure.sdk.iot.common.helpers.TestConstants;
import com.microsoft.azure.sdk.iot.common.helpers.Tools;
import com.microsoft.azure.sdk.iot.common.tests.iothub.serviceclient.ServiceClientTests;
import com.microsoft.azure.sdk.iot.service.IotHubServiceClientProtocol;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Collection;


@RunWith(Parameterized.class)
public class ServiceClientJVMRunner extends ServiceClientTests
{
    //This function is run before even the @BeforeClass annotation, so it is used as the @BeforeClass method
    @Parameterized.Parameters(name = "{0}")
    public static Collection inputsCommon() throws IOException
    {
        return ServiceClientTests.inputsCommon();
    }

    public ServiceClientJVMRunner(IotHubServiceClientProtocol protocol)
    {
        super(protocol);
    }
}
