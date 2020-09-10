package model;

public class StatesModel {
    private String stateName,stateCases,stateDeath,stateRecovered,stateActive;

    public StatesModel() {
    }

    public StatesModel(String stateName, String stateCases, String stateDeath, String stateRecovered,String stateActive) {
        this.stateName = stateName;
        this.stateCases = stateCases;
        this.stateDeath = stateDeath;
        this.stateRecovered = stateRecovered;
        this.stateActive = stateActive;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateCases() {
        return stateCases;
    }

    public void setStateCases(String stateCases) {
        this.stateCases = stateCases;
    }

    public String getStateDeath() {
        return stateDeath;
    }

    public void setStateDeath(String stateDeath) {
        this.stateDeath = stateDeath;
    }

    public String getStateRecovered() {
        return stateRecovered;
    }

    public void setStateRecovered(String stateRecovered) {
        this.stateRecovered = stateRecovered;
    }

    public String getStateActive() {
        return stateActive;
    }

    public void setStateActive(String stateActive) {
        this.stateActive = stateActive;
    }
}
