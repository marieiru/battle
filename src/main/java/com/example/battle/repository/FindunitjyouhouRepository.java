package com.example.battle.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.battle.dao.Unitjyouhoudao;
import com.example.battle.entity.Unitjyouhou;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@SuppressWarnings("serial")
@Repository
public class FindunitjyouhouRepository implements Unitjyouhoudao {

    @Autowired
    private EntityManager entityManager;

    public FindunitjyouhouRepository() {
        super();
    }

    public FindunitjyouhouRepository(EntityManager manager) {
        this();
        entityManager = manager;
    }	
	
	
    //Daoクラスで用意したsearchメソッドをオーバーライドする
    @SuppressWarnings("unchecked")
    @Override
    public List<Unitjyouhou> search(Integer sid, Integer yid) {

        //StringBuilderでSQL文を連結する
        StringBuilder sql = new StringBuilder();
        //sql.append("Select a.kid from unitkousei a inner join sendanm b on a.sid = b.id WHERE ");
      
        sql.append("Select a from Unitjyouhou a WHERE ");
      

        boolean andFlg    = false;

        //genreがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if((sid)==0) {
        } else {
            sql.append("a.sid =" + sid);
     
            andFlg   = true;
        }


        //authorがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく

        if((yid)==0) {
        } else {
            if (andFlg) sql.append(" AND ");
            sql.append("a.yid =" + yid);
       
            andFlg    = true;
        }
        
 /**
        //titleがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if(!"".equals(yid)) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.title LIKE :title");
            yidFlg = true;
            andFlg   = true;
        }
**/
        
        sql.append(" order by zid");
        /*
        QueryはSQLでデータを問い合わせるためのクエリ文に相当する機能を持つ
        entityManagerのcreateQueryメソッドを使用する
        sql変数を引数に渡す
        */
        Query query = entityManager.createQuery(sql.toString());

        //上記のif文でtrueになった場合は、各変数に値をセットする
        //今回、あいまい検索したいのでlike句を使用する
//        if (sidFlg) query.setParameter("sid", sid );
//        if (bidFlg) query.setParameter("bid", "%" + bid + "%");
//        if (yidFlg) query.setParameter("yid", "%" + yid + "%");
        return query.getResultList();	
    }
    
    //Daoクラスで用意したsearchメソッドをオーバーライドする
    @SuppressWarnings("unchecked")
    @Override
    public List<Unitjyouhou> search_sid(Integer zid) {

        //StringBuilderでSQL文を連結する
        StringBuilder sql = new StringBuilder();
        //sql.append("Select a.kid from unitkousei a inner join sendanm b on a.sid = b.id WHERE ");
      
        sql.append("Select a from Unitjyouhou a WHERE ");
 
        //genreがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if((zid)==0) {
        } else {
            sql.append("a.zid =" + zid);
        }
       
        sql.append(" order by zid");


        Query query = entityManager.createQuery(sql.toString());

        return query.getResultList();	
    }   
    
}


