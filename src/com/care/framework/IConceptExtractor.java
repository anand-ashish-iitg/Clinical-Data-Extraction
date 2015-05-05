package com.care.framework;

import java.util.List;

import com.care.exception.ComponentException;

public interface IConceptExtractor
{
    public List<String> ExtractConcepts(String data) throws ComponentException;

    public List<String> ExtractConcepts(List<String> data) throws ComponentException;
}
