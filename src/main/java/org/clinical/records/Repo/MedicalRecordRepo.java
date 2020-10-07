package org.clinical.records.Repo;

import org.clinical.records.entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepo extends JpaRepository<MedicalRecord, Integer> {

}
