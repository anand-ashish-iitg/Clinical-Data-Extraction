package com.care.framework;

import com.care.exception.ComponentException;

import java.util.List;

public interface IPreProcessor
{
    public List<String> PreProcess(String data) throws ComponentException;

    public List<String> PreProcess(List<String> data) throws ComponentException;
}
