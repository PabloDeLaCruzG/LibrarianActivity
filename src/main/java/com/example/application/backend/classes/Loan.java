package com.example.application.backend.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLoan;
    @OneToOne
    @JoinColumn(name = "idBook")
    private Book idBook;
    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client idClient;
    private Date loanDate;
    private Date returnDate;

    public Loan() {
    }

    public Loan(Book idBook, Client idClient, Date loanDate, Date returnDate) {
        this.idBook = idBook;
        this.idClient = idClient;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public int getIdLoan() {
        return idLoan;
    }

    public void setIdLoan(int idLoan) {
        this.idLoan = idLoan;
    }

    public Book getBook() {
        return idBook;
    }

    public void setBook(Book book) {
        this.idBook = book;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "idLoan=" + idLoan +
                ", idBook=" + idBook +
                ", idClient=" + idClient +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan loan)) return false;
        return idLoan == loan.idLoan && Objects.equals(idBook, loan.idBook) && Objects.equals(idClient, loan.idClient) && Objects.equals(loanDate, loan.loanDate) && Objects.equals(returnDate, loan.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLoan, idBook, idClient, loanDate, returnDate);
    }
}
