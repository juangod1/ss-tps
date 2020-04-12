package Mars;

import java.io.FileWriter;
import java.io.IOException;

public class Simulation {
    // The units are km kg seconds and radians

    private CelestialBody sun;
    private CelestialBody earth;
    private CelestialBody mars;
    private CelestialBody ship;

    private int days;
    private int delta_t = 500; //seconds in a day

    private final double LAUNCH_DISTANCE = 1500 * 1000;
    private final double LAUNCH_SPEED = 8 * 1000;
    private final double ORBITAL_EARTH_SPEED = 7.12 * 1000;
    private final double G = 6.693 * Math.pow(10, -11);

    public static void main(String[] args) throws IOException {
        Simulation s = new Simulation();

        // aca la idea es simular la salida de la nave en distintos dias y guardamos el tiempo que tardo
        for (int i=0; i<3652; i++) {
            s.initialize();
            s.simulateShip(i);
        }
    }
    
    private void initialize() {
        days = 0;

        sun = new CelestialBody(0,0,0,0,0,696340 * 1000, 19891*Math.pow(10,26));
        earth = new CelestialBody(1,7.917904169940719 * 1000, -2.867871052093815*Math.pow(10,1) * 1000, -1.436232264182898*Math.pow(10,8) * 1000, -4.222184246295860*Math.pow(10,7) * 1000,6371 * 1000,597219*Math.pow(10,19));
        mars = new CelestialBody(2,2.499118636997282*Math.pow(10,1) * 1000, -6.412328574419259*Math.pow(10,-1) * 1000,-2.471238977495339*Math.pow(10,7) * 1000, -2.183737229441134*Math.pow(10,8) * 1000,3389.5 * 1000, 641693*Math.pow(10,18));
        ship = new CelestialBody(3,0, 0, 0, 0, 6000 * 1000,2*Math.pow(10,5));

        initializeShip();

//        initializeForce(sun);
        initializeForce(earth);
        initializeForce(mars);
        initializeForce(ship);
    }

    private void initializeShip() {
        double angle = Math.atan2(earth.y, earth.x);
        if (angle < 0) angle += Math.PI;

        ship.x = earth.x + (earth.radius + LAUNCH_DISTANCE) * Math.cos(angle) * Math.signum(earth.x);
        ship.y = earth.y + (earth.radius + LAUNCH_DISTANCE) * Math.sin(angle) * Math.signum(earth.y);

        ship.vx = (LAUNCH_SPEED + ORBITAL_EARTH_SPEED) * Math.cos(Math.PI / 2 - angle) * Math.signum(earth.vx);
        ship.vy = (LAUNCH_SPEED + ORBITAL_EARTH_SPEED) * Math.sin(Math.PI / 2 - angle) * Math.signum(earth.vy);
    }

    private void initializeForce(CelestialBody body) {
        body.force = updateForce(body);
        double previousX = body.x - delta_t * body.vx + delta_t * delta_t * body.force.x / (2 * body.mass);
        double previousY = body.y - delta_t * body.vy + delta_t * delta_t * body.force.y / (2 * body.mass);
        body.force.previous = updateForce(new CelestialBody(body.id, 0, 0, previousX, previousY, body.radius, body.mass));
    }

