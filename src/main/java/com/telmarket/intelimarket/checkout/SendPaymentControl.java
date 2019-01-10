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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.jpos.iso.ISOMsg;

/**
 *
 * @author terrence
 */
@Named(value = "sendPaymentControl")
@SessionScoped
@ManagedBean
public class SendPaymentControl implements Serializable {

    @ManagedProperty(value = "#{checkOut}")
    CheckOut checkOut = new CheckOut();
    /**
     * Creates a new instance of SendPaymentControl
     */
    public SendPaymentControl() {
    }

    public CheckOut getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(CheckOut checkOut) {
        this.checkOut = checkOut;
    }
    
    public String send() throws Exception{
        
//        TransactionLogs log = new TransactionLogs();
        SendPayment pay = new SendPayment();
        
        try {
            
            pay.sendIt(checkOut);
        } 
        catch (Exception ex) {
            Logger.getLogger(CheckOut.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
//            log.createLog();
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
}
