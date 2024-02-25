package com.testcase.Service;

import com.testcase.entity.PatientRecord;

import java.util.List;

public interface PatientService {

    public PatientRecord createRecord(PatientRecord patientRecord);

    public List<PatientRecord> getAllRecord();

    public  PatientRecord getpatientById(Long patientId);

    public  PatientRecord updateRecord(PatientRecord patientRecord,Long patientId);

     void deleteRecord(Long patientId);

}
