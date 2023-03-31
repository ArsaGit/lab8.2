package com.example.lab82.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirectoryUtils implements MyUtil {

    /**
     * каталог - вывод списка файлов в каталоге
     * @param path - путь к файлу
     */
    public static void printFilesCount(String path) {
        System.out.println("Files count: " + new File(path).list().length);
    }

    /**
     * каталог - подсчет размера всех файлов в каталоге
     * @param path - путь к файлу
     */
    public static void printFilesSize(String path) {
        File folder = new File(path);
        long folderSize = folderSize(folder);
        System.out.println(
                "Folder size: " + folderSize + " B = " +
                        folderSize / 1024 + " KB = " +
                        folderSize / (1024 * 1024) + " MB");
    }

    private static long folderSize(File folder) {
        long length = 0;
        for (File file : folder.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        return length;
    }

    /**
     * каталог - придумайте собственную функцию
     * @param path - путь к файлу
     */
    public static void printFolderFiles(String path) {
        File folder = new File(path);

        System.out.println("Files in folder:");
//        for (File fileEntry : folder.listFiles()) {
//            if (fileEntry.isDirectory()) {
//                printFolderFiles(fileEntry.getAbsolutePath());
//            } else {
//                System.out.println(fileEntry.getName());
//            }
//        }
        for(String s : new File(path).list())
            System.out.println(s);
    }

    @Override
    public Boolean isSupporting(String path) {
        if(path == null) return false;

        File file = new File(path);
        if(file.isDirectory()) return true;

        return false;
    }
}
