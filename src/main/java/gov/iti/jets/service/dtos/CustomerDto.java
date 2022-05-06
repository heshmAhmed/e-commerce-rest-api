package gov.iti.jets.service.dtos;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private Date joinedDate;
    private Date birthDate;
    private String address;
    private String password;

    public CustomerDto() {
    }

    public CustomerDto(String name, String email, Date joinedDate, Date birthDate, String address, String password) {
        this.name = name;
        this.email = email;
        this.joinedDate = joinedDate;
        this.birthDate = birthDate;
        this.address = address;
    }
}
