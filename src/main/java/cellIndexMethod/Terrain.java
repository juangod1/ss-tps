package cellIndexMethod;

import particle.Particle;

import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private Cell terrain[][];
    private double length;
    private int cellsDimension;

    public Terrain(double length, int cellsDimension, List<Particle> particles) {
        this.length = length;
        this.cellsDimension = cellsDimension;

        double lengthOfCell = length/cellsDimension;
        terrain = new Cell[cellsDimension][cellsDimension];

        for(Particle particle : particles){
            int[] particleCell = getParticleCell(particle, lengthOfCell);

            int cellColumn = particleCell[0];
            int cellRow = particleCell[1];

            if(terrain[cellRow][cellColumn] == null) {
                List<Particle> cellParticles = new ArrayList<>();
                terrain[cellRow][cellColumn] = new Cell(cellColumn, cellRow, cellParticles);
            }

            terrain[cellRow][cellColumn].getParticles().add(particle);
        }
    }

    // [ row, col ]
    public int[] getParticleCell(Particle particle, double lengthOfCell){
        return new int[]{
                (int)(particle.getPosition().y/lengthOfCell),
                (int)(particle.getPosition().x/lengthOfCell)
        };
    }

    public Cell[][] getTerrain() {
        return terrain;
    }

    public double getLength() {
        return length;
    }

    public int getCellsDimension() {
        return cellsDimension;
    }
}
