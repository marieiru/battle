package com.example.battle.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormBukim {

	private Integer wno;
	@NotBlank
	private String wname;	
	@NotBlank
	private String syubetu;	
	@NotNull
	private Integer syatei;	
	@NotNull
	private Integer at;	
	@NotNull
	private Integer ap;	
	
	@NotBlank
	private String ks;		
	
	@NotBlank
	private String dmg;	
	
	private String abl;		
	
	private Boolean newBukim;
	@NotNull
	private Integer sid;	

	@NotNull
	private Integer hit;	
	
}
