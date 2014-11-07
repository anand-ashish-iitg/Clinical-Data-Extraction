package com.care.framework;

import com.care.exception.ComponentException;

import java.util.List;

/**
 * Created by AMIT on 7/11/14.
 */
public interface IDeIdentifier
{
    public List<String> DeIdentify(String data) throws ComponentException;

    public List<String> DeIdentify(List<String> data) throws ComponentException;
}
