package org.example;

import java.io.*;
import java.util.logging.Logger;

public class OtdelitCeliSBykvoi {
    static final String sortStr = "AZ";
    File file;
    File newFile;
    File fileToch;

    public void run() {
        //чистим файл
        ochistka();

        file = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZeliNew");
        newFile = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZeliNew2");
        fileToch = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZeliSBykvoi" + sortStr);
        FileWriter writer = null;
        FileWriter writer2 = null;
        BufferedReader reader = null;

        try {
            writer = new FileWriter(fileToch, true);
            writer2 = new FileWriter(newFile, true);
            String nZeli;
            String[] strArr;
            boolean sTochLiZel;
            int schetchik = 0;

            // Создаем BufferedReader с существующего FileReader для построчного считывания
            reader = new BufferedReader(new FileReader(file));
            // Считаем сначала первую строку
            String line = reader.readLine();
            while (line != null) {
                strArr = line.split(",");
                nZeli = strArr[0];
                sTochLiZel = getZelWithBykva(nZeli);
                // Если цель с буквой записываем в файл с цельбуквой иначе презаписываем старый
                if (sTochLiZel) {
                    writer.write(line + "\n");
                    schetchik++;
                } else {
                    writer2.write(line + "\n");
                }
                // Считываем остальные строки в цикле
                line = reader.readLine();
            }
            System.out.println("Перезаписано в файл " + schetchik + " целей с буквой -> " + sortStr);
            // Закрываем потоки перед удалением файлов
            closeStreamsQuietly(reader, writer2, writer);

            new FileRenameAndDelete().go();
//            file.delete();
//            newFile.renameTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //проверить на закрытость буферов доступа к файлу
    static void closeStreamsQuietly(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (Exception e) {
                Logger.getLogger(OtdelitCeliSBykvoi.class.getName()).info("Шляпа: " + e.getMessage());
            }
        }
    }

    static boolean getZelWithBykva(String str) {
        str = str.replaceAll("\\d", "");
        str = str.replace("\"", "");
        if (str.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    static void ochistka() {
        File fileToch = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZeliSBykvoi" + sortStr);
        try {
            new FileWriter(fileToch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}