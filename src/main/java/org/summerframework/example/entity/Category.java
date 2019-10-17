package org.summerframework.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.summerframework.example.root.BaseEntity;

import javax.persistence.*;
import java.util.*;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/15 0015 下午 1:41
 */

@ApiModel(value = "商品所属类目")
@Setter
@Getter
@Entity
@Table
public class Category extends BaseEntity {

    private static final long serialVersionUID = 1620243840780887765L;

    @Column(name ="category_name",nullable = false,columnDefinition =  "varchar(350) COMMENT '商品类目名称'")
    private String categoryName;

    @JsonBackReference
    @OneToMany(targetEntity = Commodity.class,mappedBy ="category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Commodity> commodities = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(categoryName, category.categoryName) &&
                Objects.equals(commodities, category.commodities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), categoryName, commodities);
    }
}
