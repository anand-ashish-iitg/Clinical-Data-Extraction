package com.care.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMIT on 17/10/14.
 */
public class Helper
{
    /**
     * Gets all the file names
     * inside the folder
     *
     * @param folderPath
     * @return
     */
    public static List<String> GetFileNames(String folderPath)
    {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        List<String> fileNames = new ArrayList<String>();
        for (File file : listOfFiles)
        {
            fileNames.add(file.getName());
        }

        return fileNames;
    }
}
