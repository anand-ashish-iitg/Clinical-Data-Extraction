package com.care.exception;

/**
 * Created by AMIT on 10/10/14.
 */
public class PlatformException extends Exception
{
        private String message = null;

        public PlatformException()
        {
                super();
        }

        public PlatformException(String message)
        {
                super(message);
                this.message = message;
        }

        public PlatformException(Throwable cause)
        {
                super(cause);
        }

        @Override
        public String toString()
        {
                return message;
        }

        @Override
        public String getMessage()
        {
                return message;
        }
}
