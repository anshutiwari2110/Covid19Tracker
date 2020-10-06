package model;

public class TestingDataModel {
    String dailyRTPCR,dailySampleReport,testedAsOf,testPositivityRate,testPerMillion,totalSampleTested;

    public TestingDataModel(String dailyRTPCR, String dailySampleReport, String testedAsOf, String testPositivityRate, String testPerMillion, String totalSampleTested) {
        this.dailyRTPCR = dailyRTPCR;
        this.dailySampleReport = dailySampleReport;
        this.testedAsOf = testedAsOf;
        this.testPositivityRate = testPositivityRate;
        this.testPerMillion = testPerMillion;
        this.totalSampleTested = totalSampleTested;
    }
    public TestingDataModel(){

    }

    public String getDailyRTPCR() {
        return dailyRTPCR;
    }

    public void setDailyRTPCR(String dailyRTPCR) {
        this.dailyRTPCR = dailyRTPCR;
    }

    public String getDailySampleReport() {
        return dailySampleReport;
    }

    public void setDailySampleReport(String dailySampleReport) {
        this.dailySampleReport = dailySampleReport;
    }

    public String getTestedAsOf() {
        return testedAsOf;
    }

    public void setTestedAsOf(String testedAsOf) {
        this.testedAsOf = testedAsOf;
    }

    public String getTestPositivityRate() {
        return testPositivityRate;
    }

    public void setTestPositivityRate(String testPositivityRate) {
        this.testPositivityRate = testPositivityRate;
    }

    public String getTestPerMillion() {
        return testPerMillion;
    }

    public void setTestPerMillion(String testPerMillion) {
        this.testPerMillion = testPerMillion;
    }

    public String getTotalSampleTested() {
        return totalSampleTested;
    }

    public void setTotalSampleTested(String totalSampleTested) {
        this.totalSampleTested = totalSampleTested;
    }
}
