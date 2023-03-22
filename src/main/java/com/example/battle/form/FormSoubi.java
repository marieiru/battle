package com.example.battle.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormSoubi {
//	@Id
	private Integer id;
	@NotNull
	private Integer kid;
	@NotNull
	private Integer wno;
	@NotNull
	private Integer wgno;
	
	private Boolean newSoubi;
}
