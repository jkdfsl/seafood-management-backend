package com.controller;

import com.annotation.IgnoreAuth;
import com.service.*;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 数据大屏控制器
 * 提供可视化大屏所需的数据接口
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private YonghuService yonghuService;

    @Autowired
    private ShangjiaService shangjiaService;

    @Autowired
    private ShengxianxinxiService shengxianxinxiService;

    @Autowired
    private ShengxiancangkuService shengxiancangkuService;

    @Autowired
    private ShengxianfenleiService shengxianfenleiService;

    /**
     * 获取大屏统计数据
     */
    @IgnoreAuth
    @RequestMapping("/statistics")
    public R getStatistics() {
        Map<String, Object> data = new HashMap<>();

        // 订单统计
        data.put("totalOrders", ordersService.selectCount(null));
        data.put("pendingOrders", ordersService.selectCount(
            new com.baomidou.mybatisplus.mapper.EntityWrapper<com.entity.OrdersEntity>()
                .eq("status", "待支付")
        ));
        data.put("paidOrders", ordersService.selectCount(
            new com.baomidou.mybatisplus.mapper.EntityWrapper<com.entity.OrdersEntity>()
                .eq("status", "已支付")
        ));
        data.put("shippedOrders", ordersService.selectCount(
            new com.baomidou.mybatisplus.mapper.EntityWrapper<com.entity.OrdersEntity>()
                .eq("status", "已发货")
        ));
        data.put("completedOrders", ordersService.selectCount(
            new com.baomidou.mybatisplus.mapper.EntityWrapper<com.entity.OrdersEntity>()
                .eq("status", "已完成")
        ));

        // 用户统计
        data.put("totalUsers", yonghuService.selectCount(null));
        data.put("totalShops", shangjiaService.selectCount(null));

        // 商品统计
        data.put("totalProducts", shengxianxinxiService.selectCount(null));
        data.put("totalWarehouses", shengxiancangkuService.selectCount(null));

        return R.ok().put("data", data);
    }

    /**
     * 获取订单趋势数据（最近7天）
     */
    @IgnoreAuth
    @RequestMapping("/orderTrend")
    public R getOrderTrend() {
        List<Map<String, Object>> trendData = new ArrayList<>();
        
        // 获取最近7天的日期
        Calendar calendar = Calendar.getInstance();
        for (int i = 6; i >= 0; i--) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            Date date = calendar.getTime();
            
            String dateStr = new java.text.SimpleDateFormat("MM-dd").format(date);
            
            // 查询当天的订单数
            int count = ordersService.selectCount(
                new com.baomidou.mybatisplus.mapper.EntityWrapper<com.entity.OrdersEntity>()
                    .ge("addtime", new java.sql.Date(date.getTime()))
                    .lt("addtime", new java.sql.Date(date.getTime() + 86400000))
            );
            
            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", dateStr);
            dayData.put("count", count);
            trendData.add(dayData);
        }

        return R.ok().put("data", trendData);
    }

    /**
     * 获取商品分类统计
     */
    @IgnoreAuth
    @RequestMapping("/productCategory")
    public R getProductCategory() {
        List<Map<String, Object>> categoryData = new ArrayList<>();
        
        // 获取所有商品分类
        List<com.entity.ShengxianfenleiEntity> categories = shengxianfenleiService.selectList(null);
        
        for (com.entity.ShengxianfenleiEntity category : categories) {
            int count = shengxianxinxiService.selectCount(
                new com.baomidou.mybatisplus.mapper.EntityWrapper<com.entity.ShengxianxinxiEntity>()
                    .eq("shengxianleixing", category.getLeixing())
            );
            
            if (count > 0) {
                Map<String, Object> item = new HashMap<>();
                item.put("name", category.getLeixing());
                item.put("value", count);
                categoryData.add(item);
            }
        }

        return R.ok().put("data", categoryData);
    }

    /**
     * 获取销售排行（Top 10商品）
     */
    @IgnoreAuth
    @RequestMapping("/salesRanking")
    public R getSalesRanking() {
        List<Map<String, Object>> rankingData = new ArrayList<>();
        
        // 获取所有订单，按商品统计销量
        List<com.entity.OrdersEntity> orders = ordersService.selectList(null);
        Map<String, Integer> productSales = new HashMap<>();
        Map<String, String> productNames = new HashMap<>();
        
        for (com.entity.OrdersEntity order : orders) {
            String goodId = String.valueOf(order.getGoodid());
            String goodName = order.getGoodname();
            int buynumber = order.getBuynumber();
            
            productSales.put(goodId, productSales.getOrDefault(goodId, 0) + buynumber);
            productNames.put(goodId, goodName);
        }
        
        // 转换为列表并排序
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(productSales.entrySet());
        sortedList.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        // 取前10
        int limit = Math.min(10, sortedList.size());
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> entry = sortedList.get(i);
            Map<String, Object> item = new HashMap<>();
            item.put("name", productNames.get(entry.getKey()));
            item.put("value", entry.getValue());
            rankingData.add(item);
        }

        return R.ok().put("data", rankingData);
    }

    /**
     * 获取订单状态分布
     */
    @IgnoreAuth
    @RequestMapping("/orderStatus")
    public R getOrderStatus() {
        List<Map<String, Object>> statusData = new ArrayList<>();
        
        String[] statuses = {"待支付", "已支付", "已发货", "已完成", "已取消", "已退款"};
        for (String status : statuses) {
            int count = ordersService.selectCount(
                new com.baomidou.mybatisplus.mapper.EntityWrapper<com.entity.OrdersEntity>()
                    .eq("status", status)
            );
            
            if (count > 0) {
                Map<String, Object> item = new HashMap<>();
                item.put("name", status);
                item.put("value", count);
                statusData.add(item);
            }
        }

        return R.ok().put("data", statusData);
    }
}
