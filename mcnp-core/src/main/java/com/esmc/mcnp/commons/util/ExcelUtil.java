package com.esmc.mcnp.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esmc.mcnp.commons.annotation.Excel;
import com.esmc.mcnp.commons.annotation.Excel.ColumnType;
import com.esmc.mcnp.commons.annotation.Excel.Type;
import com.esmc.mcnp.commons.annotation.Excels;
import com.esmc.mcnp.commons.exception.McnpException;
import com.esmc.mcnp.commons.model.AjaxResult;
import com.esmc.mcnp.commons.util.text.Convert;
import com.esmc.mcnp.config.AppConfig;

/**
 * Traitement lié à Excel
 * 
 * @author ruoyi
 */
public class ExcelUtil<T> {
	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * Nombre maximum de lignes dans la feuille Excel, 65536 par défaut
	 */
	public static final int sheetSize = 65536;

	/**
	 * Nom de la feuille de travail
	 */
	private String sheetName;

	/**
	 * Type d'exportation (EXPORT: exporter des données; IMPORT: importer un modèle)
	 */
	private Type type;

	/**
	 * Objet de classeur
	 */
	private Workbook wb;

	/**
	 * Objet de feuille de travail
	 */
	private Sheet sheet;

	/**
	 * Liste de styles
	 */
	private Map<String, CellStyle> styles;

	/**
	 * Importer et exporter la liste de données
	 */
	private List<T> list;

	/**
	 * Liste d'annotations
	 */
	private List<Object[]> fields;

	/**
	 * hauteur maximale
	 */
	private short maxHeight;

	/**
	 * Liste des statistiques
	 */
	private Map<Integer, Double> statistics = new HashMap<Integer, Double>();

	/**
	 * Format de nombre
	 */
	private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("######0.00");

	/**
	 * Objet d'entité
	 */
	public Class<T> clazz;

