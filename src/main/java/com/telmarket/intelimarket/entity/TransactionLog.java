package com.telmarket.intelimarket.entity;
// Generated Dec 18, 2018 7:06:11 PM by Hibernate Tools 4.3.1



/**
 * TransactionLog generated by hbm2java
 */
public class TransactionLog  implements java.io.Serializable {


     private Integer id;
     private String fullName;
     private String addressLine1;
     private String addressLine2;
     private String city;
     private String state;
     private String country;
     private String phone;
     private String cardName;
     private String pan;
     private String expirationDate;
     private String amount;
     private String transactionDateAndTime;

    public TransactionLog() {
    }

	
    public TransactionLog(String fullName, String addressLine1, String city, String country, String phone, String cardName, String pan, String expirationDate, String amount, String transactionDateAndTime) {
        this.fullName = fullName;
        this.addressLine1 = addressLine1;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.cardName = cardName;
        this.pan = pan;
        this.expirationDate = expirationDate;
        this.amount = amount;
        this.transactionDateAndTime = transactionDateAndTime;
    }
    public TransactionLog(String fullName, String addressLine1, String addressLine2, String city, String state, String country, String phone, String cardName, String pan, String expirationDate, String amount, String transactionDateAndTime) {
       this.fullName = fullName;
       this.addressLine1 = addressLine1;
       this.addressLine2 = addressLine2;
       this.city = city;
       this.state = state;
       this.country = country;
       this.phone = phone;
       this.cardName = cardName;
       this.pan = pan;
       this.expirationDate = expirationDate;
       this.amount = amount;
       this.transactionDateAndTime = transactionDateAndTime;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFullName() {
        return this.fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getAddressLine1() {
        return this.addressLine1;
    }
    
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    public String getAddressLine2() {
        return this.addressLine2;
    }
    
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCardName() {
        return this.cardName;
    }
    
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public String getPan() {
        return this.pan;
    }
    
    public void setPan(String pan) {
        this.pan = pan;
    }
    public String getExpirationDate() {
        return this.expirationDate;
    }
    
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    public String getAmount() {
        return this.amount;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getTransactionDateAndTime() {
        return this.transactionDateAndTime;
    }
    
    public void setTransactionDateAndTime(String transactionDateAndTime) {
        this.transactionDateAndTime = transactionDateAndTime;
    }




}


