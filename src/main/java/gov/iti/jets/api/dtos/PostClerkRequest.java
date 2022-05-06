package gov.iti.jets.api.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PostClerkRequest {
    private String name;
    private String email;
    private String password;
    private Date hireDate;
}
