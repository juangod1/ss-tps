package Oscillator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;

public class Oscillator {
    double A = 1;

    int numParticles = 1;

    double time = 0;
    double delta_t = 0.001;

    double prev_acceleration = 0;
    double prev_position = 1;
    double prev_velocity = 0;
    double prev_time = 0 - delta_t;

    Particle p = new Particle(0.015, -1*A*Force.gamma/(2*70), 0, new Point.Double(1,0), 1, 70);

    public static void main(String[] args) throws IOException {
        FileWriter f = new FileWriter("./out", false);
        Oscillator o = new Oscillator();

        o.prev_time = o.time;

        int i = 0;
        while(i++<10000){
            f.append(String.valueOf(o.numParticles+5)).append("\n\n");
            f.append("0 0.2 0.1 1 0 0").append('\n');
            f.append("0 0.1 0.1 1 0 0").append('\n');
            f.append("0 0 0.1 1 0 0").append('\n');
            f.append("0 -0.1 0.1 1 0 0").append('\n');
            f.append("0 -0.2 0.1 1 0 0").append('\n');
            f.append(String.valueOf(o.p.getPosition().getX())).append(" 0 0.1 1 1 1").append('\n');
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
        //applyBeeman(this.p, current_acceleration);
        applyGearPredictorCorrector(p,current_acceleration);
        prev_velocity = current_velocity;
        prev_position = current_position;
        prev_acceleration = current_acceleration;
    }

    Double gearR, gearR1, gearR2, gearR3, gearR4, gearR5, gearRP, gearR1P, gearR2P, gearR3P, gearR4P, gearR5P;
    public void applyGearPredictorCorrector(Particle particle, double current_acceleration){
        Double a0=3d/20,a1=251d/360,a2=1d,a3=11d/18,a4=1d/6,a5=1d/60;

        gearR = gearR==null? particle.getPosition().getX() :gearR;
        gearR1 = gearR1==null? particle.getVx() :gearR1;
        gearR2 = gearR2==null? current_acceleration :gearR2;
        gearR3 = gearR3==null? -1*Force.k*particle.getVx()/particle.getMass() - Force.gamma*current_acceleration/particle.getMass() :gearR3;
        gearR4 = gearR4==null?
                -1*Force.k*current_acceleration/particle.getMass() +
                Force.gamma*Force.k*particle.getVx()/Math.pow(particle.getMass(),2) +
                Math.pow(Force.gamma,2)*current_acceleration/Math.pow(particle.getMass(), 2)
                :gearR4;
        gearR5 = gearR5==null?
                Math.pow(Force.k,2)*particle.getVx()/Math.pow(particle.getMass(),2) +
                2*Force.gamma*current_acceleration*Force.k/Math.pow(particle.getMass(),2) -
                Math.pow(Force.gamma,2)*Force.k*particle.getVx()/Math.pow(particle.getMass(),3) -
                Math.pow(Force.gamma,3)*current_acceleration/Math.pow(particle.getMass(),3)
                :gearR5;

        gearRP = gearR + gearR1*delta_t + gearR2*Math.pow(delta_t,2)/2 + gearR3*Math.pow(delta_t,3)/6 + gearR4*Math.pow(delta_t,4)/24 + gearR5*Math.pow(delta_t,5)/120;
        gearR1P = gearR1 + gearR2*delta_t + gearR3*Math.pow(delta_t,2)/2 + gearR4*Math.pow(delta_t,3)/6 + gearR5*Math.pow(delta_t,4)/24;
        gearR2P = gearR2 + gearR3*delta_t + gearR4*Math.pow(delta_t,2)/2 + gearR5*Math.pow(delta_t,3)/6;
        gearR3P = gearR3 + gearR4*delta_t + gearR5*Math.pow(delta_t,2)/2;
        gearR4P = gearR4 + gearR5*delta_t;
        gearR5P = gearR5;

        double next_a = Force.oscillatorForce(gearRP,gearR1P);
        double dA = next_a - gearR2P;
        double dR2 = Math.pow(delta_t,2)*dA/2;

        double Rc = gearRP + a0*dR2;
        double R1c = gearR1P + a1*dR2/delta_t;
        double R2c = gearR2P + a2*dR2*2/Math.pow(delta_t,2);
        double R3c = gearR3P + a3*dR2*6/Math.pow(delta_t,3);
        double R4c = gearR4P + a4*dR2*24/Math.pow(delta_t,4);
        double R5c = gearR5P + a5*dR2*120/Math.pow(delta_t,5);

        gearR = Rc;
        gearR1 = R1c;
        gearR2 = R2c;
        gearR3 = R3c;
        gearR4 = R4c;
        gearR5 = R5c;

        particle.setPosition(new Point2D.Double(Rc,0));
        particle.setVx(R1c);
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
