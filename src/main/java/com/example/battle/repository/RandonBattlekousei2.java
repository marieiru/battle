package com.example.battle.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.battle.entity.Battlekousei2;

public interface RandonBattlekousei2  extends CrudRepository<Battlekousei2, Integer> {
	@Query("SELECT id FROM Battlekousei2 ORDER BY RANDOM() limit 1")
	Integer getRandomId();
}
