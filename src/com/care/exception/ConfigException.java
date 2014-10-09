package com.care.exception;

/**
 * Created by AMIT on 10/10/14.
 */
public class ConfigException extends Exception
{
	private String message = null;

	public ConfigException()
	{
		super();
	}

	public ConfigException(String message)
	{
		super(message);
		this.message = message;
	}

	public ConfigException(Throwable cause)
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
