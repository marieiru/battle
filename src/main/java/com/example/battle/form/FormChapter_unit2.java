package com.example.battle.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormChapter_unit2 {

	private Integer id;
	@NotNull
	private Integer zid;
	@NotBlank
	private String  yuname;
	@NotNull
	private Integer sid;
	@NotNull
	private Integer yid;
	@NotNull
	private Integer pck;
	@NotNull
	private Integer mck;	
	
	private String  img;
	
}
