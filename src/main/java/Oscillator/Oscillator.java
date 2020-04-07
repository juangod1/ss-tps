package Oscillator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;

public class Oscillator {
    double A = 100;

    int numParticles = 1;

    double time = 0;
    double delta_t = 0.001;

    double prev_acceleration = 0;
    double prev_position = 1;
    double prev_velocity = 0;
    double prev_time = 0 - delta_t;

    Particle p = new Particle(0.015, -1*A*Force.gamma/2, 0, new Point.Double(1,0), 1);

    public static void main(String[] args) throws IOException {
        FileWriter f = new FileWriter("./out", false);
        Oscillator o = new Oscillator();

        o.prev_time = o.time;

        int i = 0;
        while(i++<10000){
            f.append(String.valueOf(o.numParticles)).append("\n\n");
            f.append(String.valueOf(o.p.getPosition().getX())).append('\n');
            o.updateVelocityAndPosition();
            o.time += o.delta_t;
        }
        f.close();
    }

    public void updateVelocityAndPosition(){
        double current_velocity = this.p.getVx();
        double current_position = this.p.getPosition().getX();
        double current_acceleration = Force.oscillatorForce(p.getPosition().getX(), p.getVx())/p.getMass();
        //applyVerlet(this.p, current_acceleration);
        applyBeeman(this.p, current_acceleration);
        prev_velocity = current_velocity;
        prev_position = current_position;
        prev_acceleration = current_acceleration;
    }

    public void applyVerlet(Particle particle, double current_acceleration){
        double newPosition = 2*particle.getPosition().getX() - prev_position + Math.pow(delta_t, 2)*current_acceleration;
        double newVelocity = (newPosition-prev_position)/(2*delta_t);
        particle.setPosition(new Point2D.Double(newPosition, 0));
        particle.setVx(newVelocity);
    }

    public void applyBeeman(Particle particle, double current_acceleration){
        double newPosition = particle.getPosition().getX() + particle.getVx()*delta_t + (2d/3)*current_acceleration*Math.pow(delta_t,2) - (1f/6)*prev_acceleration*Math.pow(delta_t,2);
        double predicted_v = particle.getVx() + (3d/2)*current_acceleration*delta_t - (1d/2)*prev_acceleration*delta_t;
        double next_a = Force.oscillatorForce(newPosition,predicted_v)/particle.getMass();
        double corrected_v = particle.getVx() + (1d/3)*next_a*delta_t + (5d/6)*current_acceleration*delta_t - (1d/6)*prev_acceleration*delta_t;
        particle.setVx(corrected_v);
        particle.setPosition(new Point2D.Double(newPosition,0));
    }
}
