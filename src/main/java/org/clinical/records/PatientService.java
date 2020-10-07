package org.clinical.records;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.clinical.records.Repo.MedicalRecordRepo;
import org.clinical.records.Repo.PatientRepo;
import org.clinical.records.entities.MedicalRecord;
import org.clinical.records.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/patient")
@Consumes("application/json")
@Produces("application/json")
@CrossOriginResourceSharing(allowAllOrigins = true)
public class PatientService {
	
	@Autowired
	PatientRepo patientRepo;
	
	@Autowired
	MedicalRecordRepo medicalRepo;
	
	@Path("/getPatients")
	@GET 
	public List<Patient> getPatients(){
		return patientRepo.findAll();
	}
	
	@Path("getPatient/{id}")
	@GET 
	public Patient getPatient(@PathParam ("id") int id) {
		return patientRepo.findById(id).get();
	}
	
	
	@POST
	@Path("/addPatient")
	public Response addPatient(Patient patient) {
		Patient newPatinet = patientRepo.save(patient);
		return Response.ok(newPatinet).build();
	}
	
	@PUT
	@Path("/updatePatient")
	public Response updatePatient(Patient patient) {
		Patient repoPatient = patientRepo.findById(patient.getId()).get();
		Response response;
		if(null == repoPatient) {
			response = Response.notModified().build();
		}else {
			 Patient patient2 = patientRepo.save(patient);
			 response = Response.ok(patient2).build();
		}
		return response;
	}
	
	@DELETE
	@Path("/deletePatient/{id}")
	public Response deletePatient(@PathParam("id") int id) {
		Patient patient = patientRepo.findById(id).get();
		Response response;
		if(null == patient) {
			response = Response.status(Status.NOT_FOUND).build();
		}
		else {
			patientRepo.deleteById(id);
			response = Response.ok().build();
		}
		return response;
	}

	@Path("/analyze/{id}")
	@GET
	public Response analyzeBMI(@PathParam("id")int id) {
		Patient patient= null;
		try {
			patient = patientRepo.findById(id).get();
		}catch(Exception e){
			System.out.println(e.getCause());
		}
		
		if(null == patient) {
			return Response.status(Status.NOT_FOUND).entity("Patient id: "+id+", not found.").build();
		}
		
		
		List<MedicalRecord> medicalRecord = new ArrayList<>(patient.getMedicalRecord()) ;
		
		Response response = null ;
		boolean flag = true;
		
		for (MedicalRecord record : medicalRecord) {
			
			if(record.getComponent_name().equalsIgnoreCase("BMI")) {
				
				response = Response.ok(record).build();
				System.out.println("BMI record alredy exists.");
				flag = false;
				break;
			}
		}
		
		if(flag) {
			response = calculateBMI(medicalRecord, patient);
			if(null == response) {
				response= Response.status(Status.NOT_FOUND).entity("HW component data doesn't exists for patient.").build();
			}
		}
		
		return response;
		
		
	}
	
	private Response calculateBMI(List<MedicalRecord> medicalRecord, Patient patient ) {
		
		Response response = null;
		
		for (MedicalRecord record : medicalRecord) {
			
			if(record.getComponent_name().equalsIgnoreCase("HW")) {
				String[] heightWeight = record.getComponent_value().split("/");
				double height = Double.valueOf(heightWeight[0]) ;
				double value = 0.4536;
				height *= value;
				
				height *=height;
				Double bmi = Double.valueOf(heightWeight[1])/height ;
				System.out.println("Calculated BMI is: "+ bmi);
				
				MedicalRecord entryRecord  = new MedicalRecord();
				entryRecord.setPatient(patient);
				entryRecord.setComponent_name("BMI");
				entryRecord.setComponent_value(bmi.toString());
				
				MedicalRecord savedRecord = medicalRepo.save(entryRecord);
				
				response = Response.ok(savedRecord).build();
			}
			
		}
		
		return response;
		
	}
}
