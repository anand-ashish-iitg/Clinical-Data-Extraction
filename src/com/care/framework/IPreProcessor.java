package com.care.framework;

import java.util.List;

import com.care.exception.ComponentException;

public interface IPreProcessor
{
    public List<String> PreProcess(String data) throws ComponentException;

    public List<String> PreProcess(List<String> data) throws ComponentException;
}
