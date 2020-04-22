package by.training.railwaytunnel.trainoperator;

import by.training.railwaytunnel.tunnel.NorthTunnel;
import by.training.railwaytunnel.tunnel.SouthTunnel;
import by.training.railwaytunnel.tunnel.Tunnel;

import java.util.Scanner;

public class OperatorCLI {

    public static void main(String[] args) {

        TrainOperator operator = new TrainOperator();
        Tunnel northTunnel = NorthTunnel.getInstance();
        Tunnel southTunnel = SouthTunnel.getInstance();


        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to TrainOperator!\n"
                + "Set number of train planned to raid from West and press 'Enter':");

        int westSideTrain = scanner.nextInt();

        System.out.println("Set number of train planned to raid from East and press 'Enter' ");

        int eastSideTrain = scanner.nextInt();

        System.out.println("Trains are started!");

        operator.startTrains(westSideTrain, eastSideTrain);

        while (true) {

            if (operator.isTrainRaidFinished()) {
                System.out.println("Trains passed tunnels successfully!");

                System.out.println("NorthTunnel are passed by " + northTunnel.getWestCounter() + " trains from West\n"
                        + " and by " + northTunnel.getEastCounter() + " trains from East");

                System.out.println("SouthTunnel are passed by " + southTunnel.getWestCounter() + " trains from West\n"
                        + " and by " + southTunnel.getEastCounter() + " trains from East");

                break;
            }
        }
    }
}
