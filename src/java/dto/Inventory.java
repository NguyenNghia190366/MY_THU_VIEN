/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author DELL
 */
public class Inventory {

    private int inventory_id;
    private int book_id;
    private String book_title;
    private int admin_id;
    private String admin_name;
    private String created_date;
    private String expected_add_date;
    private String actual_add_date;
    private int quantity;
    private String status;
    private String note;

    public Inventory() {
    }

    public Inventory(int inventory_id, int book_id, String book_title, int admin_id, String admin_name, String created_date, String expected_add_date, String actual_add_date, int quantity, String status, String note) {
        this.inventory_id = inventory_id;
        this.book_id = book_id;
        this.book_title = book_title;
        this.admin_id = admin_id;
        this.admin_name = admin_name;
        this.created_date = created_date;
        this.expected_add_date = expected_add_date;
        this.actual_add_date = actual_add_date;
        this.quantity = quantity;
        this.status = status;
        this.note = note;
    }
    
    

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }


    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getExpected_add_date() {
        return expected_add_date;
    }

    public void setExpected_add_date(String expected_add_date) {
        this.expected_add_date = expected_add_date;
    }

    public String getActual_add_date() {
        return actual_add_date;
    }

    public void setActual_add_date(String actual_add_date) {
        this.actual_add_date = actual_add_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    
}
