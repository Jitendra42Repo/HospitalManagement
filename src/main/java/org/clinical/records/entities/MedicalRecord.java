package org.clinical.records.entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "medicaldata")
@Table(name = "medicaldata")
@JsonIgnoreProperties({"patient"})
public class MedicalRecord {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String component_name;
	private String component_value;
	private Timestamp measured_timestamp;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;
	
	
	public int getId() {
		return id;
	}
	public String getComponent_name() {
		return component_name;
	}
	public String getComponent_value() {
		return component_value;
	}
	
	public Patient getPatient() {
		return patient;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setComponent_name(String component_name) {
		this.component_name = component_name;
	}
	public void setComponent_value(String component_value) {
		this.component_value = component_value;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Timestamp getMeasured_timestamp() {
		return measured_timestamp;
	}
	public void setMeasured_timestamp(Timestamp measured_timestamp) {
		this.measured_timestamp = measured_timestamp;
	}
	
	
}
