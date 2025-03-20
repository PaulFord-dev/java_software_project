package wgu.c195pa.model;

import javafx.collections.ObservableList;
import wgu.c195pa.dao.ContactsDatabaseAccess;

import java.sql.SQLException;

public class Contacts {
    private int contactID;
    private  String contactName;
    private String email;

    public Contacts(int contactID, String contactName, String email){
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


public static String getContactNameFromID(Integer contactID) throws SQLException {
    String contactName = null;
    ObservableList<Contacts> contactNames = ContactsDatabaseAccess.getContacts();
    for(Contacts contact : contactNames){
        if(contactID == contact.getContactID()){
            contactName = contact.getContactName();
            break;
        }
    }
    return contactName;
}
}
