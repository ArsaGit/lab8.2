package com.example.lab82.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageFileUtils implements MyUtil {

    /**
     * изображение - вывод размера изображения
     * @param path - путь к файлу
     */
    public static void printImageSize(String path) {
        Path p = Paths.get(path);
        try{
            System.out.println("Image size: " + Files.size(p) + " bytes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * изображение - вывод информации exif (можно воспользоваться библиотекой metadata-extractor)
     * @param path - путь к файлу
     */
    public static void printExif(String path) {
        File imgFile = new File(path);

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imgFile);

            System.out.println("EXIF data: ");
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.format("[%s] - %s = %s\n",
                            directory.getName(), tag.getTagName(), tag.getDescription());
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }
            }
        } catch (ImageProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * изображение - придумайте собственную функцию
     * @param path - путь к файлу
     */
    public static void printImageResolution(String path) {
        File imgFile = new File(path);
        String width = "", height = "";

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imgFile);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if(tag.getTagName().equals("Image Width"))
                        width = tag.getDescription();
                    if(tag.getTagName().equals("Image Height"))
                        height = tag.getDescription();
                }
            }

            System.out.println("Image resolution: " + width + "x" + height);
        } catch (ImageProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean isSupporting(String path) {
        if(path == null) return false;

        File file = new File(path);

        if(!file.exists()) return false;
        if(file.isFile() && FilenameUtils.getExtension(path).equals("png")) return true;

        return false;
    }
}
