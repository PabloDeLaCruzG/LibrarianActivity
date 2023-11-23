package com.example.application.backend;


import com.example.application.backend.classes.*;
import com.example.application.backend.daos.*;
import com.example.application.backend.enums.Category;

import java.util.Date;

public class Main {

    public static void main(String[] args) {

        //LoanDAO.removeLoan(4);
        //BookDAO.removeBook(2);
        //AuthorDAO.removeAuthor(1);
        //ClientDAO.removeClient(3);

        Author author1 = new Author("Silvia", "Carrasco", "Español");
        AuthorDAO.addAuthor(author1);

        Book book1 = new Book("Microsoft", "4567890PPP", 450, author1, Category.CIENTIFICOS);
        BookDAO.addBook(book1);

        Client client1 = new Client("54321987", "Alex Palacios", "calle muralla", "palas@gmail.com");
        ClientDAO.addClient(client1);

        Loan loan1 = new Loan(book1, client1, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 3));
        LoanDAO.addLoan(loan1);

    }

}

