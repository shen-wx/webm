package com.plat.webm.news.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plat.webm.exception.Result;
import com.plat.webm.news.entity.Product;
import com.plat.webm.news.entity.Successfulcase;
import com.plat.webm.news.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author swx
 * @since 2020-08-06
 */
@Api(value = "产品Controller", tags = {"产品接口"})
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;

    //{"size":3,"current":0,"title":"","productType":"1"}
    @ApiOperation(value = "产品分页查询", notes = "参数格式的例子：{\"size\":3,\"current\":0,\"title\":\"\",\"productType\":\"1\"}" +
            " 其中，如果没有productType参数时，为所有产品类型的查询结果", httpMethod = "POST")
    @RequestMapping(value = "/SingleTableSelect", method = RequestMethod.POST)
    public Result SingleTableSelect(@RequestBody Map<String, Object> body, HttpServletRequest httpServletRequest) throws Exception {

        Page<Product> page = new Page<>();
        page.setSize(Integer.parseInt(body.get("size").toString()));
        page.setCurrent(Integer.parseInt(body.get("current").toString()));

        QueryWrapper<Product> ew = new QueryWrapper<>();
        String title="";
        if(body.containsKey("title")){
            title=body.get("title").toString();
        }
        int productType=0;
        if(body.containsKey("newsType")){
            productType=Integer.parseInt(body.get("productType").toString());
            ew.select("*")
                    .like("title", "%"+title+"%") //模糊查询
                    .eq("product_type", productType)  //精确查询
                    .eq("status", 1)
                    .orderBy(false, false,"createtime");
        }else{
            ew.select("*")
                    .like("title", "%"+title+"%") //模糊查询
                    .eq("status", 1)
                    .orderBy(false, false,"createtime");
        }


        Page<Product> postList = productService.page(page,ew);
        String js = JSONArray.toJSONString(postList);

        return Result.ok(js, "查询成功");
    }
    @ApiOperation(value = "保存或更新", notes = "参数格式json ；如果是添加数据时，id必须为空值", httpMethod = "POST")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Product body, HttpServletRequest httpServletRequest){

        productService.saveOrUpdate(body);
        return Result.ok("添加成功");
    }
    @ApiOperation(value = "删除", notes = "主键参数id的数据类型是String", httpMethod = "POST")
    @PostMapping("/deleteRecord")
    public Result deleteRecord(@RequestBody String id, HttpServletRequest httpServletRequest){

        if(productService.removeById(id)) {
            return Result.ok("删除成功");
        }else{
            return Result.ok("无记录");
        }
    }
}
