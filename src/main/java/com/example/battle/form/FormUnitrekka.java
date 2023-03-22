package com.example.battle.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormUnitrekka {


	private Integer id;
	@NotNull
	private Integer zid;
	@NotNull
	private Integer kuunz;	
	@NotNull
	private Integer mv;	
	@NotNull
	private Integer ass;
	@NotNull
	private Integer scs;
	
	private Integer sid;	
	private Integer yid;		
	
	private Boolean newUnitrekka;
	
}
