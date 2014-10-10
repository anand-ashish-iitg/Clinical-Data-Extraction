package com.care.exception;

/**
 * Created by AMIT on 10/10/14.
 */
public class IOException extends Exception
{
        private String message = null;

        public IOException()
        {
                super();
        }

        public IOException(String message)
        {
                super(message);
                this.message = message;
        }

        public IOException(Throwable cause)
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
