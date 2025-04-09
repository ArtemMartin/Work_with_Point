package org.example;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

    public void run() {
        writeKML(new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + Main.nameFileNew + ".txt"), "Zeli");
        writeKML(new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + Main.nameFileAZ + ".txt"), "ZeliAZ");
        writeKML(new File("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + Main.nameFileToch + ".txt"), "ZeliToch");
    }

    private void writeKML(File file, String strNewFile) {
        List<Tochka> listTochek = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "windows-1251"));
            String line;
            String[] mas;
            line = reader.readLine();
            List listBL;
            while (line != null) {
                mas = line.split(",");
                listBL = CSVWGStoCSVXY.refactorXYtoBL(Double.parseDouble(mas[1])
                        , Double.parseDouble(mas[2]));
                listTochek.add(new Tochka(mas[0], (double) listBL.get(1)
                        , (double) listBL.get(0)));
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
            xmlOutput.output(doc, new FileWriter("D:\\YO_NA\\Projekts\\Work_with_Point\\ManagerWorkPoint\\" + strNewFile + ".kml"));

            System.out.println("KML файл (" + strNewFile + ".kml" + ") успешно создан, всего записано "
                    + listTochek.size() + " точек!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

