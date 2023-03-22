
package com.example.battle.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.battle.entity.Unitjyouhou;
import com.example.battle.entity.Unitrekka;
import com.example.battle.form.FormUnitjyouhou;
import com.example.battle.form.FormUnitrekka;
import com.example.battle.repository.FindUnitrekkaRepository;
import com.example.battle.repository.FindunitjyouhouRepository;
import com.example.battle.repository.SlgmRepositorySendan;
import com.example.battle.repository.SlgmRepositoryUnitjyouhou;
import com.example.battle.repository.SlgmRepositoryUnitrekka;
import com.example.battle.repository.SlgmRepositoryUnityakuwari;

@Controller
@RequestMapping("/unitrekka_t")
public class Unitrekka_tController {

	@Autowired
	SlgmRepositoryUnitrekka repository;
	
	@Autowired
	SlgmRepositoryUnitjyouhou jyouhourepository;	
	
	@Autowired
	SlgmRepositorySendan categoryRepository;	
	
	@Autowired
	SlgmRepositoryUnityakuwari unityakuwariRepository;			
	
	@Autowired
	FindUnitrekkaRepository findUnitrekkaRepository;			
	
	@Autowired
	FindunitjyouhouRepository findunitjyouhou;			
	
	@ModelAttribute
	public FormUnitjyouhou setForm() {
		return new FormUnitjyouhou();
	}
	
	@ModelAttribute
	public FormUnitrekka setFormUnitrekka() {
		return new FormUnitrekka();
	}
	@GetMapping("/index")
	public String Unitrekka_t(FormUnitjyouhou formUnitjyouhou, Model model) {

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unityakuwari", unityakuwariRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));		
		
		model.addAttribute("jyouhouList", jyouhourepository.findAll(Sort.by(Sort.Direction.ASC, "zid")));
		
		formUnitjyouhou.setNewUnitjyouhou(true);
		return "unitrekka_t";
	}

    @GetMapping("/select")
    public String select(@ModelAttribute("formModel") Unitjyouhou unitjyouhou, Model model) {

        model.addAttribute("msg", "検索結果");
		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unityakuwari", unityakuwariRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));		

        List<Unitjyouhou> result = search_t(unitjyouhou.getSid(),unitjyouhou.getYid());
        model.addAttribute("jyouhouList", result);
        
		return "unitrekka_t";
    }	
    
    public List<Unitjyouhou> search_t(Integer sid, Integer yid){

        List<Unitjyouhou> result_t = new ArrayList<Unitjyouhou>();

        //すべてブランクだった場合は全件検索する
     //   if ("".equals(genre) && "".equals(author) && "".equals(title)){
        if ((sid)==0 && (yid)==0){
        	result_t = jyouhourepository.findAll(Sort.by(Sort.Direction.ASC, "zid"));
        	       //repository.findAll(Sort.by(Sort.Direction.ASC, "zid")));
        }
        else {
            //上記以外の場合、BookDataDaoImplのメソッドを呼び出す
        	result_t = findunitjyouhou.search(sid,yid);
        }
        return result_t;
    }        
    

	@GetMapping("/{zid}")
	public String showUpdatet_t(FormUnitrekka formUnitrekka, @PathVariable Integer zid, Model model) {
	
		model.addAttribute("unitList", jyouhourepository.findAll());

		formUnitrekka.setZid(zid);
		model.addAttribute("FormUnitrekka", formUnitrekka);	

//		makeUpdateModel(formUnitrekka, model);
		return "unitrekka";

	}    
    	
    
	public void insertUnitrekka(Unitrekka unitrekka) {
		repository.saveAndFlush(unitrekka);
	}

	public void updateUnitrekka(Unitrekka unitrekka) {
		repository.saveAndFlush(unitrekka);
	}

	public void deleteUnitrekkaById(Integer id) {
		repository.deleteById(id);
	}

	public Iterable<Unitrekka> selectAll() {

		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	public Optional<Unitrekka> selectOneById(Integer id) {
		return repository.findById(id);
	}	
		
	
		
	
	
	
}
