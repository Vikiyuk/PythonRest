package com.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class ClientApplication {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        ClientApplication client = new ClientApplication();

        String employeesList = client.getEmployees(new RestTemplate());
        System.out.println("Lista pracowników:");
        System.out.println(employeesList);



        String newEmployeeData = "{\"id\": 2, \"first_name\": \"Emily\", \"last_name\": \"Davis\", \"position\": \"Designer\"}";
        String addEmployeeResponse = client.addEmployee(newEmployeeData, new RestTemplate());
        System.out.println("Dodanie nowego pracownika:");
        System.out.println(addEmployeeResponse);

        int employeeId = 1;
        String employeeInfo = client.getEmployee(employeeId, new RestTemplate());
        System.out.println("Informacje o pracowniku o ID " + employeeId + ":");
        System.out.println(employeeInfo);

        int employeeIdToUpdate = 2;
        String updatedEmployeeData = "{\"first_name\": \"Jack\", \"last_name\": \"Brown\", \"position\": \"Senior Developer\"}";
        String updateEmployeeResponse = client.updateEmployee(employeeIdToUpdate, updatedEmployeeData, new RestTemplate());
        System.out.println("Aktualizacja danych pracownika o ID " + employeeIdToUpdate + ":");
        System.out.println(updateEmployeeResponse);
    }

    @GetMapping("/getEmployees")
    public String getEmployees(RestTemplate restTemplate) {
        String url = "http://localhost:5000/employees";
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }

    @GetMapping("/getEmployee/{id}")
    public String getEmployee(@PathVariable int id, RestTemplate restTemplate) {
        String url = "http://localhost:5000/employees/" + id;
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@RequestBody String employeeData, RestTemplate restTemplate) {
        String url = "http://localhost:5000/employees";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(employeeData, headers);
        return restTemplate.postForObject(url, request, String.class);
    }

    @PutMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable int id, @RequestBody String employeeData, RestTemplate restTemplate) {
        String url = "http://localhost:5000/employees/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(employeeData, headers);
        restTemplate.put(url, request);
        return "Employee updated successfully";
    }
}