	public ExcelUtil(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void init(List<T> list, String sheetName, Type type) {
		if (list == null) {
			list = new ArrayList<T>();
		}
		this.list = list;
		this.sheetName = sheetName;
		this.type = type;
		createExcelField();
		createWorkbook();
	}

	/**
	 * Pour la feuille Excel, le premier nom d'index par défaut est converti en
	 * liste
	 *
	 * @param est un flux d'entrée
	 * @return Collection après conversion
	 */
	public List<T> importExcel(InputStream is) throws Exception {
		return importExcel(StringUtils.EMPTY, is);
	}

	/**
	 * Le nom d'index de table spécifié du formulaire Excel est converti en liste
	 *
	 * @param nom de l'index de table sheetName
	 * @param est un flux d'entrée
	 * @return Collection après conversion
	 */
	public List<T> importExcel(String sheetName, InputStream is) throws Exception {
		this.type = Type.IMPORT;
		this.wb = WorkbookFactory.create(is);
		List<T> list = new ArrayList<T>();
		Sheet sheet = null;
		if (StringUtils.isNotEmpty(sheetName)) {
			// Si le nom de la feuille est spécifié, le contenu de la feuille spécifiée est
			// pris.
			sheet = wb.getSheet(sheetName);
		} else {
			// Si le nom de feuille transmis n'existe pas, il pointera vers la première
			// feuille par défaut.
			sheet = wb.getSheetAt(0);
		}

		if (sheet == null) {
			throw new IOException("La feuille de fichier n'existe pas");
		}

		// Obtenez l'indice de ligne de la dernière ligne non vide, par exemple, si le
		// nombre total de lignes est n, la valeur renvoyée est n-1
		int rows = sheet.getLastRowNum();

		if (rows > 0) {
			// Définissez une carte pour stocker le numéro de série et le champ de la
			// colonne Excel.
			Map<String, Integer> cellMap = new HashMap<String, Integer>();
			// Obtenir l'en-tête
			Row heard = sheet.getRow(0);
			for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++) {
				Cell cell = heard.getCell(i);
				if (StringUtils.isNotNull(cell)) {
					String value = this.getCellValue(heard, i).toString();
					cellMap.put(value, i);
				} else {
					cellMap.put(null, i);
				}
			}
			// Traitez uniquement lorsqu'il y a des données pour obtenir tous les champs de
			// la classe.
			Field[] allFields = clazz.getDeclaredFields();
			// Définissez une carte pour stocker le numéro de série et le champ de la
			// colonne.
			Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
			for (int col = 0; col < allFields.length; col++) {
				Field field = allFields[col];
				Excel attr = field.getAnnotation(Excel.class);
				if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
					// Définissez les propriétés du champ privé de la classe pour qu'elles soient
					// accessibles.
					field.setAccessible(true);
					Integer column = cellMap.get(attr.name());
					if (column != null) {
						fieldsMap.put(column, field);
					}
				}
			}
			for (int i = 1; i <= rows; i++) {
				// Commencez à récupérer les données de la deuxième ligne, et la première ligne
				// est l'en-tête par défaut.
				Row row = sheet.getRow(i);
				// Déterminez si la ligne actuelle est une ligne vide
				if (isRowEmpty(row)) {
					continue;
				}
				T entity = null;
				for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet()) {
					Object val = this.getCellValue(row, entry.getKey());

					// S'il n'y a pas d'instance, créez-en une nouvelle.
					entity = (entity == null ? clazz.newInstance() : entity);
					// Récupérez le champ de la colonne correspondante sur la carte.
					Field field = fieldsMap.get(entry.getKey());
					// Obtenez le type et définissez la valeur en fonction du type d'objet.
					Class<?> fieldType = field.getType();
					if (String.class == fieldType) {
						String s = Convert.toStr(val);
						if (StringUtils.endsWith(s, ".0")) {
							val = StringUtils.substringBefore(s, ".0");
						} else {
							String dateFormat = field.getAnnotation(Excel.class).dateFormat();
							if (StringUtils.isNotEmpty(dateFormat)) {
								val = DateUtils.dateToStr((Date) val, dateFormat);
							} else {
								val = Convert.toStr(val);
							}
						}
					} else if ((Integer.TYPE == fieldType || Integer.class == fieldType)
							&& StringUtils.isNumeric(Convert.toStr(val))) {
						val = Convert.toInt(val);
					} else if (Long.TYPE == fieldType || Long.class == fieldType) {
						val = Convert.toLong(val);
					} else if (Double.TYPE == fieldType || Double.class == fieldType) {
						val = Convert.toDouble(val);
					} else if (Float.TYPE == fieldType || Float.class == fieldType) {
						val = Convert.toFloat(val);
					} else if (BigDecimal.class == fieldType) {
						val = Convert.toBigDecimal(val);
					} else if (Date.class == fieldType) {
						if (val instanceof String) {
							val = DateUtils.parseDate(val);
						} else if (val instanceof Double) {
							val = DateUtil.getJavaDate((Double) val);
						}
					} else if (Boolean.TYPE == fieldType || Boolean.class == fieldType) {
						val = Convert.toBool(val, false);
					}
					if (StringUtils.isNotNull(fieldType)) {
						Excel attr = field.getAnnotation(Excel.class);
						String propertyName = field.getName();
						if (StringUtils.isNotEmpty(attr.targetAttr())) {
							propertyName = field.getName() + "." + attr.targetAttr();
						} else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
							val = reverseByExp(Convert.toStr(val), attr.readConverterExp(), attr.separator());
						} /*
							 * else if (StringUtils.isNotEmpty(attr.dictType())) { val =
							 * reverseDictByExp(Convert.toStr(val), attr.dictType(), attr.separator()); }
							 */
						ReflectUtils.invokeSetter(entity, propertyName, val);
					}
				}
				list.add(entity);
			}
		}
		return list;
	}

	/**
	 * Importez les données de la source de données de la liste dans le formulaire
	 * Excel
	 *
	 * Collecte de données d'exportation de la liste @param
	 * 
	 * @param sheetName Le nom de la feuille de calcul
	 * @return résultat
	 */
	public AjaxResult exportExcel(List<T> list, String sheetName) {
		this.init(list, sheetName, Type.EXPORT);
		return exportExcel();
	}

	/**
	 * Importez les données de la source de données de la liste dans le formulaire
	 * Excel
	 *
	 * @param sheetName Le nom de la feuille de calcul
	 * @return résultat
	 */
	public AjaxResult importTemplateExcel(String sheetName) {
		this.init(null, sheetName, Type.IMPORT);
		return exportExcel();
	}

	/**
	 * Importez les données de la source de données de la liste dans le formulaire
	 * Excel
	 *
	 * @return résultat
	 */
	public AjaxResult exportExcel() {
		OutputStream out = null;
		try {
			// Combien de feuilles sont sorties.
			double sheetNo = Math.ceil(list.size() / sheetSize);
			for (int index = 0; index <= sheetNo; index++) {
				createSheet(sheetNo, index);

				// Produire une ligne
				Row row = sheet.createRow(0);
				int column = 0;
				// Écrivez le nom d'en-tête de colonne de chaque champ
				for (Object[] os : fields) {
					Excel excel = (Excel) os[1];
					this.createCell(excel, row, column++);
				}
				if (Type.EXPORT.equals(type)) {
					fillExcelData(index, row);
					addStatisticsRow();
				}
			}
			String filename = encodingFilename(sheetName);
			out = new FileOutputStream(getAbsoluteFile(filename));
			wb.write(out);
			return AjaxResult.success(filename);
		} catch (Exception e) {
			log.error("Exporter l'exception Excel {}", e.getMessage());
			throw new McnpException("Échec de l'exportation d'Excel, veuillez contacter le webmaster!");
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * Remplissez les données Excel
	 *
	 * @param Numéro de série de l'index
	 * @param Ligne  de cellule de ligne
	 */
	public void fillExcelData(int index, Row row) {
		int startNo = index * sheetSize;
		int endNo = Math.min(startNo + sheetSize, list.size());
		for (int i = startNo; i < endNo; i++) {
			row = sheet.createRow(i + 1 - startNo);
			// Récupérez l'objet exporté.
			T vo = (T) list.get(i);
			int column = 0;
			for (Object[] os : fields) {
				Field field = (Field) os[0];
				Excel excel = (Excel) os[1];
				// Définir les propriétés privées de la classe d'entité pour qu'elles soient
				// accessibles
				field.setAccessible(true);
				this.addCell(excel, row, vo, field, column++);
			}
		}
	}

	/**
	 * Créer un style de table
	 *
	 * @param wb Objet classeur
	 * @return Liste de styles
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		// Ecrivez chaque enregistrement, chaque enregistrement correspond à une ligne
		// dans la table Excel
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		Font totalFont = wb.createFont();
		totalFont.setFontName("Arial");
		totalFont.setFontHeightInPoints((short) 10);
		style.setFont(totalFont);
		styles.put("total", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.RIGHT);
		styles.put("data3", style);

		return styles;
	}

	/**
	 * Créer une cellule
	 */
	public Cell createCell(Excel attr, Row row, int column) {
		// Créer une colonne
		Cell cell = row.createCell(column);
		// Écrire les informations de colonne
		cell.setCellValue(attr.name());
		setDataValidation(attr, row, column);
		cell.setCellStyle(styles.get("header"));
		return cell;
	}

	/**
	 * Définir les informations de cellule
	 *
	 * Valeur de cellule @param value
	 * 
	 * @param attr lié à l'annotation Informations sur la cellule @param
	 */
	public void setCellVo(Object value, Excel attr, Cell cell) {
		if (ColumnType.STRING == attr.cellType()) {
			cell.setCellValue(StringUtils.isNull(value) ? attr.defaultValue() : value + attr.suffix());
		} else if (ColumnType.NUMERIC == attr.cellType()) {
			if (value != null) {
				cell.setCellValue(StringUtils.contains(Convert.toStr(value), ".") ? Convert.toDouble(value)
								: Convert.toInt(value));
			}
		} else if (ColumnType.IMAGE == attr.cellType()) {
			ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(),
					cell.getRow().getRowNum(), (short) (cell.getColumnIndex() + 1), cell.getRow().getRowNum() + 1);
			String imagePath = Convert.toStr(value);
			if (StringUtils.isNotEmpty(imagePath)) {
				byte[] data = ImageUtils.getImage(imagePath);
				getDrawingPatriarch(cell.getSheet()).createPicture(anchor,
						cell.getSheet().getWorkbook().addPicture(data, getImageType(data)));
			}
		}
	}

	/**
	 * Obtenez la toile
	 */
	public static Drawing<?> getDrawingPatriarch(Sheet sheet) {
		if (sheet.getDrawingPatriarch() == null) {
			sheet.createDrawingPatriarch();
		}
		return sheet.getDrawingPatriarch();
	}

	/**
	 * Obtenez le type d'image, définissez le type d'insertion d'image
	 */
	public int getImageType(byte[] value) {
		String type = FileTypeUtils.getFileExtendName(value);
		if ("JPG".equalsIgnoreCase(type)) {
			return Workbook.PICTURE_TYPE_JPEG;
		} else if ("PNG".equalsIgnoreCase(type)) {
			return Workbook.PICTURE_TYPE_PNG;
		}
		return Workbook.PICTURE_TYPE_JPEG;
	}

	/**
	 * Créer un style de table
	 */
	public void setDataValidation(Excel attr, Row row, int column) {
		if (attr.name().indexOf("注：") >= 0) {
			sheet.setColumnWidth(column, 6000);
		} else {
			// Définir la largeur de la colonne
			sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
		}
		// Si les informations d'invite sont définies, la souris sera placée sur
		// l'invite.
		if (StringUtils.isNotEmpty(attr.prompt())) {
			// Il y a 2 101 colonnes d'invites par défaut.
			setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
		}
		// Si l'attribut combo est défini, cette colonne ne peut être sélectionnée mais
		// pas saisie
		if (attr.combo().length > 0) {
			// Par défaut, les colonnes 2-101 peuvent uniquement être sélectionnées et ne
			// peuvent pas être entrées.
			setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
		}
	}

	/**
	 * Ajouter une cellule
	 */
	public Cell addCell(Excel attr, Row row, T vo, Field field, int column) {
		Cell cell = null;
		try {
			// Définir la hauteur de ligne
			row.setHeight(maxHeight);
			// Décidez si vous souhaitez exporter ou non en fonction des paramètres
			// d'Excel.Dans certains cas, vous devez le laisser vide et les utilisateurs
			// doivent remplir cette colonne.
			if (attr.isExport()) {
				// Créer une cellule
				cell = row.createCell(column);
				int align = attr.align().value();
				cell.setCellStyle(styles.get("data" + (align >= 1 && align <= 3 ? align : "")));

				// Utilisé pour lire les attributs de l'objet
				Object value = getTargetValue(vo, field, attr);
				String dateFormat = attr.dateFormat();
				String readConverterExp = attr.readConverterExp();
				String separator = attr.separator();
				// String dictType = attr.dictType();
				if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
					if (value instanceof Date) {
						cell.setCellValue(DateUtils.dateToStr((Date) value, dateFormat));
					} else {
						if (value instanceof LocalDate) {
							cell.setCellValue(DateTimeFormatter.ofPattern(dateFormat).format((LocalDate) value));
						} else if (value instanceof LocalDateTime) {
							cell.setCellValue(DateTimeFormatter.ofPattern(dateFormat).format((LocalDateTime) value));
						}
					}
				} else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
					cell.setCellValue(convertByExp(Convert.toStr(value), readConverterExp, separator));
				} /*
					 * else if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotNull(value)) {
					 * cell.setCellValue(convertDictByExp(Convert.toStr(value), dictType,
					 * separator)); }
					 */else if (value instanceof BigDecimal && -1 != attr.scale()) {
					cell.setCellValue((((BigDecimal) value).setScale(attr.scale(), attr.roundingMode())).toString());
				} else {
					// Définir le type de colonne
					setCellVo(value, attr, cell);
				}
				addStatisticsData(column, Convert.toStr(value), attr);
			}
		} catch (Exception e) {
			log.error("Échec de l'exportation d'Excel {}", e);
		}
		return cell;
	}

	/**
	 * Définir l'invite de cellule POI XSSFSheet
	 *
	 * @param sheet         Feuille
	 * @param promptTitle   titre de l'invite
	 * @param promptContent Contenu du prompt
	 * @param firstRow      Ligne de départ
	 * @param endRow        end ligne
	 * @param firstCol      Colonne de début
	 * @param endCol        end column
	 */
	public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
			int firstCol, int endCol) {
		DataValidationHelper helper = sheet.getDataValidationHelper();
		DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		DataValidation dataValidation = helper.createValidation(constraint, regions);
		dataValidation.createPromptBox(promptTitle, promptContent);
		dataValidation.setShowPromptBox(true);
		sheet.addValidationData(dataValidation);
	}

	/**
	 * Pour définir certaines valeurs de colonne, seules les données prédéfinies
	 * peuvent être saisies et une liste déroulante s'affiche.
	 *
	 * @param sheet    La feuille à définir.
	 * @param textlist le contenu affiché dans la liste déroulante Ligne de
	 *                 départ @param firstRow
	 * @param endRow   end ligne
	 * @param firstCol Colonne de début
	 * @param endCol   end column
	 * @return La feuille de set.
	 */
	public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
		DataValidationHelper helper = sheet.getDataValidationHelper();
		// Charger le contenu de la liste déroulante
		DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
		// Définissez la cellule sur laquelle la validité des données est chargée, les
		// quatre paramètres sont: ligne de début, ligne de fin, colonne de début,
		// colonne de fin
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// Objet de validité des données
		DataValidation dataValidation = helper.createValidation(constraint, regions);
		// Traiter les problèmes de compatibilité Excel
		if (dataValidation instanceof XSSFDataValidation) {
			dataValidation.setSuppressDropDownArrow(true);
			dataValidation.setShowErrorBox(true);
		} else {
			dataValidation.setSuppressDropDownArrow(false);
		}

		sheet.addValidationData(dataValidation);
	}

	/**
	 * Analyser la valeur dérivée 0 = homme, 1 = femme, 2 = inconnu
	 *
	 * @param propertyValue valeur du paramètre
	 * @param converterExp  annotation de traduction
	 * @param separator     Séparateur
	 * @return valeur analysée
	 */
	public static String convertByExp(String propertyValue, String converterExp, String separator) {
		StringBuilder propertyString = new StringBuilder();
		String[] convertSource = converterExp.split(",");
		for (String item : convertSource) {
			String[] itemArray = item.split("=");
			if (StringUtils.containsAny(separator, propertyValue)) {
				for (String value : propertyValue.split(separator)) {
					if (itemArray[0].equals(value)) {
						propertyString.append(itemArray[1] + separator);
						break;
					}
				}
			} else {
				if (itemArray[0].equals(propertyValue)) {
					return itemArray[1];
				}
			}
		}
		return StringUtils.stripEnd(propertyString.toString(), separator);
	}

	/**
	 * Valeur analytique inverse mâle = 0, femelle = 1, inconnu = 2
	 *
	 * @param propertyValue valeur du paramètre
	 * @param converterExp  annotation de traduction
	 * @param separator     Séparateur
	 * @return valeur analysée
	 */
	public static String reverseByExp(String propertyValue, String converterExp, String separator) {
		StringBuilder propertyString = new StringBuilder();
		String[] convertSource = converterExp.split(",");
		for (String item : convertSource) {
			String[] itemArray = item.split("=");
			if (StringUtils.containsAny(separator, propertyValue)) {
				for (String value : propertyValue.split(separator)) {
					if (itemArray[1].equals(value)) {
						propertyString.append(itemArray[0] + separator);
						break;
					}
				}
			} else {
				if (itemArray[1].equals(propertyValue)) {
					return itemArray[0];
				}
			}
		}
		return StringUtils.stripEnd(propertyString.toString(), separator);
	}

	/**
	 * Analyser les valeurs du dictionnaire
	 *
	 * @param dictValue valeur du dictionnaire
	 * @param dictType  type de dictionnaire
	 * @param separator Séparateur
	 * @return Balise de dictionnaire
	 */
	/*
	 * public static String convertDictByExp(String dictValue, String dictType,
	 * String separator) { return DictUtils.getDictLabel(dictType, dictValue,
	 * separator); }
	 * 
	 */

	/**
	 * Valeur du dictionnaire de valeur d'analyse inverse
	 *
	 * @param dictLabel étiquette de dictionnaire
	 * @param dictType  type de dictionnaire
	 * @param separator Séparateur
	 * @return valeur du dictionnaire
	 */
	/*
	 * public static String reverseDictByExp(String dictLabel, String dictType,
	 * String separator) { return DictUtils.getDictValue(dictType, dictLabel,
	 * separator); }
	 */

	/**
	 * Statistiques totales
	 */
	private void addStatisticsData(Integer index, String text, Excel entity) {
		if (entity != null && entity.isStatistics()) {
			Double temp = 0D;
			if (!statistics.containsKey(index)) {
				statistics.put(index, temp);
			}
			try {
				temp = Double.valueOf(text);
			} catch (NumberFormatException e) {
			}
			statistics.put(index, statistics.get(index) + temp);
		}
	}

	/**
	 * Créer des lignes statistiques
	 */
	public void addStatisticsRow() {
		if (statistics.size() > 0) {
			Cell cell = null;
			Row row = sheet.createRow(sheet.getLastRowNum() + 1);
			Set<Integer> keys = statistics.keySet();
			cell = row.createCell(0);
			cell.setCellStyle(styles.get("total"));
			cell.setCellValue("合计");

			for (Integer key : keys) {
				cell = row.createCell(key);
				cell.setCellStyle(styles.get("total"));
				cell.setCellValue(DOUBLE_FORMAT.format(statistics.get(key)));
			}
			statistics.clear();
		}
	}

	/**
	 * Nom du fichier d'encodage
	 */
	public String encodingFilename(String filename) {
		filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
		return filename;
	}

	/**
	 * Obtenez le chemin de téléchargement
	 *
	 * @param filename nom de fichier
	 */
	public String getAbsoluteFile(String filename) {
		String downloadPath = AppConfig.getDownloadPath() + filename;
		File desc = new File(downloadPath);
		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		return downloadPath;
	}

	/**
	 * Récupère la valeur d'attribut dans le bean
	 *
	 * @param vo    Objet d'entité
	 * @param field Champ
	 * @param excel Annotation
	 * @return la valeur finale de l'attribut
	 * @throws Exception
	 */
	private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
		Object o = field.get(vo);
		if (StringUtils.isNotEmpty(excel.targetAttr())) {
			String target = excel.targetAttr();
			if (target.indexOf(".") > -1) {
				String[] targets = target.split("[.]");
				for (String name : targets) {
					o = getValue(o, name);
				}
			} else {
				o = getValue(o, target);
			}
		}
		return o;
	}

	/**
	 * Récupère la valeur sous la forme de la méthode get method de l'attribut de la
	 * classe
	 * 
	 * @param o
	 * @param name
	 * @return value
	 * @throws Exception
	 */
	private Object getValue(Object o, String name) throws Exception {
		if (StringUtils.isNotNull(o) && StringUtils.isNotEmpty(name)) {
			Class<?> clazz = o.getClass();
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			o = field.get(o);
		}
		return o;
	}

	/**
	 * Obtenez tous les champs définis
	 */
	private void createExcelField() {
		this.fields = new ArrayList<Object[]>();
		List<Field> tempFields = new ArrayList<>();
		tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
		tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		for (Field field : tempFields) {
			// Annotation unique
			if (field.isAnnotationPresent(Excel.class)) {
				putToField(field, field.getAnnotation(Excel.class));
			}

			// Multi-annotations
			if (field.isAnnotationPresent(Excels.class)) {
				Excels attrs = field.getAnnotation(Excels.class);
				Excel[] excels = attrs.value();
				for (Excel excel : excels) {
					putToField(field, excel);
				}
			}
		}
		this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel) objects[1]).sort()))
				.collect(Collectors.toList());
		this.maxHeight = getRowHeight();
	}

	/**
	 * Obtenez la hauteur de ligne maximale en fonction de l'annotation
	 */
	public short getRowHeight() {
		double maxHeight = 0;
		for (Object[] os : this.fields) {
			Excel excel = (Excel) os[1];
			maxHeight = maxHeight > excel.height() ? maxHeight : excel.height();
		}
		return (short) (maxHeight * 20);
	}

	/**
	 * Mettez-le dans la collection de terrain
	 */
	private void putToField(Field field, Excel attr) {
		if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
			this.fields.add(new Object[] { field, attr });
		}
	}

	/**
	 * Créer un classeur
	 */
	public void createWorkbook() {
		this.wb = new SXSSFWorkbook(500);
	}

	/**
	 * Créer une feuille de calcul
	 *
	 * @param sheetNo Numéro de la feuille
	 * @param index   Numéro de série
	 */
	public void createSheet(double sheetNo, int index) {
		this.sheet = wb.createSheet();
		this.styles = createStyles(wb);
		// Définissez le nom de la feuille de calcul.
		if (sheetNo == 0) {
			wb.setSheetName(index, sheetName);
		} else {
			wb.setSheetName(index, sheetName + index);
		}
	}

	/**
	 * Obtenir la valeur de la cellule
	 *
	 * @param row    ligne obtenue
	 * @param column Obtenir le numéro de la colonne de la cellule
	 * @return Valeur de cellule
	 */
	public Object getCellValue(Row row, int column) {
		if (row == null) {
			return row;
		}
		Object val = "";
		try {
			Cell cell = row.getCell(column);
			if (StringUtils.isNotNull(cell)) {
				if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
					val = cell.getNumericCellValue();
					if (DateUtil.isCellDateFormatted(cell)) {
						val = DateUtil.getJavaDate((Double) val); // Conversion de format de date POI Excel
					} else {
						if ((Double) val % 1 != 0) {
							val = new BigDecimal(val.toString());
						} else {
							val = new DecimalFormat("0").format(val);
						}
					}
				} else if (cell.getCellType() == CellType.STRING) {
					val = cell.getStringCellValue();
				} else if (cell.getCellType() == CellType.BOOLEAN) {
					val = cell.getBooleanCellValue();
				} else if (cell.getCellType() == CellType.ERROR) {
					val = cell.getErrorCellValue();
				}

			}
		} catch (Exception e) {
			return val;
		}
		return val;
	}

	/**
	 * Déterminez s'il s'agit d'une ligne vierge
	 *
	 * @param row Ligne testée
	 * @return
	 */
	private boolean isRowEmpty(Row row) {
		if (row == null) {
			return true;
		}
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			if (cell != null && cell.getCellType() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}
}