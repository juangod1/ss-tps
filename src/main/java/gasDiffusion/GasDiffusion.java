package gasDiffusion;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GasDiffusion {

    private static void diffuse(State state, File outputFile) throws IOException {
        // Delete file data if exists
        FileWriter f = new FileWriter(outputFile);
        f.close();

        int i=0;
        while (state.getFp() - 0.5 > Math.ulp(state.getFp())) {
            state.writeFrameToFile(outputFile);
            state.calculateNextCollision();
            state.updateParticles();
            state.updateVelocities();
            state.updateCollisions();
            if(i%1000==0) System.out.println(state.getFp());
            i++;
        }

        System.out.printf("finished in %f simulation seconds",state.time);
    }

    private static State parseInput(File staticFile, File dynamicFile) {
        List<Particle> particles = new ArrayList<>();
        double width = 0, height = 0, partitionOpening = 0;
        int N;

        try {
            Scanner scanner = new Scanner(staticFile);
            N = Integer.parseInt(scanner.nextLine());
            width = Double.parseDouble(scanner.nextLine());
            height = Double.parseDouble(scanner.nextLine());
            partitionOpening = Double.parseDouble(scanner.nextLine());

            for (int i = 0; i < N; i++) {
                String[] properties = scanner.nextLine().split(" ");
                double radius = Double.parseDouble(properties[0]);
                double mass = Double.parseDouble(properties[1]);

                particles.add(new Particle(i, radius, mass));
            }
            scanner.close();

            scanner = new Scanner(dynamicFile);
            scanner.nextLine();
            scanner.nextLine();

            for (int i = 0; i < N; i++) {
                String[] properties = scanner.nextLine().split(" ");
                double x = Double.parseDouble(properties[0]);
                double y = Double.parseDouble(properties[1]);
                double vx = Double.parseDouble(properties[2]);
                double vy = Double.parseDouble(properties[3]);
                particles.get(i).setPosition(new Point2D.Double(x,y));
                particles.get(i).setVx(vx);
                particles.get(i).setVy(vy);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new State(particles, width, height, partitionOpening);
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
        File staticFile, dynamicFile, outputFile;

        try {
            if (argsList.size() != 6)
                throw new IllegalArgumentException();
            else {
                int staticInput = argsList.indexOf("-s");
                staticFile = new File(argsList.get(staticInput+1));
                if (!staticFile.exists()) {
                    throw new IllegalArgumentException();
                }

                int dynamicInput = argsList.indexOf("-d");
                dynamicFile = new File(argsList.get(dynamicInput+1));

                if (!dynamicFile.exists()) {
                    throw new IllegalArgumentException();
                }

                int output = argsList.indexOf("-o");
                outputFile = new File(argsList.get(output+1));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameters, try: \n" +
                    "\tjava -jar CIM.jar -s path -d path -o path\n\n" +
                    "\t-s path\n\t\t determines static input path\n" +
                    "\t-d path\n\t\t determines dynamic input path\n" +
                    "\t-o path\n\t\t determines output path\n");
            return;
        }

        State state = parseInput(staticFile, dynamicFile);
        diffuse(state, outputFile);
    }
}
