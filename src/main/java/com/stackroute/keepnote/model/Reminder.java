package com.stackroute.keepnote.model;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnore;
/*
 * The class "Reminder" will be acting as the data model for the Reminder Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
@Entity
@Component
@Table(name="ReminderData")
public class Reminder {
    /*
     * This class should have seven fields
     * (reminderId,reminderName,reminderDescription,reminderType,
     * reminderCreatedBy,reminderCreationDate,notes). Out of these seven fields, the
     * field reminderId should be primary key and auto-generated. This class should
     * also contain the getters and setters for the fields along with the no-arg ,
     * parameterized constructor and toString method. The value of
     * reminderCreationDate should not be accepted from the user but should be
     * always initialized with the system date. annotate notes field with @OneToMany
     * and @JsonIgnore
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reminderId;
    private String reminderName;
    private String reminderDescription;
    private String reminderType;
    private String reminderCreatedBy;
    @OneToMany
    @JsonIgnore
    private List<Note> Notes;
    private Date reminderCreationDate;
    
    
    public Reminder() {
        super();
    }
//  public Reminder(int Int, String string, String string1, String string2, String string3, List<Note> list,
//          Date date) {
//  }
    
    
    
    public Reminder(int reminderId, String reminderName, String reminderDescription, String reminderType,
        String reminderCreatedBy, List<Note> notes, Date reminderCreationDate) {
    super();
    this.reminderId = reminderId;
    this.reminderName = reminderName;
    this.reminderDescription = reminderDescription;
    this.reminderType = reminderType;
    this.reminderCreatedBy = reminderCreatedBy;
    Notes = notes;
    this.reminderCreationDate = reminderCreationDate;
}
    public int getReminderId() {
        return reminderId;
    }
    
    public void setReminderId(int Int) {
        this.reminderId=Int;
    }
    
    public String getReminderName() {
        return reminderName;
    }
    
    public void setReminderName(String string) {
        this.reminderName=string;
    }
    
    public String getReminderDescription() {
        return reminderDescription;
    }
    public void setReminderDescription(String string) {
        this.reminderDescription=string;
    }
    public String getReminderType() {
        return reminderType;
    }
    
    public void setReminderType(String string) {
        this.reminderType=string;
    }
    
    public Date getReminderCreationDate() {
        return reminderCreationDate;
    }
    public void setReminderCreationDate(Date date) {
        this.reminderCreationDate=date;
    }
    public String getReminderCreatedBy() {
        return reminderCreatedBy;
    }
    
    public void setReminderCreatedBy(String string) {
        this.reminderCreatedBy=string;
    }
    public List<Note> getNotes() {
        return Notes;
    }
    public void setNotes(List<Note> list) {
        this.Notes=list;
    }
    @Override
    public String toString() {
        return "Reminder [reminderId=" + reminderId + ", reminderName=" + reminderName + ", reminderDescription="
                + reminderDescription + ", reminderType=" + reminderType + ", reminderCreatedBy=" + reminderCreatedBy
                + ", Notes=" + Notes + ", reminderCreationDate=" + reminderCreationDate + "]";
    }
    
}