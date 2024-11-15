package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

public class FileRenameAndDelete {
    public void go() {
        Path targetFile = Paths.get("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZeliNew2");
        Path backupFile = Paths.get("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZeliNew");
        while (Files.exists(targetFile)) {
            // Проверяем наличие файла "ZeliNew2.txt"
            if (Files.exists(targetFile)) {
                // Удаляем файл "ZeliNew.txt"
                if (Files.exists(backupFile)) {
                    try {
                        Files.delete(backupFile);
                    } catch (IOException e) {
                        Logger.getLogger(FileRenameAndDelete.class.getName()).info("Шляпа: " + e.getMessage());
                    }
                }

                // Переименовываем файл "ZeliNew2.txt" в "ZeliNew.txt"
                try {
                    Files.move(targetFile, backupFile, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    Logger.getLogger(FileRenameAndDelete.class.getName()).info("Шляпа: " + e.getMessage());
                }
            }
        }
    }
}