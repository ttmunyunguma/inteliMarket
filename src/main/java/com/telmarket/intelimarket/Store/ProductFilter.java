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

import com.telmarket.intelimarket.dao.ListDao;
import com.telmarket.intelimarket.entity.SubCategory;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author terrence
 */
@Named(value = "productFilter")
@SessionScoped
public class ProductFilter implements Serializable {

    private String mobile = "Mobile";
    private String computer = "Computer";
    private String Accessories = "Accessories";
    private String technology = "Technology";
    private String servers = "Servers";
    private String software = "Software";
    private String services = "Professional Services";
    
    /**
     * Creates a new instance of ProductFilter
     */
    public ProductFilter() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public String getAccessories() {
        return Accessories;
    }

    public void setAccessories(String Accessories) {
        this.Accessories = Accessories;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
    
    public List<SubCategory> listOfMobileSubCategories(){
        System.out.println("***************stage1 get list");
        System.out.println("******************mobilevalue = "+mobile);
        List thisList = new ListDao().subcatList(mobile);
        thisList.toString();
        return thisList;
    }
}
