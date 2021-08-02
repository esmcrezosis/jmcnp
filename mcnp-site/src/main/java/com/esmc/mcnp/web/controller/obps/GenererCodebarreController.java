package com.esmc.mcnp.web.controller.obps;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esmc.mcnp.domain.entity.obps.EuCodebarre;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.infrastructure.services.common.EuCodebarreService;
import com.esmc.mcnp.web.controller.base.BaseController;



//.apache.poi.hssf.usermodel.HSSFWorkbook;
@Controller
public class GenererCodebarreController extends BaseController { 

	private @Autowired EuCodebarreService codebarreService;



	@RequestMapping(value = "/codebarre", method = RequestMethod.GET)
	public String genererCodebarre() {
		return "distributeur/generer_codebarre";
	}


	/*@ModelAttribute("raisonvente")
	public String retrouverRaisonTegc() {
		String nomTegc = "";
		String codeTegc;

		EuUtilisateur utilisateur = getCurrentUser();
		codeTegc = utilisateur.getCodeTegc();
		if(StringUtils.isNotBlank(codeTegc)){
			EuTegc euTegc = tegcService.findById(codeTegc);
			if (euTegc != null && StringUtils.isNotBlank(euTegc.getNomTegc())) {
				nomTegc = euTegc.getNomTegc().toUpperCase();
				System.out.println("nomTegc = " + nomTegc);
			}
		}
		return nomTegc;
	}*/




	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/creationCodeBarrePourTous", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Double CreerCodeBarres(HttpServletRequest request) {
		String code;
		String codeSg="";
		String codeDet="";
		List<String> listCodeGros = new ArrayList<>();
		List<String> listCodeSemiGros = new ArrayList<>();
		List<String> listCodeDetailllant = new ArrayList<>();
		//enregistrercode = "codeMembreDemandeur="+$codeMembreDemandeur+"&typeCode="+$typeCode+"&nbreCode="+$nbreCode+"&nbreCodeSg="+$nbreCodeSg+"&nbreCodeDet="+$nbreCodeDet+"&_csrf=" + $csrf + "";

		String codeMembreDemandeur = (String) request.getParameter("codeMembreDemandeur");
		String typeCode = (String) request.getParameter("typeCode");
		Double nbreCode = Double.valueOf(request.getParameter("nbreCode"));
		Double nbreCodeSg = Double.valueOf(request.getParameter("nbreCodeSg"));
		Double nbreCodeDet = Double.valueOf(request.getParameter("nbreCodeDet"));
		System.out.println("nbreCode= "+nbreCode);
		System.out.println("nbreCodeSg= "+nbreCodeSg);
		System.out.println("nbreCodeDet= "+nbreCodeDet);
		// retrouver le vendeur connecte
		EuUtilisateur utilisateur = BaseController.getCurrentUser();
		/*String codeMembreVendeur = utilisateur.getCodeMembre();

		System.out.println("codeMembreVendeur= " + codeMembreVendeur);*/

