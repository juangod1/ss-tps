package Oscillator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;

public class Oscillator {
    private double A = 1;

    private int numParticles = 1;

    private double time = 0;
    private double delta_t = 0.001;

    private double prev_acceleration = 0;
    private double prev_position = 1;

    private Particle p = new Particle(0.015, -1*A*Force.gamma/(2*70), 0, new Point.Double(1,0), 1, 70);

    private Double gearR = null;
    private Double gearR1 = null;
    private Double gearR2 = null;
    private Double gearR3 = null;
    private Double gearR4 = null;
    private Double gearR5 = null;

    public static void main(String[] args) throws IOException {
        FileWriter f = new FileWriter("./out", false);
//        FileWriter t = new FileWriter("./tableVerlet", false);
//        FileWriter t = new FileWriter("./tableBeeman", false);
//        FileWriter t = new FileWriter("./tableAnalytical", false);
        FileWriter t = new FileWriter("./tableGearPredictorCorrector", false);
        Oscillator o = new Oscillator();

        while(o.time<=5){
            t.append(String.valueOf(o.time)).append(", ").append(String.valueOf(o.p.getPosition().getX())).append("\n");
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
        t.close();
    }

    private void updateVelocityAndPosition(){
        double current_position = this.p.getPosition().getX();
        double current_acceleration = Force.oscillatorForce(p.getPosition().getX(), p.getVx())/p.getMass();
        //applyVerlet(this.p, current_acceleration);
        //applyBeeman(this.p, current_acceleration);
        //applyAnalytical(this.p);
        applyGearPredictorCorrector(p);
        prev_position = current_position;
        prev_acceleration = current_acceleration;
    }

    private void applyGearPredictorCorrector(Particle particle){
        double a0=3d/16,a1=251d/360,a2=1d,a3=11d/18,a4=1d/6,a5=1d/60;
        double r0 = 1;

        gearR = gearR==null? particle.getPosition().getX() :gearR;
        gearR1 = gearR1==null? particle.getVx() :gearR1;
        gearR2 = gearR2==null? -1*Force.k*(particle.getPosition().getX()-r0)/particle.getMass() :gearR2;
        gearR3 = gearR3==null? -1*Force.k*gearR1/particle.getMass() :gearR3;
        gearR4 = gearR4==null? Math.pow(Force.k/particle.getMass(), 2)*(particle.getPosition().getX()-r0) :gearR4;
        gearR5 = gearR5==null? -1*Force.k*gearR3/particle.getMass() :gearR5;

        double gearRP = gearR + gearR1 * delta_t + gearR2 * Math.pow(delta_t, 2) / 2 + gearR3 * Math.pow(delta_t, 3) / 6 + gearR4 * Math.pow(delta_t, 4) / 24 + gearR5 * Math.pow(delta_t, 5) / 120;
        double gearR1P = gearR1 + gearR2 * delta_t + gearR3 * Math.pow(delta_t, 2) / 2 + gearR4 * Math.pow(delta_t, 3) / 6 + gearR5 * Math.pow(delta_t, 4) / 24;
        double gearR2P = gearR2 + gearR3 * delta_t + gearR4 * Math.pow(delta_t, 2) / 2 + gearR5 * Math.pow(delta_t, 3) / 6;
        double gearR3P = gearR3 + gearR4 * delta_t + gearR5 * Math.pow(delta_t, 2) / 2;
        double gearR4P = gearR4 + gearR5 * delta_t;
        double gearR5P = gearR5;

        double next_a = Force.oscillatorForce(gearRP, gearR1P)/particle.getMass();
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

    public void applyAnalytical(Particle particle) {
        double newPosition = Math.pow(Math.E, -(100*time/(2*70))) * Math.cos(Math.sqrt(Math.pow(10,4)/70 - 100.0/196.0)*time);
        particle.setPosition(new Point2D.Double(newPosition, 0));
    }
}
