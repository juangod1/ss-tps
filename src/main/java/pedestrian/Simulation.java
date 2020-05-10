package pedestrian;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    public static void main(String[] args) throws IOException {/*
        Pedestrian p = new Pedestrian(0.2,0,2,1, 0, 100, 0, 0);
        Pedestrian p2 = new Pedestrian(2.1,0.9,0,0, 1, 0, 0, 100);
        Pedestrian p3 = new Pedestrian(1,0.9,0,-1, 1, 0, 244, 100);
        Pedestrian p4 = new Pedestrian(0,0.9,1,0, 1, 244, 0, 100);*/

        Pedestrian p = new Pedestrian(0.3,0,-0.5,10, 0, 100, 0, 0);
        Pedestrian p2 = new Pedestrian(0.3,-4,0.2,6, 1, 0, 0, 100);
        Pedestrian p3 = new Pedestrian(-0.3,6,0.5,-4, 1, 0, 244, 100);
        Pedestrian p4 = new Pedestrian(-0.1,10,-0.2,0, 1, 244, 0, 100);

        List<Pedestrian> list = new ArrayList<>();
        list.add(p);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        Simulation s = new Simulation(list);

        FileWriter f = new FileWriter("./pedestrian", false);

        for(int i=0;i<10400;i++){
            s.writeToFile(f);
            s.updatePedestrians();
        }
    }

    double dt = 0.001;
    List<Pedestrian> list;

    public void updatePedestrians(){
        for(Pedestrian p : list){
            Vector drivingForce = p.drivingForce();
            p.drivingForce = drivingForce;
            Vector socialForce = p.socialForce(list);
            p.socialForce = socialForce;

            if (!p.initialized) {
                p.prevx = p.x - p.vx * dt;
                p.prevy = p.y - p.vy * dt;
            }

            double nextx = 2 * p.x - p.prevx + (drivingForce.x/p.mass)*dt*dt + (socialForce.x/p.mass)*dt*dt;
            double nexty = 2 * p.y - p.prevy + (drivingForce.y/p.mass)*dt*dt + (socialForce.y/p.mass)*dt*dt;

            p.vy = (nextx - p.prevx)/(2*dt);
            p.vy = (nexty - p.prevy)/(2*dt);
            p.prevx = p.x;
            p.prevy = p.y;
            p.x = nextx;
            p.y = nexty;
        }
    }

    public Simulation(List<Pedestrian> list){
        this.list = list;
    }

    public void writeToFile(FileWriter f) throws IOException{
        f.append(String.valueOf(list.size()*2)).append("\n\n");
        for(Pedestrian p : list){
            f.append(Double.toString(p.x)).append(" ")
                    .append(Double.toString(p.y)).append(" ")
                    .append(Double.toString(p.Red)).append(" ")
                    .append(Double.toString(p.Green)).append(" ")
                    .append(Double.toString(p.Blue)).append(" 0.1 ").append("\n");
            f.append(Double.toString(p.goalX)).append(" ")
                    .append(Double.toString(p.goalY)).append(" ")
                    .append(Double.toString(p.Red)).append(" ")
                    .append(Double.toString(p.Green)).append(" ")
                    .append(Double.toString(p.Blue)).append(" 0.07 ").append("\n");
        }
    }
}
