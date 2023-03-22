package com.example.battle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="bukim")
@Getter
@Setter
public class Bukim {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//追加
	@Column
	private Integer wno;
	@Column
	private String wname;	
	@Column
	private String syubetu;	
	@Column
	private Integer syatei;	
	@Column
	private Integer at;	
	@Column
	private Integer ap;	
	@Column
	private String ks;	
	@Column
	private String dmg;	
	@Column
	private String abl;		
	
	@Column
	private Integer sid;	
	
	@Column
	private Integer hit;		
	
    @ManyToOne
    @JoinColumn(name="sid",insertable=false, updatable=false )
    private Sendanm sendanm;	
	
}
