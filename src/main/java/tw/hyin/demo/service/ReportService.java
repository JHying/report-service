package tw.hyin.demo.service;

import lombok.RequiredArgsConstructor;
import org.itri.sstc.dto.report.*;
import tw.hyin.demo.dto.report.PreTestSummary;
import tw.hyin.demo.entity.PreTest;
import org.itri.sstc.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.hyin.demo.repo.PreTestRepository;

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

    private final PreTestRepository preTestRepository;

    public List<PreTestSummary> getPreTestReport() {
        List<PreTestSummary> summaries = new ArrayList<>();
        List<PreTest> summaryData = preTestRepository.findAll();
        PreTestSummary summary = new PreTestSummary();
        summary.setExportTime(new Date());
        summary.setPreTestData(summaryData);
        summary.setDataSize(summaryData.size());
        summary.setSheetName("sheet_name");
        summary.setUserCount((int) summaryData.stream().map(PreTest::getUserId).distinct().count());
        summaries.add(summary);
        return summaries;
    }

}
