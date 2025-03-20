package wgu.c195pa.model;

import javafx.collections.ObservableList;
import wgu.c195pa.dao.CountriesDatabaseAccess;
import wgu.c195pa.dao.FirstLevelDivisionsDatabaseAccess;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Countries {
    private int countryID;
    private String countryName;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    public Countries( int countryID, String countryName, Date createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy){
        this.countryID = countryID;
        this.countryName = countryName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int id) {
        this.countryID = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setName(String countryName) {
        this.countryName = countryName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public static String getCountryNameFromCustomerID(Integer divisionID) throws SQLException {

        int countryIDFromCustomer = 0;
        ObservableList<FirstLevelDivisions> firstLevelDivisionsList = FirstLevelDivisionsDatabaseAccess.getFirstLevelDivisions();

        for(FirstLevelDivisions firstLevelDivision : firstLevelDivisionsList){
            if(divisionID == firstLevelDivision.getDivisionID()){
                countryIDFromCustomer = firstLevelDivision.getCountryID();
                break;
            }
        }

        ObservableList<Countries> countriesList = CountriesDatabaseAccess.getCountries();
        String countryNameFromID = null;

        for(Countries country : countriesList){
            if(countryIDFromCustomer  == country.getCountryID()){
                countryNameFromID = country.getCountryName();
            }
        }
        return countryNameFromID;
    }
}