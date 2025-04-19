-- 菜单 SQL
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('作物批次', '2284', '1', 'batch', 'manage/batch/index', 1, 0, 'C', '0', '0', 'manage:batch:list', '#', 'admin', NOW(), '', NULL, '作物批次菜单');

-- 获取父菜单 ID
WITH parent_id AS (
    SELECT MAX(menu_id) AS id FROM sys_menu WHERE menu_name = '作物批次'
)
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES 
('作物批次查询', (SELECT id FROM parent_id), '1', '#', '', 1, 0, 'F', '0', '0', 'manage:batch:query', '#', 'admin', NOW(), '', NULL, ''),
('作物批次新增', (SELECT id FROM parent_id), '2', '#', '', 1, 0, 'F', '0', '0', 'manage:batch:add', '#', 'admin', NOW(), '', NULL, ''),
('作物批次修改', (SELECT id FROM parent_id), '3', '#', '', 1, 0, 'F', '0', '0', 'manage:batch:edit', '#', 'admin', NOW(), '', NULL, ''),
('作物批次删除', (SELECT id FROM parent_id), '4', '#', '', 1, 0, 'F', '0', '0', 'manage:batch:remove', '#', 'admin', NOW(), '', NULL, ''),
('作物批次导出', (SELECT id FROM parent_id), '5', '#', '', 1, 0, 'F', '0', '0', 'manage:batch:export', '#', 'admin', NOW(), '', NULL, '');

-- 菜单 SQL
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('员工管理', '2281', '1', 'employee', 'manage/employee/index', 1, 0, 'C', '0', '0', 'manage:employee:list', '#', 'admin', NOW(), '', NULL, '员工管理菜单');

-- 获取父菜单 ID
WITH parent_id AS (
    SELECT MAX(menu_id) AS id FROM sys_menu WHERE menu_name = '员工管理'
)
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES 
('员工管理查询', (SELECT id FROM parent_id), '1', '#', '', 1, 0, 'F', '0', '0', 'manage:employee:query', '#', 'admin', NOW(), '', NULL, ''),
('员工管理新增', (SELECT id FROM parent_id), '2', '#', '', 1, 0, 'F', '0', '0', 'manage:employee:add', '#', 'admin', NOW(), '', NULL, ''),
('员工管理修改', (SELECT id FROM parent_id), '3', '#', '', 1, 0, 'F', '0', '0', 'manage:employee:edit', '#', 'admin', NOW(), '', NULL, ''),
('员工管理删除', (SELECT id FROM parent_id), '4', '#', '', 1, 0, 'F', '0', '0', 'manage:employee:remove', '#', 'admin', NOW(), '', NULL, ''),
('员工管理导出', (SELECT id FROM parent_id), '5', '#', '', 1, 0, 'F', '0', '0', 'manage:employee:export', '#', 'admin', NOW(), '', NULL, '');

-- 菜单 SQL
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('种质系统', '2281', '1', 'germplasm', 'manage/germplasm/index', 1, 0, 'C', '0', '0', 'manage:germplasm:list', '#', 'admin', NOW(), '', NULL, '种质系统菜单');

-- 获取父菜单 ID
WITH parent_id AS (
    SELECT MAX(menu_id) AS id FROM sys_menu WHERE menu_name = '种质系统'
)
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES 
('种质系统查询', (SELECT id FROM parent_id), '1', '#', '', 1, 0, 'F', '0', '0', 'manage:germplasm:query', '#', 'admin', NOW(), '', NULL, ''),
('种质系统新增', (SELECT id FROM parent_id), '2', '#', '', 1, 0, 'F', '0', '0', 'manage:germplasm:add', '#', 'admin', NOW(), '', NULL, ''),
('种质系统修改', (SELECT id FROM parent_id), '3', '#', '', 1, 0, 'F', '0', '0', 'manage:germplasm:edit', '#', 'admin', NOW(), '', NULL, ''),
('种质系统删除', (SELECT id FROM parent_id), '4', '#', '', 1, 0, 'F', '0', '0', 'manage:germplasm:remove', '#', 'admin', NOW(), '', NULL, ''),
('种质系统导出', (SELECT id FROM parent_id), '5', '#', '', 1, 0, 'F', '0', '0', 'manage:germplasm:export', '#', 'admin', NOW(), '', NULL, '');

-- 菜单 SQL
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('机械信息', '2283', '1', 'machineInfo', 'manage/machineInfo/index', 1, 0, 'C', '0', '0', 'manage:machineInfo:list', '#', 'admin', NOW(), '', NULL, '机械信息菜单');

-- 获取父菜单 ID
WITH parent_id AS (
    SELECT MAX(menu_id) AS id FROM sys_menu WHERE menu_name = '机械信息'
)
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES 
('机械信息查询', (SELECT id FROM parent_id), '1', '#', '', 1, 0, 'F', '0', '0', 'manage:machineInfo:query', '#', 'admin', NOW(), '', NULL, ''),
('机械信息新增', (SELECT id FROM parent_id), '2', '#', '', 1, 0, 'F', '0', '0', 'manage:machineInfo:add', '#', 'admin', NOW(), '', NULL, ''),
('机械信息修改', (SELECT id FROM parent_id), '3', '#', '', 1, 0, 'F', '0', '0', 'manage:machineInfo:edit', '#', 'admin', NOW(), '', NULL, ''),
('机械信息删除', (SELECT id FROM parent_id), '4', '#', '', 1, 0, 'F', '0', '0', 'manage:machineInfo:remove', '#', 'admin', NOW(), '', NULL, ''),
('机械信息导出', (SELECT id FROM parent_id), '5', '#', '', 1, 0, 'F', '0', '0', 'manage:machineInfo:export', '#', 'admin', NOW(), '', NULL, '');



