package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.ChongjiluEntity;
import com.utils.PageUtils;

import java.util.Map;

/**
 * 充值记录
 *
 * @author 
 * @email 
 * @date 2022-03-16 15:55:56
 */
public interface ChongjiluService extends IService<ChongjiluEntity> {
    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params, Wrapper<ChongjiluEntity> wrapper);
}
