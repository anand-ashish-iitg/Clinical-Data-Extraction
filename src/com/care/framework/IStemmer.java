package com.care.framework;

import java.util.List;

import com.care.exception.ComponentException;

public interface IStemmer
{
    public List<String> Stem(String data) throws ComponentException;

    public List<String> Stem(List<String> data) throws ComponentException;
}
