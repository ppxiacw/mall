package com.atguigu.gulimall.product;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 1、引入oss-starter
 * 2、配置key，endpoint相关信息即可
 * 3、使用OSSClient 进行相关操作
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;


    @Autowired
    CategoryService categoryService;

    @Test
    public void testFindPath(){
        Long[] catelogPath = categoryService.findCatelogPath(225L);
        log.info("完整路径：{}",Arrays.asList(catelogPath));
    }


    @Test
    public void contextLoads() {
        List<CategoryEntity> cat_level = categoryService.getBaseMapper().selectList(new QueryWrapper<CategoryEntity>().eq("cat_level", 1));
        List<CategoryEntity> all = categoryService.getBaseMapper().selectList(null);
        cat_level.stream().map(categoryEntity -> {
            categoryEntity.setChildren(getChildrens(categoryEntity,all));
            return categoryEntity;
        }).collect(Collectors.toList());
        for (int i = 0; i < cat_level.size(); i++) {
            System.out.println(cat_level.get(i));
        }
    }

    //我的-------递归查找所有菜单的子菜单
    private List<CategoryEntity> MyGetChildrens(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> collect = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(item -> {
            item.setChildren(getChildrens(item, all));
            return item;
        }).collect(Collectors.toList());
        return collect;
    }

    //递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){

        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            //1、找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2)->{
            //2、菜单的排序
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }

    @Test
    public  void  map(){
        ArrayList<String> strings = new ArrayList<>();
//        strings.add("1");
//        strings.add("2");
//        strings.add("3");
        strings.stream().filter(item->{
            System.out.println("我");
            System.out.println(item.toString());
            return true;
        }).collect(Collectors.toList());
    }

    @Test
    public void test(){
        ArrayList<Integer>  arr= new ArrayList<Integer>();
        arr.add(1);
        arr.add(3);
        arr.add(2);
        arr.add(5);

        Stream<Integer> sorted = arr.stream().sorted((item1, item2) -> {
            return item1 - item2;
        });
        sorted.forEach(item->{
            System.out.println(item);
        });
    }

}
