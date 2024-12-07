package com.youtube.uploader.utils;

public class FileUtils {
    public static String[] getFileDetails(String filename) {
        String[] fileArr = filename.split("\\.");
        String name = fileArr[0];
        String ext = "." + fileArr[fileArr.length - 1];
        return new String[] { name, ext };
    }
}
