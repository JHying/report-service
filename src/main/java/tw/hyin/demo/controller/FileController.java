package tw.hyin.demo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.hyin.demo.dto.UploadFileObj;
import tw.hyin.demo.service.FileService;
import tw.hyin.java.utils.http.ResponseObj;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Path;

/**
 * API 接口
 *
 * @author YingHan 2021
 */
@RestController
@ResponseBody
@RequiredArgsConstructor
public class FileController extends FileBaseController {

    private final FileService fileService;

    @SneakyThrows
    @ApiOperation(value = "file upload")
    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseObj<ResponseObj.RspMsg>> fileUpload(@RequestParam("file") MultipartFile file, @ModelAttribute UploadFileObj uploadFileObj) {
        if (fileService.upload(file, uploadFileObj)) {
            fileService.addAttachment(uploadFileObj);
            return super.sendSuccessRsp(ResponseObj.RspMsg.SUCCESS);
        } else {
            throw new Exception("file saved failed.");
        }
    }

    @SneakyThrows
    @ApiOperation(value = "file download")
    @GetMapping(value = "/download/**")
    public ResponseEntity<?> fileDownload(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String filePath = URLDecoder.decode(requestURL.split("/download/")[1], "UTF-8");
        Path fullPath = fileService.getFullPath(filePath);
        File file = fullPath.toFile();
        if (!file.exists()) {
            return super.sendBadRequestRsp(ResponseObj.RspMsg.NOT_FOUND);
        }
        return super.downloadFile(file);
    }

    @SneakyThrows
    @ApiOperation(value = "file add watermark and download", notes = "watermark set at header (name=watermark)")
    @GetMapping(value = "/download/watermark/**")
    public ResponseEntity<?> fileDownloadWithWatermark(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String filePath = URLDecoder.decode(requestURL.split("/download/watermark/")[1], "UTF-8");
        Path fullPath = fileService.getFullPath(filePath);
        File file = fullPath.toFile();
        if (!file.exists()) {
            return super.sendBadRequestRsp(ResponseObj.RspMsg.NOT_FOUND);
        }
        return super.addWaterMark(file, true, request.getHeader("watermark"));
    }

}
