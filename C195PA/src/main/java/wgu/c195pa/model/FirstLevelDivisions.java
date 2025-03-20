package wgu.c195pa.model;

import java.sql.Date;

public class FirstLevelDivisions {
    private int divisionID;
    private String division;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    public FirstLevelDivisions(int divisionID, String division, Date createDate, String createdBy, Date lastUpdate, String lastUpdatedBy, int countryID){
        this.division = division;
        this.divisionID = divisionID;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

//    public String getDivisionFromCustomerID(Integer divisionID) throws SQLException {
//        String stateName = null;
//        ObservableList<FirstLevelDivisions> firstLevelDivisionsList = FirstLevelDivisionsDatabaseAccess.getFirstLevelDivisions();
//        for (FirstLevelDivisions firstLevelDivision : firstLevelDivisionsList){
//            if(divisionID == firstLevelDivision.getDivisionID()){
//                stateName = firstLevelDivision.getDivision();
//                break;
//            }
//        }
//        return stateName;
//    }
}

