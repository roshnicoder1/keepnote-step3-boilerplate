package com.stackroute.keepnote.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * The class "Category" will be acting as the data model for the Category Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
@Entity
@Table(name="category")
public class Category {
	/*
	 * This class should have six fields
	 * (categoryId,categoryName,categoryDescription,
	 * categoryCreatedBy,categoryCreationDate,notes). Out of these six fields, the
	 * field categoryId should be primary key and auto-generated. This class should
	 * also contain the getters and setters for the fields along with the no-arg ,
	 * parameterized constructor and toString method. The value of
	 * categoryCreationDate should not be accepted from the user but should be
	 * always initialized with the system date. annotate notes field with @OneToMany
	 * and @JsonIgnore
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int categoryId;
	private String categoryName;
	private String categoryDescription;
	private Date categoryCreatedDate;
	private String categoryCreatedBy;
	@OneToMany
	@JsonIgnore
	private List<Note> notes;
	
	public Category() {
		super();
	}

	public Category(int Int, String string, String string1, Date date, String string2, List<Note> list) {
		this.categoryId= Int;
		this.categoryName = string;
		this.categoryDescription = string1;
		this.categoryCreatedDate = date;
		this.categoryCreatedBy = string2;
		this.notes = list;
	}

	public void setCategoryId(int Int) {
		this.categoryId = Int;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String string) {
		this.categoryName = string;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String string) {
		this.categoryDescription = string;
	}

	public void setCategoryCreationDate(Date date) {
		this.categoryCreatedDate = date;
	}

	public void setCategoryCreatedBy(String string) {
		this.categoryCreatedBy = string;
	}

	public void setNotes(List<Note> list) {
		this.notes = list;
	}

	public Date getCategoryCreatedDate() {
		return categoryCreatedDate;
	}

	public void setCategoryCreatedDate(Date categoryCreatedDate) {
		this.categoryCreatedDate = categoryCreatedDate;
	}

	public String getCategoryCreatedBy() {
		return categoryCreatedBy;
	}

	public List<Note> getNotes() {
		return notes;
	}
	
	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + ", categoryDescription="
				+ categoryDescription + ", categoryCreatedDate=" + categoryCreatedDate + ", categoryCreatedBy="
				+ categoryCreatedBy + ", notes=" + notes + "]";
	}
	
	
}