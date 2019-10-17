package org.summerframework.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.summerframework.example.entity.CommodityPicture;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/15 0015 上午 11:03
 */

public interface CommodityPictureRepository extends JpaRepository<CommodityPicture,Long> , JpaSpecificationExecutor<CommodityPicture> {

/*    @Transactional
    @Modifying
    @Query(value = "delete from commodity_picture where commodityId = ?1")
     void deleteCommodityPictureByCommodityId(Long commodityId);*/
    @Modifying
    @Query(value = " delete  from  commodity_picture where  commodity_id =?1"  ,nativeQuery = true)
    void deleteCommodityPicturesByCommodityId(Long commodityId);
}
