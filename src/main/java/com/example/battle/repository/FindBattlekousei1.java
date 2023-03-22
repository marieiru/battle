package com.example.battle.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.battle.dao.Battlekousei1dao;
import com.example.battle.entity.Battlekousei1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@SuppressWarnings("serial")
@Repository
public class FindBattlekousei1 implements Battlekousei1dao {

	@Autowired
	private EntityManager entityManager;

	public FindBattlekousei1() {
		super();
	}

	public FindBattlekousei1(EntityManager manager) {
		this();
		entityManager = manager;
	}

	//Daoクラスで用意したsearchメソッドをオーバーライドする
	@SuppressWarnings("unchecked")
	@Override
	public List<Battlekousei1> search(Integer id) {

		//StringBuilderでSQL文を連結する
		StringBuilder sql = new StringBuilder();
		//sql.append("Select a.kid from unitkousei a inner join sendanm b on a.sid = b.id WHERE ");

		sql.append("Select a from Battlekousei1 a WHERE ");

		//genreがブランクではなかった場合、sql変数にappendする
		//フラグをtrueにしとく
		if ((id) == 0) {
		} else {
			sql.append("a.id =" + id);

		}

		sql.append(" order by id ");

		Query query = entityManager.createQuery(sql.toString());

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Battlekousei1> search_LIVE() {

		//StringBuilderでSQL文を連結する
		StringBuilder sql = new StringBuilder();
		//sql.append("Select a.kid from unitkousei a inner join sendanm b on a.sid = b.id WHERE ");

		sql.append("Select a from Battlekousei1 a WHERE ");

		//genreがブランクではなかった場合、sql変数にappendする
		//フラグをtrueにしとく

		sql.append(" a.death =0");
		sql.append(" AND ");
		sql.append(" a.mvg =0");


		Query query = entityManager.createQuery(sql.toString());

		return query.getResultList();
	}

}
