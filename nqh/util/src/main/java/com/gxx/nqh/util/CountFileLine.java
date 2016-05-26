package com.gxx.nqh.util;

import java.io.*;
import java.util.LinkedList;

/**
 * 统计代码行数
 * Created by GXX on 2016/4/8.
 */
public class CountFileLine {
    public static void main(String[] args) throws IOException {
        String path;
        System.out.println("Please input the file path:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        path = bufferedReader.readLine();
        System.out.println("Please input the file type:");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] types = bufferedReader.readLine().split(" ");
        System.out.println(new CountFileLine().traverseFolder1(path, types));
    }


    //非递归
    public int traverseFolder1(String path, String[] types) throws IOException {
        int lineCount = 0;

        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    //System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else {
                    if (isType(file2.getAbsolutePath(), types)) {
                        System.out.println("文件:" + file2.getAbsolutePath());
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
                        while (bufferedReader.readLine() != null) {
                            lineCount++;
                        }
                    }
                    fileNum++;
                }
            }

            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;
                    } else {
                        if (isType(file2.getAbsolutePath(), types)) {
                            System.out.println("文件:" + file2.getAbsolutePath());
                            BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
                            while (bufferedReader.readLine() != null) {
                                lineCount++;
                            }
                        }
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
        return lineCount;
    }


    //递归调用
    public void traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    public boolean isType(String path, String[] types) {

        for (int i = 0; i < types.length; i++) {
            if (path.contains(types[i])) {
                return true;
            }
        }
        return false;
    }
}
