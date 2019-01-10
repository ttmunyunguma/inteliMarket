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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

/**
 *
 * @author terrence
 */
@Named(value = "iSOMessages")
@SessionScoped
@ManagedBean
public class ISOMessages implements Serializable {

    @ManagedProperty(value = "#{checkOut}")
    CheckOut bean = new CheckOut();
    private static final String bit43 = "TelMarket_Online_Store Harare_Harare ZWE";
    static DateFormat temp = new SimpleDateFormat("MMddHHmmss");
    
    /**
     * Creates a new instance of ISOMessages
     */
    public ISOMessages() {
    }
    
    public static ISOMsg authorizationRequest(CheckOut bean) {

        ISOMsg message = new ISOMsg();
        String bit120 = String.valueOf(bean.getCardHolderName() + bean.getAddLine1() + bean.getCity() + bean.getCountry() + bean.getPhone());

        try {
            message.setMTI("0100");
            message.set(2, bean.getCardNumber());     //to be provided from form
            message.set(3, "003000");
            message.set(4, bean.getTotal());        //to be provided by shopping cart
            message.set(7, temp.format(new Date()));
            message.set(11, String.valueOf(new Random().nextInt(999999)));  //STAN to be uique for the day. Use time stamp preferably
            message.set(14, bean.getExpDate());    //to be provided from form
            message.set(18, "4814");    //Telecommunication Services including but not limited to prepaid phone services and recurring phone services
            message.set(22, "812");
            message.set(32, "123456");      //A MasterCard customer ID number that MasterCard assigned to the entity acting as the acquiring institution for a transaction. Contain a six-digit customer ID number assigned by MasterCard that identifies the institution acting as the “acquiring bank” or “merchant bank” for a transaction.
            message.set(42, "2345hfts=5682jf"); //Number assigned by the acquirer. required for POS transaction types containing DE 3 (Processing Code), subfield 1 (Cardholder Transaction Type Code), values 00 (Purchase)
            message.set(43, bit43);
            message.set(49, "840");     //United States Dollar
            message.set(61, "12310006600716");
            message.set(120, bit120);

            return message;
        } catch (ISOException ex) {
            java.util.logging.Logger.getLogger(ISOMessages.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public static ISOMsg settlementRequest(CheckOut bean) {

        ISOMsg message = new ISOMsg();
        String bit120 = String.valueOf(bean.getCardHolderName() + bean.getAddLine1() + bean.getCity() + bean.getCountry() + bean.getPhone());

        try {
            message.setMTI("0500");
            message.set(2, bean.getCardNumber());     //to be provided from form
            message.set(3, "003000");
            message.set(4, bean.getTotal());        //to be provided by shopping cart
            message.set(7, temp.format(new Date()));
            message.set(11, String.valueOf(new Random().nextInt(999999)));  //STAN to be uique for the day. Use time stamp preferably
            message.set(14, bean.getExpDate());    //to be provided from form
            message.set(18, "4814");    //Telecommunication Services including but not limited to prepaid phone services and recurring phone services
            message.set(22, "812");
            message.set(32, "123456");      //A MasterCard customer ID number that MasterCard assigned to the entity acting as the acquiring institution for a transaction. Contain a six-digit customer ID number assigned by MasterCard that identifies the institution acting as the “acquiring bank” or “merchant bank” for a transaction.
            message.set(42, "2345hfts=5682jf"); //Number assigned by the acquirer. required for POS transaction types containing DE 3 (Processing Code), subfield 1 (Cardholder Transaction Type Code), values 00 (Purchase)
            message.set(43, bit43);
            message.set(49, "840");     //United States Dollar
            message.set(61, "12310006600716");
            message.set(120, bit120);

            return message;
        } catch (ISOException ex) {
            java.util.logging.Logger.getLogger(ISOMessages.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
        
    public static ISOMsg reversalRequest(CheckOut bean) {

        ISOMsg message = new ISOMsg();
        String bit120 = String.valueOf(bean.getCardHolderName() + bean.getAddLine1() + bean.getCity() + bean.getCountry() + bean.getPhone());

        try {
            message.setMTI("0400");
            message.set(2, bean.getCardNumber());     //to be provided from form
            message.set(3, "003000");
            message.set(4, bean.getTotal());        //to be provided by shopping cart
            message.set(7, temp.format(new Date()));
            message.set(11, String.valueOf(new Random().nextInt(999999)));  //STAN to be uique for the day. Use time stamp preferably
            message.set(14, bean.getExpDate());    //to be provided from form
            message.set(18, "4814");    //Telecommunication Services including but not limited to prepaid phone services and recurring phone services
            message.set(22, "812");
            message.set(32, "123456");      //A MasterCard customer ID number that MasterCard assigned to the entity acting as the acquiring institution for a transaction. Contain a six-digit customer ID number assigned by MasterCard that identifies the institution acting as the “acquiring bank” or “merchant bank” for a transaction.
            message.set(39, "68");
            message.set(42, "2345hfts=5682jf"); //Number assigned by the acquirer. required for POS transaction types containing DE 3 (Processing Code), subfield 1 (Cardholder Transaction Type Code), values 00 (Purchase)
            message.set(43, bit43);
            message.set(49, "840");     //United States Dollar
            message.set(61, "12310006600716");
            message.set(120, bit120);

            return message;
        } catch (ISOException ex) {
            java.util.logging.Logger.getLogger(ISOMessages.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
