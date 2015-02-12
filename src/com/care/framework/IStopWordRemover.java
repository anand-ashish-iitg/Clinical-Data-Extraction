package com.care.framework;

import java.util.List;

import com.care.exception.ComponentException;

public interface IStopWordRemover
{
    public List<String> RemoveStopWords(String data) throws ComponentException;

    public List<String> RemoveStopWords(List<String> data) throws ComponentException;
}
