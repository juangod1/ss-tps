package cellIndexMethod.generator;

import cellIndexMethod.particle.Particle;
import cellIndexMethod.particle.ParticleImpl;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Generator {
    private int N;
    private int L;
    private int M;
    private double rc;
    private int amount;
    private String path;

    private Generator() {}

    private void setN(int n) {
        N = n;
    }

    private void setL(int l) {
        L = l;
    }

    private void setM(int m) {
        M = m;
    }

    private void setRc(double rc) {
        this.rc = rc;
    }

    private void setAmount(int amount) {
        this.amount = amount;
    }

    private void setPath(String path) { this.path = path; }

    private boolean notViolates(Particle curr, List<Particle> particles) {
        for (Particle particle : particles) {
            if (Math.sqrt(Math.pow(curr.getPosition().getX()-particle.getPosition().getX(),2) + Math.pow(curr.getPosition().getY()-particle.getPosition().getY(),2)) <= (curr.getRadius() + particle.getRadius()))
                return false;
        }
        return true;
    }

    private void generate() throws IOException {
        List<Particle> particlesGenerated = new ArrayList<>();
        Random randomGenerator = new Random();
        long now = System.currentTimeMillis();
        Particle curr;
        double randomX, randomY;
        int added = 0;

        for (int i = 0; i < amount; i++) {
            File fileStatic = new File(path + "/static"); //+ now + i);
            FileWriter frStatic = new FileWriter(fileStatic, true);
            File fileDynamic = new File(path + "/dynamic"); // + now + i);
            FileWriter frDynamic = new FileWriter(fileDynamic, true);

            frStatic.append(String.valueOf(N)).append("\n");
            frStatic.append(String.valueOf(L)).append("\n");
            frStatic.append(String.valueOf(M)).append("\n").append(String.valueOf(rc)).append("\n");

            while (added < N) {
                randomX = randomGenerator.nextDouble();
                randomY = randomGenerator.nextDouble();
                curr = new ParticleImpl(0.25, new Point2D.Double(randomX * L, randomY * L), 0);
                if (notViolates(curr, particlesGenerated)) {
                    particlesGenerated.add(curr);
                    added++;
                }
            }

            frDynamic.append(String.valueOf(N)).append("\n\n");

            for (Particle particle : particlesGenerated) {
                frStatic.append(String.valueOf(particle.getRadius())).append("\n");
                frDynamic.append(String.valueOf(particle.getPosition().getX())).append(" ").append(String.valueOf(particle.getPosition().getY())).append(" ").append(String.valueOf(particle.getRadius()));
                frDynamic.append(" 140 137 136").append("\n");
            }

            frStatic.close();
            frDynamic.close();
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
        Generator generator = new Generator();

        try {
            if (argsList.size() < 4 || argsList.size() > 12)
                throw new IllegalArgumentException();
            else {
                int particlesParameter = argsList.indexOf("-n");
                if (particlesParameter != -1)
                    generator.setN(Integer.parseInt(argsList.get(particlesParameter + 1)));

                int lengthParameter = argsList.indexOf("-l");
                if (lengthParameter != -1)
                    generator.setL(Integer.parseInt(argsList.get(lengthParameter + 1)));

                int cellParameter = argsList.indexOf("-m");
                if (cellParameter != -1)
                    generator.setM(Integer.parseInt(argsList.get(cellParameter + 1)));

                int radiusParameter = argsList.indexOf("-r");
                if (radiusParameter != -1)
                    generator.setRc(Integer.parseInt(argsList.get(radiusParameter + 1)));

                int amountParameter = argsList.indexOf("-a");
                if (amountParameter != -1)
                    generator.setAmount(Integer.parseInt(argsList.get(amountParameter + 1)));

                int pathParameter = argsList.indexOf("-p");
                if (pathParameter != -1)
                    generator.setPath(argsList.get(pathParameter + 1));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameters, try: \n" +
                    "\tjava -jar cellIndexMethod.generator.jar -n N -l L -m M -r rc -a amount -p path\n\n" +
                    "\t-n N\n\t\t determines amount of particles\n" +
                    "\t-l L\n\t\t determines simulation area length\n" +
                    "\t-m M\n\t\t determines amount of cells\n" +
                    "\t-r rc\n\t\t determines interaction radio\n" +
                    "\t-a amount\n\t\t determines number of inputs to generate\n" +
                    "\t-p path\n\t\t determines path were to generate inputs");
            return;
        }
        generator.generate();
    }
}
