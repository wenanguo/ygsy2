package com.cmtt.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cmtt.base.entity.R;
import com.cmtt.base.entity.SysUser;
import com.cmtt.base.entity.validated.GroupAdd;
import com.cmtt.base.entity.validated.GroupDelete;
import com.cmtt.base.entity.validated.GroupEdit;
import com.cmtt.base.service.ISysUserService;
import com.cmtt.base.utils.CommonUtils;
import com.cmtt.base.utils.RC;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cmtt.base.entity.SsWaterStation;
import com.cmtt.base.service.ISsWaterStationService;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 水站信息表 前端控制器
 * </p>
 *
 * @author yuzm
 * @since 2021-01-31
 */
@RestController
@RequestMapping("/api/base/ss-water-station")
@Api(tags = "水站信息相关")
public class SsWaterStationController {

private final Logger logger = LoggerFactory.getLogger(SsWaterStationController.class);

@Autowired
public ISsWaterStationService ssWaterStationService;

@Autowired
private ISysUserService sysUserService;


/**
* 分页获取列表
*/
@GetMapping("/list")
@ResponseBody
public R list(SsWaterStation ssWaterStation) {

    try {
        //获取查询条件，然后置空，防止框架因为有值而做等值查询——而这里需要做like查询
        String stationName = ssWaterStation.getStationName();
        Integer status = ssWaterStation.getStatus();
        ssWaterStation.setStationName(null);

        // 构建分页类
        IPage<SsWaterStation> ssWaterStationPage = new Page<>(ssWaterStation.getPageNo(), ssWaterStation.getPageSize());

        // 构造查询及排序方式
        QueryWrapper<SsWaterStation> queryWrapper = new QueryWrapper<>(ssWaterStation);
        queryWrapper.orderBy(true, ssWaterStation.getIsAsc(), ssWaterStation.getIsSortField());

        if(stationName!=null && !stationName.equals("")) {
            queryWrapper.like("a.station_name", stationName);
        }
        if(status!=null) {
            queryWrapper.eq("a.status", status);
        }
        // 执行查询
        // ssWaterStationPage = ssWaterStationService.page(ssWaterStationPage, queryWrapper);

        // 重写分页查询
        ssWaterStationPage = ssWaterStationService.getStationPage(ssWaterStationPage, queryWrapper);


                // 设置返回数据
        return R.ok().setPageResult(ssWaterStationPage);


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
        public R add(@RequestBody @Validated({GroupAdd.class})SsWaterStation ssWaterStation) {

        try {
        ssWaterStationService.save(ssWaterStation);

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
        public R edit(@RequestBody  @Validated({GroupEdit.class})SsWaterStation ssWaterStation) {


        try {

        ssWaterStationService.updateById(ssWaterStation);

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
        public R delete(@RequestBody @Validated({GroupDelete.class})SsWaterStation ssWaterStation) {

        try {

        ssWaterStationService.removeById(ssWaterStation.getId());

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

            ssWaterStationService.removeByIds(ids);

            return R.ok().setMessage("批量删除成功");
            } catch (Exception e) {
            logger.warn(e.getMessage());

            return R.err().setMessage("批量删除失败");
            }
            }

    /**
     * 获取水站字典
     */
    @GetMapping("/stationDict")
    @ResponseBody
    public R getStationDict() {

        try {
            List<Map<Integer, String>> list = ssWaterStationService.getStationDict(RC.B_NORMAL.code());
            // 设置返回数据
            return R.ok().setResult(list);
        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }

    /**
     * 获取区域字典
     */
    @GetMapping("/regionDict")
    @ResponseBody
    public R getRetionInfoDict(HttpServletRequest request) {

        try {
            String parentarea = request.getParameter("parentarea");
            if(parentarea == null || parentarea.equals("")){
                parentarea = "-1";  //未传入时，查的是省级
            }
            List<Map<String, String>> list = ssWaterStationService.getRetionInfoDict(RC.B_NORMAL.code(), parentarea);
            System.out.println("region list="+list);
            // 设置返回数据
            return R.ok().setResult(list);
        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }



    /**
     * 获取当前水站用户列表
     */
    @PostMapping("/Userlist")
    @ResponseBody
    public R userlist(SysUser sysUser,Principal principal) {

        try {
            SysUser currSysUser = CommonUtils.getUser(principal);


            if(sysUser!=null){

                // 构建分页类
                IPage<SysUser> sysUserPage = new Page<>(sysUser.getPageNo(), sysUser.getPageSize());


                // 构造查询及排序方式
                QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>(sysUser);
                queryWrapper.eq("station_id",currSysUser.getStationId());
                queryWrapper.eq("ttype",1);
                queryWrapper.eq("status",100);
                queryWrapper.orderBy(true, sysUser.getIsAsc(), sysUser.getIsSortField());

                // 执行查询
                sysUserPage = sysUserService.page(sysUserPage, queryWrapper);

                // 过滤敏感信息
                Map<String, Object> map = new HashMap();
                map.put("pageNo", sysUserPage.getCurrent());
                map.put("pageSize", sysUserPage.getSize());
                map.put("totalCount", sysUserPage.getTotal());
                map.put("totalPage", sysUserPage.getPages());


                for (SysUser sysUserTemp:sysUserPage.getRecords()) {

                    sysUserTemp.setPassword("");
                    if(sysUserTemp.getPhone()!=null){
                        String hidenPhone = sysUserTemp.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
                        sysUserTemp.setPhone(hidenPhone);
                    }
                }

                map.put("data", sysUserPage.getRecords());


                // 设置返回数据
                return R.ok().setResult(map);

            }else{
                // 设置返回数据
                return R.err().setMessage("找不到当前数据");
            }




        } catch (Exception e) {

            logger.warn(e.getMessage());

            return R.err().setMessage("系统错误");
        }
    }


}
