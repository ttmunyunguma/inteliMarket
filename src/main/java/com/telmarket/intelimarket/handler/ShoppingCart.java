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
package com.telmarket.intelimarket.handler;

import com.telmarket.intelimarket.cart.Item;
import com.telmarket.intelimarket.entity.Product;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author terrence
 */
@ManagedBean
@Named(value = "shoppingCart")
@SessionScoped
public class ShoppingCart implements Serializable {

    /**
     * Creates a new instance of ShoppingCart
     */
    private List<Item> cart = new ArrayList<>();
    private double total;
    int cartSize;
    
    public ShoppingCart() {
    }

    public List<Item> getCart() {
        return cart;
    }

    public void setCart(List<Item> cart) {
        this.cart = cart;
    }

    public double getTotal() {
        total = 0.0;
        cart.forEach((i) -> {
            total = total + (i.getQuantity()*i.getP().getProPrice());
        });
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCartSize() {
        return cartSize;
    }

    public void setCartSize(int cartSize) {
        
        this.cartSize = cartSize;
    }
    
    public String addToCart(Product p){
        if(cart.size() > 0){
            for(Item i : cart){
                if(i.getP().getProId().equals(p.getProId())){
                    i.setQuantity(i.getQuantity() + 1);
                }
            }
        }
        Item i = new Item();
        i.setQuantity(1);
        i.setP(p);
        cart.add(i);
        cartSize = cart.size();
        return null;
    }
    
    public void updateCart(){
    }
    
    public void removeCart(Item i){
        
        for(Item it : cart){
            if(it.equals(i)){
                cart.remove(i);
                break;
            }
        }
    }
    
}
