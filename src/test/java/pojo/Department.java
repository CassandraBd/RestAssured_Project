package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Department {

    // instructing jackson to bind json field called department_id to below java field
    // need to do this if you didn't put it in right
    @JsonProperty("department_id")
    private int departmentId; // json field: department_id
    @JsonProperty("department_name")
    private String name; // department_name
    private int manager_id;
    private  int location_id;
}
