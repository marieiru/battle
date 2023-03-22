package com.example.battle.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormBattlemode {

	private Integer id;
	@NotNull
	private Integer bmode1;	
	@NotNull
	private Integer bmode2;	
	@NotNull
	private Integer bmvc;	
	

	private Integer trik;			
	
	
}
