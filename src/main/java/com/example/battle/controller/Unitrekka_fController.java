
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.battle.entity.Unitrekka;
import com.example.battle.form.FormUnitrekka;
import com.example.battle.repository.FindUnitrekkaRepository;
import com.example.battle.repository.SlgmRepositorySendan;
import com.example.battle.repository.SlgmRepositoryUnitjyouhou;
import com.example.battle.repository.SlgmRepositoryUnitrekka;
import com.example.battle.repository.SlgmRepositoryUnityakuwari;

@Controller
@RequestMapping("/unitrekka_f")
public class Unitrekka_fController {

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
	
	@ModelAttribute
	public FormUnitrekka setForm() {
		return new FormUnitrekka();
	}

	@GetMapping("/index")
	public String Unitrekka_f(FormUnitrekka formUnitrekka, Model model) {

		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unityakuwari", unityakuwariRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));		
		
		model.addAttribute("rekkaList", repository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		formUnitrekka.setNewUnitrekka(true);
		return "unitrekka_f";
	}

	@GetMapping
	public String showList(FormUnitrekka formUnitrekka, Model model) {

		formUnitrekka.setNewUnitrekka(true);
		Iterable<Unitrekka> list = selectAll();

		model.addAttribute("list", list);
		model.addAttribute("title", "登録用フォーム");
		return "unitrekka_f";

	}

    @GetMapping("/select")
    public String select(@RequestParam("sid") Integer sid,@RequestParam("yid") Integer yid, Model model) {

        model.addAttribute("msg", "検索結果");
		model.addAttribute("prefecturesList", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unityakuwari", unityakuwariRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));		
        List<Unitrekka> result = search(sid,yid);
      
		model.addAttribute("rekkaList", result);

		return "unitrekka_f";
    }	

    public List<Unitrekka> search(Integer sid, Integer yid){

        List<Unitrekka> result = new ArrayList<Unitrekka>();

        //すべてブランクだった場合は全件検索する
     //   if ("".equals(genre) && "".equals(author) && "".equals(title)){
        if ((sid)==0 && (yid)==0){
        	result = repository.findAll(Sort.by(Sort.Direction.ASC, "zid"));
        }
        else {
            //上記以外の場合、BookDataDaoImplのメソッドを呼び出す
            result = findUnitrekkaRepository.search(sid,yid);
        }
        return result;
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
