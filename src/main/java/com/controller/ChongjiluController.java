package com.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;

import com.entity.ChongjiluEntity;
import com.service.ChongjiluService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 充值记录
 * 后端接口
 * @author 
 * @email 
 * @date 2022-03-16 15:55:56
 */
@RestController
@RequestMapping("/chongjilu")
public class ChongjiluController {
    @Autowired
    private ChongjiluService chongjiluService;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ChongjiluEntity chongjilu,
		HttpServletRequest request){
        EntityWrapper<ChongjiluEntity> ew = new EntityWrapper<ChongjiluEntity>();
		PageUtils page = chongjiluService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chongjilu), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ChongjiluEntity chongjilu, 
		HttpServletRequest request){
        EntityWrapper<ChongjiluEntity> ew = new EntityWrapper<ChongjiluEntity>();
		PageUtils page = chongjiluService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chongjilu), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ChongjiluEntity chongjilu){
      	EntityWrapper<ChongjiluEntity> ew = new EntityWrapper<ChongjiluEntity>();
      	ew.allEq(MPUtil.allEQMapPre( chongjilu, "chongjilu")); 
        return R.ok().put("data", chongjiluService.selectList(ew));
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ChongjiluEntity chongjilu = chongjiluService.selectById(id);
        return R.ok().put("data", chongjilu);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ChongjiluEntity chongjilu = chongjiluService.selectById(id);
        return R.ok().put("data", chongjilu);
    }
    


    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ChongjiluEntity chongjilu, HttpServletRequest request){
    	chongjilu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
        chongjiluService.insert(chongjilu);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ChongjiluEntity chongjilu, HttpServletRequest request){
    	chongjilu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
        chongjiluService.insert(chongjilu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ChongjiluEntity chongjilu, HttpServletRequest request){
        chongjiluService.updateById(chongjilu);
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        chongjiluService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
}
