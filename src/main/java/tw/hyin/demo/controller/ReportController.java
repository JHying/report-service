package tw.hyin.demo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.hyin.demo.service.ReportService;
import tw.hyin.demo.utils.JxlsUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

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
@RequiredArgsConstructor
public class ReportController extends FileBaseController {

    @Value("classpath:templates/pretestSummary.xlsx")
    Resource pretestSummaryFile;

    private final ReportService reportService;

    @SneakyThrows
    @ApiOperation(value = "word report")
    @PostMapping(value = "/{templateName}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getReport(@PathVariable(value = "templateName") String templateName,
                                       @RequestBody T object) {
        return super.createPDF(object, templateName + ".docx", true, null);
    }

    @SneakyThrows
    @ApiOperation(value = "excel report")
    @GetMapping(value = "/excel")
    public void exportExcel(HttpServletResponse response) {
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
