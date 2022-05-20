package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLoad {
    int num=0;
    public FileLoad() {
        num++;
    }
    public void gameSave(String s) throws IOException {
        File file = new File("save/game"+num+".txt");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(s);
        fileWriter.flush();
        fileWriter.close();
        }
    }
