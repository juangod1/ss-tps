package cellIndexMethod;

import particle.Particle;

import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private Cell terrain[][];
    private double length;
    private int cellsDimension;
    private int[][] directions = {{0,1},{1,-1},{1,0},{1,1}};

    public Terrain(double length, int cellsDimension, List<Particle> particles) {
        this.length = length;
        this.cellsDimension = cellsDimension;

        double lengthOfCell = length/cellsDimension;
        terrain = new Cell[cellsDimension][cellsDimension];

        for(Particle particle : particles){
            int[] particleCell = getParticleCell(particle, lengthOfCell);

            int cellRow = particleCell[0];
            int cellColumn = particleCell[1];

            if(terrain[cellRow][cellColumn] == null) {
                List<Particle> cellParticles = new ArrayList<>();
                terrain[cellRow][cellColumn] = new Cell(cellRow, cellColumn, cellParticles);
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

    public int[][] getDirections() { return directions; }

    public Cell getCellAt(int row, int col, boolean periodicContour) {
        if (periodicContour) {
            if (row < 0) row += cellsDimension;
            if (col < 0) col += cellsDimension;
            row %= cellsDimension;
            col %= cellsDimension;

        } else if (row > cellsDimension || row < 0 || col > cellsDimension || col < 0) {
                return null;
        }

        return terrain[row][col];
    }
}
