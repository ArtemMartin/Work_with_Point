package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.example.OtdelitCeliSBykvoi.closeStreamsQuietly;

public class SotrFilePoPoint {
    public  void run() {
        //чистим файл с точками
        ochistka();

        File file = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZELI");
        File newFile = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZeliNew");
        File fileToch = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZeliSTochkoi");

        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileToch, true),"windows-1251");
            OutputStreamWriter writer2 = new OutputStreamWriter(new FileOutputStream(newFile, true),"windows-1251");
            String nZeli;
            String[] strArr;
            boolean sTochLiZel;
            int schetchik = 0;

            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"windows-1251"));
            // считаем сначала первую строку
            String line = reader.readLine();
            while (line != null) {
                strArr = line.split(",");
                nZeli = strArr[0];
                sTochLiZel = getZelWithTochka(nZeli);
                //если цель с точкой записываем в файл с точками иначе презаписываем старый
                if (sTochLiZel) {
                    writer.write(line + "\n");
                    schetchik++;
                } else {
                    writer2.write(line + "\n");
                }
                // считываем остальные строки в цикле
                line = reader.readLine();
            }
            System.out.println("Перезаписано в файл " + schetchik + " целей с точками");
            closeStreamsQuietly(reader, writer2, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //проверить с точкой ли цель
    static boolean getZelWithTochka(String str) {
        char[] strCharArray = str.toCharArray();
        for (char c : strCharArray) {
            if (c == '.') return true;
        }
        return false;
    }
    static void ochistka(){
        File fileToch = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZeliSTochkoi");
        File fileToch2 = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZeliNew");
        try {
            new FileWriter(fileToch);
            new FileWriter(fileToch2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}