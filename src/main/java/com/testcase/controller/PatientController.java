package com.testcase.controller;

import com.testcase.Service.PatientService;
import com.testcase.entity.PatientRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {
  @Autowired
   private PatientService patientService;
   @PostMapping("/save")
    public PatientRecord createRecord(@RequestBody PatientRecord patientRecord){
        return patientService.createRecord(patientRecord);
    }
@GetMapping("/getAll")
public ResponseEntity<List<PatientRecord>> getAllRecord() {

        List<PatientRecord> records = patientService.getAllRecord();
        return new ResponseEntity(records, HttpStatus.OK);

}
@GetMapping("/getpatientById/{patientId}")
    public  PatientRecord getpatientById(@PathVariable("patientId") Long patientId){
    return  patientService.getpatientById(patientId);
    }
@PutMapping("/updateRecord/{patientId}")
    public  PatientRecord updateRecord(@RequestBody PatientRecord patientRecord,@PathVariable ("patientId") Long patientId) {
    return patientService.updateRecord(patientRecord,patientId);
    }
@DeleteMapping("/deleteRecord/{patientId}")
    public ResponseEntity<?> deleteRecord(@PathVariable("patientId") Long patientId){
      patientService.deleteRecord(patientId);
        return new ResponseEntity<>("this patient id deleted  " + patientId, HttpStatus.OK);
    }
}
