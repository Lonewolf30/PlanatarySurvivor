package com.lonewolf.PS;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class Configuration
{
    private HashMap<String, String> configs;

    private File file;

    public Configuration(String fileName)
    {
        configs = new HashMap<>();
        file = new File(fileName);
    }

    public String getValue(String index)
    {
        return configs.get(index);
    }

    public void loadConfigration()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null)
            {
                if (!line.startsWith("//") && !line.isEmpty())
                {
                    String[] splitString = line.split("=");
                    configs.put(splitString[0], splitString[1]);
                }
            }

            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveConfigs()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            Set<String> index = configs.keySet();
            for (String line: index)
            {
                writer.write(line + "=" + configs.get(line));
                writer.newLine();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
