package com.example.battle.dao;

import java.io.Serializable;
import java.util.List;

import com.example.battle.entity.Chapter_kousei2;

public interface Chapter_kousei2dao extends Serializable {
	public List<Chapter_kousei2> search(Integer did);
}