package Supermarket.Interfaces;

import Supermarket.Agent;

import java.util.List;

public interface SupermarketCIM {

    /**
     * @param agent con las coordenadas x e y de la posicion del peatÃ³n
     * @return vecinos visibles al peatÃ³n
     * @throws InvalidPositionException si la posiciÃ³n es invÃ¡lida (un peatÃ³n no puede tener esa posiciÃ³n)
     */
    List<Agent> getNeighbours(final Agent agent) throws InvalidPositionException;

    /**
     * @param agent con las coordenadas x e y de la posicion del peatÃ³n
     * @return paredes vecinas visibles al peatÃ³n
     * @throws InvalidPositionException si la posiciÃ³n es invÃ¡lida (un peatÃ³n no puede tener esa posiciÃ³n)
     */
    List<Segment> getWalls(final Agent agent) throws InvalidPositionException;

    /**
     * Actualiza los vecinos de los agentes acorde a las posiciones actuales de los agentes dentro del supermercado
     */
    void update();

    static SupermarketCIM getInstance() {
        return null;
    }

}
