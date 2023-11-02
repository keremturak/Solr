package org.example.repository;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.example.entity.Employee;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class EmployeeRepository {


    public Employee save(Employee employee) throws SolrServerException, IOException {
        String urlString = "http://localhost:8983/solr/TASK";
        HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
        solr.setParser(new XMLResponseParser());
        // Ekleme işlemi
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", employee.getId());
        document.addField("name", employee.getName());
        document.addField("surname", employee.getSurname());
        document.addField("updatedTime", employee.getUpdatedTime());
        solr.add(document);
        solr.commit();
        return employee;
    }

    public List<Employee> getByUpdatedTime(LocalDate date) throws SolrServerException, IOException {

        String urlString = "http://localhost:8983/solr/TASK";
        HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
        Date date2 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println(date2);
        long timestamp = date2.getTime();
        System.out.println(timestamp);
        solr.setParser(new XMLResponseParser());
        SolrQuery query = new SolrQuery();
        query.set("q", "updatedTime:["+timestamp+" TO *]");
        QueryResponse response = solr.query(query);
        List<Employee> employees = response.getBeans(Employee.class);
        return employees;
    }
}
