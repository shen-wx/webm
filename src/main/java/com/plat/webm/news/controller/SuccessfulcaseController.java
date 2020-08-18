package com.plat.webm.news.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plat.webm.exception.Result;
import com.plat.webm.news.entity.Product;
import com.plat.webm.news.entity.Successfulcase;
import com.plat.webm.news.service.IProductService;
import com.plat.webm.news.service.ISuccessfulcaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.T;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author swx
 * @since 2020-08-06
 */
@Api(value = "成功案例Controller", tags = {"成功案例接口"})
@RestController
@RequestMapping("/news/successfulcase")
public class SuccessfulcaseController {
    @Autowired
    ISuccessfulcaseService successfulcaseService;

    //{"size":3,"current":0,"title":"","successfulcase_type":"1"}
    @ApiOperation(value = "产品分页查询", notes = "参数格式的例子：{\"size\":3,\"current\":0,\"title\":\"\",\"successfulcase_type\":\"1\"}"+
            " 其中，如果没有successfulcase_type参数时，为所有成功案例类型的查询结果", httpMethod = "POST")
    @RequestMapping(value = "/SingleTableSelect", method = RequestMethod.POST)
    public Result SingleTableSelect(@RequestBody Map<String, Object> body, HttpServletRequest httpServletRequest) throws Exception {

        Page<Map<String, Object>> page = new Page<>();
        page.setSize(Integer.parseInt(body.get("size").toString()));
        page.setCurrent(Integer.parseInt(body.get("current").toString()));

        QueryWrapper<Successfulcase> ew = new QueryWrapper<>();
        String title="";
        if(body.containsKey("title")){
            title=body.get("title").toString();
        }
        int successfulcaseType=0;
        if(body.containsKey("successfulcaseType")){
            successfulcaseType=Integer.parseInt(body.get("successfulcaseType").toString());
            ew.select("*")
                    .like("title", "%"+title+"%") //模糊查询
                    .eq("status", 1)
                    .eq("successfulcase_type", successfulcaseType)  //精确查询
                    .orderBy(false, false,"createtime");
        }else {
            ew.select("*")
                    .like("title", "%" + title + "%") //模糊查询
                    .eq("status", 1)
                    .orderBy(false, false, "createtime");
        }

        Page<Map<String, Object>> postList = successfulcaseService.pageMaps(page,ew);
        String js = JSONArray.toJSONString(postList);
        //System.out.println(js);
        return Result.ok(js, "查询成功");
    }


    public void selectMapsPage() {
        LambdaQueryWrapper<Successfulcase> query = new LambdaQueryWrapper<>();
        query.ge(Successfulcase::getTitle,26).orderByDesc(Successfulcase::getTitle);

        IPage<Map<String, Object>> page = new Page<> (1,2);
        IPage<Map<String, Object>>  iPage = successfulcaseService.pageMaps(page,query);


        System.out.println("总页数："+page.getPages());
        System.out.println("总记录数："+iPage.getTotal());
        List<Map<String,Object>> list = iPage.getRecords();
        list.forEach(System.out::println);
    }

    @ApiOperation(value = "保存或更新", notes = "参数格式json ；如果是添加数据时，id必须为空值", httpMethod = "POST")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Successfulcase body, HttpServletRequest httpServletRequest){

        successfulcaseService.saveOrUpdate(body);
        return Result.ok("添加成功");
    }
    @ApiOperation(value = "删除", notes = "主键参数id的数据类型是String", httpMethod = "POST")
    @PostMapping("/deleteRecord")
    public Result deleteRecord(@RequestBody String id, HttpServletRequest httpServletRequest){

        if(successfulcaseService.removeById(id)) {
            return Result.ok("删除成功");
        }else{
            return Result.ok("无记录");
        }
    }
}
