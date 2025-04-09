package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PodgotovkaZOVKML {
    static String getStr(String[] arr) {
        String name = arr[0];
        long x = Long.parseLong(arr[1]);
        long y = Long.parseLong(arr[2]);
        x = x + 5300000;
        if (y > 50000) {
            y = y + 7300000;
        } else {
            y = y + 7400000;
        }
        return name + "," + x + "," + y;
    }

    public void run() {
        //перед началом удалить в папке программы файлы с именем начинающимся на Zov!!!!!!
        //в file передать название файла который будет подготавливатся
        System.out.println("Перед началом удалить в папке программы файлы с именем начинающимся на Zov(после ввести любойсимвол и нажать ENTER)!!!!!!");
        Scanner scanner = new Scanner(System.in);
        scanner.next();

        writeTXT("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + Main.nameFileNew);
        writeTXT("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + Main.nameFileAZ);
        writeTXT("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + Main.nameFileToch);

    }

    private void writeTXT(String strfile) {
        File file = new File(strfile);
        File file2 = new File(file + ".txt");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "windows-1251"));
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file2, true),"windows-1251");
            String line = reader.readLine();
            String strDlaZapisi;
            String[] strArr;
            while (line != null) {
                strArr = line.split(",");
                strDlaZapisi = getStr(strArr);
                writer.write(strDlaZapisi + "\n");
                line = reader.readLine();
            }
            System.out.println(strfile + " -> Перезаписано!!!");
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}