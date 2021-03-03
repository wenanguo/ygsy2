package com.cmtt.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.cmtt.base.config.ss.configuration.JwtAuthenticationToken;
import com.cmtt.base.entity.*;
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

import com.cmtt.base.service.ISsCustomerTicketService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 用户购买水票信息 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-29
 */
@RestController
@RequestMapping("/api/base/ss-customer-ticket")
@Api(tags = "用户水票相关")
public class SsCustomerTicketController {

private final Logger logger = LoggerFactory.getLogger(SsCustomerTicketController.class);

@Autowired
public ISsCustomerTicketService ssCustomerTicketService;


/**
* 分页获取列表
*/
@PostMapping("/list")
@ResponseBody
public R list(SsCustomerTicket ssCustomerTicket, String customerId, Principal principal, String status, HttpServletRequest httpServletRequest) {

try {
     // 构建分页类
    IPage<SsCustomerTicket> ssCustomerTicketPage = new Page<>(ssCustomerTicket.getPageNo(), ssCustomerTicket.getPageSize());
    SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
    String phoneType = httpServletRequest.getHeader("platform"); //判断是后端代码还是小程序

    // 构造查询及排序方式
    QueryWrapper<SsCustomerTicket> queryWrapper = new QueryWrapper<>(ssCustomerTicket);
    if("miniprogram".equals(phoneType)){
        queryWrapper.apply("use_Status={0}", status)
                .inSql("customer_id","select customer_id from ss_customer_ticket where customer_id ="+sysUser.getPhone());
    }else {
        if (customerId != null) {
            if(ssCustomerTicket.getUseStatus()!=null) {
                queryWrapper.apply("use_Status={0}", ssCustomerTicket.getUseStatus())
                        .inSql("customer_id", "select customer_id from ss_customer_ticket where customer_id =" + customerId);
            }else{
                queryWrapper.apply("use_Status={0}", RC.B_NORMAL.code())
                        .inSql("customer_id", "select customer_id from ss_customer_ticket where customer_id ="+customerId);
            }
        } else {
            return R.err();
        }
    }
       queryWrapper.orderBy(true, ssCustomerTicket.getIsAsc(), ssCustomerTicket.getIsSortField());
         // 执行查询
        ssCustomerTicketPage = ssCustomerTicketService.page(ssCustomerTicketPage, queryWrapper);
        // 设置返回数据
        return R.ok().setPageResult(ssCustomerTicketPage);
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
        @ApiOperation("用户水票表新增")
        public R add(@RequestBody @Validated({GroupAdd.class})SsCustomerTicket ssCustomerTicket) {

        try {
        ssCustomerTicketService.save(ssCustomerTicket);

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
        @ApiOperation("用户水票表修改")
        public R edit(@RequestBody  @Validated({GroupEdit.class})SsCustomerTicket ssCustomerTicket) {


        try {

        ssCustomerTicketService.updateById(ssCustomerTicket);

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
        @ApiOperation("用户水票表删除")
        public R delete(@RequestBody @Validated({GroupDelete.class})SsCustomerTicket ssCustomerTicket) {

        try {

        ssCustomerTicketService.removeById(ssCustomerTicket.getId());

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
        @ApiOperation("用户水票表批量删除")
        public R batchDelete(@RequestBody List<Integer> ids) {
            try {

            ssCustomerTicketService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }

        @GetMapping("/listAll")
        @ResponseBody
        @ApiOperation("用户购买的水票信息,传入用户编码，水票状态等，可查询相应的水票")
        public R listAll(SsCustomerTicket ssCustomerTicket, String customerId, Principal principal, String useStatus, Integer pageNo,Integer pageSize,HttpServletRequest httpServletRequest) {

            try {
                // 构建分页类
                IPage<SsCustomerTicket> ssCustomerTicketPage = new Page<>(ssCustomerTicket.getPageNo(), ssCustomerTicket.getPageSize());
               // IPage<SsCustomerTicket> ssCustomerTicketPage = new Page<>(pageNo, pageSize);

                SysUser sysUser =(SysUser)((JwtAuthenticationToken)principal).getPrincipal();
                String phoneType = httpServletRequest.getHeader("platform"); //判断是后端代码还是小程序


                // 构造查询及排序方式
                QueryWrapper<SsCustomerTicket> queryWrapper = new QueryWrapper<>(ssCustomerTicket);

                if(sysUser==null){
                    return R.err();
                }

              //  String OpenId = sysUser.getWxOpenid();
                Integer id = sysUser.getId();
                if("miniprogram".equals(phoneType)){ //是否小程序入口

                     if(sysUser==null){
                         return R.err();
                     }else{
                         if(id==null || "".equals(id)){
                            return R.err();
                         }
                         if(id!=null && !"".equals(id)) {
                             queryWrapper.eq("customer_id", id);
                         }
                         if(ssCustomerTicket.getUseStatus()!=null){
                             queryWrapper.eq("use_status",useStatus);
                         }
                     }
                }else{
                    if(customerId==null||"".equals(customerId)){
                        queryWrapper.eq("customer_id", null);
                    }else{
                        queryWrapper.eq("customer_id", customerId);
                    }

                    if(ssCustomerTicket.getUseStatus()!=null&&!"".equals(ssCustomerTicket.getUseStatus())) {
                        queryWrapper.eq("use_status",ssCustomerTicket.getUseStatus());
                    }
                    if(ssCustomerTicket.getTicketName()!=null){
                        queryWrapper.like("ticket_name",ssCustomerTicket.getTicketName());
                    }

                }


                    queryWrapper.orderBy(true, ssCustomerTicket.getIsAsc(), ssCustomerTicket.getIsSortField());

                    // 执行查询
                    ssCustomerTicketPage = ssCustomerTicketService.getAllSsCustomerTicketList(ssCustomerTicketPage, queryWrapper);

                // 设置返回数据
                return R.ok().setPageResult(ssCustomerTicketPage);


            } catch (Exception e) {

                logger.warn(e.getMessage());

                return R.err().setMessage("系统错误");
            }
        }

    /**
     * 新增
     */
    @PostMapping("/addAll")
    @ResponseBody
    public R addAll(@RequestBody @Validated({GroupAdd.class})SsCustomerTicket ssCustomerTicket, SsWaterTicket SsWaterTicket, SsOrderWaterticket SsOrderWaterticket, SsOrderDetail ssOrderDetail) {

        try {
            ssCustomerTicketService.save(ssCustomerTicket);

            return R.ok().setMessage("新增成功");

        } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("新增失败");
        }
    }

}
