package com.care.framework;

import java.util.List;

import com.care.exception.ComponentException;

public interface ITokenizer
{
    public List<String> Tokenize(String data) throws ComponentException;

    public List<String> Tokenize(List<String> data) throws ComponentException;
}
