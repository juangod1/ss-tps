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
    private Vector<Double> currentDestination;
    private int queueIndex;
    private Vector<Double> queuePosition;
    private int pickingTime;

    public StateMachineImpl() {
        state = State.GOING;
        currentTargetIndex = 0;
        clock = 0;
        Random r = new Random();
        // Picking Time: 60-90 seconds
        pickingTime = r.nextInt(31) + 60;
        queuePosition = null;
    }

    public State getState() {
        return state;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    private double distance(Vector<Double> from, Vector<Double> to) {
        return Math.sqrt(Math.pow(from.get(0) - to.get(0),2) + Math.pow(from.get(1) - to.get(1),2));
    }

    public Optional<Agent> getUpdatedAgent() throws InvalidPositionException {
        SupermarketCIM cim = SupermarketCIM.getInstance();
        List<Agent> neighbours = cim.getNeighbours(agent);
        List<Segment> walls = cim.getWalls(agent);
        OperationalModelModule om = OperationalModelModule.getInstance();

        Agent updatedAgent = null;
        switch (state) {
            case GOING:
            case LEAVING:
                if (currentTargetIndex >= agent.getShoppingListSize()) {
                    Caja caja = Caja.getInstance();
                    currentDestination = caja.getSectorPosition();
                    if (distance(currentDestination, agent.getPosition()) < 0.01) {
                        if (queuePosition == null) {
                            queueIndex = caja.whereToGo();
                            currentDestination = caja.position(queueIndex);
                            queuePosition = currentDestination;
                        } else {
                            caja.add(queueIndex, agent.getShoppingListSize(), agent);
                            state = State.QUEUEING;
                        }
                    }
                } else {
                    Graph graph = Graph.getInstance();
                    Target target = agent.getTarget(currentTargetIndex);
                    currentDestination = graph.nextPoint(agent.getPosition(), target.getPosition());
                    // Target in shopping list is less than 2 meters apart
                    if (distance(currentDestination, target.getPosition()) < 2) {
                        state = State.APPROXIMATING;
                    }
                }
                updatedAgent = om.moveAgent(agent, currentDestination, neighbours, walls);

                if (state == State.LEAVING && distance(agent.getPosition(), agent.getTarget(currentTargetIndex - 1).getPosition()) > 2) {
                    state = State.GOING;
                }
                break;
            case APPROXIMATING:
                if (distance(updatedAgent.getPosition(), currentDestination) < 0.1) {
                    state = State.BUYING;
                    clock = 0;
                }
                updatedAgent = om.moveAgent(agent, currentDestination, neighbours, walls);
                break;
            case BUYING:
                //TODO: ask for dt from main
                int dt = 4;

                if (clock > pickingTime) {
                    currentTargetIndex++;
                    state = State.LEAVING;
                }
                clock += dt;
                break;
            case QUEUEING:
                Caja caja = Caja.getInstance();
                currentDestination = caja.getPositionOf(agent.getId());
                // Agent already exited supermarket
                if (currentDestination == null) return Optional.empty();
                // Agent is already in the correct position of the queue
                if (distance(currentDestination, agent.getPosition()) < 0.01) return Optional.of(agent);

                updatedAgent = om.moveAgent(agent, currentDestination, neighbours, walls);
                break;
        }
        return Optional.of(updatedAgent);
    }


}
