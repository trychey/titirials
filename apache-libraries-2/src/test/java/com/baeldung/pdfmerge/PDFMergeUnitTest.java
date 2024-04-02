package com.baeldung.pdfmerge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import com.itextpdf.text.DocumentException;

public class PDFMergeUnitTest {

    @BeforeEach
    public void create() throws IOException {
        File tempDirectory = new File("src/test/resources/temp");
        tempDirectory.mkdirs();
        List.of(List.of("hello_world1", "file1.pdf"), List.of("hello_world2", "file2.pdf"))
            .forEach(pair -> {
                try {
                    createPDFDoc(pair.get(0), pair.get(1));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    @AfterEach
    public void destroy() throws IOException {
        Stream<Path> paths = Files.walk(Paths.get("src/test/resources/temp/"));
        paths.sorted((p1, p2) -> -p1.compareTo(p2))
            .forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    private static void createPDFDoc(String content, String filePath) throws IOException {
        PDDocument document = new PDDocument();
        for (int i = 0; i < 3; i++) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.showText(content + ", page:" + i);
                contentStream.endText();
            }
        }
        document.save("src/test/resources/temp/" + filePath);
        document.close();
    }

    @Test
    public void giveMultiplePdfs_whenMerge1Executed_thenPdfsMerged() throws IOException {
        List<String> files = List.of("src/test/resources/temp/file1.pdf", "src/test/resources/temp/file2.pdf");
        PDFMerge pdfMerge = new PDFMerge();
        pdfMerge.merge(files, "src/test/resources/temp/output.pdf");

        try (PDDocument document = PDDocument.load(new File("src/test/resources/temp/output.pdf"))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String actual = pdfStripper.getText(document);
            List.of("hello_world1", "hello_world2")
                .forEach(text -> assertTrue(actual.contains(text)));
            String expected = """
                hello_world1, page:0
                hello_world1, page:1
                hello_world1, page:2
                hello_world2, page:0
                hello_world2, page:1
                hello_world2, page:2
                """;
            assertEquals(expected, actual);
        }
    }

    @Test
    public void giveMultiplePdfs_whenMerge2Executed_thenPdfsMerged() throws IOException, DocumentException {
        List<String> files = List.of("src/test/resources/temp/file1.pdf", "src/test/resources/temp/file2.pdf");
        PDFMerge pdfMerge = new PDFMerge();
        pdfMerge.merge2(files, "src/test/resources/temp/output1.pdf");

        try (PDDocument document = PDDocument.load(new File("src/test/resources/temp/output1.pdf"))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String actual = pdfStripper.getText(document);
            List.of("hello_world1", "hello_world2")
                .forEach(text -> assertTrue(actual.contains(text)));
            String expected = """
                hello_world1, page:0
                hello_world1, page:1
                hello_world1, page:2
                hello_world2, page:0
                hello_world2, page:1
                hello_world2, page:2
                """;
            assertEquals(expected, actual);
        }
    }
}
