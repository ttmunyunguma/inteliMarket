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

import com.telmarket.intelimarket.dao.AddDao;
import com.telmarket.intelimarket.entity.TransactionLog;
import com.telmarket.intelimarket.security.EncryptField;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author terrence
 */
@Named(value = "transactionLogs")
@SessionScoped
public class TransactionLogs implements Serializable {

    TransactionLog transaction = new TransactionLog();
    EncryptField field = new EncryptField();
    CheckOut value = new CheckOut();
    
    /**
     * Creates a new instance of TransactionLogs
     */
    public TransactionLogs() {
    }
    
    public void createLog() throws Exception{
        
        transaction.setFullName(value.getFullName());
        transaction.setAddressLine1(value.getAddLine1());
        transaction.setAddressLine2(value.getAddLine2());
        transaction.setCity(value.getCity());
        transaction.setState(value.getState());
        transaction.setCountry(value.getCountry());
        transaction.setPhone(value.getPhone());
        transaction.setCardName(value.getCardHolderName());
        transaction.setPan(field.encrypt(value.getCardNumber()));
        transaction.setExpirationDate(value.getExpDate());
        transaction.setAmount(value.getTotal());
        transaction.setTransactionDateAndTime(transactionTime());
        
        AddDao.commitLog(transaction);
    }
    
    public String transactionTime(){
    
        Date date = new Date();
        return String.valueOf(date);
    }
}
