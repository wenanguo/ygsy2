package com.cmtt.base.mapper;

import com.cmtt.base.entity.SsClockinInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuzm
 * @since 2021-02-20
 */
public interface SsClockinInfoMapper extends BaseMapper<SsClockinInfo> {

    //查询用户指定月份的打卡记录
    @Select("select * from ss_clockin_info where user_id=#{userId} and DATE_FORMAT((clockin_date),'%Y%m')=#{month} order by clockin_date")
    List<SsClockinInfo> getClockinList(Integer userId, String month);

    //更新用户下班打卡时间
    @Update({"update ss_clockin_info set out_time = #{outTime} where user_id=#{userId} and clockin_date=#{clockinDate} "})
    Integer updateOutTime(LocalTime outTime, Integer userId, LocalDate clockinDate);
}
