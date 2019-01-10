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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author terrence
 */
@Named(value = "userWizard")
@SessionScoped
@ManagedBean
public class UserWizard implements Serializable {

    private CheckOut user = new CheckOut();
    private SendPaymentControl payment = new SendPaymentControl();
    private boolean skip;
    /**
     * Creates a new instance of UserWizard
     */
    public UserWizard() {
    }

    public SendPaymentControl getPayment() {
        return payment;
    }

    public void setPayment(SendPaymentControl payment) {
        this.payment = payment;
    }

    public CheckOut getUser() {
        return user;
    }

    public void setUser(CheckOut user) {
        this.user = user;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }
    
    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
        return event.getNewStep();
        }
    }
    
    public void save() {        
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + user.getFullName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