    private Force updateForce(CelestialBody body) {
        double forceX = 0;
        double forceY = 0;

        // SUN
        if (body.id != sun.id) {
            double distance = Math.sqrt(Math.pow(sun.x - body.x, 2) + Math.pow(sun.y - body.y, 2));
            double force = G * sun.mass * body.mass / Math.pow(distance, 2);
            double angle = Math.atan2(Math.abs(sun.x - body.x), Math.abs(sun.y - body.y));
            forceX += force * Math.sin(angle) * ((body.x > sun.x) ? -1 : 1);
            forceY += force * Math.cos(angle) * ((body.y > sun.y) ? -1 : 1);
        }

        // EARTH
        if (body.id != earth.id) {
            double distance = Math.sqrt(Math.pow(earth.x - body.x, 2) + Math.pow(earth.y - body.y, 2));
            double force = G * earth.mass * body.mass / Math.pow(distance, 2);
            double angle = Math.atan2(Math.abs(earth.x - body.x), Math.abs(earth.y - body.y));
            forceX += force * Math.sin(angle) * ((body.x > earth.x) ? -1 : 1);
            forceY += force * Math.cos(angle) * ((body.y > earth.y) ? -1 : 1);
        }

        // MARS
        if (body.id != mars.id) {
            double distance = Math.sqrt(Math.pow(mars.x - body.x, 2) + Math.pow(mars.y - body.y, 2));
            double force = G * mars.mass * body.mass / Math.pow(distance, 2);
            double angle = Math.atan2(Math.abs(mars.x - body.x), Math.abs(mars.y - body.y));
            forceX += force * Math.sin(angle) * ((body.x > mars.x) ? -1 : 1);
            forceY += force * Math.cos(angle) * ((body.y > mars.y) ? -1 : 1);
        }

        // SHIP
        if (body.id != ship.id) {
            double distance = Math.sqrt(Math.pow(ship.x - body.x, 2) + Math.pow(ship.y - body.y, 2));
            double force = G * ship.mass * body.mass / Math.pow(distance, 2);
            double angle = Math.atan2(Math.abs(ship.x - body.x), Math.abs(ship.y - body.y));
            forceX += force * Math.sin(angle) * ((body.x > ship.x) ? -1 : 1);
            forceY += force * Math.cos(angle) * ((body.y > ship.y) ? -1 : 1);
        }

        Force newForce = new Force();
        newForce.previous = body.force;
        newForce.x = forceX;
        newForce.y = forceY;
        return newForce;
    }

    private void applyBeeman(CelestialBody body) {
        double newX = body.x + body.vx * delta_t + 2.0/3 * body.force.x * delta_t * delta_t / body.mass - 1.0/6 * body.force.previous.x * delta_t * delta_t / body.mass;
        double newY = body.y + body.vy * delta_t + 2.0/3 * body.force.y * delta_t * delta_t / body.mass - 1.0/6 * body.force.previous.y * delta_t * delta_t / body.mass;

        Force newForce = updateForce(body);

        double newVx = body.vx + 1.0/3 * newForce.x * delta_t / body.mass + 5.0/6 * body.force.x * delta_t / body.mass - 1.0/6 * body.force.previous.x * delta_t / body.mass;
        double newVy = body.vy + 1.0/3 * newForce.y * delta_t / body.mass + 5.0/6 * body.force.y * delta_t / body.mass - 1.0/6 * body.force.previous.y * delta_t / body.mass;

        body.force = newForce;

        body.x = newX;
        body.y = newY;
        body.vx = newVx;
        body.vy = newVy;
    }

    private void simulateShip(int departureDay) throws IOException {
        FileWriter f = new FileWriter("./out" + departureDay, false);
        days = departureDay;

        while (departureDay-->0) {
//            applyBeeman(sun);
            applyBeeman(earth);
            applyBeeman(mars);
        }

        while (!checkIfReachedMars()) {
            if (checkIfMissionFailed())
                return;

            writeToFile(f);
//            applyBeeman(sun);
            applyBeeman(earth);
            applyBeeman(mars);
            applyBeeman(ship);

            days+=1;
        }
        f.close();

        System.out.print("Reached mars on day: ");
        System.out.print(days);
    }

    private boolean checkIfMissionFailed() {
        // aca va lo que habiamos hablado... de ver si me pase de la orbita
        return false;
    }

    private void writeToFile(FileWriter f) throws IOException {
        f.append(String.valueOf(4)).append("\n\n");
        f.append(String.valueOf(sun.x / 1000000000)).append(" ").append(String.valueOf(sun.y / 1000000000)).append(" ").append(String.valueOf(sun.radius / 10000000)).append(" 1 1 0\n");
        f.append(String.valueOf(earth.x / 1000000000)).append(" ").append(String.valueOf(earth.y / 1000000000)).append(" ").append(String.valueOf(earth.radius / 1000000)).append(" 0 0 1\n");
        f.append(String.valueOf(mars.x / 1000000000)).append(" ").append(String.valueOf(mars.y / 1000000000)).append(" ").append(String.valueOf(mars.radius / 1000000)).append(" 1 0 0\n");
        f.append(String.valueOf(ship.x / 1000000000)).append(" ").append(String.valueOf(ship.y / 1000000000)).append(" ").append(String.valueOf(ship.radius / 1000000)).append(" 0 0 0\n");
    }

    private boolean checkIfReachedMars(){
        return false;
    }
}
