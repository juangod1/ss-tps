package cellIndexMethod;

import particle.Particle;
import particle.ParticleImpl;
import particle.UnorderedParticlePair;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class CellIndexMethod {
    public static Map<UnorderedParticlePair, Double> cellIndexMethod(State state){

        Map<UnorderedParticlePair, Double> distances = new HashMap<UnorderedParticlePair, Double>();

        // Calculates in which cell each particle resides, O(N)
        Terrain terrain = new Terrain(state.getAreaLength(), state.getNumCells(), state.getParticles());


        for(Particle particle : state.getParticles()){
            int[] particleCell = terrain.getParticleCell(particle, state.getAreaLength());

            int cellColumn = particleCell[1];
            int cellRow = particleCell[0];

            List<Cell> neighbors = getHalfOfNeighborCells(terrain, terrain.getTerrain()[cellRow][cellColumn]);

            for(Cell neighbor : neighbors){
                for(Particle particleInNeighbor : neighbor.getParticles()){
                    UnorderedParticlePair pair = new UnorderedParticlePair(particle, particleInNeighbor);
                    if(!distances.containsKey(pair)){
                        distances.put(pair, particle.getDistanceModule(particleInNeighbor));
                    }
                }
            }
        }

        return distances;
    }

    private static List<Cell> getHalfOfNeighborCells(Terrain terrain, Cell cell){
        return null;
    }

    private static State parseInput(File staticFile, File dynamicFile) {
        List<Particle> particles = new ArrayList<Particle>();
        double areaLength = 0;
        int numCells = 0;
        double interactionRadius = 0;

        try {
            Scanner scanner = new Scanner(staticFile);
            int numParticles = Integer.parseInt(scanner.nextLine());
            areaLength = Integer.parseInt(scanner.nextLine());
            numCells = Integer.parseInt(scanner.nextLine());
            interactionRadius = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < numParticles; i++) {
                particles.add(new ParticleImpl(Integer.parseInt(scanner.nextLine())));
            }

            scanner.close();

            scanner = new Scanner(dynamicFile);
            for (int i = 0; i < numParticles; i++) {
                String position = scanner.nextLine();
                String[] coordenates = position.split(" ");
                int x = Integer.parseInt(coordenates[0]);
                int y = Integer.parseInt(coordenates[1]);
                particles.get(i).setPosition(new Point(x,y));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new State(particles, areaLength, numCells, interactionRadius);
    }

    public static void main(String args[]) {
        long startTime = System.nanoTime();
        ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));
        File staticFile, dynamicFile;

        try {
            if (argsList.size() != 4)
                throw new IllegalArgumentException();
            else {
                int staticInput = argsList.indexOf("-s");

                if (staticInput == -1) {
                    throw new IllegalArgumentException();
                }

                staticFile = new File(argsList.get(staticInput+1));

                if (!staticFile.exists()) {
                    throw new IllegalArgumentException();
                }

                int dynamicInput = argsList.indexOf("-d");

                if (dynamicInput == -1) {
                    throw new IllegalArgumentException();
                }

                dynamicFile = new File(argsList.get(dynamicInput+1));

                if (!dynamicFile.exists()) {
                    throw new IllegalArgumentException();
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameters, try: \n" +
                    "\tjava -jar CIM.jar -s path -d path\n\n" +
                    "\t-s path\n\t\t determines static input path\n" +
                    "\t-d path\n\t\t determines dynamic input path\n");
            return;
        }

        State state = parseInput(staticFile, dynamicFile);
        cellIndexMethod(state);
        
        // TODO: write result in file in output

        long endTime = System.nanoTime();
        // get difference of two nanoTime values
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
    }
}
