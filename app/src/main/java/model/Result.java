package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Result {

    public String activeCases;
    public String activeCasesNew;
    @SerializedName("regionData")
    @Expose
    public ArrayList<RegionModel> regionModels;

    public String recovered;
    public String recoveredNew;
    public String deaths;
    public String deathsNew;
    public String totalCases;
    public String sourceUrl;
    public String lastUpdatedAtApify;
    public String readMe;

}
