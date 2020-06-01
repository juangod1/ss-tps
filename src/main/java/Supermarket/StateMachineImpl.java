package Supermarket;

import Supermarket.Interfaces.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Vector;

public class StateMachineImpl implements StateMachine {
    private State state;
    private int currentTargetIndex;
    private double clock;
    private Agent agent;
    private Vector currentDestination;
    private int pickingTime;

    public StateMachineImpl() {
        state = State.GOING;
        currentTargetIndex = 0;
        clock = 0;
        Random r = new Random();
        // Picking Time: 60-90 seconds
        pickingTime = r.nextInt(31) + 60;
    }

    public State getState() {
        return state;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Optional<Agent> getUpdatedAgent() throws InvalidPositionException {
        SupermarketCIM cim = SupermarketCIM.getInstance();
        List<Agent> neighbours = cim.getNeighbours(agent);
        List<Segment> walls = cim.getWalls(agent);
        OperationalModelModule om = OperationalModelModule.getInstance();

        Agent updatedAgent = null;
        switch (state) {
            case GOING:
                if (currentTargetIndex >= agent.getShoppingListSize()) {
                    Caja caja = Caja.getInstance();
                    currentDestination = caja.getSectorPosition();
                    // TODO: change to delta
                    if (currentDestination.equals(agent.getPosition())) {
                        int indexCaja = caja.whereToGo();
                        currentDestination = caja.position(indexCaja);
                        // TODO: llamar a add
                        state = State.QUEUEING;
                    }
                } else {
                    Graph graph = Graph.getInstance();
                    Target target = agent.getTarget(currentTargetIndex);
                    currentDestination = graph.nextPoint(agent.getPosition(), target.getPosition());
                    // Product in shopping list is already visible
                    // TODO: hablarlo
                    if (currentDestination.equals(target.getPosition())) {
                        state = State.APPROXIMATING;
                        break;
                    }
                }
                updatedAgent = om.moveAgent(agent, currentDestination, neighbours, walls);
                break;
            case APPROXIMATING:
                updatedAgent = om.moveAgent(agent, currentDestination, neighbours, walls);
                // TODO: distancia
                if (updatedAgent.getPosition().equals(currentDestination)) {
                    state = State.BUYING;
                    clock = 0;
                }
                break;
            case BUYING:
                //TODO: ask for dt from main
                clock += 4;
                if (clock > pickingTime) {
                    currentTargetIndex++;
                    state = State.LEAVING;
                }
                break;
            case LEAVING:
                Graph graph = Graph.getInstance();
                currentDestination = graph.goBackToAisle(agent.getPosition());
                updatedAgent = om.moveAgent(agent, currentDestination, neighbours, walls);
                // TODO: distancia
                if (updatedAgent.getPosition().equals(currentDestination))
                    state = State.GOING;
                break;
            case QUEUEING:
                Caja caja = Caja.getInstance();
                currentDestination = caja.getPositionOf(agent.getId());
                // Agent already exited supermarket
                if (currentDestination == null) return Optional.empty();
                // Agent is already in the correct position of the queue
                // TODO: distancia
                if (currentDestination.equals(agent.getPosition())) return Optional.of(agent);

                updatedAgent = om.moveAgent(agent, currentDestination, neighbours, walls);
                break;
        }
        return Optional.of(updatedAgent);
    }


}
