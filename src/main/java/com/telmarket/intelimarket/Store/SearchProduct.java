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
package com.telmarket.intelimarket.Store;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author terrence
 */
@Named(value = "searchProduct")
@SessionScoped
@ManagedBean
public class SearchProduct implements Serializable{

    private String keyword;
    /**
     * Creates a new instance of Search
     */
    public SearchProduct() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public void searchedResults(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("searchresults.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(SearchProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clickedProduct(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("products.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(SearchProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void continueShopping(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("allproducts.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(SearchProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
