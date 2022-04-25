package gov.iti.jets.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
@DiscriminatorValue("CLERK")
public class ClerkEntity extends UserEntity {
    @Column(name = "hire_date")
    private Date hireDate;

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "ClerkEntity{" +
                "hireDate=" + hireDate +
                '}';
    }
}
