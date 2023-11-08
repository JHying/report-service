package tw.hyin.demo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.hyin.java.utils.DateDeserializer;
import tw.hyin.java.utils.DateSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YingHan on 2021.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileObj implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("檔案路徑")
    private String filePath;

    @ApiModelProperty("檔案名稱")
    private String fileName;

    @ApiModelProperty("上傳者")
    private String userId;

    @ApiModelProperty("上傳時間")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date uploadDate;

}
