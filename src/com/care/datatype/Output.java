package com.care.datatype;

/**
 * Created by AMIT on 7/10/14.
 */
public class Output
{
	private OutputType type;
	private String path;
	private GenerateOutputType generateType;

	public OutputType getType()
	{
		return type;
	}

	public void setType(OutputType type)
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

	public GenerateOutputType getGenerateType()
	{
		return generateType;
	}

	public void setGenerateType(GenerateOutputType generateType)
	{
		this.generateType = generateType;
	}
}
