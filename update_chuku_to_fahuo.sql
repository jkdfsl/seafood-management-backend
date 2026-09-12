-- 将出库状态改为发货状态
-- 执行此脚本前请先备份数据库

-- 更新待出库为待发货
UPDATE shengxianchuku SET status = '待发货' WHERE status = '待出库';

-- 更新已出库为已发货
UPDATE shengxianchuku SET status = '已发货' WHERE status = '已出库';

-- 验证更新结果
SELECT status, COUNT(*) as count FROM shengxianchuku GROUP BY status;
