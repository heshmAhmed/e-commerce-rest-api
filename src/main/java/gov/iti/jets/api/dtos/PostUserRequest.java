package gov.iti.jets.api.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class PostUserRequest {
    public String name;
    public String email;
    public String password;
    public Date hireDate;
    private Date joinedDate;
    private Date birthDate;
    private String address;
}
