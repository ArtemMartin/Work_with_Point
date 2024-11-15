package org.example;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KMLConverter {
    static Element getTochKML(Tochka tochka) {
        Element placemark = new Element("Placemark");
        Element description = new Element("description");
        description.setText(tochka.getNumber());
        Element name = new Element("name");
        name.setText(tochka.getNumber());
        Element point = new Element("Point");
        Element coordinates = new Element("coordinates");
        coordinates.setText(tochka.getLongitude() + "," + tochka.getLatitude());

        point.addContent(coordinates);
        placemark.addContent(description);
        placemark.addContent(name);
        placemark.addContent(point);
        return placemark;
    }

    static List getBL(double x, double y) {
        List<Double> list = new ArrayList();
        x += -125.0;
        y += -117.0;
        int nZonu = (int) Math.round(y * Math.pow(10.0, -6.0));
        double b = x / 6367558.4968;
        double B0 = b + Math.sin(2.0 * b) * (0.00252588685 - 1.49186E-5 * Math.pow(Math.sin(b), 2.0) + 1.1904E-7 * Math.pow(Math.sin(b), 4.0));
        double z0 = (y - (double) (10 * nZonu + 5) * Math.pow(10.0, 5.0)) / (6378245.0 * Math.cos(B0));
        double dB = -Math.pow(z0, 2.0) * Math.sin(2.0 * B0) * (0.251684631 - 0.003369263 * Math.pow(Math.sin(B0), 2.0) + 1.1276E-5 * Math.pow(Math.sin(B0), 4.0) - Math.pow(z0, 2.0) * 0.10500614 - 0.04559916 * Math.pow(Math.sin(B0), 2.0) + 0.00228901 * Math.pow(Math.sin(B0), 4.0) - 2.987E-5 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.042858 - 0.025318 * Math.pow(Math.sin(B0), 2.0) + 0.014346 * Math.pow(Math.sin(B0), 4.0) - 0.001264 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.01672 - 0.0063 * Math.pow(Math.sin(B0), 2.0) + 0.01188 * Math.pow(Math.sin(B0), 4.0) - 0.00328 * Math.pow(Math.sin(B0), 6.0))));
        double l = z0 * (1.0 - 0.0033467108 * Math.pow(Math.sin(B0), 2.0) - 5.6002E-6 * Math.pow(Math.sin(B0), 4.0) - 1.87E-8 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.16778975 + 0.16273586 * Math.pow(Math.sin(B0), 2.0) - 5.249E-4 * Math.pow(Math.sin(B0), 4.0) - 8.46E-6 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.0420025 + 0.1487407 * Math.pow(Math.sin(B0), 2.0) + 0.005942 * Math.pow(Math.sin(B0), 4.0) - 1.5E-5 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.01225 + 0.09477 * Math.pow(Math.sin(B0), 2.0) + 0.03282 * Math.pow(Math.sin(B0), 4.0) - 3.4E-4 * Math.pow(Math.sin(B0), 6.0) - Math.pow(z0, 2.0) * (0.0038 + 0.0524 * Math.pow(Math.sin(B0), 2.0) + 0.0482 * Math.pow(Math.sin(B0), 4.0) + 0.0032 * Math.pow(Math.sin(B0), 6.0))))));
        double B = (B0 + dB) * 180.0 / Math.PI;
        double L = (6.0 * ((double) nZonu - 0.5) / 57.29577951 + l) * 180.0 / Math.PI;
        list.add(B);
        list.add(L);
        return list;
    }

    public void run() {
        writeKML(new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + Main.nameFileNew + ".txt"), "Zeli");
        writeKML(new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + Main.nameFileAZ + ".txt"), "ZeliAZ");
        writeKML(new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + Main.nameFileToch + ".txt"), "ZeliToch");
    }

    private void writeKML(File file, String strNewFile) {
        List<Tochka> listTochek = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            String[] mas;
            line = reader.readLine();
            List listBL;
            while (line != null) {
                mas = line.split(",");
                listBL = getBL(Double.parseDouble(mas[1]), Double.parseDouble(mas[2]));
                listTochek.add(new Tochka(mas[0], (double) listBL.get(1), (double) listBL.get(0)));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Element kml = new Element("kml");
            Document doc = new Document(kml);

            Element folder = new Element("Folder");
            Element placemark;
            //проходимся по списку точек
            for (Tochka tochka : listTochek) {
                placemark = getTochKML(tochka);
                folder.addContent(placemark);
            }

            kml.addContent(folder);

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\"+strNewFile + ".kml"));

            System.out.println("KML файл (" + strNewFile + ".kml" + ") успешно создан, всего записано "
                    + listTochek.size() + " точек!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

