package com.example.battle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.battle.entity.Bukim;


public interface SlgmRepositoryBuki extends JpaRepository<Bukim, Integer> {

//	@Query("SELECT wno FROM bukim ORDER BY wno")
//	Integer getRandomId();	
	
//	public List<Bukim>  findAll();	
	
}
