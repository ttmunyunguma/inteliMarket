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
package com.telmarket.intelimarket.dao;

import com.telmarket.intelimarket.entity.Category;
import com.telmarket.intelimarket.entity.Product;
import com.telmarket.intelimarket.entity.SubCategory;
import com.telmarket.intelimarket.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

/**
 *
 * @author terrence
 */
public class ListDao {
    
    SessionFactory factory = HibernateUtil.getSessionFactory();
        
    public List catList(){
        
        Session session = factory.openSession();
        List<Category> cList = session.createQuery("SELECT a1.catName FROM Category a1").list();
        cList.toString();
        session.close();
        return cList;
    }
    
    public List subcatList(String name){
        
        Session session = factory.openSession();
        List<Category> scList = session.createQuery("SELECT a1.subCatName FROM SubCategory a1 WHERE a1.category.catId IN (SELECT a.catId FROM Category a WHERE lower(a.catName)= '"+name.toLowerCase()+"')").list();
        scList.toString();
        session.close();
        return scList;
    }
    
    public List<Category> catListByName(String name){
        
        Session session = factory.openSession();
        List<Category> cList = session.createQuery("SELECT a1 FROM Category a1 WHERE lower(catName) = '"+name.toLowerCase()+"'").list();
        cList.toString();
        session.close();
        return cList;
    }
    
    public List<SubCategory> subcatListByName(String name){
        
        Session session = factory.openSession();
        List<SubCategory> cList = session.createQuery("SELECT a1 FROM SubCategory a1 WHERE lower(subCatName) = '"+name.toLowerCase()+"'").list();
        cList.toString();
        session.close();
        return cList;
    }
    
    public List allProductsList(){
        
        Session session = factory.openSession();
        List<Product> pList = session.createQuery("SELECT a1 FROM Product a1").list();
        pList.toString();
//        session.close();
        return pList;
    }
    
    public List<Product> productListBySubCat(String subcat){
        
        Session session = factory.openSession();
        List<Product> pList = session.createQuery("SELECT a1 FROM Product a1 WHERE subCategory = '"+("SELECT a1 FROM SubCategory a1 WHERE subCatName = '"+subcat+"'")+"'").list();
        pList.toString();
        session.close();
        return pList;
    }
    
    public List<Product> searchProductList(String keyword){
        System.out.println("***************************LIST DAO SEARCH PRODUCT HAS BEEN CALLED***************");
        List result;
        try (Session session = factory.openSession()) {
            FullTextSession fullTextSession = Search.getFullTextSession(session);
            QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
                    .buildQueryBuilder()
                    .forEntity(Product.class).get();
            org.apache.lucene.search.Query luceneQuery = queryBuilder.simpleQueryString()
                    .onFields("subCategory","proName","proPrice","proDesc")
                    .matching(keyword).createQuery();
            org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery);
            result = fullTextQuery.list();
            result.toString();
        }
        
        return result;
    }
    
    public boolean checkUser(String email, String password){
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            org.hibernate.Query query = session.createQuery("from Users where email=:email and password=:password");
//            query.setString("firstName", firstName);
            query.setString("email", email);
            query.setString("password", password);
            List dataList = query.list();
//            System.out.println("2*************"+dataList.toString()+"****************");   //debug
            return dataList.size() == 1;
        } catch (HibernateException e) {
        }
        return false;
    }
    
}
