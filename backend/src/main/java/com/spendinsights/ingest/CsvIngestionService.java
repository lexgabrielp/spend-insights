package com.spendinsights.ingest;

import com.opencsv.CSVReaderHeaderAware;
import com.spendinsights.domain.*;
import com.spendinsights.repository.*;
import com.spendinsights.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.*;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CsvIngestionService {
    private final UploadRepository uploads;
    private final TransactionRepository txs;
    private final TransactionService classifier;

    public int ingest(User user, MultipartFile file) throws Exception {
        Upload up = new Upload();
        up.setUser(user);
        up.setFilename(file.getOriginalFilename());
        up.setContentType(file.getContentType());
        up.setSizeBytes(file.getSize());
        uploads.save(up);
        int count = 0;
        try (var r = new CSVReaderHeaderAware(new InputStreamReader(file.getInputStream()))) {
            Map<String, String> row;
            while ((row = r.readMap()) != null) {
                Transaction t = new Transaction();
                t.setUser(user);
                t.setUpload(up);
                t.setPostedAt(LocalDate.parse(first(row, "date", "posted_at", "transaction_date")));
                t.setMerchant(first(row, "merchant", "description", "payee"));
                t.setDescription(first(row, "description", "notes", "merchant"));
                BigDecimal amt = new BigDecimal(first(row, "amount", "value", "debit").replace(",", ""));
                t.setAmount(amt.abs());
                t.setType(amt.signum() < 0 ? TransactionType.EXPENSE : TransactionType.INCOME);
                t.setSource("csv");
                txs.save(t);
                classifier.categorize(user, t);
                count++;
            }
        }
        up.setStatus(UploadStatus.PROCESSED);
        uploads.save(up);
        return count;
    }

    private String first(Map<String, String> m, String... ks) {
        for (String k : ks)
            for (String key : m.keySet())
                if (key.equalsIgnoreCase(k) && m.get(key) != null && !m.get(key).isBlank()) return m.get(key);
        return "";
    }
}
