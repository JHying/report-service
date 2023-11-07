package tw.hyin.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "PreTest")
public class PreTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TEST_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "START_TIME")
    private Date startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "END_TIME")
    private Date endTime;

}
