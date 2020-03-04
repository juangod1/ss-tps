package generator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Generator {
    private int N;
    private int L;
    private int M;
    private double rc;
    private int amount;
    private String path;

    public Generator() {}

    public void setN(int n) {
        N = n;
    }

    public void setL(int l) {
        L = l;
    }

    public void setM(int m) {
        M = m;
    }

    public void setRc(double rc) {
        this.rc = rc;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPath(String path) { this.path = path; }

    public static void generate() {

    }

    public static void main(String[] args) {
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
        Generator generator = new Generator();

        try {
            if (argsList.size() != 1 && argsList.size() != 5)
                throw new IllegalArgumentException();
            else {
                int particlesParameter = argsList.indexOf("-n");
                if (particlesParameter == -1)
                    throw new IllegalArgumentException();
                generator.setN(Integer.parseInt(argsList.get(particlesParameter + 1)));

                int lengthParameter = argsList.indexOf("-l");
                if (lengthParameter == -1)
                    throw new IllegalArgumentException();
                generator.setL(Integer.parseInt(argsList.get(lengthParameter + 1)));

                int cellParameter = argsList.indexOf("-m");
                if (cellParameter == -1)
                    throw new IllegalArgumentException();
                generator.setM(Integer.parseInt(argsList.get(cellParameter + 1)));

                int radiusParameter = argsList.indexOf("-r");
                if (radiusParameter == -1)
                    throw new IllegalArgumentException();
                generator.setRc(Integer.parseInt(argsList.get(radiusParameter + 1)));

                int amountParameter = argsList.indexOf("-a");
                if (amountParameter == -1)
                    throw new IllegalArgumentException();
                generator.setAmount(Integer.parseInt(argsList.get(amountParameter + 1)));

                int pathParameter = argsList.indexOf("-p");
                if (pathParameter == -1)
                    throw new IllegalArgumentException();
                generator.setPath(argsList.get(pathParameter + 1));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameters, try: \n" +
                    "\tjava -jar generator.jar [-n N -l L -m M -r rc] -a amount -p path\n\n" +
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
