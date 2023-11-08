package tw.hyin.demo.controller;

import com.google.common.io.Files;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import tw.hyin.demo.utils.xDocReport.XDocReportUtil;
import tw.hyin.java.utils.JsonUtil;
import tw.hyin.java.utils.http.ResponseObj;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class FileBaseController {

    @Value("xdoc.template.rootpath")
    private String template_rootpath;

    protected <T> ResponseEntity<ResponseObj<T>> sendSuccessRsp(T result) {
        return new ResponseEntity(ResponseObj.builder().status(HttpStatus.OK)
                .result(result).build(), HttpStatus.OK);
    }

    protected <T> ResponseEntity<ResponseObj<T>> sendBadRequestRsp(T result) {
        List<String> errors = new ArrayList<>();
        errors.add(result.toString());
        return new ResponseEntity(ResponseObj.builder().status(HttpStatus.BAD_REQUEST)
                .errors(errors).build(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @author YingHan
     * @Description 下載檔案
     * @since 2021-12-28
     */
    protected <T> ResponseEntity<?> downloadFile(File originFile) throws Exception {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        byteOut.write(Files.toByteArray(originFile));
        byteOut.close();
        // 包在 resource 裡才能 return 給前台，否則會 406
        ByteArrayResource resource = new ByteArrayResource(byteOut.toByteArray());
        return this.setResponse(resource, originFile.getName());
    }

    /**
     * @author YingHan
     * @Description 檔案+浮水印後下載
     * @since 2021-12-28
     */
    protected <T> ResponseEntity<?> addWaterMark(File originFile, boolean deleteTemp, String watermark)
            throws Exception {
        XDocReportUtil xDocReportUtil = new XDocReportUtil();
        String outputPath = originFile.getAbsolutePath().split("\\.")[0] + "_watermark.pdf";
        String outputName = originFile.getName().split("\\.")[0] + "_watermark.pdf";
        ByteArrayOutputStream os = xDocReportUtil.addWatermark(originFile.getAbsolutePath(), outputPath, watermark);
        // 包在 resource 裡才能 return 給前台，否則會 406
        ByteArrayResource resource = new ByteArrayResource(os.toByteArray());
        // 取得串流後刪除暫存
        if (deleteTemp) {
            new File(outputPath).delete();
        }
        return this.setResponse(resource, outputName);
    }

    /**
     * @author YingHan
     * @Description 製作報表 (paramObj=變數物件)
     * @since 2021-12-28
     */
    protected <T> ResponseEntity<?> createPDF(T paramObj, String templateName, boolean deleteTemp, String watermark)
            throws Exception {
        Map<String, Object> map = (Map<String, Object>) JsonUtil.objToMap(paramObj);
        XDocReportUtil xDocReportUtil = new XDocReportUtil(template_rootpath + "/" + templateName, map);
        String fileName = templateName.split("\\.")[0] + ".pdf";
        ByteArrayResource resource = new ByteArrayResource(
                xDocReportUtil.download(fileName, ConverterTypeTo.PDF, deleteTemp, watermark).toByteArray()
        );
        return this.setResponse(resource, fileName);
    }

    /**
     * @author YingHan
     * @Description 統一的 response 方法
     * @since 2021-12-28
     */
    private <T> ResponseEntity<?> setResponse(ByteArrayResource resource, String fileName) throws Exception {
        String mimeType = URLConnection.guessContentTypeFromName(fileName);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + URLEncoder.encode(fileName, "UTF-8"))
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }

}
