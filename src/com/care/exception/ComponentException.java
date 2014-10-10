package com.care.exception;

/**
 * Created by AMIT on 10/10/14.
 */
public class ComponentException extends Exception
{
        private String message = null;

        public ComponentException()
        {
                super();
        }

        public ComponentException(String message)
        {
                super(message);
                this.message = message;
        }

        public ComponentException(Throwable cause)
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
