package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.utils.RC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.cmtt.base.entity.Article;
import com.cmtt.base.service.IArticleService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 发布号作者表 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/api/base/article")
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);



    @Autowired
    public IArticleService articleService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(Article article) {

        try {

// 构建分页类
            IPage<Article> articlePage = new Page<>(article.getPageNo(), article.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<Article> queryWrapper = new QueryWrapper<>(article);

            queryWrapper.orderBy(true, article.getIsAsc(), article.getIsSortField());




            // 执行查询
            articlePage = articleService.page(articlePage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(articlePage);


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }




    /**
     * 新增
     */
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody @Validated({GroupAdd.class}) Article article) {

        try {
            articleService.save(article);

            return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }


    }


    /**
     * 修改
     */
    @PutMapping("/edit")
    @ResponseBody
    public R edit(@RequestBody @Validated({GroupEdit.class}) Article article) {


        try {

            articleService.updateById(article);

            return R.ok().setMessage("修改成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("修改失败");
        }
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ResponseBody
    public R delete(@RequestBody @Validated({GroupDelete.class}) Article article) {

        try {

            //articleService.removeById(article.getId());
            article.setStatus(RC.B_DELETE.code());
            articleService.updateById(article);

            return R.ok().setMessage("删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("删除失败");
        }
    }


    /**
     * 删除
     */
    @DeleteMapping("/batchDelete")
    @ResponseBody
    public R batchDelete(@RequestBody List<Integer> ids) {
        try {

            articleService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }


}
