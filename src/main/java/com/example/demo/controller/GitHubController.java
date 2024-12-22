package com.example.demo.controller;

import com.example.demo.service.GitHubService;
import com.example.demo.service.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GitHubController {

    @Autowired
    private GitHubService githubService;

    @Autowired
    private PDFService pdfService;

    @GetMapping("/export-users")
    public ResponseEntity<byte[]> exportUsersPdf() {
        try {
            var users = githubService.getUsers(50).collectList().block();
            byte[] pdfBytes = pdfService.generatePdf(users);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "github-users.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}