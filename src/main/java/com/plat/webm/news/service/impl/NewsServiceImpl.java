package com.plat.webm.news.service.impl;

import com.plat.webm.news.entity.News;
import com.plat.webm.news.mapper.NewsMapper;
import com.plat.webm.news.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author swx
 * @since 2020-08-05
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
