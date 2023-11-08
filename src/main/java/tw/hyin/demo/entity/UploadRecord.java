package tw.hyin.demo.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import tw.hyin.java.utils.DateDeserializer;
import tw.hyin.java.utils.DateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author H-yin on 2021.
 */
@Data
@Entity
@DynamicInsert//解決 not null 欄位沒給值時，不會自動塞 default 的問題
@Table(name = "UploadRecord")
public class UploadRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UploadID")
    private Integer uploadId;

    @Column(name = "FilePath")
    private String filePath;

    @Column(name = "UserID")
    private String userId;

    @Column(name = "FileName")
    private String fileName;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @Column(name = "UploadDate")
    private Date uploadDate;

}
