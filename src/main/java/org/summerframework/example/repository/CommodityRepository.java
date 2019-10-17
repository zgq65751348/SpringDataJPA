package org.summerframework.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.summerframework.example.entity.Commodity;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/15 0015 上午 10:12
 */

public interface CommodityRepository extends JpaRepository<Commodity,Long>, JpaSpecificationExecutor<Commodity>, CrudRepository<Commodity,Long> {


    @Modifying
    @Query(value = "delete from commodity where id = ?1",nativeQuery = true)
    void deleteCommodityByIdIs(Long id);

}
