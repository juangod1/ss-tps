package pedestrian;

import java.util.List;
import java.util.Objects;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Pedestrian {
    double tau=0.5, velocityDrive=1, A=2000, B=0.08;
    int id, Red, Green, Blue;
    boolean initialized;
    double prevx, prevy;
    double mass=70, vx=0, vy=0, x, y, goalX, goalY;
    Vector drivingForce;
    Vector socialForce;

    public Pedestrian(double x, double y, double goalX, double goalY, int id, int Red, int Green, int Blue){
        this.x = x;
        this.y = y;
        this.goalX = goalX;
        this.goalY = goalY;
        this.id = id;
        this.Red = Red;
        this.Green = Green;
        this.Blue = Blue;
        this.initialized = false;
        drivingForce = new Vector(0,0);
        socialForce = new Vector(0,0);
    }

    public Vector drivingForce(){
        Vector goalDistance = new Vector(goalX-x,goalY-y);
        double angle = Math.atan2(goalDistance.y, goalDistance.x);

        return new Vector(
                drivingForce.x + mass*(cos(angle)*velocityDrive-vx)/tau,
                drivingForce.y + mass*(sin(angle)*velocityDrive-vy)/tau
        );
    }

    public Vector socialForce(List<Pedestrian> list){
        Vector ans = new Vector(socialForce.x,socialForce.y);

        for(Pedestrian p : list){
            if(p.equals(this)) continue;

            Vector neighborDistance = new Vector(p.x-x,p.y-y);
            double angle = Math.atan2(neighborDistance.y, neighborDistance.x);
            double forceValue = -1*A*Math.exp(-1*neighborDistance.module()/B);

            ans.x += forceValue*cos(angle);
            ans.y += forceValue*sin(angle);
        }

        return ans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedestrian that = (Pedestrian) o;
        return Double.compare(that.tau, tau) == 0 &&
                Double.compare(that.velocityDrive, velocityDrive) == 0 &&
                id == that.id &&
                Double.compare(that.mass, mass) == 0 &&
                Double.compare(that.vx, vx) == 0 &&
                Double.compare(that.vy, vy) == 0 &&
                Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.goalX, goalX) == 0 &&
                Double.compare(that.goalY, goalY) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tau, velocityDrive, id, mass, vx, vy, x, y, goalX, goalY);
    }
}
