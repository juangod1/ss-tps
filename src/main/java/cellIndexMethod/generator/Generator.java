package cellIndexMethod.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Generator {
    private int N;
    private int L;
    private int M;
    private double rc;
    private int amount;
    private String path;

    private Generator() {
        N = 0;
        L = 0;
        M = 0;
        rc = 0;
    }

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

    private void generate() throws IOException {
        Random randomGenerator = new Random();
        long now = System.currentTimeMillis();
        boolean nSent;
        boolean mSent;

        if (L == 0)
            L = 20;
        if (rc == 0)
            rc = 1;

        int start = 0;
        int end = L;

        for (int i = 0; i < amount; i++) {
            File file = new File(path + "/static" + now + i);
            FileWriter fr = new FileWriter(file, true);
            Double random;

            if (N == 0) {
                N = randomGenerator.nextInt(10000);
                nSent = false;
            } else
                nSent = true;
            fr.append(String.valueOf(N)).append("\n");

            if (M == 0) {
                M = randomGenerator.nextInt((int) (L / rc));
                mSent = false;
            } else
                mSent = true;
            fr.append(String.valueOf(M)).append("\n");

            if (!mSent)
                M = 0;
            if (!nSent)
                N = 0;

            fr.append(String.valueOf(L)).append("\n").append(String.valueOf(rc)).append("\n");
            for (int j = 0; j < N; j++) {
                fr.append(String.valueOf(0.25)).append("\n");
            }
            fr.close();

            file = new File(path + "/dynamic" + now + i);
            fr = new FileWriter(file, true);

            for (int j = 0; j < N; j++) {
                random = randomGenerator.nextDouble();
                fr.append(String.valueOf(start + (random * (end - start)))).append(" ");

                random = randomGenerator.nextDouble();
                fr.append(String.valueOf(start + (random * (end - start)))).append("\n");
            }
            fr.close();
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
        Generator generator = new Generator();

        try {
            if (argsList.size() < 2 || argsList.size() > 6)
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
                    "\tjava -jar cellIndexMethod.generator.jar [-n N] [-l L] [-m M] [-r rc] -a amount -p path\n\n" +
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
