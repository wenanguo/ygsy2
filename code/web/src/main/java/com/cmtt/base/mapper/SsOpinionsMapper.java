package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsOpinions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmtt.base.entity.SsOrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-02-16
 */
@Mapper
@Component(value = "ssOpinionsMapper")
public interface SsOpinionsMapper extends BaseMapper<SsOpinions> {


    @Insert(" \n" +
            " insert into ss_opinions(content,parentId,goodsId,customerId,opinion_type,createTime,replayId) \n" +
            " values \n" +
            "(#{content},#{parentId},#{goodsId},#{customerId},#{opinionType},#{createTime},#{replayId})"
    )
    boolean saveOpinionById(SsOpinions ssOpinions);

    @Update(" \n" +
            " update ss_opinions set status=2  where id=#{id}"
    )
    boolean updateOpinionById(Integer id);

    @Select(
            "select * from ( \n" +
            "  select a.id as id,customerId,content,status,goodsId,opinion_type,createtime,replyContent,replyTime,phone,username from (  \n" +
            " select a.id as id,a.customerId as customerId, a.content,a.status,a.goodsId,a.opinion_type,a.createtime,b.content as replyContent,b.createTime as replyTime from  \n" +
            " (select * from ss_opinions where parentId is null) a \n" +
                    "  left JOIN  \n" +
                    "  (select * from ss_opinions) b  \n" +
                    "  on a.id = b.parentId ) a  \n" +
                    " left join  \n" +
                    " (select id,wx_openID,phone,username from sys_user) b \n" +
                    "  on  b.id = a.customerId) c  \n" +
                    " ${ew.customSqlSegment} "
    )
    IPage<SsOpinions> getAllOpinions(IPage<SsOpinions> page, @Param(Constants.WRAPPER) Wrapper<SsOpinions> queryWrapper);

}
