package main.ar.com.codoacodo.model;

import java.sql.Date;

public class Employee {

    private Integer id, userId;
    private String personId, fullName, address, type;
    private Date contractDate;
    private boolean active;

    public Employee() {}

    @Override
    public String toString() {
        return String.format(
                "[ID=%d, IDU=%d, DNI=%s, NOMBRE COMPLETO=%s, DOMICILIO=%s, TIPO=%s, FECHA=%s, ACTIVO=%s]",
                id,
                userId,
                personId,
                fullName,
                address,
                type,
                contractDate,
                active ? "si" : "no"
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
