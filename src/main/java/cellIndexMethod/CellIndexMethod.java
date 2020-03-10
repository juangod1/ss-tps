package cellIndexMethod;

import cellIndexMethod.particle.Particle;
import cellIndexMethod.particle.ParticleImpl;
import cellIndexMethod.particle.UnorderedParticlePair;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;
import java.util.List;

public class CellIndexMethod {
    private static Map<UnorderedParticlePair, Double> cellIndexMethodMap(State state){

        Map<UnorderedParticlePair, Double> distances = new HashMap<>();

        // Calculates in which cell each cellIndexMethod.particle resides, O(N)
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
        int numParticles;
        double interactionRadius = 0;
        double maxRadius = 0;

        try {
            Scanner scanner = new Scanner(staticFile);
            numParticles = Integer.parseInt(scanner.nextLine());
            areaLength = Integer.parseInt(scanner.nextLine());
            numCells = Integer.parseInt(scanner.nextLine());
            interactionRadius = Double.parseDouble(scanner.nextLine());

            for (int i = 0; i < numParticles; i++) {
                double radius = Double.parseDouble(scanner.nextLine());
                particles.add(new ParticleImpl(radius, i));
                if (radius>maxRadius)
                    maxRadius = radius;
            }

            scanner.close();

            scanner = new Scanner(dynamicFile);
            scanner.nextLine();
            scanner.nextLine();
            for (int i = 0; i < numParticles; i++) {
                String position = scanner.nextLine();
                String[] coordinates = position.split(" ");
                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);
                particles.get(i).setPosition(new Point2D.Double(x,y));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if ((areaLength/numCells) > (interactionRadius + 2 * maxRadius))
            return new State(particles, areaLength, numCells, interactionRadius);
        return null;
    }

    public static void writeSolution(Map<Particle, List<Particle>> solution, File output, int id, File dynamic) throws IOException {
        FileWriter fr = new FileWriter(output, false);
        RandomAccessFile raf = new RandomAccessFile(dynamic, "rw");
        long pointer;
        String lineData;
        int line = -1;
        List<Integer> ids = new ArrayList<>();;

        for (Map.Entry<Particle, List<Particle>> entry : solution.entrySet()) {
            fr.append(entry.getKey().toString()).append(" ");
            for (Particle proximate : entry.getValue()) {
                fr.append(proximate.toString()).append(" ");
                if (entry.getKey().getId() == id) {
                    ids.add(proximate.getId());
                }
            }
            fr.append("\n");
            if (entry.getKey().getId() == id) {
                while ((lineData = raf.readLine()) != null) {
                    if (lineData.length() == 0)
                        continue;
                    if (line == id) {
                        lineData = lineData.replace(" 140", " 255");
                        lineData = lineData.replace(" 137", " 000");
                        lineData = lineData.replace(" 136", " 000");
                        pointer = raf.getFilePointer() - lineData.length()-2;
                        raf.seek(pointer);
                        raf.writeBytes("\n" + lineData);
                    } else if (ids.contains(line)) {
                        lineData = lineData.replace(" 140", " 000");
                        lineData = lineData.replace(" 137", " 000");
                        lineData = lineData.replace(" 136", " 204");
                        pointer = raf.getFilePointer() - lineData.length()-2;
                        raf.seek(pointer);
                        raf.writeBytes("\n" + lineData);
                    }
                    line++;
                }
            }
        }
        fr.close();
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
        File staticFile, dynamicFile, outputFile;
        boolean periodicContour = true;
        int id = 0;

        try {
            if (argsList.size() < 6 || argsList.size() > 9)
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

                int output = argsList.indexOf("-o");

                if (output == -1) {
                    throw new IllegalArgumentException();
                }

                outputFile = new File(argsList.get(output+1));

                int idIndex = argsList.indexOf("-i");
                id = Integer.parseInt(argsList.get(idIndex+1));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid parameters, try: \n" +
                    "\tjava -jar CIM.jar -s path -d path [-p] -o path -i id\n\n" +
                    "\t-s path\n\t\t determines static input path\n" +
                    "\t-d path\n\t\t determines dynamic input path\n" +
                    "\t-p\n\t\t sets periodic contour true\n" +
                    "\t-o path\n\t\t determines output path\n" +
                    "\t-i id\n\t\t determines the id of the particle to color\n");
            return;
        }

        State state = parseInput(staticFile, dynamicFile);
        if (state == null) {
            System.out.println("Invalid number of cells\n");
            return;
        }
        state.setPeriodicContour(periodicContour);
        Map<Particle, List<Particle>> solution = cellIndexMethod(state);

        writeSolution(solution, outputFile, id, dynamicFile);

        long endTime = System.nanoTime();
        // get difference of two nanoTime values
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
    }
}
