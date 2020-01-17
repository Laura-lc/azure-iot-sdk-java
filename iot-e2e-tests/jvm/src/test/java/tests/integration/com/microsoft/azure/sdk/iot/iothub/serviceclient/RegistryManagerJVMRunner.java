/*
 *  Copyright (c) Microsoft. All rights reserved.
 *  Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package tests.integration.com.microsoft.azure.sdk.iot.iothub.serviceclient;

import com.microsoft.azure.sdk.iot.common.helpers.TestConstants;
import com.microsoft.azure.sdk.iot.common.helpers.Tools;
import com.microsoft.azure.sdk.iot.common.tests.iothub.serviceclient.RegistryManagerTests;
import org.junit.BeforeClass;

import java.io.IOException;

public class RegistryManagerJVMRunner extends RegistryManagerTests
{
    @BeforeClass
    public static void setUp() throws IOException
    {
        RegistryManagerTests.setUp();
    }
}
