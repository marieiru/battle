package com.example.battle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.battle.entity.Sendanm;

public interface SlgmRepositorySendan extends JpaRepository<Sendanm, Integer> {

//	@Query("SELECT id FROM sendanm ORDER BY id")
//	Integer getRandomId();	
	
//	public List<Sendanm>  findAll();
	
}

