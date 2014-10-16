package com.care.datatype;

/**
 * Created by AMIT on 7/10/14.
 */
public class Component
{
    private ComponentType type;
    private String path;
    private String className;

    public ComponentType getType()
    {
        return type;
    }

    public void setType(ComponentType type)
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

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }
}
