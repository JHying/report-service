package tw.hyin.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tw.hyin.demo.dto.UploadFileObj;
import tw.hyin.demo.entity.UploadRecord;
import tw.hyin.demo.repo.UploadRecordRepository;
import tw.hyin.java.utils.FileUtil;
import tw.hyin.java.utils.Log;
import tw.hyin.java.utils.PojoUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * 
 * @author YingHan 2021-11-02
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

	@Value("${server.upload.rootpath}")
	private String rootPath;

	private final UploadRecordRepository uploadRecordRepository;


	public boolean upload(MultipartFile uploadFile, UploadFileObj uploadFileObj) throws Exception {
		FileUtil fileUtil;
		Path fullPath = Paths.get(this.rootPath, uploadFileObj.getFilePath());

		if (uploadFileObj.getFileName() != null) {
			fileUtil = new FileUtil(fullPath, uploadFileObj.getFileName());
		} else {
			fileUtil = new FileUtil(fullPath);
		}

		return fileUtil.saveFile(uploadFile);
	}

	public Path getFullPath(String... filePath) {
		return Paths.get(this.rootPath, filePath);
	}

	public void addAttachment(UploadFileObj uploadFileObj) throws Exception {
		// 新增 UploadRecord
		FileUtil fileUtil = new FileUtil(Path.of(this.rootPath + "/" + uploadFileObj.getFilePath()));
		if (fileUtil.getFile().exists()) {
			UploadRecord uploadRecord = PojoUtil.convertPojo(uploadFileObj, UploadRecord.class);
			uploadRecord.setFilePath(uploadFileObj.getFilePath() + "/" + uploadFileObj.getFileName());
			uploadRecord.setUserId(uploadFileObj.getUserId());
			uploadRecord.setUploadDate(new Date());
			uploadRecordRepository.save(uploadRecord);
			Log.info("insert into uploadRecord: " + this.rootPath + "/" + uploadRecord.getFilePath());
		} else {
			throw new Exception("File not exist! Fail to insert upload data.");
		}
	}

}
