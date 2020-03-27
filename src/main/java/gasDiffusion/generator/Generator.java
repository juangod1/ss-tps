package gasDiffusion.generator;

import gasDiffusion.Particle;

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
    private double width;
    private double height;
    private double partitionOpening;
    private int amount;
    private String path;

    private Generator() {}

    private void setN(int n) {
        N = n;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setPartitionOpening(double partitionOpening) {
        this.partitionOpening = partitionOpening;
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
        if ((curr.getPosition().getX()-curr.getRadius()) < 0 || curr.getPosition().getX()+curr.getRadius() > width/2) return false;
        if ((curr.getPosition().getY()-curr.getRadius()) < 0 || curr.getPosition().getY()+curr.getRadius() > height) return false;

        return true;
    }

    private void generate() throws IOException {
        List<Particle> particlesGenerated = new ArrayList<>();
        Random randomGenerator = new Random();
        Particle curr;
        double randomX, randomY, randomVx, calculatedVy;
        boolean positive;
        int added = 0;

        for (int i = 0; i < amount; i++) {
            File fileStatic = new File(path + "/static" + i);
            FileWriter frStatic = new FileWriter(fileStatic);
            File fileDynamic = new File(path + "/dynamic" + i);
            FileWriter frDynamic = new FileWriter(fileDynamic);

            frStatic.append(String.valueOf(N)).append("\n");
            frStatic.append(String.valueOf(width)).append("\n");
            frStatic.append(String.valueOf(height)).append("\n");
            frStatic.append(String.valueOf(partitionOpening)).append("\n");

            while (added < N) {
                randomX = randomGenerator.nextDouble();
                randomY = randomGenerator.nextDouble();
                randomVx = randomGenerator.nextDouble() * 0.01 * (randomGenerator.nextBoolean()? 1 : -1);
                calculatedVy = Math.sqrt(Math.pow(0.01,2)-Math.pow(randomVx,2)) * (randomGenerator.nextBoolean()? 1 : -1);
                curr = new Particle(0.0015, randomVx, calculatedVy, new Point2D.Double(randomX * 0.12, randomY * 0.09), 0);
                if (notViolates(curr, particlesGenerated)) {
                    particlesGenerated.add(curr);
                    added++;
                }
            }

            frDynamic.append(String.valueOf(N)).append("\n\n");

            for (Particle particle : particlesGenerated) {
                frStatic.append(String.valueOf(particle.getRadius())).append(" 1").append("\n");
                frDynamic.append(String.valueOf(particle.getPosition().getX())).append(" ").append(String.valueOf(particle.getPosition().getY())).append(" ");
                frDynamic.append(String.valueOf(particle.getVx())).append(" ").append(String.valueOf(particle.getVy())).append(" ").append(String.valueOf(particle.getRadius())).append("\n");
            }

            frStatic.close();
            frDynamic.close();
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
        Generator generator = new Generator();

        try {
            if (argsList.size() != 12)
                throw new IllegalArgumentException();
            else {
                int particlesParameter = argsList.indexOf("-n");
                if (particlesParameter != -1)
                    generator.setN(Integer.parseInt(argsList.get(particlesParameter + 1)));

                int widthParameter = argsList.indexOf("-w");
                if (widthParameter != -1)
                    generator.setWidth(Double.parseDouble(argsList.get(widthParameter + 1)));

                int heightParameter = argsList.indexOf("-h");
                if (heightParameter != -1)
                    generator.setHeight(Double.parseDouble(argsList.get(heightParameter + 1)));

                int openingParameter = argsList.indexOf("-o");
                if (openingParameter != -1)
                    generator.setPartitionOpening(Double.parseDouble(argsList.get(openingParameter + 1)));

                int amountParameter = argsList.indexOf("-a");
                if (amountParameter != -1)
                    generator.setAmount(Integer.parseInt(argsList.get(amountParameter + 1)));

                int pathParameter = argsList.indexOf("-p");
                if (pathParameter != -1)
                    generator.setPath(argsList.get(pathParameter + 1));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameters, try: \n" +
                    "\tjava -jar generator.jar -n N -w W -h H -o O -a amount -p path\n\n" +
                    "\t-n N\n\t\t determines amount of particles\n" +
                    "\t-w W\n\t\t determines board width\n" +
                    "\t-h H\n\t\t determines board height\n" +
                    "\t-o O\n\t\t determines partition opening size\n" +
                    "\t-a amount\n\t\t determines number of inputs to generate\n" +
                    "\t-p path\n\t\t determines path were to generate inputs");
            return;
        }
        generator.generate();
    }
}
