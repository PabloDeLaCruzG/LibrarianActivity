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
        return "Cliente{" +
                "idCliente=" + idClient +
                ", dni='" + dni + '\'' +
                ", nombre='" + name + '\'' +
                ", direccion='" + address + '\'' +
                ", email='" + email + '\'' +
                ", prestamos=" + loan +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client cliente = (Client) o;
        return idClient == cliente.idClient && Objects.equals(dni, cliente.dni) && Objects.equals(name, cliente.name) && Objects.equals(address, cliente.address) && Objects.equals(email, cliente.email) && Objects.equals(loan, cliente.loan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, dni, name, address, email, loan);
    }
}
