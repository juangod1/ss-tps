package Mars;

import java.io.FileWriter;
import java.io.IOException;

public class SolarSystem {
    // The units are m kg seconds and radians

    private CelestialBody sun;
    private CelestialBody earth;
    private CelestialBody mars;
    private CelestialBody venus;
    private CelestialBody ship;

    private int delta;
    private int delta_t;
    private int MISSION_DELTAS;
    private int DELTAS_PER_DAY;

    private final double LAUNCH_DISTANCE = 1500 * 1000;
    private final double LAUNCH_SPEED = 10 * 1000;
    private final double ORBITAL_EARTH_SPEED = 7.12 * 1000;
    private final double G = 6.693 * Math.pow(10, -11);

    private Double dmax;
    
    void initialize(int delta_t, int mission_deltas, int deltas_per_day) {
        this.delta_t = delta_t;
        this.MISSION_DELTAS = mission_deltas;
        this.DELTAS_PER_DAY = deltas_per_day;
        delta = 0;
        dmax = null;

        sun = new CelestialBody(0,0,0,0,0,696340 * 1000, 19891*Math.pow(10,26));
        earth = new CelestialBody(1,7.917904169940719 * 1000, -2.867871052093815*Math.pow(10,1) * 1000, -1.436232264182898*Math.pow(10,8) * 1000, -4.222184246295860*Math.pow(10,7) * 1000,6371 * 1000,597219*Math.pow(10,19));
        mars = new CelestialBody(2,2.499118636997282*Math.pow(10,1) * 1000, -6.412328574419259*Math.pow(10,-1) * 1000,-2.471238977495339*Math.pow(10,7) * 1000, -2.183737229441134*Math.pow(10,8) * 1000,3389.5 * 1000, 641693*Math.pow(10,18));
        venus = new CelestialBody(4, -1.277430863250054*Math.pow(10,1) * 1000, -3.283269283110196*Math.pow(10,1) * 1000, -1.001685942527938*Math.pow(10,8) * 1000, 3.867126784527869*Math.pow(10,7) * 1000,6051.8 * 1000 , 486732*Math.pow(10,19));

        initializeForce(sun);
        initializeForce(earth);
        initializeForce(mars);
        initializeForce(venus);
    }

    private void initializeShip() {
        ship = new CelestialBody(3,0, 0, 0, 0, 3389.5 * 1000/2,2*Math.pow(10,5));
        double angle = Math.atan2(earth.y, earth.x);

        ship.x = earth.x + (earth.radius + LAUNCH_DISTANCE) * Math.cos(angle);
        ship.y = earth.y + (earth.radius + LAUNCH_DISTANCE) * Math.sin(angle);

        ship.vx = earth.vx + Math.abs((ORBITAL_EARTH_SPEED + LAUNCH_SPEED) * Math.sin(angle)) * Math.signum(earth.vx);
        ship.vy = earth.vy + Math.abs((ORBITAL_EARTH_SPEED + LAUNCH_SPEED) * Math.cos(angle)) * Math.signum(earth.vy);

        initializeForce(ship);
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

        // VENUS
        if (body.id != venus.id) {
            double distance = Math.sqrt(Math.pow(venus.x - body.x, 2) + Math.pow(venus.y - body.y, 2));
            double force = G * venus.mass * body.mass / Math.pow(distance, 2);
            double angle = Math.atan2(Math.abs(venus.x - body.x), Math.abs(venus.y - body.y));
            forceX += force * Math.sin(angle) * ((body.x > venus.x) ? -1 : 1);
            forceY += force * Math.cos(angle) * ((body.y > venus.y) ? -1 : 1);
        }

        // SHIP
        if (ship != null) {
            if (body.id != ship.id) {
                double distance = Math.sqrt(Math.pow(ship.x - body.x, 2) + Math.pow(ship.y - body.y, 2));
                double force = G * ship.mass * body.mass / Math.pow(distance, 2);
                double angle = Math.atan2(Math.abs(ship.x - body.x), Math.abs(ship.y - body.y));
                forceX += force * Math.sin(angle) * ((body.x > ship.x) ? -1 : 1);
                forceY += force * Math.cos(angle) * ((body.y > ship.y) ? -1 : 1);
            }
        }

        Force newForce = new Force();
        newForce.previous = body.force;
        newForce.x = forceX;
        newForce.y = forceY;
        return newForce;
    }

