package Supermarket;

import Supermarket.Interfaces.StateMachine;
import Supermarket.Interfaces.Target;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Agent {
    private int id;
    private Vector<Double> position;
    private Vector<Double> velocity;
    private List<Target> shoppingList;
    private StateMachine stateMachine;

    public Agent(int id, Vector<Double> position, Vector<Double> velocity, List<Target> shoppingList, StateMachine stateMachine) {
        this.id = id;
        this.position = position;
        this.velocity = velocity;
        this.shoppingList = shoppingList;
        this.stateMachine = stateMachine;
        this.stateMachine.setAgent(this);
    }

    public StateMachine.State getAgentState(){
        return stateMachine.getState();
    }

    public int getId() {
        return id;
    }

    public Vector<Double> getPosition() {
        return position;
    }

    public void setPosition(Vector<Double> position) {
        this.position = position;
    }

    public Vector<Double> getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector<Double> velocity) {
        this.velocity = velocity;
    }

    public int getShoppingListSize() {
        return shoppingList.size();
    }

    public Target getTarget(int index) {
        return shoppingList.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agent)) return false;
        Agent agent = (Agent) o;
        return getId() == agent.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}