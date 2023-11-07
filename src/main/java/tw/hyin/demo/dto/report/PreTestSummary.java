package tw.hyin.demo.dto.report;

import lombok.Data;
import tw.hyin.demo.entity.PreTest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author JHying(Rita) on 2022.
 * @description
 */
@Data
public class PreTestSummary implements Serializable {

    private Date exportTime;
    private Integer dataSize;
    private Integer userCount;
    private String sheetName;
    private List<PreTest> preTestData;

}
