package com.example.battle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.battle.entity.Unityakuwarim;

public interface SlgmRepositoryUnityakuwari extends JpaRepository<Unityakuwarim, Integer> {
	
//	@Query("SELECT id FROM unityakuwarim ORDER BY id")
//	Integer getRandomId();	
	
//	public List<Unityakuwarim>  findAll();
}
