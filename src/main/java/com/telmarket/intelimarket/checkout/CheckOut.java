/*
 * Terrence Takunda Munyunguma [https://github.com/TerrenceTakunda]
 *  Copyright (C) 2018 ttmunyunguma@gmail.com
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.telmarket.intelimarket.checkout;

import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import org.jpos.iso.ISOMsg;

/**
 *
 * @author terrence
 */
@Named(value = "checkOut")
@SessionScoped
@ManagedBean
public class CheckOut implements Serializable {

    /**
     * Creates a new instance of CheckOut
     */
    public CheckOut() {
        countryOptions = new ArrayList<>();
        countryOptions.add("Zimbabwe");
        countryOptions.add("Botswana");
        countryOptions.add("Malawi");
        countryOptions.add("South Africa");
        countryOptions.add("Zambia");
        countryOptions.add("Namibia");
    
    }
    
    private String total;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String addLine1;
    private String addLine2;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String cardHolderName;
    private String cardNumber;
    private Date expDate;
    private String CVV;
    private String transmissionDateAndTime;
    List<String> countryOptions;


public String send() throws Exception{
        
        TransactionLogs log = new TransactionLogs();
        SendPayment pay = new SendPayment();
        
        try {
            
            pay.sendIt(this);
        } 
        catch (Exception ex) {
            Logger.getLogger(CheckOut.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            log.createLog();
        }
        
        ISOMsg incoming = pay.getResp();
        
        if(incoming != null){
            if(incoming.hasField(39)==true){
                if(null == String.valueOf(incoming.getValue(39)))return "/responce/error.xhtml";
                
                    else switch (String.valueOf(incoming.getValue(39))) {
                        case "00":
                            return "/responce/success.xhtml";
                        case "13":
                            return "/responce/error_amount.xhtml";
                        case "54":
                            return "/responce/error_expired.xhtml";
                        case "78":
                            return "/responce/error_suspicious.xhtml";
                        case "12":
                            return "/responce/error_reqInvalid.xhtml";
                        case "51":
                            return "/responce/error_insuficientFunds.xhtml";    
                        default:
                            return "/responce/error.xhtml";
                    }
            }
            else
                return "/responce/error.xhtml";  
        }
        else
            return "/responce/error_noresponce.xhtml";  
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddLine1() {
        return addLine1;
    }

    public void setAddLine1(String addLine1) {
        this.addLine1 = addLine1;
    }

    public String getAddLine2() {
        return addLine2;
    }

    public void setAddLine2(String addLine2) {
        this.addLine2 = addLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getTransmissionDateAndTime() {
        return transmissionDateAndTime;
    }

    public void setTransmissionDateAndTime(String transmissionDateAndTime) {
        this.transmissionDateAndTime = transmissionDateAndTime;
    }

    public List<String> getCountryOptions() {
        return countryOptions;
    }

    public void setCountryOptions(List<String> countryOptions) {
        this.countryOptions = countryOptions;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}