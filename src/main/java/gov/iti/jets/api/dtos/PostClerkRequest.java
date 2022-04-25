package gov.iti.jets.api.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class PostClerkRequest {
    public String name;
    public String email;
    public String password;
    public Date hireDate;
}
