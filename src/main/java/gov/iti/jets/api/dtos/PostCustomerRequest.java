package gov.iti.jets.api.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PostCustomerRequest {
    private String name;
    private String email;
    private Date joinedDate;
    private Date birthDate;
    private String address;
    private String password;
}
