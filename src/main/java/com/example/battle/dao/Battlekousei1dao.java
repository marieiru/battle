package com.example.battle.dao;

import java.io.Serializable;
import java.util.List;

import com.example.battle.entity.Battlekousei1;

public interface Battlekousei1dao  extends Serializable {
	public List<Battlekousei1> search(Integer did);
	public List<Battlekousei1> search_LIVE();
	
}
