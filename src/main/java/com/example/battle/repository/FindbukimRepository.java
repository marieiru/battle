package com.example.battle.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.battle.dao.Bukimdao;
import com.example.battle.entity.Bukim;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;


@SuppressWarnings("serial")
@Repository
public class FindbukimRepository implements Bukimdao {	
	
    @Autowired
    private EntityManager entityManager;

    public FindbukimRepository() {
        super();
    }

    public FindbukimRepository(EntityManager manager) {
        this();
        entityManager = manager;
    }		
	
    //Daoクラスで用意したsearchメソッドをオーバーライドする
    @SuppressWarnings("unchecked")
    @Override
    public List<Bukim> search(Integer sid,Integer h) {

        //StringBuilderでSQL文を連結する
        StringBuilder sql = new StringBuilder();
        //sql.append("Select a.kid from unitkousei a inner join sendanm b on a.sid = b.id WHERE ");
      
        sql.append("Select a from Bukim a WHERE ");
 
        //genreがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if((sid)==0) {
        } else {
            sql.append("a.sid =" + sid);
            
        }

        if((h)==0) {
        	sql.append(" AND ");
            sql.append(" Not (a.syubetu LIKE '白兵')");      
        } else if((h)==2) {
        
        } else {
        	sql.append(" AND ");
            sql.append("a.syubetu LIKE '白兵'");

        }        
        
        
        sql.append(" order by wno");
 
        Query query = entityManager.createQuery(sql.toString());

        return query.getResultList();
    }	    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Bukim> search_wno(Integer wno) {

        //StringBuilderでSQL文を連結する
        StringBuilder sql = new StringBuilder();
        //sql.append("Select a.kid from unitkousei a inner join sendanm b on a.sid = b.id WHERE ");
      
        sql.append("Select a from Bukim a WHERE ");
 
        //genreがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if((wno)==0) {
        } else {
            sql.append("a.wno =" + wno);
            
        }

  
        
        
        sql.append(" order by wno");
 
        Query query = entityManager.createQuery(sql.toString());

        return query.getResultList();
    }	       
	
}
