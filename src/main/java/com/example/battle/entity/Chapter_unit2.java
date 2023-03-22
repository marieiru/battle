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
@Table(name = "chapter_unit2")
@Getter
@Setter
public class Chapter_unit2 {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//追加
	@Column
	private Integer id;
	@Column
	private Integer zid;
	@Column
	private String  yuname;
	@Column
	private Integer sid;
	@Column
	private Integer yid;
	@Column
	private Integer pck;
	@Column
	private Integer mck;
	
	@Column
	private String img;	

    @ManyToOne
    @JoinColumn(name="sid",insertable=false, updatable=false )
    private Sendanm sendanm;
  
    @ManyToOne
    @JoinColumn(name="yid",insertable=false, updatable=false )
    private Unityakuwarim unityakuwarim;			
	
}
