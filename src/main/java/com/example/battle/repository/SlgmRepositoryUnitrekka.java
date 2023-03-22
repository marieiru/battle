package com.example.battle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.battle.entity.Unitrekka;

public interface SlgmRepositoryUnitrekka  extends JpaRepository<Unitrekka, Integer> {
	
//	@Query("SELECT id FROM unitrekka ORDER BY id")
//	Integer getRandomId();	
	
//	public List<Unitrekka>  findAll();
}
