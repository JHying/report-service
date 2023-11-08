package tw.hyin.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.hyin.demo.dto.report.UploadRecordSummary;
import tw.hyin.demo.entity.UploadRecord;
import tw.hyin.demo.repo.UploadRecordRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JHying(Rita) on 2022.
 * @description
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final UploadRecordRepository uploadRecordRepository;

    public List<UploadRecordSummary> getPreTestReport() {
        List<UploadRecordSummary> summaries = new ArrayList<>();
        List<UploadRecord> summaryData = uploadRecordRepository.findAll();
        UploadRecordSummary summary = new UploadRecordSummary();
        summary.setExportTime(new Date());
        summary.setUploadRecords(summaryData);
        summary.setDataSize(summaryData.size());
        summary.setSheetName("sheet_name");
        summary.setUserCount((int) summaryData.stream().map(UploadRecord::getUserId).distinct().count());
        summaries.add(summary);
        return summaries;
    }

}
