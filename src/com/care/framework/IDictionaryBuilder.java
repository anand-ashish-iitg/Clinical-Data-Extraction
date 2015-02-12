package com.care.framework;

import java.util.List;

import com.care.exception.ComponentException;

public interface IDictionaryBuilder
{
    public Object BuildDictionary(List<String> data, Object dictionary) throws ComponentException;
}
