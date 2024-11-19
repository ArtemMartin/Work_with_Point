package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.OtdelitCeliSBykvoi.closeStreamsQuietly;

public class ClearingTargetsBySquares {
    List<String> list = new ArrayList<>();
    File file = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZELI");
    File newFile;

    public void provIYdalPoKoord() {
        double y2;
        double x;
        double x0;
        String[] mass2;
        double y;
        double y0;
        double x2;
        boolean a = false;
        boolean b = false;

        updateList();

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ввести нижнюю часть квадрата очистки, сокращенные первые две цыфры квадрата.");
            System.out.print("X = ");
            x = scanner.nextInt();
            System.out.print("Y = ");
            y = scanner.nextInt();
            System.out.print("Шаг по Х = ");
            x0 = scanner.nextInt();
            System.out.print("Шаг по У = ");
            y0 = scanner.nextInt();

            x *= 1000;
            y *= 1000;
            x0 = x + x0;
            y0 = y + y0;

            newFile = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\NewZELI");
            FileWriter writer = new FileWriter(newFile, true);
            //зацыкливаем пока не удалит всё
            int j;
            while (!b) {
                a = false;
                for (j = 0; j < list.size(); j++) {
                    mass2 = list.get(j).split(",");
                    x2 = Double.parseDouble(mass2[1]);
                    y2 = Double.parseDouble(mass2[2]);

                    if (x2 >= x && y2 >= y && x2 <= x0 && y2 <= y0) {
                        System.out.println(" № Цели " + mass2[0] + " X = " + mass2[1] + " Y = " + mass2[2]);
                        list.remove(j);
                        System.out.println("Удалено..." + mass2[0]);
                        a = true;
                    }
                }
                if (!a && j >= list.size()) b = true;
            }
            if (!a) {
                System.out.println("Квадрат уже чист!!!");
            }

            for (String s : list) {
                writer.write(s + "\n");
            }
            writer.close();
            closeStreamsQuietly(writer);
            file.delete();
            newFile.renameTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateList() {
        list.clear();
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
            closeStreamsQuietly(reader);
            System.out.println("Файл прочтен!!!");
            System.out.println("Всего " + list.size() + " целей");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
