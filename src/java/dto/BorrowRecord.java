/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class BorrowRecord {
    private int id;
    private User user;
    private Book book;
    private Date borrow_date;
    private Date due_date;
    private Date return_date;
    private String status;

    public BorrowRecord(int id, User user, Book book, Date borrow_date, Date due_date, Date return_date, String status) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.borrow_date = borrow_date;
        this.due_date = due_date;
        this.return_date = return_date;
        this.status = status;
    }

    public BorrowRecord(int id, User user, Book book, Date borrow_date, Date due_date, Date return_date) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.borrow_date = borrow_date;
        this.due_date = due_date;
        this.return_date = return_date;
        this.status = "active";
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public Date getBorrow_date() {
        return borrow_date;
    }

    public Date getDue_date() {
        return due_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setBorrow_date(Date borrow_date) {
        this.borrow_date = borrow_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
