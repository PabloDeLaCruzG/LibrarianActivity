package com.example.application.backend.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idClient;
    private String dni;
    private String name;
    private String address;
    private String email;
    @OneToMany(mappedBy = "idClient")
    private List<Loan> loan = new ArrayList<>();

    public Client() {
    }

    public Client(String dni, String name, String address, String email) {
        this.dni = dni;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Loan> getLoan() {
        return loan;
    }

    public void setLoan(List<Loan> loan) {
        this.loan = loan;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", loan=" + loan +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return idClient == client.idClient && Objects.equals(dni, client.dni) && Objects.equals(name, client.name) && Objects.equals(address, client.address) && Objects.equals(email, client.email) && Objects.equals(loan, client.loan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, dni, name, address, email, loan);
    }
}
