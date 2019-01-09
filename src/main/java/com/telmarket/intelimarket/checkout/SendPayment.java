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

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMUX;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.ISO87APackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

/**
 *
 * @author terrence
 */
@Named(value = "sendPayment")
@SessionScoped
public class SendPayment implements Serializable {
     
    private ISOMsg resp = null;
    private ISO87APackager packager;
    private ASCIIChannel channel;
    private ISOMUX mux;
    static String HOST = "127.0.0.1";
    static int PORT = 1990;
    static int TIMEOUT = 30000;
    static DateFormat date = new SimpleDateFormat("MMdd");
    static DateFormat time = new SimpleDateFormat("HHmmss");
    static DateFormat temp = new SimpleDateFormat("MMddHHmmss");
    static String bit43 = "TelMarket_Online_Store Harare_Harare ZWE";
    
    /**
     * Creates a new instance of SendPayment
     */
    public SendPayment() {
        try {
            this.packager = new ISO87APackager();
            this.channel = new ASCIIChannel(HOST, PORT, packager);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SendPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void connect() throws IOException {
    }
    
    public void sendIt(CheckOut bean) {
        
        try {
            System.out.println(">> CONNECTION");
            this.channel.setHost(HOST);
            this.channel.setPort(PORT);
            this.channel.connect();

            this.mux = new ISOMUX(channel);
            Thread muxThread = new Thread(mux);
            muxThread.start();

            System.out.println("Connected with " + channel.getHost() + ":" + channel.getPort() + " ? " + mux.isConnected());
            System.out.println(channel.getSocket());
            System.out.println(">> SENDING");

            Logger logger = new Logger();
            logger.addListener(new SimpleLogListener(System.out));
            ((LogSource) channel).setLogger(logger, "channel-logger");
            ISOMsg req = authorizationRequest(bean);
            req.setPackager(packager);
            System.out.println("Request message: " + req);        //for debug
            byte[] data = req.pack();
            System.out.println("Request byte message: " + data);
            req.setDirection(ISOMsg.OUTGOING);

            System.out.println("MUX Connection status: " + mux.isConnected());   //for debug

            resp = mux.request(req, TIMEOUT);
            System.out.println("Success");      //for debug

            if (resp != null) {

                System.out.println("Responce message: " + String.valueOf(resp));   //for debug
                
                if ((resp.hasField(39)) && ("00".equals(String.valueOf(resp.getValue(39))))) {
                    ISOMsg settle = settlementRequest(bean);
                    settle.setPackager(packager);
                    System.out.println(settle);    //for debug
                    settle.setDirection(ISOMsg.OUTGOING);
                    mux.send(settle);
                }
            } else {
                ISOMsg rev = reversalRequest(bean);
                rev.setPackager(packager);
                System.out.println(rev);        //for debug
                rev.setDirection(ISOMsg.OUTGOING);
                mux.send(rev);
            }

        } catch (ISOException | IOException ex) {
            java.util.logging.Logger.getLogger(SendPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static ISOMsg authorizationRequest(CheckOut bean) {
        
        ISOMsg message = new ISOMsg();
        String bit120 = String.valueOf(bean.getCardHolderName() + bean.getAddLine1() + bean.getCity() + bean.getCountry() + bean.getPhone());

        try {
            message.setMTI("0100");
            message.set(2, bean.getCardNumber());     //to be provided from form
            message.set(3, "003000");
            message.set(4, bean.getTotal());        //to be provided by shopping cart
            message.set(7, temp.format(new Date()));
            message.set(11, String.valueOf(new Random().nextInt(999999)));  //STAN to be uique for the day. Use time stamp preferably
            message.set(14, "bean.getExpDate()");    //to be provided from form
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
            java.util.logging.Logger.getLogger(SendPayment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    private static ISOMsg reversalRequest(CheckOut bean) {

        ISOMsg message = new ISOMsg();
        String bit120 = String.valueOf(bean.getCardHolderName() + bean.getAddLine1() + bean.getCity() + bean.getCountry() + bean.getPhone());

        try {
            message.setMTI("0400");
            message.set(2, bean.getCardNumber());     //to be provided from form
            message.set(3, "003000");
            message.set(4, bean.getTotal());        //to be provided by shopping cart
            message.set(7, temp.format(new Date()));
            message.set(11, String.valueOf(new Random().nextInt(999999)));  //STAN to be uique for the day. Use time stamp preferably
            message.set(14, "bean.getExpDate()");    //to be provided from form
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
            java.util.logging.Logger.getLogger(SendPayment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    private static ISOMsg settlementRequest(CheckOut bean) {

        ISOMsg message = new ISOMsg();
        String bit120 = String.valueOf(bean.getCardHolderName() + bean.getAddLine1() + bean.getCity() + bean.getCountry() + bean.getPhone());

        try {
            message.setMTI("0500");
            message.set(2, bean.getCardNumber());     //to be provided from form
            message.set(3, "003000");
            message.set(4, bean.getTotal());        //to be provided by shopping cart
            message.set(7, temp.format(new Date()));
            message.set(11, String.valueOf(new Random().nextInt(999999)));  //STAN to be uique for the day. Use time stamp preferably
            message.set(14, "bean.getExpDate()");    //to be provided from form
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
            java.util.logging.Logger.getLogger(SendPayment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    
    public ISOMsg getResp() {
        return resp;
    }

    public void setResp(ISOMsg resp) {
        this.resp = resp;
    }
}
