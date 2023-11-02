![Solr](https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Apache_Solr_logo.svg/1920px-Apache_Solr_logo.svg.png)

# Apache Solr Entegrasyonu

`    implementation group: 'org.apache.solr', name: 'solr-solrj', version: '6.4.0'`

Bu proje, Apache Solr veri depolama ve sorgulama teknolojisini kullanmaktadır. Controller sınıfı içerisinde, veri kaydetme ve belirli bir tarihten sonraki güncellenmiş kayıtları sorgulama işlemlerini gerçekleştiren iki ana metod bulunmaktadır.


###Employee sınıfım

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Field("id")
    String id;
    @Field("name")
    String name;
    @Field("surname")
    String surname;
    @Field("updatedTime")
    Long updatedTime;
}
```

###Employe Save metodu
```java
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
```

###getByUpdatedTime metodu
```java
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
```
