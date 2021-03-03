package com.cmtt.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 客户信息
 * </p>
 *
 * @author lin
 * @since 2021-01-28
 */
@Data
public class SsCustomerInfoNew extends SsCustomerInfo implements Serializable {

     // @ApiModelProperty(value = "订单总数")
    private String orderTotal;

   // @ApiModelProperty(value = "水票总数")
    private String ticketTotal;

    //@ApiModelProperty(value = "总金额")
    private String moneyTotal;
    //总积分
    private String totalPoints;

    private String address;

    private String stationName;

    private String waterStationAddress;

    private String haveTicket;

    private Integer stationId;

    private Integer addressId;

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getTicketTotal() {
        return ticketTotal;
    }

    public void setTicketTotal(String ticketTotal) {
        this.ticketTotal = ticketTotal;
    }

    public String getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(String moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getWaterStationAddress() {
        return waterStationAddress;
    }

    public void setWaterStationAddress(String waterStationAddress) {
        this.waterStationAddress = waterStationAddress;
    }

    public String getHaveTicket() {
        return haveTicket;
    }

    public void setHaveTicket(String haveTicket) {
        this.haveTicket = haveTicket;
    }
}
