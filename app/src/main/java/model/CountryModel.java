package model;

import org.json.JSONObject;

public class CountryModel {

    public String cases;
    public String todayCases;
    public String deaths;
    public String todayDeaths;
    public String recovered;
    public String todayRecovered;
    public String active;
    public String critical;
    public String tests;

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

/* public static CountryModel parseJSONObject(JSONObject jsonObject){
        CountryModel indiaDetails = new CountryModel();

        indiaDetails.cases = jsonObject.optString("cases");
        indiaDetails.todayCases = jsonObject.optString("todayCases");
        indiaDetails.deaths = jsonObject.optString("deaths");
        indiaDetails.todayDeaths = jsonObject.optString("todayDeaths");
        indiaDetails.recovered = jsonObject.optString("recovered");
        indiaDetails.todayRecovered = jsonObject.optString("todayRecovered");
        indiaDetails.active = jsonObject.optString("active");
        indiaDetails.critical = jsonObject.optString("critical");
        indiaDetails.tests = jsonObject.optString("tests");


        return indiaDetails;
    }*/
}
