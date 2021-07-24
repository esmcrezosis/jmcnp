package com.esmc.mcnp.components;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.esmc.mcnp.core.utils.DateUtility;
import com.esmc.mcnp.model.obpsd.EuReglementWari;
import com.esmc.mcnp.model.obpsd.EuTraite;
import com.esmc.mcnp.services.obpsd.EuReglementWariService;
import com.esmc.mcnp.services.obpsd.EuTraiteService;

@Component
public class CSVService {

	private EuReglementWariService regWariService;
	private EuTraiteService traiteService;

	@Autowired
	public CSVService(EuReglementWariService regWariService, EuTraiteService traiteService) {
		this.regWariService = regWariService;
		this.traiteService = traiteService;
	}

	@Transactional(rollbackFor = Exception.class)
	public void loadReglementFromCSV(InputStream in) throws ParseException, IOException {
		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		Reader rin = new InputStreamReader(in);
		EuReglementWari regWar = null;
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader().parse(rin);
		int nbre = 0;
		for (CSVRecord record : records) {
			String numTransaction = record.get("Numero transaction");
			List<EuReglementWari> regs = regWariService.findByNumeroTransaction(numTransaction);
			if (regs.isEmpty()) {
				regWar = new EuReglementWari();
				regWar.setDatePaiement(DateUtility.asLocalDateTime(formatter.parse(record.get("Date paiement"))));
				regWar.setNumeroTransaction(numTransaction);
				regWar.setMatricule(record.get("Matricule"));
				regWar.setEtablissement(record.get("Etablissement"));
				regWar.setMontant(Double.valueOf(record.get("Montant")));
				regWariService.add(regWar);

				EuTraite traite = traiteService.findByNumeroTraite("00" + regWar.getMatricule());
				if (Objects.nonNull(traite)) {
					if (traite.getTraitePayer() == 0 || traite.getTraitePayer() == null) {
						traite.setTraitePayer(1);
						traiteService.update(traite);
						nbre++;
					}
				}
			}
		}
		System.out.println("Nombre de traites mises à jour :" + nbre);
	}

	public void payerTraiter(String numeroTraite) {
		EuTraite traite = traiteService.findByNumeroTraite(numeroTraite);
		if (Objects.nonNull(traite)) {
			System.out.println("OPI trouvé = " + traite.getTraiteId() + " == " + traite.getTraiteNumero());
			if (traite.getTraitePayer() == 0 || traite.getTraitePayer() == null) {
				traite.setTraitePayer(1);
				traiteService.update(traite);
			}
		}
	}

	// @Scheduled(fixedRate = 3600000)
	@Transactional(rollbackFor = Exception.class)
	public void loadReglementFromCSV() throws IOException {
		System.out.println("Loading File ...");
		File file = ResourceUtils.getFile("classpath:data/");
		List<File> files = Arrays.asList(file.listFiles());
		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		if (!files.isEmpty()) {
			files.forEach(f -> {
				EuReglementWari regWar = null;
				Reader in;
				try {
					in = new FileReader(f);
					Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader()
							.parse(in);
					for (CSVRecord record : records) {
						String numTransaction = record.get("Numero transaction");
						List<EuReglementWari> regs = regWariService.findByNumeroTransaction(numTransaction);
						if (regs.isEmpty()) {
							regWar = new EuReglementWari();
							regWar.setDatePaiement(
									DateUtility.asLocalDateTime(formatter.parse(record.get("Date paiement"))));
							regWar.setNumeroTransaction(numTransaction);
							regWar.setMatricule(record.get("Matricule"));
							regWar.setEtablissement(record.get("Etablissement"));
							regWar.setMontant(Double.valueOf(record.get("Montant")));
							regWariService.add(regWar);

							EuTraite traite = traiteService.findByNumeroTraite("00" + regWar.getMatricule());
							if (Objects.nonNull(traite)) {
								traite.setTraitePayer(1);
								traiteService.update(traite);
							}
						}
					}
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
