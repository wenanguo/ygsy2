package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.cmtt.base.entity.SsWaterTicket;
import com.cmtt.base.service.ISsWaterTicketService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 水票信息 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
@RestController
@RequestMapping("/api/base/ss-water-ticket")
@Api(tags = "水票管理")
public class SsWaterTicketController {

private final Logger logger = LoggerFactory.getLogger(SsWaterTicketController.class);

@Autowired
public ISsWaterTicketService ssWaterTicketService;


/**
* 分页获取列表
*/
@GetMapping("/list")
@ResponseBody
@ApiOperation("根据水票code查询水票详情")
public R list(SsWaterTicket ssWaterTicket, HttpServletRequest httpServletRequest,String status,String ticketCode) {

try {

    String phoneType = httpServletRequest.getHeader("platform"); //判断是后端代码还是小程序
   // SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();

    // 构建分页类
    IPage<SsWaterTicket> ssWaterTicketPage = new Page<>(ssWaterTicket.getPageNo(), ssWaterTicket.getPageSize());

    // 构造查询及排序方式
    QueryWrapper<SsWaterTicket> queryWrapper = new QueryWrapper<>(ssWaterTicket);

    if("miniprogram".equals(phoneType)){//小程序
        queryWrapper.eq("status", status)
        .eq("ticket_code",ticketCode);

    }else {//后台


        if (ssWaterTicket.getStatus() != null && !"".equals(ssWaterTicket.getStatus())) {
            queryWrapper.eq("status", ssWaterTicket.getStatus());
        }
        if (ssWaterTicket.getTicketName() != null) {
            queryWrapper.like("ticket_name", ssWaterTicket.getTicketName());
        }
    }
        queryWrapper.orderBy(true, ssWaterTicket.getIsAsc(), ssWaterTicket.getIsSortField());

        // 执行查询
        ssWaterTicketPage = ssWaterTicketService.page(ssWaterTicketPage, queryWrapper);

        // 设置返回数据
        return R.ok().setPageResult(ssWaterTicketPage);


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
        public R add(@RequestBody @Validated({GroupAdd.class})SsWaterTicket ssWaterTicket) {

        try {
        ssWaterTicketService.save(ssWaterTicket);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})SsWaterTicket ssWaterTicket) {


        try {

        ssWaterTicketService.updateById(ssWaterTicket);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})SsWaterTicket ssWaterTicket) {

        try {

        ssWaterTicketService.removeById(ssWaterTicket.getId());

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

            ssWaterTicketService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }

    /**
     * 获取水票字典
     */
    @GetMapping("/ticketDict")
    @ResponseBody
    @ApiOperation("水票列表-获取水票字典值")
    public R getGoodsType() {

        try {
            List<Map<Integer, String>> list = ssWaterTicketService.getTicketDict(RC.B_NORMAL.code());
            // 设置返回数据
            return R.ok().setResult(list);
        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

}
