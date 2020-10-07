package org.clinical.records;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.clinical.records.Repo.MedicalRecordRepo;
import org.clinical.records.Repo.PatientRepo;
import org.clinical.records.dto.MedicalRecordRequest;
import org.clinical.records.entities.MedicalRecord;
import org.clinical.records.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Consumes("application/json")
@Produces("application/json")
@Path("/healthRecords")
@CrossOriginResourceSharing(allowAllOrigins = true)
public class MedicalRecordService {
	
	@Autowired
	MedicalRecordRepo medicalRepo;
	
	@Autowired
	PatientRepo repo;
	
	@Path("/saveRecord")
	@POST
	public Response saveMedicalRecord(MedicalRecordRequest request) {
		Response response;
		Patient patient = repo.findById(request.getPatient_id()).get();
		
		if(null == patient) {
			return Response.status(Status.NOT_FOUND).build();
		}else {
			MedicalRecord medicalRecord = new MedicalRecord();
			medicalRecord.setPatient(patient);
			medicalRecord.setComponent_name(request.getComponent_name());
			medicalRecord.setComponent_value(request.getComponent_value());
			if(null != request.getMeasured_timestamp()) {
				medicalRecord.setMeasured_timestamp(request.getMeasured_timestamp());
			}
			medicalRepo.save(medicalRecord);
			
			response = Response.ok(medicalRecord).build();
		}
		return response;
	}
	
}
