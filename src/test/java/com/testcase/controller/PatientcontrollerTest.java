package com.testcase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.testcase.Repo.PatienRecordRepo;
import com.testcase.Service.PatientService;
import com.testcase.entity.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PatientcontrollerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();


    @Mock
    private PatienRecordRepo patienRecordRepo;

    @InjectMocks
    private PatientController patientcontroller;
    @Mock
    private PatientService patientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(patientcontroller).build();
    }

    PatientRecord patientRecord = new PatientRecord(1L, "demo1", 16, "demo22");
    PatientRecord patientRecord1 = new PatientRecord(2L, "demo1", 26, "demo22");
    PatientRecord patientRecord2 = new PatientRecord(3L, "demo2", 26, "demo22");

    @Test
    public void getAllRecord_success() throws Exception {
        List<PatientRecord> records = new ArrayList<>(Arrays.asList(patientRecord, patientRecord1, patientRecord2));
        Mockito.when(patientService.getAllRecord()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patient/getAll").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3))).andExpect(jsonPath("$[2].patientName", is("demo2")));

    }

    @Test
    public void getpatientById_success() throws Exception {
        // PatientRecord record = new PatientRecord(1L, "demo1", 1, "demo22");
        Mockito.when(patientService.getpatientById(patientRecord.getPatientId())).thenReturn(patientRecord);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patient/getpatientById/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue())).andExpect(jsonPath("$.patientName", is("demo1")));

    }

    @Test
    public void createRecord_success() throws Exception {
        PatientRecord patientRecord = PatientRecord.builder().patientId(1L).patientName("Demo").patientAge(26).patientAddress("demo").build();

        Mockito.when(patientService.createRecord(patientRecord)).thenReturn(patientRecord);

        String content = objectWriter.writeValueAsString(patientRecord);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/v1/patient/save").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
        mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue())).andExpect(jsonPath("$.patientName", is("Demo")));

    }

    @Test
    public void updateRecord_success() throws Exception {
        PatientRecord patientRecord = new PatientRecord(1L, "John", 30, "New Address");

        Mockito.when(patientService.updateRecord(patientRecord, 1L)).thenReturn(patientRecord);
        // String requestBody = "{\"patientName\": \"John\", \"patientAge\": 30, \"patientAddress\": \"New Address\"}";
        String requestBody = "{\"patientId\": 1, \"patientName\": \"John\", \"patientAge\": 30, \"patientAddress\": \"New Address\"}";

        mockMvc.perform(put("/api/v1/patient/updateRecord/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientName").value("John"))
                .andExpect(jsonPath("$.patientAge").value(30))
                .andExpect(jsonPath("$.patientAddress").value("New Address"));
    }

    @Test
    public void deleteRecord_success() throws Exception {
        Long patientId = 1L;
        mockMvc.perform(delete("/api/v1/patient/deleteRecord/{patientId}", patientId))
                .andExpect(status().isOk());
        Mockito.verify(patientService, times(1)).deleteRecord(patientId);
    }

    //Failure Test Case//


}
