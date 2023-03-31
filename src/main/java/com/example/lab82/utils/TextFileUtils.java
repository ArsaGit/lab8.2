package com.example.lab82.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TextFileUtils implements MyUtil {

    /**
     * текстовый файл - подсчет и вывод количества строк
     * @param path - путь к файлу
     */
    public static void printRowsCount(String path) {
        List<String> rows = readLines(path);
        int rowsCount = rows.size();
        System.out.println("Rows count: " + rowsCount);
    }

    /**
     * текстовый файл - вывод частоты вхождения каждого символа
     * @param path - путь к файлу
     */
    public static void printCharacterFrequency(String path) {
        List<String> rows = readLines(path);
        String unifiedString = String.join("", rows);

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < unifiedString.length(); i++) {
            char c = unifiedString.charAt(i);
            Integer val = map.get(c);
            if (val != null) {
                map.put(c, val + 1);
            }
            else {
                map.put(c, 1);
            }
        }

        System.out.println("Character Frequency:");
        for(Map.Entry<Character, Integer> entry : map.entrySet()) {
            System.out.println("Char: " + entry.getKey() + ", Count: " + entry.getValue());
        }
    }

    /**
     * текстовый файл - придумайте собственную функцию
     * просто длина всех строк
     * @param path - путь к файлу
     */
    public static void printCharacterCount(String path) {
        List<String> rows = readLines(path);
        String unifiedString = String.join("", rows);

        System.out.println("File length: " + unifiedString.length());
    }

    private static List<String> readLines(String path) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(path));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    @Override
    public Boolean isSupporting(String path) {
        if(path == null) return false;

        File file = new File(path);

        if(!file.exists()) return false;
        if(file.isFile() && FilenameUtils.getExtension(path).equals("txt")) return true;

        return false;
    }
}
