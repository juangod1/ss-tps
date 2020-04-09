package Mars;

import com.sun.javafx.PlatformUtil;

public class Simulation {
    // The units are km kg seconds and radians

    CelestialBody sun = new CelestialBody(0,0,0,0,696340, 1.989*Math.pow(10,30));
    CelestialBody earth = new CelestialBody(7.917904169940719, -2.867871052093815*Math.pow(10,1), -1.436232264182898*Math.pow(10,8), -4.222184246295860*Math.pow(10,7),6371,597219*Math.pow(10,19));
    CelestialBody mars = new CelestialBody(2.499118636997282*Math.pow(10,1), -6.412328574419259*Math.pow(10,-1),-2.471238977495339*Math.pow(10,7), -2.183737229441134*Math.pow(10,8),3389.5, 641693*Math.pow(10,18));

    double shipX=0, shipY=0, shipVx=0, shipVy=0;
    int days=0;

    final double LAUNCH_DISTANCE = 1500;
    final double LAUNCH_SPEED = 8;
    final double ORBITAL_EARTH_SPEED = 7.12;

    public static void main(String[] args) {
        simulatePlanets(3652);

        // aca la idea es simular la salida de la nave en todos los dias del archivo y guardamos el tiempo que tardo
        for(int i=0;i<3652;i++){
            simulateShip(0);
        }
    }

    // La idea es primero escribir tipo 10 anios de trayectoria de la tierra y marte
    private static void simulatePlanets(int days) {
        Simulation s = new Simulation();

        while(days-->0){
            s.applyBeeman(s.earth);
            s.applyBeeman(s.mars);
            s.writeToFile();
        }
    }

    private static void simulateShip(int departureDay){
        Simulation s = new Simulation();
        s.days += departureDay;

        while(departureDay-->0){
            s.applyBeeman(s.earth);
            s.applyBeeman(s.mars);
        }

        s.initializeShip();

        while(!s.checkIfReachedMars()){
            if(s.checkIfMissionFailed())
                return;

            s.applyBeeman(s.earth);
            s.applyBeeman(s.mars);
            s.applyBeemanToShip();
            s.days+=1;
        }

        System.out.print("Reached mars on day: ");
        System.out.print(s.days);
    }

    private void initializeShip(){
        double angle = Math.atan(earth.y/earth.x);

        shipX = earth.x + LAUNCH_DISTANCE*Math.cos(angle);
        shipY = earth.y + LAUNCH_DISTANCE*Math.sin(angle);

        shipVx = (LAUNCH_SPEED+ORBITAL_EARTH_SPEED)*Math.cos(Math.PI/2 - angle)*(earth.y>=0?-1:1);
        shipVy = (LAUNCH_SPEED+ORBITAL_EARTH_SPEED)*Math.sin(Math.PI/2 - angle)*(earth.x>=0?-1:1);
    }

    private boolean checkIfMissionFailed(){
        // aca va lo que habiamos hablado... de ver si me pase de la orbita
        return false;
    }

    private void writeToFile(){

    }

    private void applyBeeman(CelestialBody body){

    }

    private void applyBeemanToShip(){

    }

    private boolean checkIfReachedMars(){
        return false;
    }
}
