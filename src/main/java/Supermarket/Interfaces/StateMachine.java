package Supermarket.Interfaces;

import Supermarket.Agent;

import java.util.Optional;

// Each agent in the supermarket contains a state machine
public interface StateMachine {

    // This function should be called by the main method for each agent, on each time interval.
    // Communicates with the operational model and returns a new, updated agent for the main method to update all of the agents synchronously.
    //     Returns the new Agent if the agent has something to do
    //     Returns Optional.empty when the agent leaves the supermarket
    Optional<Agent> getUpdatedAgent() throws InvalidPositionException;

    // Returns the current state of the state machine
    State getState();

    void setAgent(Agent agent);

    enum State {
        GOING,
        APPROXIMATING,
        BUYING,
        LEAVING,
        QUEUEING
    }
}
