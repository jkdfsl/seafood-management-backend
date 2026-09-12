package com.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.dao.ChongjiluDao;
import com.entity.ChongjiluEntity;
import com.service.ChongjiluService;
import com.utils.PageUtils;
import com.utils.Query;

import java.util.Map;

@Service("chongjiluService")
public class ChongjiluServiceImpl extends ServiceImpl<ChongjiluDao, ChongjiluEntity> implements ChongjiluService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ChongjiluEntity> page = this.selectPage(
                new Query<ChongjiluEntity>(params).getPage(),
                new EntityWrapper<ChongjiluEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<ChongjiluEntity> wrapper) {
        Page<ChongjiluEntity> page = new Query<ChongjiluEntity>(params).getPage();
        page.setRecords(baseMapper.selectList(wrapper));
        return new PageUtils(page);
    }
}
