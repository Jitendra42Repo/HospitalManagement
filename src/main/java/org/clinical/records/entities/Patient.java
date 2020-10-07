package org.clinical.records.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Patient {
	 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String last_name;
	private String first_name;
	private int age;
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER,mappedBy = "patient")
	private List<MedicalRecord> medicalRecord;
	
	public List<MedicalRecord> getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(List<MedicalRecord> medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public int getId() {
		return id;
	}
	
	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFirst_name() {
		return first_name;
	}
	public int getAge() {
		return age;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
