package org.example;

import java.util.Scanner;

public class Main {
    final static String nameFileNew = "ZeliNew";
    final static String nameFileAZ = "ZeliSBykvoiAZ";
    final static String nameFileToch = "ZeliSTochkoi";



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int vvod;
        while (true) {
            System.out.println("---------------------ver 1.05------------------------");
            System.out.println("0 - Проверка на совпадения по номерам и координатам");
            System.out.println("10 - Почистить квадрат от целей");
            System.out.println("1 - Отсеять номера с точкой");
            System.out.println("2 - Отсеять номера с буквой или буквами(выполнять только после пункта 1)!!!");
            System.out.println("3 - Подготовка к ZOVКарте или конвертации в kml");
            System.out.println("4 - Конвертация в kml");
            System.out.println("6 - Конвертировать kml в csv");
            System.out.println("9 - Exit...");
            System.out.println("-----------------------------------------------------");
            vvod = scanner.nextInt();
            switch (vvod) {
                case 0:
                    new ProverkaPoint().run();
                    break;
                case 10:
                    new ClearingTargetsBySquares().provIYdalPoKoord();
                    break;
                case 1:
                    new SotrFilePoPoint().run();
                    break;
                case 2:
                    new OtdelitCeliSBykvoi().run();
                    break;
                case 3:
                    new PodgotovkaZOVKML().run();
                    break;
                case 4:
                    new KMLConverter().run();
                    break;
                case 6:
                    new KMLToCSVConverter().run();
                    break;
                case 9:
                    return;

            }
        }
    }
}