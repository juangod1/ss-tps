package cellIndexMethod;

import cellIndexMethod.particle.Particle;

import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private Cell terrain[][];
    private double length;
    private int cellsDimension;
    private final int[][] directions = {{0,1},{1,-1},{1,0},{1,1}};

    public Terrain(double length, int cellsDimension, List<Particle> particles) {
        this.length = length;
        this.cellsDimension = cellsDimension;

        double lengthOfCell = length/cellsDimension;
        terrain = new Cell[cellsDimension][cellsDimension];

        for(Particle particle : particles){
            initializeParticleCells(particle, lengthOfCell, terrain);
            //initializeOverlappingParticleCells(cellIndexMethod.particle, lengthOfCell, terrain);
        }
    }

    private void initializeParticleCells(Particle particle, double lengthOfCell, Cell[][] terrain){
        int[] particleCell = getParticleCell(particle, lengthOfCell);

        int cellRow = particleCell[0];
        int cellColumn = particleCell[1];

        if(terrain[cellRow][cellColumn] == null) {
            List<Particle> cellParticles = new ArrayList<>();
            terrain[cellRow][cellColumn] = new Cell(cellRow, cellColumn, cellParticles, null);
        }

        terrain[cellRow][cellColumn].getParticles().add(particle);
    }

//    private void initializeOverlappingParticleCells(Particle cellIndexMethod.particle, double lengthOfCell, Cell[][] terrain){
//        for(Cell cell: getOverlappingParticleCells(cellIndexMethod.particle, lengthOfCell)){
//            if(cell.getOverlappingParticles() == null){
//                List<Particle> list = new ArrayList<>();
//                cell.setOverlappingParticles(list);
//            }
//
//            cell.getOverlappingParticles().add(cellIndexMethod.particle);
//        }
//    }

    private ArrayList<Cell> getOverlappingParticleCells(Particle particle, double lengthOfCell){
        ArrayList<Cell> overlaps = new ArrayList<>();

        for(int[] direction : directions){
            int[] particleCell = getParticleCell(particle, lengthOfCell);
            int[] positionCell = getPositionCell(
                    particle.getPosition().x + direction[0],
                    particle.getPosition().y + direction[1],
                    lengthOfCell);

            if(particleCell != positionCell){
                if(terrain[positionCell[0]][positionCell[1]] == null){
                    List<Particle> cellParticles = new ArrayList<>();
                    terrain[positionCell[0]][positionCell[1]] = new Cell(positionCell[0], positionCell[1], cellParticles, null);
                }

                overlaps.add(terrain[positionCell[0]][positionCell[1]]);
            }
        }

        return overlaps;
    }

    // [ row, col ]
    protected int[] getParticleCell(Particle particle, double lengthOfCell){
        return getPositionCell(particle.getPosition().x, particle.getPosition().y, lengthOfCell);
    }

    protected int[] getPositionCell(double x, double y, double lengthOfCell){
        return new int[]{
                (int)(y/lengthOfCell),
                (int)(x/lengthOfCell)
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

        } else if (row >= cellsDimension || row < 0 || col >= cellsDimension || col < 0) {
                return null;
        }

        return terrain[row][col];
    }
}
