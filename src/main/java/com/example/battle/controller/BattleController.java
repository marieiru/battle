package com.example.battle.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.battle.entity.Battlekousei1;
import com.example.battle.entity.Battlekousei2;
import com.example.battle.entity.Battlemode;
import com.example.battle.entity.Battlereport;
import com.example.battle.entity.Bukim;
import com.example.battle.entity.Chapter_kousei1;
import com.example.battle.entity.Chapter_kousei2;
import com.example.battle.entity.Unitrekka;
import com.example.battle.form.FormBattlemode;
import com.example.battle.form.FormBattlereport;
import com.example.battle.repository.FindBattlekousei1;
import com.example.battle.repository.FindBattlekousei2;
import com.example.battle.repository.FindUnitrekkaRepository;
import com.example.battle.repository.FindbukimRepository;
import com.example.battle.repository.RandonBattlekousei1;
import com.example.battle.repository.RandonBattlekousei2;
import com.example.battle.repository.SlgmRepositoryBattlekousei1;
import com.example.battle.repository.SlgmRepositoryBattlekousei2;
import com.example.battle.repository.SlgmRepositoryBattlemode;
import com.example.battle.repository.SlgmRepositoryBattlereport;
import com.example.battle.repository.SlgmRepositoryChapter_kousei1;
import com.example.battle.repository.SlgmRepositoryChapter_kousei2;
import com.example.battle.repository.SlgmRepositoryChapter_unit1;
import com.example.battle.repository.SlgmRepositoryChapter_unit2;

@Controller
@RequestMapping("/battle")
public class BattleController {

	@Autowired
	SlgmRepositoryChapter_unit1 repoChapter_unit1;

	@Autowired
	SlgmRepositoryChapter_unit2 repoChapter_unit2;

	@Autowired
	SlgmRepositoryBattlekousei1 repoBattlekousei1;

	@Autowired
	SlgmRepositoryBattlekousei2 repoBattlekousei2;

	@Autowired
	SlgmRepositoryChapter_kousei1 repoChapter_kousei1;

	@Autowired
	SlgmRepositoryChapter_kousei2 repoChapter_kousei2;

	@Autowired
	SlgmRepositoryBattlemode repoBattlemode;

	@Autowired
	SlgmRepositoryBattlereport repoBattlereport;

	@Autowired
	FindUnitrekkaRepository findUnitrekkaRepository;

	@Autowired
	FindBattlekousei1 findBK1Repo;

	@Autowired
	FindBattlekousei2 findBK2Repo;

	@Autowired
	RandonBattlekousei1 randomBK1;

	@Autowired
	RandonBattlekousei2 randomBK2;

	@Autowired
	FindbukimRepository Findbuki;

