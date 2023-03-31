package com.example.lab82.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class AudioFileUtils implements MyUtil {

    /**
     * mp3 - вывод названия трека из тегов (можно воспользоваться утилитой ffmpeg)
     * @param path - путь к файлу
     */
    public static void printTrackName(String path) {
        for (String tag : getTags(path)) {
            if(tag.contains("title"))
                System.out.println("Track title: " +
                        tag.split("=")[1].replaceAll("\"", ""));
        }
    }

    /**
     * mp3 - вывод длительности в секундах
     * @param path - путь к файлу
     */
    public static void printTrackLength(String path) {
        for (String tag : getTags(path)) {
            if(tag.contains("duration"))
                System.out.println("Track duration: " +
                        tag.split("=")[1].replaceAll("\"", "") + " seconds");
        }
    }

    /**
     * mp3 - придумайте собственную функцию
     * @param path - путь к файлу
     */
    public static void printArtistName(String path) {
        for (String tag : getTags(path)) {
            if(tag.contains("artist"))
                System.out.println("Artist name: " +
                        tag.split("=")[1].replaceAll("\"", ""));
        }
    }

    private static List<String> getTags(String path) {
        File file = new File(path);
        String ffmpegPath = "D:\\IdeaProjects\\lab8.2\\src\\main\\resources\\ffmpeg-6.0-essentials_build\\bin";

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe","/c","ffprobe -v error -of flat -show_format " + file.getAbsolutePath())
                .directory(new File(ffmpegPath));

        List<String> tagList = new ArrayList<>();

        try {
            Process process = processBuilder.start();
            BufferedReader buffer = (new BufferedReader(new InputStreamReader(process.getInputStream())));
            String tag = buffer.readLine();
            while (tag != null) {
                tagList.add(tag);
                tag = buffer.readLine();
            }
        } catch (Exception e) { e.printStackTrace(); }

        return tagList;
    }

    @Override
    public Boolean isSupporting(String path) {
        if(path == null) return false;

        File file = new File(path);

        if(!file.exists()) return false;
        if(file.isFile() && FilenameUtils.getExtension(path).equals("mp3")) return true;

        return false;
    }
}