-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('机械信息', '2283', '1', 'machineInfo', 'manage/machineInfo/index', 1, 0, 'C', '0', '0', 'manage:machineInfo:list', '#', 'admin', current_timestamp, '', null, '机械信息菜单');

-- 按钮父菜单ID
--SELECT @parentId := LAST_INSERT_ID();
select max(menu_id) from sys_menu;
-- 菜单 SQL
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('机械类别', '2283', '1', 'machineType', 'manage/machineType/index', 1, 0, 'C', '0', '0', 'manage:machineType:list', '#', 'admin', NOW(), '', NULL, '机械类别菜单');

-- 获取父菜单 ID
WITH parent_id AS (
    SELECT MAX(menu_id) AS id FROM sys_menu WHERE menu_name = '机械类别'
)
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES 
('机械类别查询', (SELECT id FROM parent_id), '1', '#', '', 1, 0, 'F', '0', '0', 'manage:machineType:query', '#', 'admin', NOW(), '', NULL, ''),
('机械类别新增', (SELECT id FROM parent_id), '2', '#', '', 1, 0, 'F', '0', '0', 'manage:machineType:add', '#', 'admin', NOW(), '', NULL, ''),
('机械类别修改', (SELECT id FROM parent_id), '3', '#', '', 1, 0, 'F', '0', '0', 'manage:machineType:edit', '#', 'admin', NOW(), '', NULL, ''),
('机械类别删除', (SELECT id FROM parent_id), '4', '#', '', 1, 0, 'F', '0', '0', 'manage:machineType:remove', '#', 'admin', NOW(), '', NULL, ''),
('机械类别导出', (SELECT id FROM parent_id), '5', '#', '', 1, 0, 'F', '0', '0', 'manage:machineType:export', '#', 'admin', NOW(), '', NULL, '');

-- 菜单 SQL
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('农资信息', '2282', '1', 'materialInfo', 'manage/materialInfo/index', 1, 0, 'C', '0', '0', 'manage:materialInfo:list', '#', 'admin', NOW(), '', NULL, '农资信息菜单');

-- 获取父菜单 ID
WITH parent_id AS (
    SELECT MAX(menu_id) AS id FROM sys_menu WHERE menu_name = '农资信息'
)
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES 
('农资信息查询', (SELECT id FROM parent_id), '1', '#', '', 1, 0, 'F', '0', '0', 'manage:materialInfo:query', '#', 'admin', NOW(), '', NULL, ''),
('农资信息新增', (SELECT id FROM parent_id), '2', '#', '', 1, 0, 'F', '0', '0', 'manage:materialInfo:add', '#', 'admin', NOW(), '', NULL, ''),
('农资信息修改', (SELECT id FROM parent_id), '3', '#', '', 1, 0, 'F', '0', '0', 'manage:materialInfo:edit', '#', 'admin', NOW(), '', NULL, ''),
('农资信息删除', (SELECT id FROM parent_id), '4', '#', '', 1, 0, 'F', '0', '0', 'manage:materialInfo:remove', '#', 'admin', NOW(), '', NULL, ''),
('农资信息导出', (SELECT id FROM parent_id), '5', '#', '', 1, 0, 'F', '0', '0', 'manage:materialInfo:export', '#', 'admin', NOW(), '', NULL, '');

-- 菜单 SQL
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('农资类别', '2282', '1', 'materialType', 'manage/materialType/index', 1, 0, 'C', '0', '0', 'manage:materialType:list', '#', 'admin', NOW(), '', NULL, '农资类别菜单');

-- 获取父菜单 ID
WITH parent_id AS (
    SELECT MAX(menu_id) AS id FROM sys_menu WHERE menu_name = '农资类别'
)
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES 
('农资类别查询', (SELECT id FROM parent_id), '1', '#', '', 1, 0, 'F', '0', '0', 'manage:materialType:query', '#', 'admin', NOW(), '', NULL, ''),
('农资类别新增', (SELECT id FROM parent_id), '2', '#', '', 1, 0, 'F', '0', '0', 'manage:materialType:add', '#', 'admin', NOW(), '', NULL, ''),
('农资类别修改', (SELECT id FROM parent_id), '3', '#', '', 1, 0, 'F', '0', '0', 'manage:materialType:edit', '#', 'admin', NOW(), '', NULL, ''),
('农资类别删除', (SELECT id FROM parent_id), '4', '#', '', 1, 0, 'F', '0', '0', 'manage:materialType:remove', '#', 'admin', NOW(), '', NULL, ''),
('农资类别导出', (SELECT id FROM parent_id), '5', '#', '', 1, 0, 'F', '0', '0', 'manage:materialType:export', '#', 'admin', NOW(), '', NULL, '');
