package org.summerframework.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.summerframework.example.entity.Category;
import org.summerframework.example.entity.Commodity;
import org.summerframework.example.service.CommodityService;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/15 0015 上午 10:17
 */

@Api(description = "商品管理")
@RestController
@RequestMapping(value = "/index")
@Slf4j
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation(value = "单一保存一个商品",notes = "null",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "商品名",required = false,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name="price",value = "商品单价",required = false,dataType = "double",paramType = "query"),
            @ApiImplicitParam(name="stock",value = "商品库存",required = false,dataType = "int",paramType = "query"),
    })
    @PostMapping(value = "/save/one")
    public Object saveOne(@RequestBody Commodity commodity){
        return commodityService.saveOne(commodity);
    }


    @ApiOperation(value = "保存一个商品和几张图片",notes = "null",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "商品名",required = false,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name="price",value = "商品单价",required = false,dataType = "double",paramType = "query"),
            @ApiImplicitParam(name="stock",value = "商品库存",required = false,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name="commodityPictureSet",value = "商品图片",required = false,dataType = "string",paramType = "query"),
    })
    @PostMapping(value = "/save/one/and/many")
    public Object saveOneCommodityAndManyPicture(@RequestBody Commodity commodity){
        log.info("commodity:"+commodity);
        log.info("commodity.getCommodityPictureSet()"+commodity.getCommodityPictureSet().size());
       return  commodityService.saveOneCommodityAndManyPicture(commodity);
    }

    @ApiOperation(value = "保存一个商品类目",notes = "null",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name="categoryName",value = "类目名称",required = false,dataType = "string",paramType = "query"),
    })
    @PutMapping(value = "/save/category")
    public Object saveCategory(@RequestBody Category category){
        return commodityService.saveCategory(category);
    }

    @ApiOperation(value = "更据商品编号查训商品",notes = "null",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "商品标号",required = false,dataType = "long",paramType = "query"),
    })
    @GetMapping(value = "/query/commodity/byId")
    @ResponseBody
    public Commodity   findOneCommodity(Long id) throws JsonProcessingException {
        log.info("id="+id);
        log.info("log:"+objectMapper.writeValueAsString(commodityService.findOneCommodityById(id)));
        return commodityService.findOneCommodityById(id) ;
    }

    @ApiOperation(value = "分页排序查训商品",notes = "null",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "商品编号",required = false,dataType = "long",paramType = "query"),
            @ApiImplicitParam(name="name",value = "商品名称",required = false,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name="category.id",value = "商品类目",required = false,dataType = "long",paramType = "query"),
            @ApiImplicitParam(name="page",value = "行",required = false,dataType = "long",paramType = "query"),
            @ApiImplicitParam(name="size",value = "条",required = false,dataType = "long",paramType = "query"),
    })
    @GetMapping(value = "/query/commodity/page/sort")
    public Object PageData(@RequestBody Commodity commodity){
        return commodityService.object(commodity);
    }

    @ApiOperation(value = "更据商品编号删除商品",notes = "null",httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "商品编号",required = false,dataType = "long",paramType = "query")
    })
    @DeleteMapping(value = "/delete/commodity/byId")
    public String deleteCommodityById(Long id){
        return  commodityService.deleteCommodity(id);
    }

    @ApiOperation(value = "更据商品编号删除商品",notes = "null",httpMethod = "POST")
    @PostMapping(value = "/delete/CommodityAndPicture/byID")
    public  String deleteCommodityAndPicture(Long id){
        return  commodityService.deleteCommodityAndPictureById(id);
    }

    @ApiOperation(value = "更据商品编号修改商品",notes = "null",httpMethod = "POST")
    @PostMapping(value = "/update/CommodityAndPicture/byID")
    public  String updateCommodityAndPicture(@RequestBody Commodity commodity){
        return  commodityService.update(commodity);
    }
}
