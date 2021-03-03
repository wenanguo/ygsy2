package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.controller.miniappparams.GoodsInputParam;
import com.cmtt.base.controller.miniappparams.GoodsPageInputParam;
import com.cmtt.base.controller.miniappparams.PageInputParam;
import com.cmtt.base.entity.*;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ISsGoodsTagService;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.cmtt.base.service.ISsGoodsInfoService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-01-29
 */
@RestController
@RequestMapping("/api/base/ss-goods-info")
@Api(tags = "商品相关")
public class SsGoodsInfoController {

    private final Logger logger = LoggerFactory.getLogger(SsGoodsInfoController.class);

    @Autowired
    public ISsGoodsInfoService ssGoodsInfoService;

    @Autowired
    public ISsGoodsTagService ssGoodsTagService;


    /**
     * 分页获取列表
     */
    @GetMapping("/list")
    @ResponseBody
    public R list(SsGoodsInfo ssGoodsInfo) {

        try {
            //获取查询条件，然后置空，防止框架因为有值而做等值查询——而这里需要做like查询
            String goodsName = ssGoodsInfo.getGoodsName();
            Integer goodsTypeId = ssGoodsInfo.getGoodsTypeId();
            ssGoodsInfo.setGoodsName(null);
            ssGoodsInfo.setGoodsTypeId(null);
            // 构建分页类
            IPage<SsGoodsInfo> ssGoodsInfoPage = new Page<>(ssGoodsInfo.getPageNo(), ssGoodsInfo.getPageSize());

            // 构造查询及排序方式
            QueryWrapper<SsGoodsInfo> queryWrapper = new QueryWrapper<>(ssGoodsInfo);
            queryWrapper.orderBy(true, ssGoodsInfo.getIsAsc(), ssGoodsInfo.getIsSortField());
            if (goodsName != null && !goodsName.equals("")) {
                queryWrapper.like("goods_name", goodsName);
            }
            if (goodsTypeId != null) {
                queryWrapper.eq("goods_type_id", goodsTypeId);
            }
            // 执行查询
            ssGoodsInfoPage = ssGoodsInfoService.page(ssGoodsInfoPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssGoodsInfoPage);


        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

    /**
     * 小程序主页：分页获取列表，传入分页、每页数据大小和请求类型
     */
    @PostMapping("/goodsList.app")
    @ResponseBody
    @ApiOperation("获取小程序商品列表，根据请求类型返回三种结果：主页商品列表、水列表、水票列表")
    public R goodsList(@RequestBody @Valid GoodsPageInputParam params) {

        try {
            // 构建分页类
            IPage<SsGoodsInfo> ssGoodsInfoPage = new Page<>(params.getPageNo(), params.getPageSize());

            // 构造查询及排序方式
            // 默认获取主页商品列表，包括水和水票
/*
            LambdaQueryWrapper<SsGoodsInfo> queryWrapper = Wrappers.<SsGoodsInfo>lambdaQuery()
                    .orderByDesc(SsGoodsInfo::getSort)//序号降序
                    .orderByDesc(SsGoodsInfo::getIsrecommend)//推荐降序
                    .gt(SsGoodsInfo::getExpiredTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()))//失效时间之前
                    .lt(SsGoodsInfo::getPublishTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()))//发布时间之后
                    .eq(SsGoodsInfo::getStatus, RC.B_NORMAL.code());//状态是上架

            if(params.getType()==2){
                // type=2， 获取水列表
                queryWrapper.isNull(SsGoodsInfo::getTicketId); //水票ID为空，表示只获取水
            }else if(params.getType()==3){
                // type=3， 获取水票列表
                queryWrapper.isNotNull(SsGoodsInfo::getTicketId); //水票ID不为空，表示只获取水票
            }
            // 执行查询
            // ssGoodsInfoPage = ssGoodsInfoService.page(ssGoodsInfoPage, queryWrapper);
*/

            QueryWrapper<SsGoodsInfo> queryWrapper = new QueryWrapper<>();
            //queryWrapper.orderByDesc("a.sort", "a.isrecommend");//序号、推荐降序
            queryWrapper.orderByDesc("a.sort");//序号、推荐降序

            if (params.getType()!=null && params.getType() == 2) {
                // type=2， 获取水列表
                queryWrapper.isNull("ticket_id"); //水票ID为空，表示只获取水
            } else if (params.getType()!=null && params.getType() == 3) {
                // type=3， 获取水票列表
                queryWrapper.isNotNull("a.ticket_id"); //水票ID不为空，表示只获取水票
            }
            queryWrapper.gt("a.expired_time", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));//失效时间之前
            queryWrapper.lt("a.publish_time", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));//发布时间之后
            queryWrapper.eq("a.status", RC.B_NORMAL.code());//状态是上架

            // 执行查询
            ssGoodsInfoPage = ssGoodsInfoService.getGoodsPage(ssGoodsInfoPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssGoodsInfoPage);
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
    public R add(@RequestBody @Validated({GroupAdd.class}) SsGoodsInfo ssGoodsInfo, Principal principal, HttpServletRequest request) {
        try {
            SysUser sysUser = null;
            if(null != principal){
                sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
                ssGoodsInfo.setCreateBy(sysUser.getId());
            }
            ssGoodsInfo.setLastUpdateBy(sysUser.getId());
            ssGoodsInfoService.save(ssGoodsInfo);
            Integer goodsId = ssGoodsInfoService.getLastGoodsId();

            // 保存商品tag信息
            String idArr = request.getParameter("tagIdArray");
            if (null != idArr) {
                String[] idList = idArr.split(",");
                if (idList.length > 0) {
                    for (String tagId : idList) {
                        SsGoodsTag goodsTag = new SsGoodsTag();
                        goodsTag.setTagId(Integer.parseInt(tagId));
                        goodsTag.setGoodsId(goodsId);
                        ssGoodsTagService.save(goodsTag);
                    }
                } else {
                    return R.err().setMessage("未获取TagId，新增失败");
                }
            }
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
    public R edit(@RequestBody @Validated({GroupEdit.class}) SsGoodsInfo ssGoodsInfo, Principal principal, HttpServletRequest request) {
        try {
            SysUser sysUser = null;
            if(null != principal){
                sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
                ssGoodsInfo.setLastUpdateBy(sysUser.getId());
            }

            ssGoodsInfo.setCreateTime(null); // 置空，避免框架更新成当前时间
            ssGoodsInfoService.updateById(ssGoodsInfo);
            Integer goodsId = ssGoodsInfo.getId();

            // 保存商品tag信息
            String idArr = request.getParameter("tagIdArray");
            if (null != idArr) {
                String[] idList = idArr.split(",");
                if (idList.length > 0) {
                    int num = ssGoodsTagService.deleteByGoodsId(goodsId);
                    for (String tagId : idList) {
                        SsGoodsTag goodsTag = new SsGoodsTag();
                        goodsTag.setTagId(Integer.parseInt(tagId));
                        goodsTag.setGoodsId(goodsId);
                        goodsTag.setCreateTime(null);
                        ssGoodsTagService.save(goodsTag);
                    }
                } else {
                    return R.err().setMessage("未获取TagId，新增失败");
                }
            }

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
    public R delete(@RequestBody @Validated({GroupDelete.class}) SsGoodsInfo ssGoodsInfo) {

        try {

            ssGoodsInfoService.removeById(ssGoodsInfo.getId());

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

            ssGoodsInfoService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
        }
    }

    /**
     * 获取商品信息字典
     */
    @GetMapping("/goodsDict")
    @ResponseBody
    public R getGoodsInfoDict() {

        try {
            List<Map<Integer, String>> list = ssGoodsInfoService.getGoodsDict(RC.B_NORMAL.code());
            // 设置返回数据
            return R.ok().setResult(list);
        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

    /**
     * 小程序二级页面：获取商品详情，传入商品ID
     */
    @PostMapping("/goodsDetail.app")
    @ResponseBody
    @ApiOperation("获取主页商品列表点击后的商品详情，其中goodsTagStr多个值时使用'~|'两个字符分隔")
    public R goodsDetail(@RequestBody @Valid GoodsInputParam params, Principal principal) {

        try {
            SysUser sysUser = null;
            if(null != principal){
                sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
            }
            // 构造查询
            //LambdaQueryWrapper<SsGoodsInfo> queryWrapper = Wrappers.<SsGoodsInfo>lambdaQuery()
            //        .eq(SsGoodsInfo::getId, params.getGoodsId());//商品ID

            // 执行查询，返回数据
            // return R.ok().setResult(ssGoodsInfoService.getOne(queryWrapper));

            SsGoodsInfo ssGoodsInfo = ssGoodsInfoService.getGoodsDetail(params.getGoodsId());

            //用户登录情况下，获取用户对应商品价值的水票数量
            if(ssGoodsInfo.getTicketId()==null && sysUser!=null){//商品时查询对应的水票数量
                Integer customerTicketnum = ssGoodsInfoService.getTicketNum(sysUser.getId(), ssGoodsInfo.getUnitPrice());
                ssGoodsInfo.setCustomerTicketnum(customerTicketnum);
            }

            return R.ok().setResult(ssGoodsInfo);

        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("系统错误");
        }
    }

    /**
     * 小程序二级页面：获取用户上一次购买的商品详情
     */
    @PostMapping("/lastGoodsDetail.app")
    @ResponseBody
    @ApiOperation("获取商品详情：用户已登录，获取上一次购买的商品；用户未登录或从未购买时，获取购买最多的商品")
    public R getLastGoodsDetail(@RequestBody @Valid GoodsInputParam params, Principal principal) {
        try {
            SysUser sysUser = null;
            if(null != principal){
                sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
            }

            SsGoodsInfo ssGoodsInfo = null;
            if (null == sysUser) {
                // 用户未登录，则获取购买最多的水商品信息
                ssGoodsInfo = this.queryGoodsWithBuyMost(params.getType());
            } else {
                // 用户已登录， 则先查询用户上一次购买过的水
                Integer userId = sysUser.getId();

                // 构造查询及排序方式
                if (params.getType() == 1) {
                    // 购买过的所有商品
                    ssGoodsInfo = ssGoodsInfoService.getLastGoodsDetailWithAll(userId);
                } else if (params.getType() == 2) {
                    // 上一次购买过的水
                    ssGoodsInfo = ssGoodsInfoService.getLastGoodsDetail(userId);
                } else if (params.getType() == 3) {
                    // 上一次购买过的水票
                    ssGoodsInfo = ssGoodsInfoService.getLastTicketDetail(userId);
                }
                //未有购买记录，则获取购买最多的
                if (null == ssGoodsInfo) {
                    ssGoodsInfo = this.queryGoodsWithBuyMost(params.getType());
                }
                //用户登录情况下，获取用户对应商品价值的水票数量
                if(params.getType()!=3){//商品时查询对应的水票数量
                    Integer customerTicketnum = ssGoodsInfoService.getTicketNum(userId, ssGoodsInfo.getUnitPrice());
                    ssGoodsInfo.setCustomerTicketnum(customerTicketnum);
                }
            }

            return R.ok().setResult(ssGoodsInfo);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return R.err().setMessage("系统错误");
        }
    }

    /**
     * 查询购买最多的商品信息
     *
     * @param type
     * @return
     */
    private SsGoodsInfo queryGoodsWithBuyMost(Integer type) {
        SsGoodsInfo ssGoodsInfo = null;
        if (type == 1) {
            // 所有商品购买最多的
            ssGoodsInfo = ssGoodsInfoService.getAllWithBuyMost(RC.B_NORMAL.code());
        } else if (type == 2) {
            // 购买最多的水
            ssGoodsInfo = ssGoodsInfoService.getGoodsWithBuyMost(RC.B_NORMAL.code());
        } else if (type == 3) {
            // 购买最多的水票
            ssGoodsInfo = ssGoodsInfoService.getTicketWithBuyMost(RC.B_NORMAL.code());
        }
        return ssGoodsInfo;
    }

    /**
     * 小程序主页：分页获取水站商品列表，传入分页、每页数据大小和请求类型
     */
    @PostMapping("/stationGoodsList.app")
    @ResponseBody
    @ApiOperation("获取小程序端水站商品列表")
    public R stationGoodsList(@RequestBody @Valid PageInputParam params, Principal principal) {

        try {
            // 构建分页类
            IPage<SsGoodsInfo> ssGoodsInfoPage = new Page<>(params.getPageNo(), params.getPageSize());
            QueryWrapper<SsGoodsInfo> queryWrapper = new QueryWrapper<>();

            if(null != principal){
                SysUser sysUser = (SysUser) ((JwtAuthenticationToken) principal).getPrincipal();
                if(null != sysUser.getStationId()) {
                    queryWrapper.eq("g.station_id", sysUser.getStationId());
                }else{
                    return R.err().setMessage("水站获取失败");
                }
            }

            queryWrapper.orderByDesc("a.sort");//序号、推荐降序
            queryWrapper.isNull("a.ticket_id"); //水票ID为空，表示只获取水
            queryWrapper.gt("a.expired_time", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));//失效时间之前
            queryWrapper.lt("a.publish_time", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));//发布时间之后
            queryWrapper.eq("a.status", RC.B_NORMAL.code());//状态是上架
            queryWrapper.eq("g.status", RC.B_NORMAL.code());//水站商品表中是正常状态

            // 执行查询
            ssGoodsInfoPage = ssGoodsInfoService.getStationGoodsPage(ssGoodsInfoPage, queryWrapper);

            // 设置返回数据
            return R.ok().setPageResult(ssGoodsInfoPage);
        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }
}
