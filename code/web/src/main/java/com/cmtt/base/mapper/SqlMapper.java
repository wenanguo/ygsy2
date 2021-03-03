package com.cmtt.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmtt.base.entity.SsCustomerInfoNew;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component(value = "sqlMapper")
public interface SqlMapper extends BaseMapper<SsCustomerInfoNew> {
    List<Map<String,Object>> sqltest(Map<String,Object> map);



}