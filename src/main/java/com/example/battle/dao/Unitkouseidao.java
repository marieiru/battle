package com.example.battle.dao;

import java.io.Serializable;
import java.util.List;

import com.example.battle.entity.Unitkousei;

public interface Unitkouseidao  extends Serializable {
	public List<Unitkousei> search(Integer zid);
}
