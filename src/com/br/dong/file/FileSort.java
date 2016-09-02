package com.br.dong.file;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * PACKAGE_NAME:com.br.dong.file
 * AUTHOR: hexOr
 * DATE :2016-08-11 15:47
 * DESCRIPTION:排序文件
 */
public class FileSort {
    public static void main(String[] args) {
        File file = new File("d:/");
        File [] fs = file.listFiles();
        Arrays.sort(fs, new CompratorByLastModified());
        for (int i = 0; i < fs.length; i++) {
            System.out.println(new Date(fs[i].lastModified()).toLocaleString());
        }
    }
    static class CompratorByLastModified implements Comparator<File>
    {
        public int compare(File f1, File f2) {
            long diff = f1.lastModified()-f2.lastModified();
            if(diff>0)
                return -1;
            else if(diff==0)
                return 0;
            else
                return 1;
        }
        public boolean equals(Object obj){
            return true;
        }
    }
}
