package com.example.battle.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormBattlereport {

	private Integer id;
	
	private Integer sno;	
	private Integer tno;	
	
	//射撃での成果
	
	private Integer hit1;		
	
	private Integer damage1;	
	
	private Integer uunz1;		
	
	private Integer kia1;		
	
	private Integer hit2;		
	
	private Integer damage2;	
	
	private Integer uunz2;			
	
	private Integer kia2;
	
	//白兵での成果
	
	private Integer ahit1;		
	
	private Integer adamage1;	
	
	private Integer auunz1;		
	
	private Integer akia1;			
	
	
	private Integer ahit2;		
	
	private Integer adamage2;	
	
	private Integer auunz2;				
	
	private Integer akia2;		
	
	
	private String comme;		
	
	
}
