package Mars;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Simulation {
    // The units are km kg and radians

    CelestialBody sun = new CelestialBody(696340, 1.989*Math.pow(10,30), 0);
    CelestialBody earth = new CelestialBody(6371, 5.972*Math.pow(10,24), 149597870);
    CelestialBody mars = new CelestialBody(3389.5, 6.39*Math.pow(10,23), 229000000);

    double shipX, shipY, shipVx, shipVy;

    public static void main(String[] args) throws IOException {
        simulateWithStartingDay(0);
    }

    private static void simulateWithStartingDay(int startingDay) throws IOException {
        Simulation s = new Simulation();

        BufferedReader earthCSV = new BufferedReader(new FileReader("./src/main/java/Mars/earth.csv"));
        BufferedReader marsCSV = new BufferedReader(new FileReader("./src/main/java/Mars/mars.csv"));
        int days = 3652;
        days-=startingDay;

        String earthRow="", marsRow;
        if(startingDay==0){
            earthRow = earthCSV.readLine();
            earthCSV = new BufferedReader(new FileReader("./src/main/java/Mars/earth.csv"));
        } else {
            while(startingDay-->0) {
                earthRow = earthCSV.readLine();
                marsRow = marsCSV.readLine();
            }
        }

        s.initializeShip(earthRow.split(" "));

        while (days-->0) {
            earthRow = earthCSV.readLine();
            marsRow = marsCSV.readLine();

            String[] earthData = earthRow.split(" ");
            String[] marsData = marsRow.split(" ");

            s.earth.theta = Helper.RAtoTheta(Double.parseDouble(earthData[2]),Double.parseDouble(earthData[3]),Double.parseDouble(earthData[4]));
            s.mars.theta = Helper.RAtoTheta(Double.parseDouble(marsData[2]),Double.parseDouble(marsData[3]),Double.parseDouble(marsData[4]));

            s.updateShipPosition();

            if(s.checkIfReachedMars()){
                System.out.println("Reached mars on:");
                System.out.println(earthData[0]);
            }
        }

        earthCSV.close();
        marsCSV.close();
    }

    private void initializeShip(String[] earthData){

    }

    private void updateShipPosition(){

    }

    private boolean checkIfReachedMars(){


        return false;
    }
}
