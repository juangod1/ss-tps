package cellIndexMethod;

import particle.Particle;
import particle.UnorderedParticlePair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellIndexMethod {
    public static Map<UnorderedParticlePair, Double> cellIndexMethod(
            List<Particle> particles, double areaLength, int numCells, double interactionRadius){

        Map<UnorderedParticlePair, Double> distances = new HashMap<UnorderedParticlePair, Double>();

        // Calculates in which cell each particle resides, O(N)
        Terrain terrain = new Terrain(areaLength, numCells, particles);


        for(Particle particle : particles){
            int[] particleCell = terrain.getParticleCell(particle, areaLength);

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
}
