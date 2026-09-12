-- 给订单表添加仓库号字段
ALTER TABLE orders ADD COLUMN cangkubianhao VARCHAR(50) DEFAULT NULL COMMENT '发货仓库编号';

-- 验证
SELECT orderid, goodname, status, cangkubianhao FROM orders LIMIT 10;
