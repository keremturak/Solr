package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;

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