		if(StringUtils.isNotBlank(codeMembreDemandeur) && StringUtils.isNotBlank(typeCode) && !Double.isNaN(nbreCode) && !Double.isNaN(nbreCodeSg) && !Double.isNaN(nbreCodeDet)) {

			if(typeCode.equals("g")){
				if(nbreCode>0){
					//grossiste simple
					String cb = codebarreService.getLastCodebarresGros();
					System.out.println("cb = " + cb);
					if (cb == null) {
						cb = "G0";
						String code2 = cb.substring(1);
						int nbrecb = Integer.parseInt(code2);
						for (int i = 0; i < nbreCode; i++) {
							nbrecb += 1;
							code = "G" + String.valueOf(nbrecb);
							listCodeGros.add(code);
						}
						System.out.println("list.size= " + listCodeGros.size());
						for (String cbar : listCodeGros) {
							EuCodebarre euCodebarre = new EuCodebarre();
							euCodebarre.setCodebarre(cbar);
							euCodebarre.setDateGenerer(new Date());
							euCodebarre.setCodemembreDem(codeMembreDemandeur);
							euCodebarre.setIdUtilisateur(utilisateur.getIdUtilisateur());
							euCodebarre.setTypeCodebar("g");
							codebarreService.add(euCodebarre);

						}
					} else {
						String code2 = cb.substring(1);
						int nbrecb = Integer.parseInt(code2);
						for (int i = 0; i < nbreCode; i++) {
							nbrecb += 1;
							code = "G" + String.valueOf(nbrecb);
							listCodeGros.add(code);
						}
						System.out.println("list.size= " + listCodeGros.size());
						for (String cbar : listCodeGros) {
							EuCodebarre euCodebarre = new EuCodebarre();
							euCodebarre.setCodebarre(cbar);
							euCodebarre.setDateGenerer(new Date());
							euCodebarre.setCodemembreDem(codeMembreDemandeur);
							euCodebarre.setIdUtilisateur(utilisateur.getIdUtilisateur());
							euCodebarre.setTypeCodebar("g");
							codebarreService.add(euCodebarre);

						}
					}
				}
				if(nbreCodeSg>0){
					//codes semi grossiste à partir du grossiste

					for (int i = 0; i < listCodeGros.size(); i++) {

						for (int j = 1; j <= nbreCodeSg; j++) {
							codeSg = listCodeGros.get(i) + "SG" + j;
							listCodeSemiGros.add(codeSg);

						}


					}

					System.out.println("listCodeSemiGros.size= " + listCodeSemiGros.size());
					for (String cbar : listCodeSemiGros) {
						EuCodebarre euCodebarre = new EuCodebarre();
						euCodebarre.setCodebarre(cbar);
						euCodebarre.setDateGenerer(new Date());
						euCodebarre.setCodemembreDem(codeMembreDemandeur);
						euCodebarre.setIdUtilisateur(utilisateur.getIdUtilisateur());
						euCodebarre.setTypeCodebar("sg");
						codebarreService.add(euCodebarre);

					}


				}
				if(nbreCodeDet>0){
					//codes detaillant à partir du semi grossiste

					for (int i = 0; i < listCodeSemiGros.size(); i++) {
						for (int k = 1; k <= nbreCodeDet; k++) {

							codeDet = listCodeSemiGros.get(i) + "D" + k;
							listCodeDetailllant.add(codeDet);
						}
					}

					System.out.println("listCodeDetailllant.size= " + listCodeDetailllant.size());
					for (String cbar : listCodeDetailllant) {
						EuCodebarre euCodebarre = new EuCodebarre();
						euCodebarre.setCodebarre(cbar);
						euCodebarre.setDateGenerer(new Date());
						euCodebarre.setCodemembreDem(codeMembreDemandeur);
						euCodebarre.setIdUtilisateur(utilisateur.getIdUtilisateur());
						euCodebarre.setTypeCodebar("dt");
						codebarreService.add(euCodebarre);

					}

				}

			}else if(typeCode.equals("sg")){
				if(nbreCode>0){
					//codes  semi grossiste simples

					String cb = codebarreService.getLastCodebarresSemiGros();

					if (cb == null) {
						cb = "SG0";
						String code2 = cb.substring(2);
						int nbrecb = Integer.parseInt(code2);
						for (int i = 0; i < nbreCode; i++) {
							nbrecb += 1;
							code = "SG" + String.valueOf(nbrecb);
							listCodeSemiGros.add(code);
						}
						for (String cbar : listCodeSemiGros) {
							EuCodebarre euCodebarre = new EuCodebarre();
							euCodebarre.setCodebarre(cbar);
							euCodebarre.setDateGenerer(new Date());
							euCodebarre.setCodemembreDem(codeMembreDemandeur);
							euCodebarre.setIdUtilisateur(utilisateur.getIdUtilisateur());
							euCodebarre.setTypeCodebar("sg");
							codebarreService.add(euCodebarre);
						}
					} else {
						System.out.println("max cb semi gros =" + cb);
						int lengt = cb.length();
						int index = cb.indexOf("S");
						String code2 = cb.substring(index + 2, lengt);
						System.out.println("code2 semi gros= " + code2);
						int nbrecb = Integer.parseInt(code2);
						System.out.println("nbrecodes= " + nbreCode);
						for (int i = 0; i < nbreCode; i++) {
							nbrecb += 1;
							code = "SG" + String.valueOf(nbrecb);
							System.out.println("code= " + code);
							listCodeSemiGros.add(code);
						}
						for (String cbar : listCodeSemiGros) {
							EuCodebarre euCodebarre = new EuCodebarre();
							euCodebarre.setCodebarre(cbar);
							euCodebarre.setDateGenerer(new Date());
							euCodebarre.setCodemembreDem(codeMembreDemandeur);
							euCodebarre.setIdUtilisateur(utilisateur.getIdUtilisateur());
							euCodebarre.setTypeCodebar("sg");
							codebarreService.add(euCodebarre);
						}
					}
				}
				if(nbreCodeDet>0){
					//codes detaillant à partir du semi grossiste

					for (int i = 0; i < listCodeSemiGros.size(); i++) {
						for (int q = 1; q <= nbreCodeDet; q++) {
							codeDet = listCodeSemiGros.get(i) + "D" + q;
							listCodeDetailllant.add(codeDet);
						}
					}

					System.out.println("listCodeDetailllant.size= " + listCodeDetailllant.size());
					for (String cbar : listCodeDetailllant) {
						EuCodebarre euCodebarre = new EuCodebarre();
						euCodebarre.setCodebarre(cbar);
						euCodebarre.setDateGenerer(new Date());
						euCodebarre.setCodemembreDem(codeMembreDemandeur);
						euCodebarre.setIdUtilisateur(utilisateur.getIdUtilisateur());
						euCodebarre.setTypeCodebar("det");
						codebarreService.add(euCodebarre);

					}
				}
			}else if(typeCode.equals("det")){					

				if(nbreCode>0){
					//code detaillants simples
					String cb = codebarreService.getLastCodebarresDetaillant();
					if (cb == null) {
						cb = "D0";
						String code2 = cb.substring(1);
						int nbrecb = Integer.parseInt(code2);
						for (int i = 0; i < nbreCode; i++) {
							nbrecb += 1;
							code = "D" + String.valueOf(nbrecb);
							listCodeDetailllant.add(code);
						}
						for (String cbar : listCodeDetailllant) {
							EuCodebarre euCodebarre = new EuCodebarre();
							euCodebarre.setCodebarre(cbar);
							euCodebarre.setDateGenerer(new Date());
							euCodebarre.setCodemembreDem(codeMembreDemandeur);
							euCodebarre.setIdUtilisateur(utilisateur.getIdUtilisateur());
							euCodebarre.setTypeCodebar("dt");
							codebarreService.add(euCodebarre);
						}
					} else {
						System.out.println("last cb= "+cb);
						int lengt = cb.length();
						int index = cb.indexOf("D");

						String code2 = cb.substring(index + 1, lengt);

						int nbrecb = Integer.parseInt(code2);
						for (int i = 0; i < nbreCode; i++) {
							nbrecb += 1;
							code = "D" + String.valueOf(nbrecb);
							listCodeDetailllant.add(code);
						}
						for (String cbar : listCodeDetailllant) {
							EuCodebarre euCodebarre = new EuCodebarre();
							euCodebarre.setCodebarre(cbar);
							euCodebarre.setDateGenerer(new Date());
							euCodebarre.setCodemembreDem(codeMembreDemandeur);
							euCodebarre.setIdUtilisateur(utilisateur.getIdUtilisateur());
							euCodebarre.setTypeCodebar("dt");
							codebarreService.add(euCodebarre);
						}
					}
				}
			}
			return Double.valueOf(0);
		}
		//Revoir la saise des données
		return Double.valueOf(1);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/creerFichierExcel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String>  CreerFichierExcel() {
		List<String> listLien = new ArrayList<>();
		String nomFichier="c:/workbook.xls";
		
		try {
		
		// créer un nouveau fichier excel
		FileOutputStream out;
		out = new FileOutputStream(nomFichier);

		// créer un classeur
		Workbook wb = new HSSFWorkbook();
		// créer une feuille
		Sheet mySheet = wb.createSheet();

		// créer une ligne de à l'index 2 dans la feuille Excel
		Row myRow = null;      
		myRow = mySheet.createRow(2);

		// Ajouter des données dans les cellules
		myRow.createCell(0).setCellValue("Java Execl");
		myRow.createCell(1).setCellValue("Mesexemple.com");
		myRow.createCell(2).setCellValue("Sakoba Adams");

		wb.close();
		out.close();
		wb.write(out);
		listLien.add("1");
		listLien.add(nomFichier);
		
		} catch(IOException e) {
			e.printStackTrace();
			listLien.add("0");
			
			
		}
		
		
		return listLien;
				
		
		
		
	}


	




}
