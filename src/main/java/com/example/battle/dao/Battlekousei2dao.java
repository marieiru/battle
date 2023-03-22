package com.example.battle.dao;

import java.io.Serializable;
import java.util.List;

import com.example.battle.entity.Battlekousei2;

public interface Battlekousei2dao  extends Serializable {
	public List<Battlekousei2> search(Integer did);
	public List<Battlekousei2> search_LIVE();
}
