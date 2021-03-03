package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cmtt.base.entity.SsStationBank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 水站绑定的银行卡信息 Mapper 接口
 * </p>
 *
 * @author Andrew.Wen
 * @since 2021-01-31
 */
public interface SsStationBankMapper extends BaseMapper<SsStationBank> {

    /**
     * 获取银行列表信息
     * @return
     */
    @Select({"select id, bankname from ss_bankcode"})
    List<Map<Integer, String>> getBankList();

    /**
     * 小程序：分页获取水站银行卡列表
     * @param queryWrapper
     * @return
     */
    @Select({"select a.id, a.station_id as stationId, a.account_name as accountName, a.bank_code as bankCode, a.bank_branch as bankBranch \n" +
            ", a.phone, a.`status`, a.id_number as idNumber, b.bankname as bankName, c.station_name as stationName \n" +
            " from ss_station_bank a \n" +
            " left join ss_bankcode b on a.bank_name=b.id \n" +
            " inner join ss_water_station c on a.station_id=c.id \n" +
            " ${ew.customSqlSegment} " })
    IPage<SsStationBank> getStationBankPage(IPage<SsStationBank> page, @Param(Constants.WRAPPER) Wrapper<SsStationBank> queryWrapper);

}