	@GetMapping("/index")
	public String bukim(FormBattlemode formBattlemode, Model model) {

		model.addAttribute("chapter1List", repoChapter_unit1.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("chapter2List", repoChapter_unit2.findAll(Sort.by(Sort.Direction.ASC, "id")));

		//演算テーブルをクリア
		//		repoBattlekousei1.deleteAll();
		//		repoBattlekousei2.deleteAll();
		//	repoBattlemode.deleteAll();
		//		repoBattlereport.deleteAll();

		// reportList
		model.addAttribute("reportList", repoBattlereport.findAll(Sort.by(Sort.Direction.ASC, "id")));

		List<Battlemode> result_mode = repoBattlemode.findAll(Sort.by(Sort.Direction.DESC, "id"));
		if (result_mode != null && result_mode.size() >= 1) {

			formBattlemode.setBmode1(result_mode.get(0).getBmode1());
			formBattlemode.setBmode2(result_mode.get(0).getBmode2());
			formBattlemode.setBmvc(result_mode.get(0).getBmvc());
			formBattlemode.setTrik(result_mode.get(0).getTrik());

		} else {
			formBattlemode.setBmvc(20);
			formBattlemode.setTrik(1);
		}

		return "battle";
	}

	@ModelAttribute
	public FormBattlemode setFormBattlemode() {
		return new FormBattlemode();
	}

	@ModelAttribute
	public FormBattlereport setFormBattlereport() {
		return new FormBattlereport();
	}

	@GetMapping("/report")
	public String report(@ModelAttribute("formModel") FormBattlemode formBattlemode, Model model) {

		model.addAttribute("msg", "検索結果");
		model.addAttribute("chapter1List", repoChapter_unit1.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("chapter2List", repoChapter_unit2.findAll(Sort.by(Sort.Direction.ASC, "id")));
		repoBattlereport.deleteAll();
		repoBattlekousei1.deleteAll();
		repoBattlekousei2.deleteAll();
		repoBattlemode.deleteAll();

		//設定を保存
		Battlemode battlemode = new Battlemode();
		battlemode.setBmode1(formBattlemode.getBmode1());
		battlemode.setBmode2(formBattlemode.getBmode2());
		battlemode.setBmvc(formBattlemode.getBmvc());
		battlemode.setTrik(formBattlemode.getTrik());
		repoBattlemode.saveAndFlush(battlemode);

		//最初に両方のリストを対戦用DBに複写しておく
		List<Chapter_kousei1> result_Ck1 = repoChapter_kousei1.findAll(Sort.by(Sort.Direction.DESC, "id"));

		if (result_Ck1 != null && result_Ck1.size() >= 1) {
			for (int i = 0; i <= result_Ck1.size() - 1; i++) {

				Battlekousei1 battlekousei1 = new Battlekousei1();

				battlekousei1.setAbl(result_Ck1.get(i).getAbl());
				battlekousei1.setAk(result_Ck1.get(i).getAk());
				battlekousei1.setAsa(result_Ck1.get(i).getAsa());
				battlekousei1.setAssp(result_Ck1.get(i).getAssp());
				battlekousei1.setAt(result_Ck1.get(i).getAt());
				battlekousei1.setBid(result_Ck1.get(i).getBid());
				battlekousei1.setWno(result_Ck1.get(i).getWno());
				battlekousei1.setWno2(result_Ck1.get(i).getWno2());
				battlekousei1.setWno3(result_Ck1.get(i).getWno3());
				battlekousei1.setWno4(result_Ck1.get(i).getWno4());
				battlekousei1.setWno5(result_Ck1.get(i).getWno5());
				battlekousei1.setWno6(result_Ck1.get(i).getWno6());
				battlekousei1.setWno7(result_Ck1.get(i).getWno7());

				battlekousei1.setKid(result_Ck1.get(i).getKid());
				battlekousei1.setKname(result_Ck1.get(i).getKname());

				battlekousei1.setLd(result_Ck1.get(i).getLd());
				battlekousei1.setMvg(formBattlemode.getBmvc() / 2);
				battlekousei1.setTaff(result_Ck1.get(i).getTaff());
				battlekousei1.setUunz(result_Ck1.get(i).getUunz());
				battlekousei1.setZid(result_Ck1.get(i).getZid());

				if (i < result_Ck1.size() - 1) {
					repoBattlekousei1.save(battlekousei1);
				} else {
					repoBattlekousei1.save(battlekousei1);
					repoBattlekousei1.flush();
				}

			}

		}

		List<Chapter_kousei2> result_Ck2 = repoChapter_kousei2.findAll(Sort.by(Sort.Direction.DESC, "id"));

		if (result_Ck2 != null && result_Ck2.size() >= 1) {
			for (int i = 0; i <= result_Ck2.size() - 1; i++) {
				Battlekousei2 battlekousei2 = new Battlekousei2();

				battlekousei2.setAbl(result_Ck2.get(i).getAbl());
				battlekousei2.setAk(result_Ck2.get(i).getAk());
				battlekousei2.setAsa(result_Ck2.get(i).getAsa());
				battlekousei2.setAssp(result_Ck2.get(i).getAssp());
				battlekousei2.setAt(result_Ck2.get(i).getAt());
				battlekousei2.setBid(result_Ck2.get(i).getBid());
				battlekousei2.setWno(result_Ck2.get(i).getWno());
				battlekousei2.setWno2(result_Ck2.get(i).getWno2());
				battlekousei2.setWno3(result_Ck2.get(i).getWno3());
				battlekousei2.setWno4(result_Ck2.get(i).getWno4());
				battlekousei2.setWno5(result_Ck2.get(i).getWno5());
				battlekousei2.setWno6(result_Ck2.get(i).getWno6());
				battlekousei2.setWno7(result_Ck2.get(i).getWno7());

				battlekousei2.setKid(result_Ck2.get(i).getKid());
				battlekousei2.setKname(result_Ck2.get(i).getKname());

				battlekousei2.setLd(result_Ck2.get(i).getLd());
				battlekousei2.setMvg(formBattlemode.getBmvc() / 2);
				battlekousei2.setTaff(result_Ck2.get(i).getTaff());
				battlekousei2.setUunz(result_Ck2.get(i).getUunz());
				battlekousei2.setZid(result_Ck2.get(i).getZid());

				if (i < result_Ck2.size() - 1) {
					repoBattlekousei2.save(battlekousei2);
				} else {
					repoBattlekousei2.save(battlekousei2);
					repoBattlekousei2.flush();
				}

			}
		}
		////複写おわり

		//対戦ターン経過　1-7
		Random random = new Random();

		for (int tn = 0; tn <= 6; tn++) {

			int ks = 0; //回数
			int num = 0; //回数の変換前									

			String syubetu = "";
			int syatei = 0;
			int at = 0;
			int ap = 0;

			String Sks = "";
			String dmg = "";
			String abl = "";

			int hit = 0;
			int sss = 0;

			//ダメージ判定のフラグ
			int damageF = 0;
			int SVRF = 0;
			int Gekiha1 = 0;
			int Gekiha2 = 0;
			int DMGM = 0;

			int hit1 = 0;
			int hit2 = 0;

			int DMGC1 = 0;
			int DMGC2 = 0;

			int UUNZ1 = 0;
			int UUNZ2 = 0;

			int akia1 = 0;
			int akia2 = 0;
			int ahit1 = 0;
			int ahit2 = 0;
			int aDMGC1 = 0;
			int aDMGC2 = 0;
			int aUUNZ1 = 0;
			int aUUNZ2 = 0;

			int EnId = 0;
			int EnTaff = 0;
			int EnSV1 = 0;
			int EnSV2 = 0;
			int EnUUNZ = 0;

			int kyori = 0;
			int IDOUF = 0;
			String BUKIn = "";

			String BTComment = "";

			int chargeF = 0;

			int IDF = 0;

			//先攻側 移動処理
			IDOUF = 0;
			if (formBattlemode.getBmode1() == 1) {
			} else {
				IDOUF = 1;
			}
			//移動処理が存在する場合、移動する
			if (IDOUF == 1) {
				List<Battlekousei1> result_Bk11 = repoBattlekousei1.findAll(Sort.by(Sort.Direction.DESC, "id"));
				if (result_Bk11 != null && result_Bk11.size() >= 1) {

					for (int i = 0; i <= result_Bk11.size() - 1; i++) {

						//構成員の現在の能力値をとりだす
						List<Unitrekka> result_zid = findUnitrekkaRepository.search_zid(result_Bk11.get(i).getZid(),
								result_Bk11.get(i).getUunz());

						if (result_zid != null && result_zid.size() >= 1) {

							Battlekousei1 battlekousei1 = new Battlekousei1();

							battlekousei1.setId(result_Bk11.get(i).getId());
							battlekousei1.setAbl(result_Bk11.get(i).getAbl());
							battlekousei1.setAk(result_Bk11.get(i).getAk());
							battlekousei1.setAsa(result_Bk11.get(i).getAsa());
							battlekousei1.setAssp(result_Bk11.get(i).getAssp());
							battlekousei1.setAt(result_Bk11.get(i).getAt());
							battlekousei1.setBid(result_Bk11.get(i).getBid());
							battlekousei1.setWno(result_Bk11.get(i).getWno());
							battlekousei1.setWno2(result_Bk11.get(i).getWno2());
							battlekousei1.setWno3(result_Bk11.get(i).getWno3());
							battlekousei1.setWno4(result_Bk11.get(i).getWno4());
							battlekousei1.setWno5(result_Bk11.get(i).getWno5());
							battlekousei1.setWno6(result_Bk11.get(i).getWno6());
							battlekousei1.setWno7(result_Bk11.get(i).getWno7());

							battlekousei1.setKid(result_Bk11.get(i).getKid());
							battlekousei1.setKname(result_Bk11.get(i).getKname());

							battlekousei1.setLd(result_Bk11.get(i).getLd());

							if ((result_Bk11.get(i).getMvg() - result_zid.get(0).getMv()) < 0) {
								battlekousei1.setMvg(0);
							} else {
								battlekousei1.setMvg(result_Bk11.get(i).getMvg() - result_zid.get(0).getMv());
							}

							battlekousei1.setTaff(result_Bk11.get(i).getTaff());
							battlekousei1.setUunz(result_Bk11.get(i).getUunz());
							battlekousei1.setZid(result_Bk11.get(i).getZid());

							repoBattlekousei1.saveAndFlush(battlekousei1);

						}
					}
				}

			}

			//先攻側
			//先攻側
			//先攻側
			//先攻側を一体ずつ射撃で処理
			List<Battlekousei1> result_Bk1 = repoBattlekousei1.findAll(Sort.by(Sort.Direction.DESC, "id"));

			if (result_Bk1 != null && result_Bk1.size() >= 1) {
				for (int i = 0; i <= result_Bk1.size() - 1; i++) {

					IDF = result_Bk1.get(i).getId();

					kyori = result_Bk1.get(i).getMvg();

					//後攻が全滅している場合を想定して
					List<Battlekousei2> result_Ck21 = repoBattlekousei2.findAll();
					if (result_Ck21 != null && result_Ck21.size() >= 1) {

						//ランダムにひとり取り出して距離をとる
						Optional<Battlekousei2> result_Ck22 = selectOneRandomId2();

						Battlekousei2 battlekousei2 = new Battlekousei2();

						battlekousei2.setId(result_Ck22.get().getId());
						battlekousei2.setAbl(result_Ck22.get().getAbl());
						battlekousei2.setAk(result_Ck22.get().getAk());
						battlekousei2.setAsa(result_Ck22.get().getAsa());
						battlekousei2.setAssp(result_Ck22.get().getAssp());
						battlekousei2.setAt(result_Ck22.get().getAt());
						battlekousei2.setBid(result_Ck22.get().getBid());
						battlekousei2.setWno(result_Ck22.get().getWno());
						battlekousei2.setWno2(result_Ck22.get().getWno2());
						battlekousei2.setWno3(result_Ck22.get().getWno3());
						battlekousei2.setWno4(result_Ck22.get().getWno4());
						battlekousei2.setWno5(result_Ck22.get().getWno5());
						battlekousei2.setWno6(result_Ck22.get().getWno6());
						battlekousei2.setWno7(result_Ck22.get().getWno7());

						battlekousei2.setKid(result_Ck22.get().getKid());
						battlekousei2.setKname(result_Ck22.get().getKname());

						battlekousei2.setLd(result_Ck22.get().getLd());
						battlekousei2.setMvg(result_Ck22.get().getMvg());
						battlekousei2.setTaff(result_Ck22.get().getTaff());
						battlekousei2.setUunz(result_Ck22.get().getUunz());
						battlekousei2.setZid(result_Ck22.get().getZid());

						kyori = kyori + result_Ck22.get().getMvg();

						EnId = result_Ck22.get().getId();
						EnTaff = result_Ck22.get().getTaff();
						EnSV1 = result_Ck22.get().getAsa();
						EnSV2 = result_Ck22.get().getAssp();
						EnUUNZ = result_Ck22.get().getUunz();

						//ここで検索してうまくできるか　テスト					

						//構成員の現在の能力値をとりだす
						List<Unitrekka> result_zid = findUnitrekkaRepository.search_zid(result_Bk1.get(i).getZid(),
								result_Bk1.get(i).getUunz());

						if (result_zid != null && result_zid.size() >= 1) {
							chargeF = 0;
							//射程内なら射撃 1-4
							if ((Findbuki.search_wno(result_Bk1.get(i).getWno()).get(0).getSyatei() >= kyori)
									|| (Findbuki.search_wno(result_Bk1.get(i).getWno2()).get(0).getSyatei() >= kyori)
									|| (Findbuki.search_wno(result_Bk1.get(i).getWno3()).get(0).getSyatei() >= kyori)
									|| (Findbuki.search_wno(result_Bk1.get(i).getWno4()).get(0).getSyatei() >= kyori)) {
								chargeF = 1;
							}

							// 
							if ((chargeF == 1) && (formBattlemode.getBmode1() != 3)) {
								//1-4 でまわす なるべく簡潔化したい
								for (int kk = 0; kk <= 3; kk++) {

									if (kk == 0) {
										List<Bukim> Buki1 = Findbuki.search_wno(result_Bk1.get(i).getWno());
										syubetu = Buki1.get(0).getSyubetu();
										syatei = Buki1.get(0).getSyatei();
										at = Buki1.get(0).getAt();
										ap = Buki1.get(0).getAp();

										dmg = Buki1.get(0).getDmg();
										abl = Buki1.get(0).getAbl();
										hit = Buki1.get(0).getHit();
										sss = Buki1.size();
										Sks = Buki1.get(0).getKs();

										BUKIn = Buki1.get(0).getWname();

									} else if (kk == 1) {
										List<Bukim> Buki2 = Findbuki.search_wno(result_Bk1.get(i).getWno2());
										syubetu = Buki2.get(0).getSyubetu();
										syatei = Buki2.get(0).getSyatei();
										at = Buki2.get(0).getAt();
										ap = Buki2.get(0).getAp();

										dmg = Buki2.get(0).getDmg();
										abl = Buki2.get(0).getAbl();
										hit = Buki2.get(0).getHit();
										sss = Buki2.size();
										Sks = Buki2.get(0).getKs();
										BUKIn = Buki2.get(0).getWname();
									} else if (kk == 2) {
										List<Bukim> Buki3 = Findbuki.search_wno(result_Bk1.get(i).getWno3());
										syubetu = Buki3.get(0).getSyubetu();
										syatei = Buki3.get(0).getSyatei();
										at = Buki3.get(0).getAt();
										ap = Buki3.get(0).getAp();

										dmg = Buki3.get(0).getDmg();
										abl = Buki3.get(0).getAbl();
										hit = Buki3.get(0).getHit();
										sss = Buki3.size();
										Sks = Buki3.get(0).getKs();
										BUKIn = Buki3.get(0).getWname();
									} else {
										List<Bukim> Buki4 = Findbuki.search_wno(result_Bk1.get(i).getWno4());
										syubetu = Buki4.get(0).getSyubetu();
										syatei = Buki4.get(0).getSyatei();
										at = Buki4.get(0).getAt();
										ap = Buki4.get(0).getAp();

										dmg = Buki4.get(0).getDmg();
										abl = Buki4.get(0).getAbl();
										hit = Buki4.get(0).getHit();
										sss = Buki4.size();
										Sks = Buki4.get(0).getKs();
										BUKIn = Buki4.get(0).getWname();
									}

									//ここで武器ごとに射程確認
									if ((sss >= 1) && (syatei >= kyori)) {

										if (Sks.equals("1D")) {
											ks = random.nextInt(5) + 1;
										} else if (Sks.equals("2D")) {
											ks = random.nextInt(5) + random.nextInt(5) + 2;
										} else if (Sks.equals("3D")) {
											ks = random.nextInt(5) + random.nextInt(5) + random.nextInt(5) + 3;
										} else {
											ks = Integer.parseInt(Sks);
										}

										if ((syubetu.equals("ラピッド")) && ((syatei / 2) >= kyori)) {
											ks = (ks * 2);
										}

										//武器の射撃回数分まわす
										for (int sbki = 0; sbki <= ks - 1; sbki++) {

											damageF = 0;
											SVRF = 0;

											//命中判定
											//	int randomValue1 = random.nextInt(5);
											// result_zid
											// result_Ck22.get().getMvg();

											//		int hit1 = 0;
											//		int hit2 = 0;

											//		int DMGC1 = 0;
											//		int DMGC2 = 0;
											// (BUKIn.equals("装備無し")) {
											if (BUKIn.equals("装備無し")) {

											} else {
												if (result_zid.get(0).getScs() <= 1 + hit + random.nextInt(5)) {

													hit1 = hit1 + 1;

													int rr = random.nextInt(5) + 1;

													//ダメージ判定
													if ((at >= EnTaff * 2) && (rr >= 2)) {
														damageF = 1;
													} else if ((at >= EnTaff) && (rr >= 3)) {
														damageF = 1;
													} else if ((at == EnTaff) && (rr >= 4)) {
														damageF = 1;
													} else if ((at < EnTaff) && (rr >= 5)) {
														damageF = 1;
													} else if ((at * 2 <= EnTaff) && (rr >= 6)) {
														damageF = 1;
													} else {
														damageF = 0;
													}

												}

												// List<Unitrekka> result_zid =

												//致傷量
												if (damageF == 1) { //ダメージ判定 成功なら

													DMGC1 = DMGC1 + 1;

													//相手側のセーヴィング

													int SVR = random.nextInt(5) + 1;

													//スペシャルセーブなし
													if (EnSV2 == 0) {
														if ((SVR >= (EnSV1 + ap))) {
															SVRF = 1;
														} else {
															SVRF = 0;
														}
													} else {
														if (((EnSV1 + ap) >= EnSV2) && (SVR >= EnSV2)) {
															SVRF = 1;
														} else if (((EnSV1 + ap) < EnSV2) && (SVR >= (EnSV1 + ap))) {
															SVRF = 1;
														} else {
															SVRF = 0;
														}
													}
												}

												//		int UUNZ1 = 0;
												//		int UUNZ2 = 0;	
												//		Gekiha1 = 0;
												//撃破数
												if ((SVRF == 0) && (damageF == 1)) {

													if (dmg.equals("1D")) {
														DMGM = random.nextInt(5) + 1;
													} else if (dmg.equals("2D")) {
														DMGM = random.nextInt(5) + random.nextInt(5) + 2;
													} else if (dmg.equals("3D")) {
														DMGM = random.nextInt(5) + random.nextInt(5) + random.nextInt(5)
																+ 3;
													} else {
														DMGM = Integer.parseInt(dmg);
													}

													UUNZ1 = UUNZ1 + DMGM;

													//ウーンズを減らし、死亡の場合はレコード削除
													if ((EnUUNZ - DMGM) > 0) {
														//	result_Ck22.get().getId();

														//
														//	battlekousei2.setUunz(result_Ck22.get().getUunz() - DMGM);

														//		Battlekousei2 battlekousei2 = new Battlekousei2();

														battlekousei2.setId(battlekousei2.getId());
														battlekousei2.setAbl(battlekousei2.getAbl());
														battlekousei2.setAk(battlekousei2.getAk());
														battlekousei2.setAsa(battlekousei2.getAsa());
														battlekousei2.setAssp(battlekousei2.getAssp());
														battlekousei2.setAt(battlekousei2.getAt());
														battlekousei2.setBid(battlekousei2.getBid());
														battlekousei2.setWno(battlekousei2.getWno());
														battlekousei2.setWno2(battlekousei2.getWno2());
														battlekousei2.setWno3(battlekousei2.getWno3());
														battlekousei2.setWno4(battlekousei2.getWno4());
														battlekousei2.setWno5(battlekousei2.getWno5());
														battlekousei2.setWno6(battlekousei2.getWno6());
														battlekousei2.setWno7(battlekousei2.getWno7());

														battlekousei2.setKid(battlekousei2.getKid());
														battlekousei2.setKname(battlekousei2.getKname());

														battlekousei2.setLd(battlekousei2.getLd());
														battlekousei2.setMvg(battlekousei2.getMvg());
														battlekousei2.setTaff(battlekousei2.getTaff());
														battlekousei2.setUunz(battlekousei2.getUunz() - DMGM);

														EnUUNZ = (EnUUNZ - DMGM);

														battlekousei2.setZid(battlekousei2.getZid());

														repoBattlekousei2.saveAndFlush(battlekousei2);

													} else {
														//相手が死亡する場合　相手を削除して　次を抽出する

														Gekiha1 = Gekiha1 + 1;
														repoBattlekousei2.deleteById(EnId);

														//後攻が全滅している場合を想定して
														List<Battlekousei2> result_Ck24 = repoBattlekousei2.findAll();
														if (result_Ck24 != null && result_Ck24.size() >= 1) {

															//ランダムにひとり取り出して距離をとる
															Optional<Battlekousei2> result_Ck23 = selectOneRandomId2();

															battlekousei2.setId(result_Ck23.get().getId());
															battlekousei2.setAbl(result_Ck23.get().getAbl());
															battlekousei2.setAk(result_Ck23.get().getAk());
															battlekousei2.setAsa(result_Ck23.get().getAsa());
															battlekousei2.setAssp(result_Ck23.get().getAssp());
															battlekousei2.setAt(result_Ck23.get().getAt());
															battlekousei2.setBid(result_Ck23.get().getBid());
															battlekousei2.setWno(result_Ck23.get().getWno());
															battlekousei2.setWno2(result_Ck23.get().getWno2());
															battlekousei2.setWno3(result_Ck23.get().getWno3());
															battlekousei2.setWno4(result_Ck23.get().getWno4());
															battlekousei2.setWno5(result_Ck23.get().getWno5());
															battlekousei2.setWno6(result_Ck23.get().getWno6());
															battlekousei2.setWno7(result_Ck23.get().getWno7());

															battlekousei2.setKid(result_Ck23.get().getKid());
															battlekousei2.setKname(result_Ck23.get().getKname());

															battlekousei2.setLd(result_Ck23.get().getLd());
															battlekousei2.setMvg(result_Ck23.get().getMvg());
															battlekousei2.setTaff(result_Ck23.get().getTaff());
															battlekousei2.setUunz(result_Ck23.get().getUunz());
															battlekousei2.setZid(result_Ck23.get().getZid());

															kyori = result_Bk1.get(i).getMvg()
																	+ result_Ck23.get().getMvg();

															EnId = result_Ck23.get().getId();
															EnTaff = result_Ck23.get().getTaff();
															EnSV1 = result_Ck23.get().getAsa();
															EnSV2 = result_Ck23.get().getAssp();
															EnUUNZ = result_Ck23.get().getUunz();
														} else {
															BTComment = "後攻が全滅";
															//	break;
														}
													}

												}

											}
										}

									}

								}

							} else if (formBattlemode.getBmode1() != 1) {

								//追加移動分の処理

								//	List<Battlekousei1> result_Bk11 = repoBattlekousei1.findAll(Sort.by(Sort.Direction.DESC, "id"));
								if (result_Bk1 != null && result_Bk1.size() >= 1) {

									Battlekousei1 battlekousei1 = new Battlekousei1();

									battlekousei1.setId(result_Bk1.get(i).getId());
									battlekousei1.setAbl(result_Bk1.get(i).getAbl());
									battlekousei1.setAk(result_Bk1.get(i).getAk());
									battlekousei1.setAsa(result_Bk1.get(i).getAsa());
									battlekousei1.setAssp(result_Bk1.get(i).getAssp());
									battlekousei1.setAt(result_Bk1.get(i).getAt());
									battlekousei1.setBid(result_Bk1.get(i).getBid());
									battlekousei1.setWno(result_Bk1.get(i).getWno());
									battlekousei1.setWno2(result_Bk1.get(i).getWno2());
									battlekousei1.setWno3(result_Bk1.get(i).getWno3());
									battlekousei1.setWno4(result_Bk1.get(i).getWno4());
									battlekousei1.setWno5(result_Bk1.get(i).getWno5());
									battlekousei1.setWno6(result_Bk1.get(i).getWno6());
									battlekousei1.setWno7(result_Bk1.get(i).getWno7());

									battlekousei1.setKid(result_Bk1.get(i).getKid());
									battlekousei1.setKname(result_Bk1.get(i).getKname());

									battlekousei1.setLd(result_Bk1.get(i).getLd());

									if ((result_Bk1.get(i).getMvg() - ((random.nextInt(5) + 1))) < 0) {
										battlekousei1.setMvg(0);
									} else {
										battlekousei1.setMvg(result_Bk1.get(i).getMvg() - ((random.nextInt(5) + 1)));
									}

									battlekousei1.setTaff(result_Bk1.get(i).getTaff());
									battlekousei1.setUunz(result_Bk1.get(i).getUunz());
									battlekousei1.setZid(result_Bk1.get(i).getZid());

									repoBattlekousei1.saveAndFlush(battlekousei1);

								}

							}

							/////////

						}
					} else {
						BTComment = "後攻が全滅";

					}

				} // 繰り返しの終端
			} else {
				//先攻が全滅した時の処理を後で追加すること

			}

			//後攻側 移動処理
			IDOUF = 0;
			if (formBattlemode.getBmode2() == 1) {
			} else {
				IDOUF = 1;
			}
			//移動処理が存在する場合、移動する
			if (IDOUF == 1) {
				List<Battlekousei2> result_Bk11 = repoBattlekousei2.findAll(Sort.by(Sort.Direction.DESC, "id"));
				if (result_Bk11 != null && result_Bk11.size() >= 1) {
					for (int i = 0; i <= result_Bk11.size() - 1; i++) {
						//構成員の現在の能力値をとりだす
						List<Unitrekka> result_zid = findUnitrekkaRepository.search_zid(result_Bk11.get(i).getZid(),
								result_Bk11.get(i).getUunz());

						if (result_zid != null && result_zid.size() >= 1) {

							Battlekousei2 battlekousei2 = new Battlekousei2();

							battlekousei2.setId(result_Bk11.get(i).getId());
							battlekousei2.setAbl(result_Bk11.get(i).getAbl());
							battlekousei2.setAk(result_Bk11.get(i).getAk());
							battlekousei2.setAsa(result_Bk11.get(i).getAsa());
							battlekousei2.setAssp(result_Bk11.get(i).getAssp());
							battlekousei2.setAt(result_Bk11.get(i).getAt());
							battlekousei2.setBid(result_Bk11.get(i).getBid());
							battlekousei2.setWno(result_Bk11.get(i).getWno());
							battlekousei2.setWno2(result_Bk11.get(i).getWno2());
							battlekousei2.setWno3(result_Bk11.get(i).getWno3());
							battlekousei2.setWno4(result_Bk11.get(i).getWno4());
							battlekousei2.setWno5(result_Bk11.get(i).getWno5());
							battlekousei2.setWno6(result_Bk11.get(i).getWno6());
							battlekousei2.setWno7(result_Bk11.get(i).getWno7());

							battlekousei2.setKid(result_Bk11.get(i).getKid());
							battlekousei2.setKname(result_Bk11.get(i).getKname());

							battlekousei2.setLd(result_Bk11.get(i).getLd());

							if ((result_Bk11.get(i).getMvg() - result_zid.get(0).getMv()) < 0) {
								battlekousei2.setMvg(0);
							} else {
								battlekousei2.setMvg(result_Bk11.get(i).getMvg() - result_zid.get(0).getMv());
							}

							battlekousei2.setTaff(result_Bk11.get(i).getTaff());
							battlekousei2.setUunz(result_Bk11.get(i).getUunz());
							battlekousei2.setZid(result_Bk11.get(i).getZid());

							repoBattlekousei2.saveAndFlush(battlekousei2);

						}
					}
				}

			}

			///後攻側
			///後攻側
			///後攻側
			///後攻側
			//後攻側を一体ずつ射撃で処理
			List<Battlekousei2> result_Bk2 = repoBattlekousei2.findAll(Sort.by(Sort.Direction.DESC, "id"));

			if (result_Bk2 != null && result_Bk2.size() >= 1) {
				for (int i = 0; i <= result_Bk2.size() - 1; i++) {

					kyori = result_Bk2.get(i).getMvg();

					//先攻が全滅している場合を想定して
					List<Battlekousei1> result_Ck21 = repoBattlekousei1.findAll();
					if (result_Ck21 != null && result_Ck21.size() >= 1) {

						//ランダムにひとり取り出して距離をとる
						Optional<Battlekousei1> result_Ck22 = selectOneRandomId1();

						Battlekousei1 battlekousei1 = new Battlekousei1();

						battlekousei1.setId(result_Ck22.get().getId());
						battlekousei1.setAbl(result_Ck22.get().getAbl());
						battlekousei1.setAk(result_Ck22.get().getAk());
						battlekousei1.setAsa(result_Ck22.get().getAsa());
						battlekousei1.setAssp(result_Ck22.get().getAssp());
						battlekousei1.setAt(result_Ck22.get().getAt());
						battlekousei1.setBid(result_Ck22.get().getBid());
						battlekousei1.setWno(result_Ck22.get().getWno());
						battlekousei1.setWno2(result_Ck22.get().getWno2());
						battlekousei1.setWno3(result_Ck22.get().getWno3());
						battlekousei1.setWno4(result_Ck22.get().getWno4());
						battlekousei1.setWno5(result_Ck22.get().getWno5());
						battlekousei1.setWno6(result_Ck22.get().getWno6());
						battlekousei1.setWno7(result_Ck22.get().getWno7());

						battlekousei1.setKid(result_Ck22.get().getKid());
						battlekousei1.setKname(result_Ck22.get().getKname());

						battlekousei1.setLd(result_Ck22.get().getLd());
						battlekousei1.setMvg(result_Ck22.get().getMvg());
						battlekousei1.setTaff(result_Ck22.get().getTaff());
						battlekousei1.setUunz(result_Ck22.get().getUunz());
						battlekousei1.setZid(result_Ck22.get().getZid());

						kyori = kyori + result_Ck22.get().getMvg();

						EnId = result_Ck22.get().getId();
						EnTaff = result_Ck22.get().getTaff();
						EnSV1 = result_Ck22.get().getAsa();
						EnSV2 = result_Ck22.get().getAssp();
						EnUUNZ = result_Ck22.get().getUunz();

						//ここで検索してうまくできるか　テスト					

						//構成員の現在の能力値をとりだす
						List<Unitrekka> result_zid = findUnitrekkaRepository.search_zid(result_Bk2.get(i).getZid(),
								result_Bk2.get(i).getUunz());

						if (result_zid != null && result_zid.size() >= 1) {

							chargeF = 0;
							//射程内なら射撃 1-4
							if ((Findbuki.search_wno(result_Bk2.get(i).getWno()).get(0).getSyatei() >= kyori)
									|| (Findbuki.search_wno(result_Bk2.get(i).getWno2()).get(0).getSyatei() >= kyori)
									|| (Findbuki.search_wno(result_Bk2.get(i).getWno3()).get(0).getSyatei() >= kyori)
									|| (Findbuki.search_wno(result_Bk2.get(i).getWno4()).get(0).getSyatei() >= kyori)) {
								chargeF = 1;
							}

							// 
							if ((chargeF == 1) && (formBattlemode.getBmode2() != 3)) {
								//1-4 でまわす なるべく簡潔化したい
								for (int kk = 0; kk <= 3; kk++) {

									if (kk == 0) {
										List<Bukim> Buki1 = Findbuki.search_wno(result_Bk2.get(i).getWno());
										syubetu = Buki1.get(0).getSyubetu();
										syatei = Buki1.get(0).getSyatei();
										at = Buki1.get(0).getAt();
										ap = Buki1.get(0).getAp();

										dmg = Buki1.get(0).getDmg();
										abl = Buki1.get(0).getAbl();
										hit = Buki1.get(0).getHit();
										sss = Buki1.size();
										Sks = Buki1.get(0).getKs();

										BUKIn = Buki1.get(0).getWname();

									} else if (kk == 1) {
										List<Bukim> Buki2 = Findbuki.search_wno(result_Bk2.get(i).getWno2());
										syubetu = Buki2.get(0).getSyubetu();
										syatei = Buki2.get(0).getSyatei();
										at = Buki2.get(0).getAt();
										ap = Buki2.get(0).getAp();

										dmg = Buki2.get(0).getDmg();
										abl = Buki2.get(0).getAbl();
										hit = Buki2.get(0).getHit();
										sss = Buki2.size();
										Sks = Buki2.get(0).getKs();
										BUKIn = Buki2.get(0).getWname();
									} else if (kk == 2) {
										List<Bukim> Buki3 = Findbuki.search_wno(result_Bk2.get(i).getWno3());
										syubetu = Buki3.get(0).getSyubetu();
										syatei = Buki3.get(0).getSyatei();
										at = Buki3.get(0).getAt();
										ap = Buki3.get(0).getAp();

										dmg = Buki3.get(0).getDmg();
										abl = Buki3.get(0).getAbl();
										hit = Buki3.get(0).getHit();
										sss = Buki3.size();
										Sks = Buki3.get(0).getKs();
										BUKIn = Buki3.get(0).getWname();
									} else {
										List<Bukim> Buki4 = Findbuki.search_wno(result_Bk2.get(i).getWno4());
										syubetu = Buki4.get(0).getSyubetu();
										syatei = Buki4.get(0).getSyatei();
										at = Buki4.get(0).getAt();
										ap = Buki4.get(0).getAp();

										dmg = Buki4.get(0).getDmg();
										abl = Buki4.get(0).getAbl();
										hit = Buki4.get(0).getHit();
										sss = Buki4.size();
										Sks = Buki4.get(0).getKs();
										BUKIn = Buki4.get(0).getWname();
									}

									//ここで武器ごとに射程確認
									if ((sss >= 1) && (syatei >= kyori)) {

										if (Sks.equals("1D")) {
											ks = random.nextInt(5) + 1;
										} else if (Sks.equals("2D")) {
											ks = random.nextInt(5) + random.nextInt(5) + 2;
										} else if (Sks.equals("3D")) {
											ks = random.nextInt(5) + random.nextInt(5) + random.nextInt(5) + 3;
										} else if (Sks.equals("4D")) {
											ks = random.nextInt(5) + random.nextInt(5) + random.nextInt(5)
													+ +random.nextInt(5) + 4;
										} else {
											ks = Integer.parseInt(Sks);
										}

										// (syubetu.equals("ラピッド")) {
										if ((syubetu.equals("ラピッド")) && ((syatei / 2) >= kyori)) {
											ks = (ks * 2);
										}

										//武器の射撃回数分まわす
										for (int sbki = 0; sbki <= ks - 1; sbki++) {

											damageF = 0;
											SVRF = 0;

											//命中判定
											//	int randomValue1 = random.nextInt(5);
											// result_zid
											// result_Ck22.get().getMvg();

											//		int hit1 = 0;
											//		int hit2 = 0;

											//		int DMGC1 = 0;
											//		int DMGC2 = 0;
											// (BUKIn.equals("装備無し")) {
											if (BUKIn.equals("装備無し")) {

											} else {

												if (result_zid.get(0).getScs() <= 1 + hit + random.nextInt(5)) {

													hit2 = hit2 + 1;

													int rr = random.nextInt(5) + 1;

													//ダメージ判定
													if ((at >= EnTaff * 2) && (rr >= 2)) {
														damageF = 1;
													} else if ((at >= EnTaff) && (rr >= 3)) {
														damageF = 1;
													} else if ((at == EnTaff) && (rr >= 4)) {
														damageF = 1;
													} else if ((at < EnTaff) && (rr >= 5)) {
														damageF = 1;
													} else if ((at * 2 <= EnTaff) && (rr >= 6)) {
														damageF = 1;
													} else {
														damageF = 0;
													}

												}

												// List<Unitrekka> result_zid =

												//致傷量
												if (damageF == 1) { //ダメージ判定 成功なら

													DMGC2 = DMGC2 + 1;

													//相手側のセーヴィング

													int SVR = random.nextInt(5) + 1;

													//スペシャルセーブなし
													if (EnSV2 == 0) {
														if ((SVR >= (EnSV1 + ap))) {
															SVRF = 1;
														} else {
															SVRF = 0;
														}
													} else {
														if (((EnSV1 + ap) >= EnSV2) && (SVR >= EnSV2)) {
															SVRF = 1;
														} else if (((EnSV1 + ap) < EnSV2) && (SVR >= (EnSV1 + ap))) {
															SVRF = 1;
														} else {
															SVRF = 0;
														}
													}
												}

												//		int UUNZ1 = 0;
												//		int UUNZ2 = 0;	
												//		Gekiha1 = 0;
												//撃破数
												if ((SVRF == 0) && (damageF == 1)) {

													if (dmg.equals("1D")) {
														DMGM = random.nextInt(5) + 1;
													} else if (dmg.equals("2D")) {
														DMGM = random.nextInt(5) + random.nextInt(5) + 2;
													} else if (dmg.equals("3D")) {
														DMGM = random.nextInt(5) + random.nextInt(5) + random.nextInt(5)
																+ 3;
													} else if (dmg.equals("4D")) {
														DMGM = random.nextInt(5) + random.nextInt(5) + random.nextInt(5)
																+ random.nextInt(5)
																+ 4;

													} else {
														DMGM = Integer.parseInt(dmg);
													}

													UUNZ2 = UUNZ2 + DMGM;

													//ウーンズを減らし、死亡の場合はレコード削除
													if ((EnUUNZ - DMGM) > 0) {
														//	result_Ck22.get().getId();

														//
														//	battlekousei2.setUunz(result_Ck22.get().getUunz() - DMGM);

														//		Battlekousei2 battlekousei2 = new Battlekousei2();

														battlekousei1.setId(battlekousei1.getId());
														battlekousei1.setAbl(battlekousei1.getAbl());
														battlekousei1.setAk(battlekousei1.getAk());
														battlekousei1.setAsa(battlekousei1.getAsa());
														battlekousei1.setAssp(battlekousei1.getAssp());
														battlekousei1.setAt(battlekousei1.getAt());
														battlekousei1.setBid(battlekousei1.getBid());
														battlekousei1.setWno(battlekousei1.getWno());
														battlekousei1.setWno2(battlekousei1.getWno2());
														battlekousei1.setWno3(battlekousei1.getWno3());
														battlekousei1.setWno4(battlekousei1.getWno4());
														battlekousei1.setWno5(battlekousei1.getWno5());
														battlekousei1.setWno6(battlekousei1.getWno6());
														battlekousei1.setWno7(battlekousei1.getWno7());

														battlekousei1.setKid(battlekousei1.getKid());
														battlekousei1.setKname(battlekousei1.getKname());

														battlekousei1.setLd(battlekousei1.getLd());
														battlekousei1.setMvg(battlekousei1.getMvg());
														battlekousei1.setTaff(battlekousei1.getTaff());
														battlekousei1.setUunz(battlekousei1.getUunz() - DMGM);

														EnUUNZ = (EnUUNZ - DMGM);

														battlekousei1.setZid(battlekousei1.getZid());

														repoBattlekousei1.saveAndFlush(battlekousei1);

													} else {
														//相手が死亡する場合　相手を削除して　次を抽出する

														Gekiha2 = Gekiha2 + 1;
														repoBattlekousei1.deleteById(EnId);

														//先攻が全滅している場合を想定して
														List<Battlekousei1> result_Ck24 = repoBattlekousei1.findAll();
														if (result_Ck24 != null && result_Ck24.size() >= 1) {

															//ランダムにひとり取り出して距離をとる
															Optional<Battlekousei1> result_Ck23 = selectOneRandomId1();

															battlekousei1.setId(result_Ck23.get().getId());
															battlekousei1.setAbl(result_Ck23.get().getAbl());
															battlekousei1.setAk(result_Ck23.get().getAk());
															battlekousei1.setAsa(result_Ck23.get().getAsa());
															battlekousei1.setAssp(result_Ck23.get().getAssp());
															battlekousei1.setAt(result_Ck23.get().getAt());
															battlekousei1.setBid(result_Ck23.get().getBid());
															battlekousei1.setWno(result_Ck23.get().getWno());
															battlekousei1.setWno2(result_Ck23.get().getWno2());
															battlekousei1.setWno3(result_Ck23.get().getWno3());
															battlekousei1.setWno4(result_Ck23.get().getWno4());
															battlekousei1.setWno5(result_Ck23.get().getWno5());
															battlekousei1.setWno6(result_Ck23.get().getWno6());
															battlekousei1.setWno7(result_Ck23.get().getWno7());

															battlekousei1.setKid(result_Ck23.get().getKid());
															battlekousei1.setKname(result_Ck23.get().getKname());

															battlekousei1.setLd(result_Ck23.get().getLd());
															battlekousei1.setMvg(result_Ck23.get().getMvg());
															battlekousei1.setTaff(result_Ck23.get().getTaff());
															battlekousei1.setUunz(result_Ck23.get().getUunz());
															battlekousei1.setZid(result_Ck23.get().getZid());

															kyori = result_Bk2.get(i).getMvg()
																	+ result_Ck23.get().getMvg();

															EnId = result_Ck23.get().getId();
															EnTaff = result_Ck23.get().getTaff();
															EnSV1 = result_Ck23.get().getAsa();
															EnSV2 = result_Ck23.get().getAssp();
															EnUUNZ = result_Ck23.get().getUunz();
														} else {
															BTComment = "先攻が全滅";
															//	break;
														}
													}

												}

											}
										}

									}
								}

							} else if (formBattlemode.getBmode2() != 1) {

								//追加移動分の処理

								//	List<Battlekousei1> result_Bk11 = repoBattlekousei1.findAll(Sort.by(Sort.Direction.DESC, "id"));
								if (result_Bk2 != null && result_Bk2.size() >= 1) {

									Battlekousei2 battlekousei2 = new Battlekousei2();

									battlekousei2.setId(result_Bk2.get(i).getId());
									battlekousei2.setAbl(result_Bk2.get(i).getAbl());
									battlekousei2.setAk(result_Bk2.get(i).getAk());
									battlekousei2.setAsa(result_Bk2.get(i).getAsa());
									battlekousei2.setAssp(result_Bk2.get(i).getAssp());
									battlekousei2.setAt(result_Bk2.get(i).getAt());
									battlekousei2.setBid(result_Bk2.get(i).getBid());
									battlekousei2.setWno(result_Bk2.get(i).getWno());
									battlekousei2.setWno2(result_Bk2.get(i).getWno2());
									battlekousei2.setWno3(result_Bk2.get(i).getWno3());
									battlekousei2.setWno4(result_Bk2.get(i).getWno4());
									battlekousei2.setWno5(result_Bk2.get(i).getWno5());
									battlekousei2.setWno6(result_Bk2.get(i).getWno6());
									battlekousei2.setWno7(result_Bk2.get(i).getWno7());

									battlekousei2.setKid(result_Bk2.get(i).getKid());
									battlekousei2.setKname(result_Bk2.get(i).getKname());

									battlekousei2.setLd(result_Bk2.get(i).getLd());

									if ((result_Bk2.get(i).getMvg() - ((random.nextInt(5) + 1))) < 0) {
										battlekousei2.setMvg(0);
									} else {
										battlekousei2.setMvg(result_Bk2.get(i).getMvg() - ((random.nextInt(5) + 1)));
									}

									battlekousei2.setTaff(result_Bk2.get(i).getTaff());
									battlekousei2.setUunz(result_Bk2.get(i).getUunz());
									battlekousei2.setZid(result_Bk2.get(i).getZid());

									repoBattlekousei2.saveAndFlush(battlekousei2);

								}

							}

						}
						BTComment = "距離" + kyori + "";
					} else {
						BTComment = "先攻が全滅";

					}

				}
			} else {
				//先攻が全滅した時の処理を後で追加すること

			}

			/////////////
			/////////////
			/////////////白兵戦闘の処理 START

			List<Battlekousei1> result_AS1 = findBK1Repo.search_LIVE();
			if (result_AS1 != null && result_AS1.size() >= 1) {
				for (int i = 0; i <= result_AS1.size() - 1; i++) {

					//構成員の現在の能力値をとりだす
					List<Unitrekka> result_zid = findUnitrekkaRepository.search_zid(result_AS1.get(i).getZid(),
							result_AS1.get(i).getUunz());

					if (result_zid != null && result_zid.size() >= 1) {
						//		chargeF = 0;

						//		if ((Findbuki.search_wno(result_Bk1.get(i).getWno()).get(0).getSyatei() >= kyori)
						//				|| (Findbuki.search_wno(result_Bk1.get(i).getWno2()).get(0).getSyatei() >= kyori)
						//				|| (Findbuki.search_wno(result_Bk1.get(i).getWno3()).get(0).getSyatei() >= kyori)
						//				|| (Findbuki.search_wno(result_Bk1.get(i).getWno4()).get(0).getSyatei() >= kyori)) {
						//			chargeF = 1;
						//		}

						kyori = result_AS1.get(i).getMvg();

						//後攻が全滅している場合を想定して
						List<Battlekousei2> result_Ck22 = findBK2Repo.search_LIVE();
						if (result_Ck22 != null && result_Ck22.size() >= 1) {

							Battlekousei2 battlekousei2 = new Battlekousei2();

							battlekousei2.setId(result_Ck22.get(0).getId());
							battlekousei2.setAbl(result_Ck22.get(0).getAbl());
							battlekousei2.setAk(result_Ck22.get(0).getAk());
							battlekousei2.setAsa(result_Ck22.get(0).getAsa());
							battlekousei2.setAssp(result_Ck22.get(0).getAssp());
							battlekousei2.setAt(result_Ck22.get(0).getAt());
							battlekousei2.setBid(result_Ck22.get(0).getBid());
							battlekousei2.setWno(result_Ck22.get(0).getWno());
							battlekousei2.setWno2(result_Ck22.get(0).getWno2());
							battlekousei2.setWno3(result_Ck22.get(0).getWno3());
							battlekousei2.setWno4(result_Ck22.get(0).getWno4());
							battlekousei2.setWno5(result_Ck22.get(0).getWno5());
							battlekousei2.setWno6(result_Ck22.get(0).getWno6());
							battlekousei2.setWno7(result_Ck22.get(0).getWno7());

							battlekousei2.setKid(result_Ck22.get(0).getKid());
							battlekousei2.setKname(result_Ck22.get(0).getKname());

							battlekousei2.setLd(result_Ck22.get(0).getLd());
							battlekousei2.setMvg(result_Ck22.get(0).getMvg());
							battlekousei2.setTaff(result_Ck22.get(0).getTaff());
							battlekousei2.setUunz(result_Ck22.get(0).getUunz());
							battlekousei2.setZid(result_Ck22.get(0).getZid());

							kyori = kyori + result_Ck22.get(0).getMvg();

							EnId = result_Ck22.get(0).getId();
							EnTaff = result_Ck22.get(0).getTaff();
							EnSV1 = result_Ck22.get(0).getAsa();
							EnSV2 = result_Ck22.get(0).getAssp();
							EnUUNZ = result_Ck22.get(0).getUunz();

							for (int kk = 0; kk <= 2; kk++) {

								if (kk == 0) {
									List<Bukim> Buki1 = Findbuki.search_wno(result_AS1.get(i).getWno5());
									syubetu = Buki1.get(0).getSyubetu();
									syatei = Buki1.get(0).getSyatei();
									at = Buki1.get(0).getAt();
									ap = Buki1.get(0).getAp();

									dmg = Buki1.get(0).getDmg();
									abl = Buki1.get(0).getAbl();
									hit = Buki1.get(0).getHit();
									sss = Buki1.size();
									Sks = Buki1.get(0).getKs();

									BUKIn = Buki1.get(0).getWname();

								} else if (kk == 1) {
									List<Bukim> Buki2 = Findbuki.search_wno(result_AS1.get(i).getWno6());
									syubetu = Buki2.get(0).getSyubetu();
									syatei = Buki2.get(0).getSyatei();
									at = Buki2.get(0).getAt();
									ap = Buki2.get(0).getAp();

									dmg = Buki2.get(0).getDmg();
									abl = Buki2.get(0).getAbl();
									hit = Buki2.get(0).getHit();
									sss = Buki2.size();
									Sks = Buki2.get(0).getKs();
									BUKIn = Buki2.get(0).getWname();
								} else if (kk == 2) {
									List<Bukim> Buki3 = Findbuki.search_wno(result_AS1.get(i).getWno7());
									syubetu = Buki3.get(0).getSyubetu();
									syatei = Buki3.get(0).getSyatei();
									at = Buki3.get(0).getAt();
									ap = Buki3.get(0).getAp();

									dmg = Buki3.get(0).getDmg();
									abl = Buki3.get(0).getAbl();
									hit = Buki3.get(0).getHit();
									sss = Buki3.size();
									Sks = Buki3.get(0).getKs();
									BUKIn = Buki3.get(0).getWname();

								}

								sss = 0;
								// (BUKIn.equals("装備無し")) {
								if (BUKIn.equals("装備無し")) {

									if (kk == 0) {
										at = result_AS1.get(0).getAt();
										dmg = "1";
										ks = result_AS1.get(0).getAk();
										sss = 1;

									} else {

										sss = 0;

									}
								}

								if ((sss >= 1)) {

									if (result_zid.get(0).getScs() <= 1 + hit + random.nextInt(5)) {

										ahit1 = ahit1 + 1;

										int rr = random.nextInt(5) + 1;

										//ダメージ判定
										if ((at >= EnTaff * 2) && (rr >= 2)) {
											damageF = 1;
										} else if ((at >= EnTaff) && (rr >= 3)) {
											damageF = 1;
										} else if ((at == EnTaff) && (rr >= 4)) {
											damageF = 1;
										} else if ((at < EnTaff) && (rr >= 5)) {
											damageF = 1;
										} else if ((at * 2 <= EnTaff) && (rr >= 6)) {
											damageF = 1;
										} else {
											damageF = 0;
										}

									}

									// List<Unitrekka> result_zid =

									//致傷量
									if (damageF == 1) { //ダメージ判定 成功なら

										aDMGC1 = aDMGC1 + 1;

										//相手側のセーヴィング

										int SVR = random.nextInt(5) + 1;

										//スペシャルセーブなし
										if (EnSV2 == 0) {
											if ((SVR >= (EnSV1 + ap))) {
												SVRF = 1;
											} else {
												SVRF = 0;
											}
										} else {
											if (((EnSV1 + ap) >= EnSV2) && (SVR >= EnSV2)) {
												SVRF = 1;
											} else if (((EnSV1 + ap) < EnSV2) && (SVR >= (EnSV1 + ap))) {
												SVRF = 1;
											} else {
												SVRF = 0;
											}
										}
									}

									//撃破数
									if ((SVRF == 0) && (damageF == 1)) {

										if (dmg.equals("1D")) {
											DMGM = random.nextInt(5) + 1;
										} else if (dmg.equals("2D")) {
											DMGM = random.nextInt(5) + random.nextInt(5) + 2;
										} else if (dmg.equals("3D")) {
											DMGM = random.nextInt(5) + random.nextInt(5) + random.nextInt(5)
													+ 3;
										} else {
											DMGM = Integer.parseInt(dmg);
										}

										aUUNZ1 = aUUNZ1 + DMGM;

										//ウーンズを減らし、死亡の場合はレコード削除
										if ((EnUUNZ - DMGM) > 0) {

											battlekousei2.setId(battlekousei2.getId());
											battlekousei2.setAbl(battlekousei2.getAbl());
											battlekousei2.setAk(battlekousei2.getAk());
											battlekousei2.setAsa(battlekousei2.getAsa());
											battlekousei2.setAssp(battlekousei2.getAssp());
											battlekousei2.setAt(battlekousei2.getAt());
											battlekousei2.setBid(battlekousei2.getBid());
											battlekousei2.setWno(battlekousei2.getWno());
											battlekousei2.setWno2(battlekousei2.getWno2());
											battlekousei2.setWno3(battlekousei2.getWno3());
											battlekousei2.setWno4(battlekousei2.getWno4());
											battlekousei2.setWno5(battlekousei2.getWno5());
											battlekousei2.setWno6(battlekousei2.getWno6());
											battlekousei2.setWno7(battlekousei2.getWno7());

											battlekousei2.setKid(battlekousei2.getKid());
											battlekousei2.setKname(battlekousei2.getKname());

											battlekousei2.setLd(battlekousei2.getLd());
											battlekousei2.setMvg(battlekousei2.getMvg());
											battlekousei2.setTaff(battlekousei2.getTaff());
											battlekousei2.setUunz(battlekousei2.getUunz() - DMGM);

											EnUUNZ = (EnUUNZ - DMGM);

											battlekousei2.setZid(battlekousei2.getZid());

											repoBattlekousei2.saveAndFlush(battlekousei2);

										} else {

											akia1 = akia1 + 1;

											battlekousei2.setId(battlekousei2.getId());
											battlekousei2.setAbl(battlekousei2.getAbl());
											battlekousei2.setAk(battlekousei2.getAk());
											battlekousei2.setAsa(battlekousei2.getAsa());
											battlekousei2.setAssp(battlekousei2.getAssp());
											battlekousei2.setAt(battlekousei2.getAt());
											battlekousei2.setBid(battlekousei2.getBid());
											battlekousei2.setWno(battlekousei2.getWno());
											battlekousei2.setWno2(battlekousei2.getWno2());
											battlekousei2.setWno3(battlekousei2.getWno3());
											battlekousei2.setWno4(battlekousei2.getWno4());
											battlekousei2.setWno5(battlekousei2.getWno5());
											battlekousei2.setWno6(battlekousei2.getWno6());
											battlekousei2.setWno7(battlekousei2.getWno7());

											battlekousei2.setKid(battlekousei2.getKid());
											battlekousei2.setKname(battlekousei2.getKname());

											battlekousei2.setLd(battlekousei2.getLd());
											battlekousei2.setMvg(battlekousei2.getMvg());
											battlekousei2.setTaff(battlekousei2.getTaff());
											battlekousei2.setUunz(battlekousei2.getUunz() - DMGM);

											battlekousei2.setZid(battlekousei2.getZid());

											battlekousei2.setDeath(1);

											repoBattlekousei2.saveAndFlush(battlekousei2);

										}
									}
								}

							}

						}

					}

				}
			}

			/////////////白兵戦闘の処理 END
			/////////////
			/////////////			

			Battlereport battlereport = new Battlereport();

			battlereport.setSno(1);
			battlereport.setTno(tn + 1);

			//射撃での成果
			battlereport.setHit1(hit1);
			battlereport.setHit2(hit2);

			battlereport.setDamage1(DMGC1);
			battlereport.setDamage2(DMGC2);

			battlereport.setUunz1(UUNZ1);
			battlereport.setUunz2(UUNZ2);

			battlereport.setKia1(Gekiha1);
			battlereport.setKia2(Gekiha2);

			//白兵での成果
			battlereport.setAhit1(0);
			battlereport.setAhit2(0);

			battlereport.setAdamage1(0);
			battlereport.setAdamage2(0);

			battlereport.setAuunz1(0);
			battlereport.setAuunz2(0);

			battlereport.setAkia1(0);
			battlereport.setAkia2(0);

			battlereport.setComme(BTComment);

			repoBattlereport.saveAndFlush(battlereport);

			if ((BTComment.equals("先攻が全滅")) || (BTComment.equals("後攻が全滅"))) {

				break;

			}
		}

		model.addAttribute("reportList", repoBattlereport.findAll(Sort.by(Sort.Direction.ASC, "id")));

		formBattlemode.setBmvc(20);
		formBattlemode.setTrik(1);
		return "redirect:/battle/index";

	}

	public Optional<Battlekousei1> selectOneRandomId1() {
		Integer randId = randomBK1.getRandomId();

		if (randId == null) {
			return Optional.empty();
		}
		return randomBK1.findById(randId);
	}

	public Optional<Battlekousei2> selectOneRandomId2() {
		Integer randId = randomBK2.getRandomId();

		if (randId == null) {
			return Optional.empty();
		}
		return randomBK2.findById(randId);
	}

}
