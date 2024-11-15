package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.lang3.StringUtils.strip;
import static org.example.OtdelitCeliSBykvoi.closeStreamsQuietly;

public class KMLToCSVConverter {
    private static void writeCSV(List<Map<String, String>> points, File outputFile) throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(outputFile), CSVFormat.DEFAULT)) {
            for (Map<String, String> point : points) {
                printer.printRecord(point.entrySet());
            }
        }
    }


    private List<Map<String, String>> readKMLPoints(String inputFile) throws IOException {
        List<Map<String, String>> points = new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<Placemark>")) {
                    Map<String, String> point = parsePoint(line, reader);
                    points.add(point);
                }
            }
        } catch (IOException e) {
            myLogger("KMLToCSVConverter", "readKMLPoints()", e.getMessage());
        }
        return points;
    }

    private Map<String, String> parsePoint(String line, BufferedReader reader) throws IOException {
        Map<String, String> point = new HashMap<>();
        line = reader.readLine();
        String name = vuchlenitIzStroki(line);
        String coordinates;
        while (!line.contains("</Placemark>")) {
            if (line.contains("<Point>")) {
                line = reader.readLine();
                line = reader.readLine();
                coordinates = vuchlenitCoordinatu(line);
                point.put(name, coordinates);
            }
            line = reader.readLine();
        }
        return point;
    }

    public void run() {
        // Путь к исходному KML-файлу
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ввести файл с расширением kml(файл должен быть экспортирован с SASPlanet!) вводить без kml: ");
        String inputFile = scanner.nextLine();
        // Путь к выходному CSV-файлу
        File outputFile = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + inputFile + ".csv");

        List<Map<String, String>> points = null;
        try {
            points = readKMLPoints("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + inputFile + ".kml");
            writeCSV(points, outputFile);
            perezapisatFail(outputFile);
            System.out.println("Конвертация прошла успешно!!!");
        } catch (IOException e) {
            myLogger("KMLToCSVConverter", "run()", e.getMessage());
        }
    }

    public void perezapisatFail(File file) {
        File newFile = new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\New" + file.getName());
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
            newFile.createNewFile();
            String line;
            String[] mass;
            while ((line = reader.readLine()) != null) {
                line = line.replace('=', ',');
                mass = line.split(",");
                try {
                    writer.write(strip(mass[0], "\"") + "," + strip(mass[1], "\"")
                            + "," + strip(mass[2], "\"") + "\n");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    myLogger("KMLToCSVConverter", "perezapisatFail()", ex.getMessage());
                }
            }
            closeStreamsQuietly(reader);
            closeStreamsQuietly(writer);
            file.delete();
            newFile.renameTo(file);
        } catch (IOException e) {
            myLogger("KMLToCSVConverter", "perezapisatFail()", e.getMessage());
        }

    }

    private void myLogger(String klass, String method, String e) {
        Logger.getLogger(KMLToCSVConverter.class.getName()).info("Shlapa -> " + "Class: " + klass + " Method: " + method + ". " + e);
    }

    //из xml строки вычленяем значение
    public String vuchlenitIzStroki(String str) {
        String[] arrStr = str.split(">");
        String[] arrStr2 = arrStr[1].split("<");
        return arrStr2[0];
    }

    public String vuchlenitCoordinatu(String str) {
        String[] arrStr = str.split(">");
        String[] arrStr2 = arrStr[1].split("<");
        String[] arrStr3 = arrStr2[0].split(",");
        str = arrStr3[1] + "," + arrStr3[0];
        return str;
    }
}