/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Admin
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private int published_year;
    private int total_copies;
    private int available_copies;
    private String status;
    private String url;

    public Book(int id, String title, String author, String isbn, String category, int published_year, int total_copies, int available_copies, String status, String url) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.published_year = published_year;
        this.total_copies = total_copies;
        this.available_copies = available_copies;
        this.status = status;
        this.url = url;
    }
    
    

    public Book(int id, String title, String author, String isbn, String category, int published_year, int total_copies, String status,String url) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.published_year = published_year;
        this.total_copies = total_copies;
        this.available_copies = total_copies;
        this.status = status;
        this.url = url;
    }

    public Book(int id, String title, String author, String isbn, String category, int published_year, int total_copies, String url) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.published_year = published_year;
        this.total_copies = total_copies;
        this.available_copies = total_copies;
        this.status = "active";
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCategory() {
        return category;
    }

    public int getPublished_year() {
        return published_year;
    }

    public int getTotal_copies() {
        return total_copies;
    }

    public int getAvailable_copies() {
        return available_copies;
    }

    public String getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPublished_year(int published_year) {
        this.published_year = published_year;
    }

    public void setTotal_copies(int total_copies) {
        this.total_copies = total_copies;
    }

    public void setAvailable_copies(int available_copies) {
        this.available_copies = available_copies;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
