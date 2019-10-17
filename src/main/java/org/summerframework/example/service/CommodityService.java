package org.summerframework.example.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summerframework.example.entity.Category;
import org.summerframework.example.entity.Commodity;
import org.summerframework.example.entity.CommodityPicture;
import org.summerframework.example.repository.CategoryRepository;
import org.summerframework.example.repository.CommodityPictureRepository;
import org.summerframework.example.repository.CommodityRepository;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.SetUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/15 0015 上午 10:14
 */
@Service
@Slf4j
public class CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private CommodityPictureRepository commodityPictureRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Object saveOne(Commodity commodity) {
        commodityRepository.save(commodity);
        return "SUCCESS";
    }

    @Transactional
    public Object saveOneCommodityAndManyPicture(Commodity commodity) {
        Category category = categoryRepository.getOne(commodity.getCategory().getId());
        if (null == category) {
            log.info("类目不存在！");
            return null;
        }
        log.info("类目名称："+category.getCategoryName());
        if(commodity.getId() != null){
            Date createTime = commodityRepository.getOne(commodity.getId()).getCreateTime();
            commodity.setCreateTime(createTime);
        }

        Set<CommodityPicture> set = new HashSet<>(commodity.getCommodityPictureSet());
        if(!SetUtils.isEmpty(set)){
            set.forEach(f->{
                log.info("commodityPicture对象：" + f);
                f.setCommodity(commodity);

            });
        }
        commodity.setCategory(category);  //商品所属类目
        commodityRepository.save(commodity);    //这里出现序列化异常的问题
        return  "SUCCESS";
    }

    public Object saveCategory(Category category) {
        categoryRepository.save(category);
        return "SUCCESS";
    }

    public Commodity findOneCommodityById(Long id) {
        Commodity commodity = commodityRepository.findOne(new Specification<Commodity>() {
            @Override
            public Predicate toPredicate(Root<Commodity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("id").as(Long.class), id);
            }
        }).orElse(new Commodity());
        log.info("commodity:" + commodity);
        log.info("commodity.getCategory().getCategoryName()" + commodity.getCategory().getCategoryName());
        return commodity;
    }

    public Object object(Commodity commodity) {
        log.info("page:" + commodity.getPage() + "行" + commodity.getSize() + "条");
        PageRequest pageRequest = new PageRequest(commodity.getPage() - 1, commodity.getSize(), Sort.Direction.DESC, "id");
        Page p = commodityRepository.findAll(new Specification<Commodity>() {
            @Override
            public Predicate toPredicate(Root<Commodity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(commodity.getName())) {
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + commodity.getName() + "%"));
                }
                if (null != commodity.getId()) {
                    predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), commodity.getId()));
                }
                if (null != commodity.getCategory()) {
                    if(commodity.getCategory().getId()!= null){
                        predicates.add(criteriaBuilder.equal(root.get("category"), commodity.getCategory().getId()));
                        log.info("commodity.getCategory().getId(): " + commodity.getCategory().getId());
                    }
                }
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        }, pageRequest);
        return p;
    }


    @Transactional
   public String deleteCommodity(Long id){
        log.info("请求参数>>>>id："+id);
        //根据id查找该商品
        Commodity commodity = commodityRepository.getOne(id);
        commodityRepository.delete(commodity);
        log.info("对象信息>>>>commodity"+commodity);
        log.info("commodity对象关联的分类信息:"+commodity.getCategory());
        Set<CommodityPicture> commodityPictureSets = commodity.getCommodityPictureSet();
        if(!SetUtils.isEmpty(commodityPictureSets)){
            commodityPictureSets.forEach(f->{
                log.info("commodity对象关联的图片信息："+f);
            });
            commodityPictureRepository.deleteInBatch(commodityPictureSets);
        }
        return "请查看数据库";

   }

    @Transactional
   public  String deleteCommodityAndPictureById(Long id){
        commodityRepository.deleteCommodityByIdIs(id);
        commodityPictureRepository.deleteCommodityPicturesByCommodityId(id);
        return  "商品图片（）";
   }

   @Transactional
   public  String update(Commodity commodity){
        Commodity commodityModel = commodityRepository.getOne(commodity.getId());
        if(null == commodityModel){
            return "未找到";
        }
       Category category = categoryRepository.getOne(commodity.getCategory().getId());
       if (null == category) {
           log.info("类目不存在！");
           return "类目不存在！";
       }
       commodity.setCategory(category);
       Set<CommodityPicture> set = new HashSet<>(commodity.getCommodityPictureSet());
       if(!SetUtils.isEmpty(set)){
           commodityPictureRepository.deleteCommodityPicturesByCommodityId(commodity.getId());
           set.forEach(f->{
               log.info("commodityPicture对象：" + f);
               f.setCommodity(commodity);
           });
       }
       commodityRepository.saveAndFlush(commodity);
        return  "SUCCESS";
   }

}
