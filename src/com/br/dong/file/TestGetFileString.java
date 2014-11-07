package com.br.dong.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-6
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class TestGetFileString {
    public static void main(String[] args) {
        File file = new File("your file path.");

        try {
            String content = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
