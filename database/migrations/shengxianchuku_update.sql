-- 修改生鲜出库表，添加订单相关字段
ALTER TABLE shengxianchuku 
ADD COLUMN orderid VARCHAR(255) COMMENT '订单编号',
ADD COLUMN goodname VARCHAR(255) COMMENT '商品名称',
ADD COLUMN buynumber INT COMMENT '购买数量',
ADD COLUMN `status` VARCHAR(255) DEFAULT '待出库' COMMENT '出库状态';
