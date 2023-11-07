package tw.hyin.demo.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import tw.hyin.demo.service.ReportService;
import tw.hyin.demo.utils.JxlsUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JHying(Rita) on 2022.
 * @description
 */
@RestController
@ResponseBody
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    @Value("classpath:templates/pretestSummary.xlsx")
    Resource pretestSummaryFile;

    private final ReportService reportService;

    @SneakyThrows
    @ApiOperation(value = "下載前測總表")
    @GetMapping(value = "/pretest/summary")
    public void exportPretestExcel(HttpServletResponse response) {
        InputStream is = pretestSummaryFile.getInputStream();
        String fileName = "download.xlsx";
        response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        OutputStream os = response.getOutputStream();
        Map<String, Object> model = new HashMap<>();
        model.put("summaries", reportService.getPreTestReport());
        JxlsUtils.exportExcel(is, os, model);
        is.close();
        os.close();
    }

}
