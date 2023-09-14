package org.example;

import java.io.File;
import java.time.LocalDateTime;

public class FileProvider {
    private static final String FILE_NAME = "raport";
    private static final String FILE_EXTENSION = ".csv";

    public static File getFile(){
        String rootPath = System.getProperty("user.dir");
        return new File(rootPath + "/" + FILE_NAME + "-" + LocalDateTime.now().toString().replace(":", "") + FILE_EXTENSION);
    }
}
