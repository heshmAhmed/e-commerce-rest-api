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

    public ClerkDto(String name, String email, Date hireDate) {
        this.name = name;
        this.email = email;
        this.hireDate = hireDate;
    }
}
