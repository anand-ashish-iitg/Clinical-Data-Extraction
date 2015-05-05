package com.care.exception;

/**
 * Created by AMIT on 10/10/14.
 */
public class InputOutputException extends Exception
{
    private String message = null;

    public InputOutputException()
    {
        super();
    }

    public InputOutputException(String message)
    {
        super(message);
        this.message = message;
    }

    public InputOutputException(Throwable cause)
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
