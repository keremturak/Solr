package org.example.controller;

import lombok.AllArgsConstructor;
import org.apache.solr.client.solrj.SolrServerException;
import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    @PostMapping("/save")
    public Employee save(@RequestBody  Employee employee) throws SolrServerException, IOException {
        return employeeRepository.save(employee);
    }
    @GetMapping("/get-by-updated-time")
    public List<Employee> getByUpdatedTime(LocalDate date) throws SolrServerException, IOException {
        return employeeRepository.getByUpdatedTime(date);
    }
}
