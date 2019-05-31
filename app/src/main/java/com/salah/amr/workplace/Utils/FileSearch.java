package com.salah.amr.workplace.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by User on 7/24/2017.
 */

public class FileSearch {

    /**
     * Search a directory and return a list of all **directories** contained inside
     *
     * @param directory
     * @return
     */
    public static ArrayList<String> getDirectoryPaths(String directory) {
        ArrayList<String> pathArray = new ArrayList<>();
        File file = new File(directory);

        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    pathArray.add(files[i].getAbsolutePath());
                }
            }
        }
        return pathArray;
    }

    /**
     * Search a directory and return a list of all **files** contained inside
     *
     * @param directory
     * @return
     */
    public static ArrayList<String> getFilePaths(String directory) {
        ArrayList<String> pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    pathArray.add(listFiles[i].getAbsolutePath());
                }
            }
        }
        return pathArray;
    }
}
