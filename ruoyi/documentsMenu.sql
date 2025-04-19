-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('系统文档', '2286', '1', 'documents', 'data/documents/index', 1, 0, 'C', '0', '0', 'data:documents:list', '#', 'admin', current_timestamp, '', null, '系统文档菜单');

-- 按钮父菜单ID
--SELECT @parentId := LAST_INSERT_ID();
select max(menu_id) from sys_menu;

/*
   全局替换 @parentId 为 上面语句查询出来的 max(menu_id)
*/

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('系统文档查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'data:documents:query',        '#', 'admin', current_timestamp, '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('系统文档新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'data:documents:add',          '#', 'admin', current_timestamp, '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('系统文档修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'data:documents:edit',         '#', 'admin', current_timestamp, '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('系统文档删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'data:documents:remove',       '#', 'admin', current_timestamp, '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('系统文档导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'data:documents:export',       '#', 'admin', current_timestamp, '', null, '');