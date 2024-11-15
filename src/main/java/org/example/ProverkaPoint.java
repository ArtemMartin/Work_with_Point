package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.OtdelitCeliSBykvoi.closeStreamsQuietly;

public class ProverkaPoint {
    List<String> list = new ArrayList<>();
    File file = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\ZELI");
    File newFile;
    boolean a = false;

    private void provIYdalPoKoord() {
        double raznX;
        double y2;
        double x;
        String[] mass2;
        double y;
        String[] mass;
        double raznY;
        double x2;

        try {
            newFile = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\New" + file.getName());
            FileWriter writer = new FileWriter(newFile, true);
            for (int i = 0; i < list.size(); i++) {
                mass = list.get(i).split(",");
                x = Double.parseDouble(mass[1]);
                y = Double.parseDouble(mass[2]);
                for (int j = i + 1; j < list.size(); j++) {
                    mass2 = list.get(j).split(",");
                    x2 = Double.parseDouble(mass2[1]);
                    y2 = Double.parseDouble(mass2[2]);
                    raznX = Math.abs(x - x2);
                    raznY = Math.abs(y - y2);
                    if (raznX <= 25 && raznY <= 25) {
                        a = true;
                        System.out.println("Совпадение по координатам целей");
                        System.out.println(" № Цели " + mass[0] + " X = " + mass[1] + " Y = " + mass[2]);
                        System.out.println(" № Цели " + mass2[0] + " X = " + mass2[1] + " Y = " + mass2[2]);
                        list.remove(j);
                        System.out.println("Удалено..." + mass2[0]);
                    }
                }
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

    private void provekIYdalPoNomer() {
        String nZeli2;
        String[] mass2;
        String[] mass;
        String nZeli;

        try {
            newFile = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\New" + file.getName());
            FileWriter writer = new FileWriter(newFile, true);
            for (int i = 0; i < list.size(); i++) {
                mass = list.get(i).split(",");
                nZeli = mass[0];
                for (int j = i + 1; j < list.size(); j++) {
                    mass2 = list.get(j).split(",");
                    nZeli2 = mass2[0];
                    if (nZeli.equals(nZeli2)) {
                        a = true;
                        System.out.println("Совпадение по номерам целей" + " № Цели " + mass2[0]
                                + " X = " + mass2[1] + " Y = " + mass2[2]);
                        list.remove(j);
                        System.out.println("Удалено..." + nZeli2);
                    }
                }
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


    public void run() {
        //обновить список целей
        updateList();
        a = false;
        provekIYdalPoNomer();
        System.out.println("Проверка по номерам закончена, продолжаем по координатам!!!");

        //обновить список целей
        updateList();
        provIYdalPoKoord();
        if (!a) {
            System.out.println("Совпадений не найдено!!!");
        }
    }

}
