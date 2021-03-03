package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.miniappparams.PageInputParam;
import com.cmtt.base.controller.miniappparams.SsOpinionsParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.utils.CommonUtils;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import com.cmtt.base.service.ISsOpinionsService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-16
 */
@RestController
@RequestMapping("/api/base/ss-opinions")
public class SsOpinionsController {

private final Logger logger = LoggerFactory.getLogger(SsOpinionsController.class);

@Autowired
public ISsOpinionsService ssOpinionsService;


/**
* 分页获取列表
*/
    @GetMapping("/list")
    @ResponseBody
    public R list(SsOpinions ssOpinions) {

    try {

        // 构建分页类
        IPage<SsOpinions> ssOpinionsPage = new Page<>(ssOpinions.getPageNo(), ssOpinions.getPageSize());

        // 构造查询及排序方式
        QueryWrapper<SsOpinions> queryWrapper = new QueryWrapper<>(ssOpinions);
        queryWrapper.isNull("parentId");
        queryWrapper.orderBy(true, ssOpinions.getIsAsc(), ssOpinions.getIsSortField());

            // 执行查询
            ssOpinionsPage = ssOpinionsService.page(ssOpinionsPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssOpinionsPage);


            } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
            }
    }



    @GetMapping("/listByCustomerId.app")
    @ResponseBody
    @ApiOperation("根据用户id查询意见反馈")
    public R listByCustomerId(SsOpinions ssOpinions, Principal principal, HttpServletRequest httpServletRequest) {

        try {

            String phoneType = httpServletRequest.getHeader("platform"); //判断是后端代码还是小程序

            // 构建分页类
            IPage<SsOpinions> ssOpinionsPage = new Page<>(ssOpinions.getPageNo(), ssOpinions.getPageSize());
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }

            // 构造查询及排序方式
//            LambdaQueryWrapper<SsOpinions> queryWrapper = Wrappers.<SsOpinions>lambdaQuery()
//                    .eq(SsOpinions::getCustomerId, sysUser.getWxOpenid());
            QueryWrapper<SsOpinions> queryWrapper = new QueryWrapper<>(ssOpinions);
            if("miniprogram".equals(phoneType)){//小程序
                queryWrapper.eq("customerId",sysUser.getId());
            }

            if(ssOpinions.getStatus()!=null){
                queryWrapper.eq("status",ssOpinions.getStatus());
            }


            queryWrapper.orderBy(true, ssOpinions.getIsAsc(), ssOpinions.getIsSortField());

            // 执行查询
            ssOpinionsPage = ssOpinionsService.getAllOpinions(ssOpinionsPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssOpinionsPage);



        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

    @GetMapping("/listReplyByCustomerId.app")
    @ResponseBody
    @ApiOperation("根据用户意见id查询意见回复")
    public R listReplyByCustomerId(SsOpinions ssOpinions,Principal principal) {

        try {

            // 构建分页类
            IPage<SsOpinions> ssOpinionsPage = new Page<>(ssOpinions.getPageNo(), ssOpinions.getPageSize());
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }

            // 构造查询及排序方式
            LambdaQueryWrapper<SsOpinions> queryWrapper = Wrappers.<SsOpinions>lambdaQuery()
                    .eq(SsOpinions::getParentId, ssOpinions.getId());

            return R.ok().setResult(ssOpinionsService.page(ssOpinionsPage,queryWrapper));


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
        public R add(@RequestBody @Validated({GroupAdd.class})SsOpinions ssOpinions) {

        try {
        ssOpinionsService.save(ssOpinions);

        return R.ok().setMessage("新增成功");

        } catch (Exception e) {
        logger.warn(e.getMessage());

        return R.err().setMessage("新增失败");
        }


        }


    /**
     * 新增
     */
    @PostMapping("/addOpinions.app")
    @ResponseBody
    @ApiOperation("意见反馈新增接口")
    public R addOpinions(@RequestBody @Validated({GroupAdd.class}) SsOpinionsParam ssOpinionsParam, Principal principal) {

        try {
            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }

            Integer CustomerId = sysUser.getId();

            SsOpinions ssOpinions = new SsOpinions();

            BeanUtils.copyProperties(ssOpinionsParam,ssOpinions);

            LocalDateTime createTime = LocalDateTime.now(); //创建时间

            ssOpinions.setCustomerId(CustomerId+"");
            ssOpinions.setCreateTime(createTime);
            ssOpinions.setContent(ssOpinionsParam.getContent());
            ssOpinions.setOpinionType(ssOpinionsParam.getOpinionType());
            ssOpinions.setStatus(1);
            ssOpinionsService.save(ssOpinions);

            return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }
    }



    /**
     * 新增普通意见回复
     */
    @PostMapping("/addReply")
    @ResponseBody
    @ApiOperation("普通意见回复")
    public R addReply (Principal principal,@RequestBody @Validated({GroupAdd.class})SsOpinions ssOpinion) {

        try {

            SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

            if(sysUser==null){
                return R.err();
            }
            Integer CustomerId = sysUser.getId();
           // String id = String.valueOf(System.currentTimeMillis()/ 1000000); //
         //   String id = CommonUtils.getDateNo(1);
            SsOpinions ssOpinions = new SsOpinions();
            LocalDateTime createTime = LocalDateTime.now(); //创建时间
           // ssOpinions.setId(Integer.parseInt(id));
            ssOpinions.setParentId(ssOpinion.getId());
            ssOpinions.setCreateTime(createTime);
            ssOpinions.setOpinionType("1");
            ssOpinions.setContent(ssOpinion.getContent());
            ssOpinions.setReplayId(CustomerId+"");
            ssOpinionsService.saveOpinionById(ssOpinions);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})SsOpinions ssOpinions) {


        try {

        ssOpinionsService.updateById(ssOpinions);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})SsOpinions ssOpinions) {

        try {

        ssOpinionsService.removeById(ssOpinions.getId());

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

            ssOpinionsService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }

}
