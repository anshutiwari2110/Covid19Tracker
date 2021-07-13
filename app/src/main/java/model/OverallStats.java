package model;

public class OverallStats {
    String labels;
    float cases;

    public OverallStats(String labels, float cases) {
        this.labels = labels;
        this.cases = cases;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public float getCases() {
        return cases;
    }

    public void setCases(float cases) {
        this.cases = cases;
    }
}


