package com.care.datatype;

public class Input
{
	private InputType type;
	private String path;
	private ParseInputType parseType;

	public InputType getType()
	{
		return type;
	}

	public void setType(InputType type)
	{
		this.type = type;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public ParseInputType getParseType()
	{
		return parseType;
	}

	public void setParseType(ParseInputType parseType)
	{
		this.parseType = parseType;
	}
}
