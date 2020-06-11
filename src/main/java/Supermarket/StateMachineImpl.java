package Supermarket;

import Supermarket.Interfaces.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Vector;

import static java.lang.Thread.sleep;

public class StateMachineImpl implements StateMachine {
    private State state;
    private int currentTargetIndex;
    private double clock;
    private Agent agent;
    public Vector<Double> currentDestination;
    public Vector<Double> target;
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

    public String stateToColorString(State s){
        switch (s){
            case GOING:
                return " 1 1 0\n";
            case APPROXIMATING:
                return " 1 0 0\n";
            case LEAVING:
                return " 1 0 1\n";
            case BUYING:
                return " 0 1 0\n";
            default:
                return " 0 0 0\n";
        }
    }

    public static void main(String args[]) throws InvalidPositionException, IOException {
        StateMachineImpl sm = new StateMachineImpl();

        Vector<Double> v1 = new Vector<>(0,0);v1.add(0,0.0);v1.add(1,20.0);
        Vector<Double> v2 = new Vector<>(0,0);v2.add(0,0.0);v2.add(1,0.0);

        Agent agent = new Agent(1,v1,v2,null,sm);

        Vector<Double> asdf = new Vector<>(0,0);asdf.add(0,0.0);asdf.add(1,20.0);
        sm.currentDestination = asdf;

        Vector<Double> fdsa = new Vector<>(0,0);fdsa.add(0,0.0);fdsa.add(1,10.0);
        sm.target = fdsa;

        FileWriter f = new FileWriter("./out", false);

        while(agent.getAgentState() != State.QUEUEING){
            f.append(String.valueOf(3)).append("\n\n");
            f.append(String.valueOf(agent.getPosition().get(0))).append(" ").append(String.valueOf(agent.getPosition().get(1))).append(" ").append(sm.stateToColorString(sm.getState()));
            f.append(String.valueOf(sm.target.get(0))).append(" ").append(String.valueOf(sm.target.get(1))).append(" ").append(" 1 1 1\n");
            f.append(String.valueOf(v2.get(0))).append(" ").append(String.valueOf(v2.get(1))).append(" ").append(" 1 1 1\n");

            sm.getUpdatedAgent().get();
        }
        f.append(String.valueOf(3)).append("\n\n");
        f.append(String.valueOf(agent.getPosition().get(0))).append(" ").append(String.valueOf(agent.getPosition().get(1))).append(" ").append(" 0 0 1\n");
        f.append(String.valueOf(sm.target.get(0))).append(" ").append(String.valueOf(sm.target.get(1))).append(" ").append(" 1 1 1\n");
        f.append(String.valueOf(v2.get(0))).append(" ").append(String.valueOf(v2.get(1))).append(" ").append(" 1 1 1\n");
        f.close();
    }
    public boolean flag=false;
    public Optional<Agent> getUpdatedAgent(){
        Agent updatedAgent = null;

        switch (state) {
            case GOING:
            case LEAVING:
                if (flag) {

                    Vector<Double> caja = new Vector<>(0,0);caja.add(0,0.0);caja.add(1,0.0);

                    currentDestination.set(1, currentDestination.get(1)-0.1);

                    if (distance(caja, agent.getPosition()) < 0.01) {
                        state = State.QUEUEING;
                    }
                } else {
                    currentDestination.set(1, currentDestination.get(1)-0.1);
                    // Target in shopping list is less than 2 meters apart
                    if (distance(currentDestination, target) <= 2 && state == State.GOING) {
                        state = State.APPROXIMATING;
                    }
                }
                agent.setPosition(currentDestination);

                if (state == State.LEAVING && distance(agent.getPosition(), target) > 2) {
                    state = State.GOING;
                    flag = true;
                }
                break;
            case APPROXIMATING:
                currentDestination.set(1, currentDestination.get(1)-0.1);
                if (distance(agent.getPosition(), target) < 0.1) {
                    state = State.BUYING;
                    clock = 0;
                }
                agent.setPosition(currentDestination);
                break;
            case BUYING:
                clock+=1;
                if(clock>50)
                    state = State.LEAVING;
                break;
            case QUEUEING:
                break;
        }
        return Optional.of(agent);
    }


}
