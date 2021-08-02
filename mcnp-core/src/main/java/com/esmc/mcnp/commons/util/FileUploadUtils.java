package com.esmc.mcnp.commons.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

/**
 * File upload tools
 */
public class FileUploadUtils {
	/**
	 * Default size 50M
	 */
	public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

	/**
	 * Default maximum length of file name 100
	 */
	public static final int DEFAULT_FILE_NAME_LENGTH = 100;

	/**
	 * Default storage address
	 */
	private static String defaultBaseDir = "";

	private static int counter = 0;

	public static void setDefaultBaseDir(String defaultBaseDir) {
		FileUploadUtils.defaultBaseDir = defaultBaseDir;
	}

	/**
	 * Default relative path
	 * 
	 * @return
	 */
	public static String getDefaultBaseDir() {
		try {
			Properties properties = Resources.getResourceAsProperties("application.properties");
			defaultBaseDir = properties.getProperty("spring.servlet.upload.file-path");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(defaultBaseDir)) {
			defaultBaseDir = ServletUtils.getRealPath() + File.separator + "upload";
		}
		return defaultBaseDir;
	}

	/**
	 * File upload with default configuration
	 *
	 * @param file Uploaded files
	 * @return file name
	 * @throws Exception
	 */
	public static final String upload(MultipartFile file) throws IOException {
		try {
			return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	/**
	 * Upload according to file path
	 *
	 * @param baseDir Relative application base directory
	 * @param file    Uploaded files
	 * @return file name
	 * @throws IOException
	 */
	public static final String upload(String baseDir, MultipartFile file) throws IOException {
		try {
			return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	/**
	 * File Upload
	 *
	 * @param baseDir   Relative application base directory
	 * @param file      Uploaded files
	 * @param extension Upload file type
	 * @return returns the name of the uploaded file
	 */
	public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
			throws FileUploadException, IOException {
		int fileNamelength = file.getOriginalFilename().length();
		if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
			throw new FileUploadException("La longueur maximale par défaut du nom de fichier est"
					+ FileUploadUtils.DEFAULT_FILE_NAME_LENGTH + "，En fait" + fileNamelength);
		}

		assertAllowed(file, allowedExtension);
		System.out.println("Chemin de stockage = " + baseDir);
		String fileName = extractFilename(file);
		System.out.println("Nom du fichier = " + fileName);
		File desc = getAbsoluteFile(baseDir, fileName);
		file.transferTo(desc);
		return fileName;
	}

	/**
	 * Encoding file name
	 */
	public static final String extractFilename(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String extension = getExtension(file);
		fileName = DateUtils.datePath() + "/" + encodingFilename(fileName) + "." + extension;
		return fileName;
	}

	private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
		String absFileName = uploadDir + File.separator + fileName;
		System.out.println("Chemin absolue = " + absFileName);
		File desc = new File(absFileName);

		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		if (!desc.exists()) {
			desc.createNewFile();
		}
		return desc;
	}

	/**
	 * Encoding file name
	 */
	private static final String encodingFilename(String fileName) {
		fileName = fileName.replace("_", " ");
		fileName = MD5Utils.hash(fileName + System.nanoTime() + counter++);
		return fileName;
	}

	/**
	 * File size verification
	 *
	 * @param file Uploaded files
	 * @return
	 * @throws FileUploadException
	 */
	public static final void assertAllowed(MultipartFile file, String[] allowedExtension) throws FileUploadException {
		long size = file.getSize();
		if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
			throw new FileUploadException("La taille par défaut est" + (DEFAULT_MAX_SIZE / 1024 / 1024)
					+ "M, réellement" + (size / 1024 / 1024) + "M");
		}

		String fileName = file.getOriginalFilename();
		String extension = getExtension(file);
		if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
			throw new FileUploadException("Autoriser le format de fichier" + Arrays.toString(allowedExtension)
					+ "，télécharger le nom du fichier" + fileName);
		}

	}

	/**
	 * Déterminer si le type MIME est un type MIME autorisé
	 *
	 * @param extension
	 * @param allowedExtension
	 * @return
	 */
	public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
		for (String str : allowedExtension) {
			if (str.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the suffix of the file name
	 *
	 * @param file Form file
	 * @return suffix
	 */
	public static final String getExtension(MultipartFile file) {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (StringUtils.isEmpty(extension)) {
			extension = MimeTypeUtils.getExtension(file.getContentType());
		}
		return extension;
	}
}