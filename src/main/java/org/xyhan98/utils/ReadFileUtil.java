package org.xyhan98.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ReadFileUtil {

    /**
     * read the content of .docx file
     * @param file
     * @return
     * @throws IOException
     */
    public static String readDocx(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                content.append(paragraph.getText()).append("\n");
            }
        }
        return content.toString();
    }

    /**
     * read the content of .doc file
     * @param file
     * @return
     * @throws IOException
     */
    public static String readDoc(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             HWPFDocument document = new HWPFDocument(fis)) {

            WordExtractor extractor = new WordExtractor(document);
            String[] paragraphs = extractor.getParagraphText();

            for (String paragraph : paragraphs) {
                content.append(paragraph).append("\n");
            }
        }
        return content.toString();
    }

    /**
     * read the content of word type file
     * @param file
     * @return
     * @throws IOException
     */
    public static String readWord(File file) throws IOException {
        String fileName = file.getName();
        if (fileName.endsWith(".docx")) {
            return readDocx(file);
        } else if (fileName.endsWith(".doc")) {
            return readDoc(file);
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }

    /**
     * read the content of .pdf file
     * @param file
     * @return
     * @throws IOException
     */
    public static String readPDF(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            if (!document.isEncrypted()) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                return pdfStripper.getText(document);
            } else {
                throw new IOException("The PDF document is encrypted and cannot be read");
            }
        }
    }
}