    private void applyBeeman(boolean toShip) {
        updatePosition(sun);
        updatePosition(earth);
        updatePosition(mars);
        updatePosition(venus);
        if (toShip)
            updatePosition(ship);


        Force newForceSun = updateForce(sun);
        Force newForceEarth = updateForce(earth);
        Force newForceMars = updateForce(mars);
        Force newForceVenus = updateForce(venus);
        Force newForceShip = null;
        if (toShip)
            newForceShip = updateForce(ship);

        updateVelocity(sun, newForceSun);
        updateVelocity(earth, newForceEarth);
        updateVelocity(mars, newForceMars);
        updateVelocity(venus, newForceVenus);
        if (toShip)
            updateVelocity(ship, newForceShip);
    }

    private void updatePosition(CelestialBody body) {
        double newX = body.x + body.vx * delta_t + 2.0/3 * body.force.x * delta_t * delta_t / body.mass - 1.0/6 * body.force.previous.x * delta_t * delta_t / body.mass;
        double newY = body.y + body.vy * delta_t + 2.0/3 * body.force.y * delta_t * delta_t / body.mass - 1.0/6 * body.force.previous.y * delta_t * delta_t / body.mass;

        body.x = newX;
        body.y = newY;
    }

    private void updateVelocity(CelestialBody body, Force newForce) {
        double newVx = body.vx + 1.0/3 * newForce.x * delta_t / body.mass + 5.0/6 * body.force.x * delta_t / body.mass - 1.0/6 * body.force.previous.x * delta_t / body.mass;
        double newVy = body.vy + 1.0/3 * newForce.y * delta_t / body.mass + 5.0/6 * body.force.y * delta_t / body.mass - 1.0/6 * body.force.previous.y * delta_t / body.mass;

        body.force = newForce;
        body.force.previous.previous = null;

        body.vx = newVx;
        body.vy = newVy;
    }

    void simulateShip(int departureDeltas) throws IOException {
        int deltas = departureDeltas;
        FileWriter f = new FileWriter("./out" + departureDeltas * delta_t / (24*60*60), false);
        delta = departureDeltas;

        while (departureDeltas-->0) {
            applyBeeman(false);
        }

        initializeShip();

        writeToFile(f);

        while (!checkIfReachedMars()) {
            if (delta>MISSION_DELTAS) {
                System.out.print("Mission with departure date ");
                System.out.print(deltas/DELTAS_PER_DAY);
                System.out.println(" did not reach mars.");
                System.out.println(dmax);
                return;
            }

            if(delta%10==0)
                writeToFile(f);

            applyBeeman(true);

            delta+=1;
        }
        f.close();

        System.out.print("REACHED MARS ORBIT with departure date ");
        System.out.print(deltas/DELTAS_PER_DAY);
        System.out.print(" after ");
        System.out.print(delta/DELTAS_PER_DAY);
        System.out.println(" days of travel.");
        System.exit(0);
    }

    private void writeToFile(FileWriter f) throws IOException {
        f.append(String.valueOf(5)).append("\n\n");
        f.append(String.valueOf(sun.x / 1000000000)).append(" ").append(String.valueOf(sun.y / 1000000000)).append(" ").append(String.valueOf(sun.radius / 100000000)).append(" 1 1 0\n");
        f.append(String.valueOf(earth.x / 1000000000)).append(" ").append(String.valueOf(earth.y / 1000000000)).append(" ").append(String.valueOf(earth.radius / 1500000)).append(" 0 0 1\n");
        f.append(String.valueOf(mars.x / 1000000000)).append(" ").append(String.valueOf(mars.y / 1000000000)).append(" ").append(String.valueOf(mars.radius / 1500000)).append(" 1 0 0\n");
        f.append(String.valueOf(venus.x / 1000000000)).append(" ").append(String.valueOf(venus.y / 1000000000)).append(" ").append(String.valueOf(venus.radius / 1500000)).append(" 1 1 1\n");
        f.append(String.valueOf(ship.x / 1000000000)).append(" ").append(String.valueOf(ship.y / 1000000000)).append(" ").append(String.valueOf(ship.radius / 1500000)).append(" 0 0 0\n");
    }

    private boolean checkIfReachedMars(){
        double d = Math.sqrt(Math.pow(ship.x-mars.x,2)+Math.pow(ship.y-mars.y,2)) - mars.radius;

        if(dmax==null)
            dmax=d;
        else
            dmax = dmax > d ? d : dmax;

        if (d <= 1000*1000)
            return true;

        return false;
    }
}
