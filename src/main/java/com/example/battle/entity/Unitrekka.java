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
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="unitrekka")
public class Unitrekka {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//追加
	@Column
	private Integer id;
	@Column
	private Integer zid;
	@Column
	private Integer kuunz;	
	@Column
	private Integer mv;	
	@Column
	private Integer ass;
	@Column
	private Integer scs;
	@Column
	private Integer sid;	
	@Column
	private Integer yid;		
	
    @ManyToOne
    @JoinColumn(name="zid",insertable=false, updatable=false )
    private Unitjyouhou unitjyouhou;	

}
