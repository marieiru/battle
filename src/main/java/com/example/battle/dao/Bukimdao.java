package com.example.battle.dao;

import java.io.Serializable;
import java.util.List;

import com.example.battle.entity.Bukim;

public interface Bukimdao extends Serializable {
	public List<Bukim> search(Integer sid,Integer h);
	public List<Bukim> search_wno(Integer wno);
}
