package com.care.framework;

import java.util.List;

public interface IPreProcessor
{
    public List<String> PreProcess(String data);

    public List<String> PreProcess(List<String> data);
}
