/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Admin
 */
public class Fine {
     private int id;
    private BorrowRecord borrowRecord;
    private float find_amount;
    private String paid_status;

    public Fine(int id, BorrowRecord borrowRecord, float find_amount) {
        this.id = id;
        this.borrowRecord = borrowRecord;
        this.find_amount = find_amount;
        this.paid_status = "unpaid";
    }

    public int getId() {
        return id;
    }

    public BorrowRecord getBorrowRecord() {
        return borrowRecord;
    }

    public float getFind_amount() {
        return find_amount;
    }

    public String getPaid_status() {
        return paid_status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBorrowRecord(BorrowRecord borrowRecord) {
        this.borrowRecord = borrowRecord;
    }

    public void setFind_amount(float find_amount) {
        this.find_amount = find_amount;
    }

    public void setPaid_status(String paid_status) {
        this.paid_status = paid_status;
    }
    
    
    
}
