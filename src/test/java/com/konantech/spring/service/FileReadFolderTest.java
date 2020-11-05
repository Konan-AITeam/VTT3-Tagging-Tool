package com.konantech.spring.service;

import org.junit.Test;

import java.io.File;
import java.io.IOException;


public class FileReadFolderTest {

    @Test
    public void FileReadFolderTest() throws Exception {
        showFileList("Z:/proxyshot/2018/04/30/624_1/");
    }

    public void showFileList(String path) throws Exception {
        File dir = new File(path);
        File[] files = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];

            if (file.isFile()) {
                System.out.println("[File]"+file.getCanonicalPath().toString());
            } else if (file.isDirectory()) {
                System.out.println("[Directory]"+file.getCanonicalPath().toString());
                try {
                    showFileList(file.getCanonicalPath().toString());
                } catch (Exception e) {
                }
            }
        }
    }
}
