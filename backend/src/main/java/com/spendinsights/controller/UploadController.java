package com.spendinsights.controller;

import com.spendinsights.ingest.CsvIngestionService;
import com.spendinsights.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
public class UploadController {
    private final CsvIngestionService csv;

    @PostMapping("/csv")
    Map<String, Object> csv(@AuthenticationPrincipal UserPrincipal p, @RequestParam MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();

        if (filename == null || !filename.toLowerCase().endsWith(".csv")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only CSV uploads are supported. PDF/JPG OCR is not implemented yet.");
        }

        return Map.of("imported", csv.ingest(p.user(), file));
    }

    @PostMapping("/receipt")
    Map<String, Object> receipt() {
        return Map.of("status", "PDF receipt endpoint scaffolded; add OCR if needed. Text PDFs parse through PDFBox in production extension.");
    }
}