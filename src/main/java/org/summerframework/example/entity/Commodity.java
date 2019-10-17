package org.summerframework.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.summerframework.example.root.BaseEntity;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <p>
 *   //@Column(name = "stock",nullable = false,columnDefinition = "bigint(10) COMMENT ''库存")
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/15 0015 上午 9:50
 */
@Setter
@Getter
@Entity
@Table
@DynamicUpdate
@ApiModel(value = "商品类")
public class Commodity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1620243840780887725L;

    @ApiModelProperty(value="商品所属类目")
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;

    @Column(name = "name",nullable = false,columnDefinition = "varchar(350) COMMENT '商品名'")
    @ApiModelProperty(value="商品名",name="name",example="家用洗洁精")
    private String name;

    @Column(name = "price",nullable = false,columnDefinition = "decimal(10,2) COMMENT '单价'")
    @ApiModelProperty(value="单价",name="price",example="12.50")
    private BigDecimal price;

    @ApiModelProperty(value="库存",name="stock",example="100")
    private Long stock;

    @OneToMany(targetEntity =CommodityPicture.class,fetch = FetchType.EAGER,mappedBy = "commodity",cascade = CascadeType.ALL)
    private Set<CommodityPicture> commodityPictureSet = new HashSet<>();

   @Transient
   private Integer page;

   @Transient
   private Integer size;

    @Override
    public String toString() {
        return "Commodity{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }


}
