package Oscillator;

import java.awt.*;
import java.awt.geom.Point2D;

public class Oscillator {
    double A = 1;


    double time = 0;
    double delta_t = 0.01;

    double prev_position = 1;
    double prev_velocity = -1*A*Force.gamma/2;
    double prev_time = 0 - delta_t;

    Particle p = new Particle(0.015, -1*A*Force.gamma/2, 0, new Point.Double(1,0), 1);

    public static void main(String[] args) {
        Oscillator o = new Oscillator();

        o.prev_time = o.time;
        o.time += o.delta_t;

        while(true){
            o.updateVelocityAndPosition();
        }
    }

    public void updateVelocityAndPosition(){
        prev_velocity = this.p.getVx();
        prev_position = this.p.getPosition().getX();
        applyVerlet(this.p);
    }

    public void applyVerlet(Particle particle){
        double newPosition = 2*particle.getPosition().getX() - prev_position + Math.pow(delta_t, 2)*Force.oscillatorForce(particle.getPosition().getX(), particle.getVx())/particle.getMass();
        double newVelocity = 2*particle.getVx() - prev_velocity + Math.pow(delta_t, 2)*Force.oscillatorForce(particle.getPosition().getX(), particle.getVx())/particle.getMass();
        particle.setPosition(new Point2D.Double(newPosition, 0));
        particle.setVx(newVelocity);
    }
}
