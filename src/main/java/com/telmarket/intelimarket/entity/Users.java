package com.telmarket.intelimarket.entity;
// Generated Dec 18, 2018 7:06:11 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Users generated by hbm2java
 */
public class Users  implements java.io.Serializable {


     private Integer userId;
     private int roleId;
     private String firstName;
     private String lastName;
     private String email;
     private String password;
     private Date regDate;
     private String contactNo;
     private Set productOrders = new HashSet(0);

    public Users() {
    }

	
    public Users(int roleId, String firstName, String lastName, String email, String password, Date regDate) {
        this.roleId = roleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.regDate = regDate;
    }
    public Users(int roleId, String firstName, String lastName, String email, String password, Date regDate, String contactNo, Set productOrders) {
       this.roleId = roleId;
       this.firstName = firstName;
       this.lastName = lastName;
       this.email = email;
       this.password = password;
       this.regDate = regDate;
       this.contactNo = contactNo;
       this.productOrders = productOrders;
    }
   
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public int getRoleId() {
        return this.roleId;
    }
    
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getRegDate() {
        return this.regDate;
    }
    
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
    public String getContactNo() {
        return this.contactNo;
    }
    
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public Set getProductOrders() {
        return this.productOrders;
    }
    
    public void setProductOrders(Set productOrders) {
        this.productOrders = productOrders;
    }




}


