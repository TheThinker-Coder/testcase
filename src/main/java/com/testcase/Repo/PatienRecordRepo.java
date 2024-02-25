package com.testcase.Repo;

import com.testcase.entity.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatienRecordRepo extends JpaRepository<PatientRecord,Long> {
}
