package org.example;

import org.osgeo.proj4j.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVWGStoCSVXY {
    File file;

    public CSVWGStoCSVXY() {
    }


    public static List<Double> refactorBLtoXY(double l, double b) {
        List<Double> list = new ArrayList();
// Создаем исходную и целевую системы координат
        CRSFactory factory = new CRSFactory();
        CoordinateReferenceSystem srcCRS = factory.createFromName("EPSG:4326");
        CoordinateReferenceSystem dstCRS = factory.createFromName("EPSG:28407");

        // Создаем объект для преобразования координат
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform transform = ctFactory.createTransform(srcCRS, dstCRS);

        // Преобразуем координаты
        //сначала вводим долготу потом широту
        ProjCoordinate srcCoord = new ProjCoordinate(l, b);
        ProjCoordinate dstCoord = new ProjCoordinate();
        transform.transform(srcCoord, dstCoord);

        // Выводим результат(наоборот x->y)
        //System.out.println("Преобразованные координаты: " + dstCoord.x + ", " + dstCoord.y);
        //возвращаем масив x, y
        list.add((double) Math.round(dstCoord.y));
        list.add((double) Math.round(dstCoord.x));
        return list;
    }

    public static List<Double> refactorXYtoBL(double x, double y) {
        List<Double> list = new ArrayList();
// Создаем исходную и целевую системы координат
        CRSFactory factory = new CRSFactory();
        CoordinateReferenceSystem srcCRS = factory.createFromName("EPSG:28407");
        CoordinateReferenceSystem dstCRS = factory.createFromName("EPSG:4326");

        // Создаем объект для преобразования координат
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform transform = ctFactory.createTransform(srcCRS, dstCRS);

        // Преобразуем координаты
        //сначала вводим долготу потом широту
        ProjCoordinate srcCoord = new ProjCoordinate(y, x);
        ProjCoordinate dstCoord = new ProjCoordinate();
        transform.transform(srcCoord, dstCoord);

        // Выводим результат(наоборот x->y)
        //System.out.println("Преобразованные координаты: " + dstCoord.x + ", " + dstCoord.y);
        //возвращаем масив b, l
        list.add(dstCoord.y);
        list.add(dstCoord.x);
        return list;
    }

    public void preobrazovatFilBLtoFilXY() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ввести название файла для преобразования BL в XY: ");
        file = new File(scanner.nextLine());
        File newFile = new File("new" + file);
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile)))) {
            String line;
            String[] mas;
            line = reader.readLine();
            List<Double> listXY;
            while (!(line == null)) {
                mas = line.split(",");
                listXY = refactorBLtoXY(Double.parseDouble(mas[2])
                        , Double.parseDouble(mas[1]));
                writer.write(mas[0] + "," + Math.round(listXY.get(0))
                        + "," + Math.round(listXY.get(1)) + "\n");
                line = reader.readLine();
            }
            System.out.println("Done!!!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

