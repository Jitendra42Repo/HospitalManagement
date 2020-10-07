package org.clinical.records.dto;

import java.sql.Timestamp;

public class MedicalRecordRequest {

	private int patient_id;
	private String component_name;
	private String component_value;
	private Timestamp measured_timestamp;
	
	
	public int getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}
	public String getComponent_name() {
		return component_name;
	}
	public void setComponent_name(String component_name) {
		this.component_name = component_name;
	}
	public String getComponent_value() {
		return component_value;
	}
	public void setComponent_value(String component_value) {
		this.component_value = component_value;
	}
	public Timestamp getMeasured_timestamp() {
		return measured_timestamp;
	}
	public void setMeasured_timestamp(Timestamp measured_timestamp) {
		this.measured_timestamp = measured_timestamp;
	}
	
	
	
}
