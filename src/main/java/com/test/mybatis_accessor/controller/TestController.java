package com.test.mybatis_accessor.controller;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.relation.EntityDtoServiceRelation;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/mvc_test")
public class TestController {

    protected ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private MybatisAccessorService mybatisAccessorService;

    private boolean getBoolean(Boolean value) {
        if(value == null) {
            return false;
        }
        return value;
    }

    protected Collection convertFromList(List mapList, Class clazz) {
        Class actualClass = mapList.get(0).getClass();
        if(!Map.class.isAssignableFrom(actualClass)) {
            return mapList;
        }
        List<Object> result = new ArrayList<>();
        for(Object map : mapList) {
            result.add(objectMapper.convertValue(map, clazz));
        }
        return result;

    }

    public Object convertFromMap(Object object, Class clazz) {
        if(object == null) {
            return null;
        } else if(object instanceof Map){
            return objectMapper.convertValue(object, clazz);
        } else if(object instanceof List){
            return this.convertFromList((List) object, clazz);
        } else if(object instanceof Collection){
            return this.convertFromList(new ArrayList((Collection) object), clazz);
        }
        return object;
    }

    /*
     *** 通过ID获取实体类，转换成dto后返回
     *** 指定sub_entities参数可返回关联的子项
     */
    @GetMapping("/entity/{dto_name}/{id}")
    public Object testGetById(@PathVariable("dto_name") String dto_name
            , @PathVariable("id") String id
            , @RequestParam(value = "sub_entities", required = false) String[] sub_entities) throws MybatisAccessorException {
        return mybatisAccessorService.getDtoById(dto_name , id, false, Arrays.asList(sub_entities));
    }

    /*
     *** 读取dto中QueryField注解信息，组装成查询条件后查询实体列表，转化dto列表后返回
     *** page_index、page_size指定分页信息
     */
    @PostMapping("/annotation/entities/{dto_name}")
    public Object testGetPages(@PathVariable("dto_name") String dto_name
            , @RequestParam(value = "page_index", required = false) Integer pageIndex
            , @RequestParam(value = "page_size", required = false) Integer pageSize
            , @RequestBody(required = true) Map map) throws MybatisAccessorException {
        EntityDtoServiceRelation relation = mybatisAccessorService.getRelation(null, dto_name);
        Object object = convertFromMap(map, relation.getDtoClass());
        if(pageIndex == null || pageSize == null) {
            return mybatisAccessorService.getDtoListByAnnotation(object);
        }
        return mybatisAccessorService.getDtoPageByAnnotation(object , pageIndex, pageSize);
    }

    /*
     *** 通过ID删除指定实体
     *** 如果在mybatis-plus开启逻辑删除后，仍要执行物理删除，可将physic_delete置为true
     */
    @DeleteMapping("/entity/{dto_name}/{id}")
    public Object testDeleteById(@PathVariable("dto_name") String dto_name
            , @RequestParam(value = "include_all_children", required = false) Boolean includeAllChildren
            , @RequestParam(value = "children", required = false) String[] children
            , @PathVariable("id") String id
            , @RequestParam(value = "update_children_only", required = false) Boolean updateChildrenOnly) throws MybatisAccessorException {
        return mybatisAccessorService.deleteByIds(dto_name,Collections.singleton(id), includeAllChildren, Arrays.asList(children), getBoolean(updateChildrenOnly), null);
    }

    /*
     *** 通过ID列表删除多个实体
     *** 如果在mybatis-plus开启逻辑删除后，仍要执行物理删除，可将physic_delete置为true
     */
    @DeleteMapping("/entities/{dto_name}")
    public Object testDeleteByIds(@PathVariable("dto_name") String dto_name
            , @RequestParam(value = "include_all_children", required = false) Boolean includeAllChildren
            , @RequestParam(value = "children", required = false) String[] children
            , @RequestBody Set<Serializable> ids
            , @RequestParam(value = "update_children_only", required = false) Boolean updateChildrenOnly) throws MybatisAccessorException {
        return mybatisAccessorService.deleteByIds(dto_name, ids, includeAllChildren, Arrays.asList(children), getBoolean(updateChildrenOnly), null);
    }

    /*
     *** 保存一个实体
     */
    @PostMapping("/entity/{dto_name}")
    public Object testSaveEntity(@PathVariable("dto_name") String dto_name
            , @RequestBody Map map
            , @RequestParam(value = "sub_entities", required = false) String[] children
            , @RequestParam(value = "update_children_only", required = false) Boolean updateChildrenOnly) throws MybatisAccessorException {
        EntityDtoServiceRelation relation = mybatisAccessorService.getRelation(null, dto_name);
        Object object = convertFromMap(map, relation.getDtoClass());
        return mybatisAccessorService.save(object, false, Arrays.asList(children), getBoolean(updateChildrenOnly), null);
    }

    /*
     *** 保存多个实体
     */
    @PostMapping("/entities/{dto_name}")
    public Object testSaveEntities(@PathVariable("dto_name") String dto_name
            , @RequestBody List<Object> mapList
            , @RequestParam(value = "sub_entities", required = false) String[] children
            , @RequestParam(value = "update_children_only", required = false) Boolean updateChildrenOnly) throws MybatisAccessorException {
        EntityDtoServiceRelation relation = mybatisAccessorService.getRelation(null, dto_name);
        List object = (List) convertFromMap(mapList, relation.getDtoClass());

        return mybatisAccessorService.saveList(object, false, Arrays.asList(children), getBoolean(updateChildrenOnly), null);
    }

    /*
     *** 修改一个实体
     *** 指定sub_entities，可将关联的子项实体删除并重新插入新的记录
     */
    @PutMapping("/entity/{dto_name}")
    public Object testUpdate(@PathVariable("dto_name") String dto_name
            , @RequestParam(value = "children", required = false) String[] children
            , @RequestBody Map map
            , @RequestParam(value = "update_children_only", required = false) Boolean updateChildrenOnly) throws MybatisAccessorException {

        EntityDtoServiceRelation relation = mybatisAccessorService.getRelation(null, dto_name);
        Object object = convertFromMap(map, relation.getDtoClass());

        return mybatisAccessorService.update(object, false, Arrays.asList(children)
                , getBoolean(updateChildrenOnly), null);
    }


    /*
     *** 修改多个实体
     *** 指定sub_entities，可将关联的子项实体删除并重新插入新的记录
     */
    @PutMapping("/entities/{dto_name}")
    public Object testUpdateEntities(@PathVariable("dto_name") String dto_name
            , @RequestParam(value = "children", required = false) String[] children
            , @RequestBody List<Object> mapList
            , @RequestParam(value = "update_children_only", required = false) Boolean updateChildrenOnly) throws MybatisAccessorException {

        EntityDtoServiceRelation relation = mybatisAccessorService.getRelation(null, dto_name);
        List object = (List) convertFromMap(mapList, relation.getDtoClass());

        return mybatisAccessorService.updateList(object, false, Arrays.asList(children)
                , getBoolean(updateChildrenOnly), null);
    }

}
