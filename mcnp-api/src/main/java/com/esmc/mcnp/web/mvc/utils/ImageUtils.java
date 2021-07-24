/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.web.mvc.utils;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

/**
 *
 * @author HP
 */
public class ImageUtils {

    public static void generatePDFFromImage(String imgFileName, String fileDest) throws FileNotFoundException, MalformedURLException {
        PdfWriter writer = new PdfWriter(fileDest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Image img = new Image(ImageDataFactory.create(imgFileName));
        Rectangle rec = new Rectangle(img.getImageScaledWidth(), img.getImageScaledHeight());
        PageSize psize = new PageSize(rec);
        try (Document document = new Document(pdfDoc, psize)) {
            img.setFixedPosition(0, 0);
            document.add(img);
        }
    }
    
    public static void generatePDFFromImage(byte[] imgFileName, String fileDest) throws FileNotFoundException, MalformedURLException {
        PdfWriter writer = new PdfWriter(fileDest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Image img = new Image(ImageDataFactory.create(imgFileName));
        Rectangle rec = new Rectangle(img.getImageScaledWidth(), img.getImageScaledHeight());
        PageSize psize = new PageSize(rec);
        try (Document document = new Document(pdfDoc, psize)) {
            img.setFixedPosition(0, 0);
            document.add(img);
        }
    }
}
