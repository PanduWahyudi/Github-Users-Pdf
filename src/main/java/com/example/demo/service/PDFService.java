package com.example.demo.service;

import com.example.demo.model.GitHubUser;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PDFService {
    
    public byte[] generatePdf(List<GitHubUser> users) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        PdfWriter.getInstance(document, out);
        document.open();
        
        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("GitHub Users List", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);
        
        // Create table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        
        // Add headers
        addTableHeader(table);
        
        // Add rows
        for (GitHubUser user : users) {
            addTableRow(table, user);
        }
        
        document.add(table);
        document.close();
        
        return out.toByteArray();
    }
    
    private void addTableHeader(PdfPTable table) {
        String[] headers = {"ID", "Username", "Type", "Profile URL"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }
    }
    
    private void addTableRow(PdfPTable table, GitHubUser user) {
        table.addCell(String.valueOf(user.getId()));
        table.addCell(user.getLogin());
        table.addCell(user.getType());
        table.addCell(user.getHtmlUrl());
    }
}