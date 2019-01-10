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
            ISOMsg req = ISOMessages.authorizationRequest(bean);
            req.setPackager(packager);
            System.out.println("****Request message: " + req);        //for debug
            byte[] data = req.pack();
            System.out.println("****Request byte message: " + data);    //for debug
            req.setDirection(ISOMsg.OUTGOING);

            System.out.println("MUX Connection status: " + mux.isConnected());   //for debug

            resp = mux.request(req, TIMEOUT);
            System.out.println("****Success****");      //for debug

            if (resp != null) {

                System.out.println("Responce message: " + String.valueOf(resp));   //for debug
                
                if ((resp.hasField(39)) && ("00".equals(String.valueOf(resp.getValue(39))))) {
                    ISOMsg settle = ISOMessages.settlementRequest(bean);
                    settle.setPackager(packager);
                    System.out.println(settle);    //for debug
                    settle.setDirection(ISOMsg.OUTGOING);
                    mux.send(settle);
                }
            } else {
                ISOMsg rev = ISOMessages.reversalRequest(bean);
                rev.setPackager(packager);
                System.out.println(rev);        //for debug
                rev.setDirection(ISOMsg.OUTGOING);
                mux.send(rev);
            }

        } catch (ISOException | IOException ex) {
            java.util.logging.Logger.getLogger(SendPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public ISOMsg getResp() {
        return resp;
    }

    public void setResp(ISOMsg resp) {
        this.resp = resp;
    }
}
