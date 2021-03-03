package com.cmtt.base.mapper;

import com.cmtt.base.entity.SsDeliveryMan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

/**
 * <p>
 * 配送员信息 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
public interface SsDeliveryManMapper extends BaseMapper<SsDeliveryMan> {

    //验证手机号
    @Select({"select count(0) from sys_user where phone=#{phone}"})
    Integer queryUserNumByPhone(String phone);

    //验证用户名
    @Select({"select count(0) from sys_user where username=#{username}"})
    Integer queryUsername(String username);

   /* //添加配送员
    @Insert({"insert into sys_user(username, nickname, phone, memo, sex, icon, station_id, status, create_time, ttype) values(#{username}, #{nickname}, #{status}, #{memo}, #{sex}, #{icon}, #{ttype}, #{createTime}, #{phone}) "})
    Integer addDeliveryUser(String username, String nickname, String phone, String memo, Integer sex, String icon, Integer stationId, Integer status, LocalDateTime createTime, Integer ttype);

    //更新配送员
    @Update({"update sys_user set username = #{username}, nickname=#{nickname}, memo=#{memo}, sex=#{sex}, icon=#{icon}, status=#{status}, update_time=#{updateTime}, ttype=#{ttype} where phone=#{phone} "})
    Integer updateDeliveryUser(String username, String nickname, String memo, Integer sex, String icon, Integer status, LocalDateTime updateTime, Integer ttype, String phone);

    //删除配送员
    @Update({"delete from sys_user where phone=#{phone} "})
    Integer deleteDeliveryUser(String phone);*/

}
