/*
 *  Copyright (c) Microsoft. All rights reserved.
 *  Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.device.transport.amqps.exceptions;

import com.microsoft.azure.sdk.iot.device.exceptions.ProtocolException;

/**
 * This exception is thrown when a amqp:connection:forced error is encountered over an AMQP connection
 *
 <a href="http://docs.oasis-open.org/amqp/core/v1.0/os/amqp-core-complete-v1.0-os.pdf">For additional details, see this link</a>
 */
public class AmqpConnectionForcedException extends ProtocolException
{
    public static final String errorCode = "amqp:connection:forced";

    public AmqpConnectionForcedException()
    {
        super();
    }

    public AmqpConnectionForcedException(String message)
    {
        super(message);
    }

    public AmqpConnectionForcedException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public AmqpConnectionForcedException(Throwable cause)
    {
        super(cause);
    }

    @Override
    public boolean isRetryable() {
        return true;
    }
}
