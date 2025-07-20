package org.example;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Logger;

public class CSVtoZeli {

    public void make() {
        int checkout = 1;
        int checkVusota = 0;
        int vusota = 0;
        System.out.println("------------Преобразовать CSV в Zeli----------");
        System.out.println("Перед началом работы убедись что разделитель запятая и в файле " +
                "структура формата имя, X, Y, h или имя, X, Y");
        while (checkout == 1) {
            Scanner scaner = new Scanner(System.in);
            System.out.print("Ввести название файла с расширением: ");
            String fileName = scaner.nextLine();
            File file = new File(fileName);
            //чистим временный файл
            try {
                ochistka();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            System.out.print("Высота с файла - 1, вручную - 0: ");
            checkVusota = scaner.nextInt();
            if (checkVusota == 0) {
                System.out.print("Ввести высоту: ");
                vusota = scaner.nextInt();
            }
            File newFile = new File("Zeli");
            String line;
            String name;
            String prefiks = "";
            //добавляем к имени цели префикс
            System.out.print("Добавить префикс перед номером цели - 1, НЕТ - 0: ");
            int q = scaner.nextInt();
            if (q == 1) {
                System.out.print("Ввести префикс: ");
                prefiks = scaner.next();
            }

            long x = 0;
            long y = 0;
            double h = 0;
            try {
                FileWriter writer = new FileWriter(newFile, StandardCharsets.UTF_8, true);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String[] mass;
                line = reader.readLine();
                while ((line) != null) {
                    mass = line.split(",");
                    name = prefiks + mass[0];
                    try {
                        //обрабатываем x y h
                        x = Long.parseLong(getString(mass[1]));
                        x = x % 100000;
                        y = Long.parseLong(getString(mass[2]));
                        y = y % 100000;
                        if (checkVusota == 1) {
                            h = Double.parseDouble(mass[3]);
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        Logger.getLogger(Main.class.getName()).info("Шляпа: " + ex.getMessage());
                    }
                    if (checkVusota == 0) {
                        writer.write(name + "," + x + "," + y + "," + vusota + "," + 0 + "," + 0 + "\n");
                    } else {
                        writer.write(name + "," + x + "," + y + "," + h + "," + 0 + "," + 0 + "\n");
                    }
                    line = reader.readLine();

                }
                reader.close();
                writer.close();
                System.out.println("Успешно!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.delete();
            newFile.renameTo(file);
            System.out.print("Продолжить - 1, НЕТ - 0: ");
            checkout = scaner.nextInt();
        }
    }

    public void ochistka() throws IOException {
        File newFile = new File("Zeli");
        new FileWriter(newFile);
    }

    public String getString(String str) {
        StringBuilder digit = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                digit.append(c);
            }
        }
        return digit.toString();
    }
}
