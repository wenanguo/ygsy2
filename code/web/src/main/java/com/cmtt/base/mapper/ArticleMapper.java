package com.cmtt.base.mapper;

import com.cmtt.base.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 发布号作者表 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-28
 */
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("select * from Article")
    Article getArticleOne();

}
