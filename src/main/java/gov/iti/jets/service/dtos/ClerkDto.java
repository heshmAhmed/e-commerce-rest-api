package gov.iti.jets.service.dtos;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ClerkDto {
    private Long id;
    private String name;
    private String email;
    private Date hireDate;
    private String password;

    public ClerkDto(String name, String email, Date hireDate, String password) {
        this.name = name;
        this.email = email;
        this.hireDate = hireDate;
        this.password = password;

    }
}
