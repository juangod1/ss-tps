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
    private static Map<UnorderedParticlePair, Double> cellIndexMethodMap(State state){

        Map<UnorderedParticlePair, Double> distances = new HashMap<>();

        // Calculates in which cell each particle resides, O(N)
        Terrain terrain = new Terrain(state.getAreaLength(), state.getNumCells(), state.getParticles());


        for(Particle particle : state.getParticles()){
            int[] particleCell = terrain.getParticleCell(particle, terrain.getCellsDimension());

            int cellColumn = particleCell[1];
            int cellRow = particleCell[0];

            List<Cell> neighbors = getHalfOfNeighborCells(terrain, terrain.getTerrain()[cellRow][cellColumn], state.getPeriodicContour());

            for(Cell neighbor : neighbors){
                for(Particle particleInNeighbor : neighbor.getParticles()){
                    double distance = particle.getDistanceModule(particleInNeighbor);

                    UnorderedParticlePair pair = new UnorderedParticlePair(particle, particleInNeighbor);
                    if(!distances.containsKey(pair) && checkIfParticlesInteract(particle, particleInNeighbor, state, distance)){
                        distances.put(pair, distance);
                    }
                }
            }
        }

        return distances;
    }

    public static Map<Particle, List<Particle>> cellIndexMethod(State state){
        Map<UnorderedParticlePair, Double> map = cellIndexMethodMap(state);

        Map<Particle, List<Particle>> finalMap = new HashMap<>();

        for(UnorderedParticlePair particlePair : map.keySet()){
            for(Particle particle : particlePair.getPair()){
                if(!finalMap.containsKey(particle)){
                    finalMap.put(particle, new ArrayList<>());
                }

                finalMap.get(particle).add(particlePair.getOtherParticle(particle));
            }
        }

        return finalMap;
    }

    private static boolean checkIfParticlesInteract(Particle p1, Particle p2, State state, double distance){
        return p1 != p2 &&
            distance <= state.getInteractionRadius() + p1.getRadius() + p2.getRadius();
    }

    private static List<Cell> getHalfOfNeighborCells(Terrain terrain, Cell cell, boolean periodicContour){
        List<Cell> neighbors = new ArrayList<>();

        for (int i = 0; i < terrain.getDirections().length; i++) {
            int row = cell.getRow() + terrain.getDirections()[i][0];
            int col = cell.getColumn() + terrain.getDirections()[i][1];

            Cell neighbor = terrain.getCellAt(row,col, periodicContour);
            if (neighbor != null)
                neighbors.add(neighbor);
        }

        neighbors.add(cell);

        return neighbors;
    }

    private static State parseInput(File staticFile, File dynamicFile) {
        List<Particle> particles = new ArrayList<>();
        double areaLength = 0;
        int numCells = 0;
        double interactionRadius = 0;

        try {
            Scanner scanner = new Scanner(staticFile);
            int numParticles = Integer.parseInt(scanner.nextLine());
            areaLength = Integer.parseInt(scanner.nextLine());
            numCells = Integer.parseInt(scanner.nextLine());
            interactionRadius = Double.parseDouble(scanner.nextLine());

            for (int i = 0; i < numParticles; i++) {
                particles.add(new ParticleImpl(Double.parseDouble(scanner.nextLine()), i));
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

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
        File staticFile, dynamicFile;
        boolean periodicContour = true;

        try {
            if (argsList.size() < 4 || argsList.size() > 5)
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

                int periodic = argsList.indexOf("-p");

                if (periodic == -1)
                    periodicContour = false;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameters, try: \n" +
                    "\tjava -jar CIM.jar -s path -d path\n\n" +
                    "\t-s path\n\t\t determines static input path\n" +
                    "\t-d path\n\t\t determines dynamic input path\n" +
                    "\t-p\n\t\t sets periodic contour true\n");
            return;
        }

        State state = parseInput(staticFile, dynamicFile);
        state.setPeriodicContour(periodicContour);
        Map<Particle, List<Particle>> solution = cellIndexMethod(state);

        // TODO: write result in file in output

        long endTime = System.nanoTime();
        // get difference of two nanoTime values
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
    }
}
