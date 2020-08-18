package com.plat.webm.news.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plat.webm.exception.Result;
import com.plat.webm.news.entity.News;
import com.plat.webm.news.service.INewsService;

import com.plat.webm.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import io.swagger.annotations.*;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author swx
 * @since 2020-08-05
 */
@Api(value = "新闻资讯Controller", tags = {"新闻资讯接口"})
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    INewsService newsService;

    //{"size":3,"current":0,"title":"","newsType":"1"}
    @ApiOperation(value = "新闻分页查询", notes = "参数格式的例子：{\"size\":3,\"current\":0,\"title\":\"\",\"newsType\":\"1\"}" +
            " 其中，如果没有newsType参数时，为所有新闻资讯类型的查询结果", httpMethod = "POST")
    @RequestMapping(value = "/SingleTableSelect", method = RequestMethod.POST)
    public Result SingleTableSelect(@RequestBody Map<String, Object> body, HttpServletRequest httpServletRequest) throws Exception {

        Page<News> page = new Page<>();
        page.setSize(Integer.parseInt(body.get("size").toString()));
        page.setCurrent(Integer.parseInt(body.get("current").toString()));

        QueryWrapper<News> ew = new QueryWrapper<>();

        String title="";
        if(body.containsKey("title")){
            title=body.get("title").toString();
        }
        int newsType=0;
        if(body.containsKey("newsType")){
            newsType=Integer.parseInt(body.get("newsType").toString());

            ew.select("*")
                    .like("title", "%"+title+"%") //模糊查询
                    .eq("news_type", newsType)  //精确查询
                    .eq("status", 1)
                    .orderBy(false, false,"createtime");
        }else{
            ew.select("*")
                    .like("title", "%"+title+"%") //模糊查询
                    .eq("status", 1)
                    .orderBy(false, false,"createtime");
        }


        Page<News> postList = newsService.page(page,ew);
        String js = JSONArray.toJSONString(postList);

        return Result.ok(js, "查询成功");
    }
    @ApiOperation(value = "保存或更新", notes = "参数格式json ；如果是添加数据时，id必须为空值", httpMethod = "POST")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody News body, HttpServletRequest httpServletRequest){

        newsService.saveOrUpdate(body);
        return Result.ok("添加成功");
    }
    @ApiOperation(value = "删除", notes = "主键参数id的数据类型是String", httpMethod = "POST")
    @PostMapping("/deleteRecord")
    public Result deleteRecord(@RequestBody String id, HttpServletRequest httpServletRequest){

        if(newsService.removeById(id)) {
            return Result.ok("删除成功");
        }else{
            return Result.ok("无记录");
        }
    }


}
