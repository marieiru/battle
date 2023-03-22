package com.example.battle.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.battle.entity.Battlekousei1;


public interface RandonBattlekousei1  extends CrudRepository<Battlekousei1, Integer> {
	@Query("SELECT id FROM Battlekousei1 ORDER BY RANDOM() limit 1")
	Integer getRandomId();
}


