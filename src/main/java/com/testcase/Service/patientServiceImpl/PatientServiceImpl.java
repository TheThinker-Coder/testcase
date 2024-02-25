package com.testcase.Service.patientServiceImpl;

import com.testcase.Repo.PatienRecordRepo;
import com.testcase.Service.PatientService;
import com.testcase.entity.PatientRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatienRecordRepo patienRecordRepo;
    @Override
    public PatientRecord createRecord(PatientRecord patientRecord) {
        PatientRecord patientRecord1 = new PatientRecord();
        patientRecord1.setPatientId(patientRecord.getPatientId());
        patientRecord1.setPatientName(patientRecord.getPatientName());
        patientRecord1.setPatientAge(patientRecord.getPatientAge());
        patientRecord1.setPatientAddress(patientRecord.getPatientAddress());
        patienRecordRepo.save(patientRecord1);
        return patientRecord1;
    }

    @Override
    public List<PatientRecord> getAllRecord() {
        List<PatientRecord> records = patienRecordRepo.findAll();
        List<PatientRecord> patientRecords = new ArrayList<>();
        for(PatientRecord patientRecord : records){
            PatientRecord patientRecord1 = new PatientRecord();
            patientRecord1.setPatientId(patientRecord.getPatientId());
            patientRecord1.setPatientName(patientRecord.getPatientName());
            patientRecord1.setPatientAge(patientRecord.getPatientAge());
            patientRecord1.setPatientAddress(patientRecord.getPatientAddress());
            patientRecords.add(patientRecord1);
        }
        return  patientRecords;
    }

    @Override
    public PatientRecord getpatientById(Long patientId) {
       // PatientRecord patientRecord1 = new PatientRecord();
        return patienRecordRepo.findById(patientId).get();

    }

    @Override
    public PatientRecord updateRecord(PatientRecord patientRecord, Long patientId) {
        Optional<PatientRecord> optionalDbPatient = patienRecordRepo.findById(patientId);
        if (optionalDbPatient.isPresent()) {
            PatientRecord dbPatient = optionalDbPatient.get();
            dbPatient.setPatientName(patientRecord.getPatientName());
            dbPatient.setPatientAge(patientRecord.getPatientAge());
            dbPatient.setPatientAddress(patientRecord.getPatientAddress());

            patienRecordRepo.save(dbPatient);
            return dbPatient;
        } else {

            return null;
        }
    }


    public void deleteRecord(Long patientId) {
          patienRecordRepo.deleteById(patientId);

    }


}
