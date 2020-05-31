package Supermarket.Interfaces;

import Supermarket.Agent;

import java.util.List;
import java.util.Vector;

/*
 	INPUTS:
		Un agente, que contiene una posiciÃ³n y una velocidad.
		Un target, que contiene una posiciÃ³n.
		Una lista de vecinos de tipo agente, donde cada uno contiene una posiciÃ³n y una velocidad.
		Una lista de vecinos de tipo obstÃ¡culo (segmentos), donde cada uno contiene las posiciones de dos puntos que definen el segmento.
		Un estado, correspondiente al estado en que se encuentra el agente. Que se toma directamente del agente.

	OUTPUTS:
		Un agente con la posiciÃ³n actualizada un instante de tiempo hacia adelante.

 */
public interface OperationalModelModule {
    Agent moveAgent(Agent agent, Vector target, List<Agent> neighbours, List<Segment> walls);

    static OperationalModelModule getInstance() {
        return null;
    }
}