package com.care.datatype;

/**
 * Created by AMIT on 7/10/14.
 */
public class Component
{
    private ComponentLoadType loadType;
    private ComponentType type;
    private String path;
    private String className;
    private String dependencyPath;

    public ComponentLoadType getLoadType()
    {
        return loadType;
    }

    public void setLoadType(ComponentLoadType loadType)
    {
        this.loadType = loadType;
    }

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

    public String getDependencyPath()
    {
        return dependencyPath;
    }

    public void setDependencyPath(String dependencyPath)
    {
        this.dependencyPath = dependencyPath;
    }
}
