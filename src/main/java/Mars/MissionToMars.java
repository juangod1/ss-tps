package Mars;

import java.io.IOException;

public class MissionToMars {

    public static void main(String[] args) throws IOException {
        int delta_t = 10;
        int ONE_YEAR_IN_SECONDS = 31540000;
        int MISSION_DELTAS = ONE_YEAR_IN_SECONDS/delta_t;
        int DELTAS_PER_DAY = 24*60*60/delta_t;

        SolarSystem s = new SolarSystem();

        // aca la idea es simular la salida de la nave en distintos dias y guardamos el tiempo que tardo
        for (int i = 0; i < MISSION_DELTAS; i += DELTAS_PER_DAY) {
            s.initialize(delta_t, MISSION_DELTAS, DELTAS_PER_DAY);
            s.simulateShip(i);
        }
    }
}
