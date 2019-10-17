package org.summerframework.example.root;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public abstract  class BaseEntity implements Serializable {
    private static final long serialVersionUID = -4505117821220216969L;

    @Id
    @GeneratedValue(generator = "snowFlakeId")
    @GenericGenerator(name = "snowFlakeId", strategy = "org.summerframework.example.utils.IDGenerator")
    @Basic(optional = false)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    @Column(name = "create_time", columnDefinition = "datetime  COMMENT '创建时间'",updatable=false)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GTM+8")
    @Column(name = "update_time", columnDefinition = "datetime  COMMENT '修改时间'")
    private Date updateTime;

    /**
     * 数据插入前的操作
     */
    @PrePersist
    public void setInsertBefore() {
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    /**
     * 数据修改前的操作
     */
    @PreUpdate
    public void setUpdateBefore() {
        this.updateTime = new Date();
    }


}
