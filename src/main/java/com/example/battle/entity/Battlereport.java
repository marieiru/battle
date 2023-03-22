package com.example.battle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="battlereport")
@Getter
@Setter
public class Battlereport {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//追加
	@Column
	private Integer id;

	@Column
	private Integer sno;			
	
	@Column
	private Integer tno;			
	
	//射撃での成果
	@Column
	private Integer hit1;		
	@Column
	private Integer damage1;	
	@Column
	private Integer uunz1;		
	@Column
	private Integer kia1;		
	
	private Integer hit2;		
	@Column
	private Integer damage2;	
	@Column
	private Integer uunz2;			
	@Column
	private Integer kia2;
	
	//白兵での成果
	@Column
	private Integer ahit1;		
	@Column
	private Integer adamage1;	
	@Column
	private Integer auunz1;		
	@Column
	private Integer akia1;			
	
	
	private Integer ahit2;		
	@Column
	private Integer adamage2;	
	@Column
	private Integer auunz2;				
	@Column
	private Integer akia2;		
	
	@Column
	private String comme;	

}