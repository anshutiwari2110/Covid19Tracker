package model;

public class StatesModel {
    private String stateName,stateCases,stateDeath,stateRecovered,stateActive;
    private String incStateDeath,incStateRecovered, incStateConfirmed,lastUpdated;

    public StatesModel(String stateName, String stateCases, String stateDeath, String stateRecovered, String stateActive, String incStateDeath, String incStateRecovered, String incStateConfirmed,String lastUpdated) {
        this.stateName = stateName;
        this.stateCases = stateCases;
        this.stateDeath = stateDeath;
        this.stateRecovered = stateRecovered;
        this.stateActive = stateActive;
        this.incStateDeath = incStateDeath;
        this.incStateRecovered = incStateRecovered;
        this.incStateConfirmed = incStateConfirmed;
        this.lastUpdated = lastUpdated;
    }
    public StatesModel(){

    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getIncStateDeath() {
        return incStateDeath;
    }

    public void setIncStateDeath(String incStateDeath) {
        this.incStateDeath = incStateDeath;
    }

    public String getIncStateRecovered() {
        return incStateRecovered;
    }

    public void setIncStateRecovered(String incStateRecovered) {
        this.incStateRecovered = incStateRecovered;
    }

    public String getIncStateConfirmed() {
        return incStateConfirmed;
    }

    public void setIncStateConfirmed(String incStateConfirmed) {
        this.incStateConfirmed = incStateConfirmed;
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
