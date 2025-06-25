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
public class BookRequest {
    private int id;
    private User user;
    private Book book;
    private Date request_date;
    private String status;

    public BookRequest(int id, User user, Book book, Date request_date, String status) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.request_date = request_date;
        this.status = status;
    }

    public BookRequest(int id, User user, Book book, Date request_date) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.request_date = request_date;
        this.status = "pending";
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

    public Date getRequest_date() {
        return request_date;
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

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
