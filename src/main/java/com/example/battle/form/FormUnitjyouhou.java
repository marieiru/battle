package com.example.battle.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormUnitjyouhou {
	
//	@Id
	private Integer zid;
	@NotBlank
	private String yuname;	
	
	@NotNull
	private Integer sid;	

	@NotNull
	private Integer yid;	
	
	@NotNull
	private Integer pck;	
	@NotNull
	private Integer mck;	
	@NotNull
	private Integer ninnzuu;	
	@NotNull
	private Integer zouin;	
	@NotNull
	private Integer sousuu;	
	@NotNull
	private Integer kbzouin;		
	
	private String img;
	
	private Boolean newUnitjyouhou;
	
}
