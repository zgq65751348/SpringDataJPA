package org.summerframework.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.summerframework.example.root.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/15 0015 上午 10:06
 */
@Setter
@Getter
@Entity
@Table
@DynamicUpdate
@ApiModel(value = "商品图片")
@EqualsAndHashCode(callSuper = true)
public class CommodityPicture extends BaseEntity {

    private static final long serialVersionUID = 1620243840780887733L;

    @Column(name="picture_path",nullable = false,columnDefinition = "varchar(750) COMMENT '商品图片地址'")
    private String path;

    @ManyToOne(targetEntity =Commodity.class,cascade = CascadeType.ALL)
    @JoinColumn(name ="commodity_id",referencedColumnName = "id")
    @JsonBackReference
    private Commodity commodity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommodityPicture that = (CommodityPicture) o;
        return Objects.equals(path, that.path) &&
                Objects.equals(commodity, that.commodity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), path, commodity);
    }
}
