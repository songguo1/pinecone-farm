-- Drop and create tables with PostgreSQL syntax
DROP TABLE IF EXISTS agri_batch_task;
CREATE TABLE agri_batch_task (
  task_id bigserial PRIMARY KEY,
  batch_id bigint,
  task_head bigint,
  task_head_name varchar(255),
  task_name varchar(50) NOT NULL DEFAULT '',
  plan_start timestamp,
  plan_finish timestamp,
  actual_start timestamp,
  actual_finish timestamp,
  task_detail varchar(2000) NOT NULL DEFAULT '',
  task_images text,
  task_videos text,
  remark varchar(2000) NOT NULL DEFAULT '',
  status integer NOT NULL DEFAULT 0,
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);
COMMENT ON TABLE agri_batch_task IS '批次任务表';
COMMENT ON COLUMN agri_batch_task.task_id IS '任务ID';
COMMENT ON COLUMN agri_batch_task.batch_id IS '批次ID';
COMMENT ON COLUMN agri_batch_task.task_head IS '任务负责人';
COMMENT ON COLUMN agri_batch_task.task_name IS '任务名称';
COMMENT ON COLUMN agri_batch_task.plan_start IS '计划开始日期';
COMMENT ON COLUMN agri_batch_task.plan_finish IS '计划结束日期';
COMMENT ON COLUMN agri_batch_task.actual_start IS '实际开始日期';
COMMENT ON COLUMN agri_batch_task.actual_finish IS '实际结束日期';
COMMENT ON COLUMN agri_batch_task.task_detail IS '任务详情';
COMMENT ON COLUMN agri_batch_task.task_images IS '图片资料';
COMMENT ON COLUMN agri_batch_task.task_videos IS '视频资料';
COMMENT ON COLUMN agri_batch_task.remark IS '备注';
COMMENT ON COLUMN agri_batch_task.status IS '0未分配 1已分配 2进行中 3已完成';
COMMENT ON COLUMN agri_batch_task.order_num IS '排序';
COMMENT ON COLUMN agri_batch_task.create_by IS '创建者ID';
COMMENT ON COLUMN agri_batch_task.create_time IS '创建时间';
COMMENT ON COLUMN agri_batch_task.update_by IS '修改人ID';
COMMENT ON COLUMN agri_batch_task.update_time IS '修改时间';
COMMENT ON COLUMN agri_batch_task.del_flag IS '删除标志（0代表存在 2代表删除）';

-- Insert data with adjusted syntax
INSERT INTO agri_batch_task (task_id, batch_id, task_head, task_head_name, task_name, plan_start, plan_finish, actual_start, actual_finish, task_detail, task_images, task_videos, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag) 
VALUES 
(1, 3, 6, '路人', '育苗', '2024-09-26 00:00:00', '2024-10-16 00:00:00', NULL, NULL, '', '/profile/iot/17/2024-1015-101854.png', NULL, '1233777', 1, 0, 17, '2024-09-26 14:38:28', 17, '2024-10-24 10:17:15', '0'),
(2, 3, 17, NULL, '施肥', '2024-10-10 00:00:00', '2024-10-23 00:00:00', NULL, NULL, '', NULL, NULL, '', 0, 0, 17, '2024-09-26 14:38:28', 0, NULL, '0'),
(3, 4, 15, NULL, '育苗', '2024-10-15 00:00:00', '2024-11-04 00:00:00', NULL, NULL, '', NULL, NULL, '', 0, 0, 17, '2024-10-15 10:19:51', 0, NULL, '2'),
(4, 4, 15, NULL, '施肥', '2024-10-29 00:00:00', '2024-11-11 00:00:00', NULL, NULL, '', NULL, NULL, '', 0, 0, 17, '2024-10-15 10:19:51', 0, NULL, '2'),
(5, 3, 6, NULL, '新增任务', '2024-09-27 00:00:00', '2024-09-28 00:00:00', NULL, NULL, '插入新的任务', NULL, NULL, '', 3, 0, 17, '2024-10-15 10:24:12', 17, '2024-10-16 10:38:37', '0'),
(6, 5, 15, NULL, '育苗', '2024-10-15 00:00:00', '2024-11-04 00:00:00', NULL, NULL, '', NULL, NULL, '', 0, 0, 17, '2024-10-15 11:06:37', 0, NULL, '2'),
(7, 5, 15, NULL, '施肥', '2024-10-29 00:00:00', '2024-11-11 00:00:00', NULL, NULL, '', NULL, NULL, '', 0, 0, 17, '2024-10-15 11:06:37', 0, NULL, '2'),
(8, 6, 17, NULL, '育苗', '2024-10-15 00:00:00', '2024-11-04 00:00:00', NULL, NULL, '', NULL, NULL, '', 0, 0, 17, '2024-10-15 11:13:32', 17, '2024-10-15 13:31:40', '2'),
(9, 6, 17, NULL, '施肥', '2024-10-29 00:00:00', '2024-11-11 00:00:00', NULL, NULL, '', NULL, NULL, '', 0, 0, 17, '2024-10-15 11:13:32', 0, NULL, '2'),
(10, 7, 15, NULL, '育苗', '2024-10-01 00:00:00', '2024-10-21 00:00:00', NULL, NULL, '', NULL, NULL, '', 0, 0, 17, '2024-10-16 10:26:33', 0, NULL, '0'),
(11, 7, 15, NULL, '施肥', '2024-10-15 00:00:00', '2024-10-28 00:00:00', NULL, NULL, '', NULL, NULL, '', 0, 0, 17, '2024-10-16 10:26:33', 0, NULL, '0'),
(12, 7, NULL, NULL, '测试任务', '2024-10-16 00:00:00', '2024-10-17 00:00:00', '2024-10-16 00:00:00', '2024-10-18 00:00:00', '测试', NULL, NULL, '', 0, 0, 17, '2024-10-16 11:07:00', 15, '2024-10-16 11:07:45', '0'),
(13, 8, 5, '杨成达', '播种', '2024-10-01 00:00:00', '2024-10-21 00:00:00', '2024-10-01 00:00:00', '2024-10-08 00:00:00', '', NULL, NULL, '2222', 0, 0, 12, '2024-10-18 14:47:07', 12, '2024-10-18 14:49:56', '0'),
(14, 8, 12, NULL, '浇水', '2024-10-22 00:00:00', '2024-11-04 00:00:00', NULL, NULL, '', NULL, NULL, '', 0, 0, 12, '2024-10-18 14:47:07', 0, NULL, '0'),
(15, 8, NULL, NULL, '额外添加任务', '2024-10-02 00:00:00', '2024-10-04 00:00:00', NULL, NULL, '', NULL, NULL, '', 0, 0, 12, '2024-10-18 14:48:05', 0, NULL, '0');

-- Create other tables with similar adjustments
DROP TABLE IF EXISTS agri_cost_employee;
CREATE TABLE agri_cost_employee (
  cost_id bigserial PRIMARY KEY,
  task_id bigint NOT NULL DEFAULT 0,
  employee_id bigint NOT NULL DEFAULT 0,
  working_hours numeric(8,2) NOT NULL DEFAULT 0.00,
  working_start timestamp,
  working_finish timestamp,
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);
COMMENT ON TABLE agri_cost_employee IS '人工工时表';
COMMENT ON COLUMN agri_cost_employee.cost_id IS 'ID';
COMMENT ON COLUMN agri_cost_employee.task_id IS '任务ID';
COMMENT ON COLUMN agri_cost_employee.employee_id IS '雇员ID';
COMMENT ON COLUMN agri_cost_employee.working_hours IS '工时';
COMMENT ON COLUMN agri_cost_employee.working_start IS '开始日期';
COMMENT ON COLUMN agri_cost_employee.working_finish IS '结束日期';
COMMENT ON COLUMN agri_cost_employee.remark IS '备注';
COMMENT ON COLUMN agri_cost_employee.status IS '状态';
COMMENT ON COLUMN agri_cost_employee.order_num IS '排序';
COMMENT ON COLUMN agri_cost_employee.create_by IS '创建者ID';
COMMENT ON COLUMN agri_cost_employee.create_time IS '创建时间';
COMMENT ON COLUMN agri_cost_employee.update_by IS '修改人ID';
COMMENT ON COLUMN agri_cost_employee.update_time IS '修改时间';
COMMENT ON COLUMN agri_cost_employee.del_flag IS '删除标志（0代表存在 2代表删除）';

INSERT INTO agri_cost_employee (cost_id, task_id, employee_id, working_hours, working_start, working_finish, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag)
VALUES (1, 1, 6, 30.00, '2024-09-26 00:00:00', '2024-09-27 00:00:00', '', '0', 0, 17, '2024-09-26 14:55:54', 0, NULL, '0');

DROP TABLE IF EXISTS agri_cost_material;
CREATE TABLE agri_cost_material (
  cost_id bigserial PRIMARY KEY,
  task_id bigint NOT NULL DEFAULT 0,
  material_id bigint NOT NULL DEFAULT 0,
  material_count integer NOT NULL DEFAULT 0,
  measure_unit varchar(10) NOT NULL DEFAULT '',
  working_start timestamp,
  working_finish timestamp,
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);
COMMENT ON TABLE agri_cost_material IS '农资用量表';
COMMENT ON COLUMN agri_cost_material.cost_id IS 'ID';
COMMENT ON COLUMN agri_cost_material.task_id IS '任务ID';
COMMENT ON COLUMN agri_cost_material.material_id IS '农资ID';
COMMENT ON COLUMN agri_cost_material.material_count IS '使用数量';
COMMENT ON COLUMN agri_cost_material.measure_unit IS '计量单位';
COMMENT ON COLUMN agri_cost_material.working_start IS '开始日期';
COMMENT ON COLUMN agri_cost_material.working_finish IS '结束日期';
COMMENT ON COLUMN agri_cost_material.remark IS '备注';
COMMENT ON COLUMN agri_cost_material.status IS '状态';
COMMENT ON COLUMN agri_cost_material.order_num IS '排序';
COMMENT ON COLUMN agri_cost_material.create_by IS '创建者ID';
COMMENT ON COLUMN agri_cost_material.create_time IS '创建时间';
COMMENT ON COLUMN agri_cost_material.update_by IS '修改人ID';
COMMENT ON COLUMN agri_cost_material.update_time IS '修改时间';
COMMENT ON COLUMN agri_cost_material.del_flag IS '删除标志（0代表存在 2代表删除）';

INSERT INTO agri_cost_material (cost_id, task_id, material_id, material_count, measure_unit, working_start, working_finish, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag)
VALUES (1, 1, 6, 2, '袋', '2024-10-15 00:00:00', '2024-10-16 00:00:00', '', '0', 0, 17, '2024-10-15 10:18:33', 0, NULL, '0');

DROP TABLE IF EXISTS agri_crop_batch;
CREATE TABLE agri_crop_batch (
  batch_id bigserial PRIMARY KEY,
  batch_name varchar(50) NOT NULL,
  germplasm_id bigint NOT NULL,
  start_time timestamp,
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0',
  batch_head bigint NOT NULL DEFAULT 0
);
COMMENT ON TABLE agri_crop_batch IS '作物批次表';
COMMENT ON COLUMN agri_crop_batch.batch_id IS '批次ID';
COMMENT ON COLUMN agri_crop_batch.batch_name IS '批次名称';
COMMENT ON COLUMN agri_crop_batch.germplasm_id IS '种质ID';
COMMENT ON COLUMN agri_crop_batch.start_time IS '开始时间';
COMMENT ON COLUMN agri_crop_batch.remark IS '备注';
COMMENT ON COLUMN agri_crop_batch.status IS '状态';
COMMENT ON COLUMN agri_crop_batch.order_num IS '排序';
COMMENT ON COLUMN agri_crop_batch.create_by IS '创建者ID';
COMMENT ON COLUMN agri_crop_batch.create_time IS '创建时间';
COMMENT ON COLUMN agri_crop_batch.update_by IS '修改人ID';
COMMENT ON COLUMN agri_crop_batch.update_time IS '修改时间';
COMMENT ON COLUMN agri_crop_batch.del_flag IS '删除标志（0代表存在 2代表删除）';
COMMENT ON COLUMN agri_crop_batch.batch_head IS '负责人Id';

INSERT INTO agri_crop_batch (batch_id, batch_name, germplasm_id, start_time, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag, batch_head)
VALUES 
(1, '黄瓜', 4, '2024-09-01 00:00:00', '', '0', 0, 9, '2024-09-18 11:44:21', 0, NULL, '2', 10),
(2, '番茄', 5, '2024-09-01 00:00:00', '', '0', 0, 9, '2024-09-18 11:45:23', 0, NULL, '2', 10),
(3, '兰花培育001', 6, '2024-09-26 00:00:00', '', '0', 0, 17, '2024-09-26 14:38:28', 0, NULL, '0', 17),
(4, '测试批次', 6, '2024-10-15 00:00:00', '', '0', 0, 17, '2024-10-15 10:19:51', 0, NULL, '2', 15),
(5, '测试', 6, '2024-10-15 00:00:00', '', '0', 0, 17, '2024-10-15 11:06:37', 0, NULL, '2', 15),
(6, '333', 6, '2024-10-15 00:00:00', '', '0', 0, 17, '2024-10-15 11:13:32', 0, NULL, '2', 17),
(7, '测试批次', 6, '2024-10-01 00:00:00', '', '0', 0, 17, '2024-10-16 10:26:33', 0, NULL, '0', 15),
(8, '测试', 4, '2024-10-01 00:00:00', '', '0', 0, 12, '2024-10-18 14:47:07', 0, NULL, '0', 12);

DROP TABLE IF EXISTS agri_employee;
CREATE TABLE agri_employee (
  employee_id bigserial PRIMARY KEY,
  employee_code varchar(20) NOT NULL DEFAULT '',
  employee_name varchar(10) NOT NULL DEFAULT '',
  employee_type char(1) NOT NULL DEFAULT '0',
  employee_tel varchar(20) NOT NULL DEFAULT '',
  employee_sex char(1) NOT NULL DEFAULT '0',
  employee_address varchar(200) NOT NULL DEFAULT '',
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);
COMMENT ON TABLE agri_employee IS '雇员表';
COMMENT ON COLUMN agri_employee.employee_id IS '雇员ID';
COMMENT ON COLUMN agri_employee.employee_code IS '编码';
COMMENT ON COLUMN agri_employee.employee_name IS '姓名';
COMMENT ON COLUMN agri_employee.employee_type IS '字典 agriculture_employee_type';
COMMENT ON COLUMN agri_employee.employee_tel IS '手机号码';
COMMENT ON COLUMN agri_employee.employee_sex IS '字典 sys_user_sex';
COMMENT ON COLUMN agri_employee.employee_address IS '地址';
COMMENT ON COLUMN agri_employee.remark IS '备注';
COMMENT ON COLUMN agri_employee.status IS '状态';
COMMENT ON COLUMN agri_employee.order_num IS '排序';
COMMENT ON COLUMN agri_employee.create_by IS '创建者ID';
COMMENT ON COLUMN agri_employee.create_time IS '创建时间';
COMMENT ON COLUMN agri_employee.update_by IS '修改人ID';
COMMENT ON COLUMN agri_employee.update_time IS '修改时间';
COMMENT ON COLUMN agri_employee.del_flag IS '删除标志（0代表存在 2代表删除）';

INSERT INTO agri_employee (employee_id, employee_code, employee_name, employee_type, employee_tel, employee_sex, employee_address, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag)
VALUES 
(1, '1001', '张三', '0', '1', '0', '江苏省盐城市', '', '0', 0, 10, '2023-05-17 13:57:38', 0, '2023-05-17 16:10:50', '0'),
(2, '002', '李四', '0', '18112857630', '0', '123', '', '0', 0, 1, '2023-05-18 09:16:02', 0, NULL, '2'),
(3, '002', '乔军', '0', '18112857630', '0', '盐城市', '', '0', 0, 1, '2023-06-12 14:18:44', 0, NULL, '0'),
(4, '001', '姚琼', '0', '18111118888', '1', '高教公寓', '', '0', 0, 8, '2023-08-26 13:46:51', 0, NULL, '2'),
(5, '002', '杨成达', '0', '18112857630', '0', '盐城市亭湖区', '', '0', 0, 10, '2024-08-19 10:56:25', 0, NULL, '0'),
(6, '002', '路人', '0', '18112822222', '0', '222', '', '0', 0, 17, '2024-09-26 14:46:23', 0, NULL, '0');

DROP TABLE IF EXISTS agri_germplasm;
CREATE TABLE agri_germplasm (
  germplasm_id bigserial PRIMARY KEY,
  crop_name varchar(50) NOT NULL DEFAULT '',
  crop_en_name varchar(50) NOT NULL DEFAULT '',
  germplasm_name varchar(50) NOT NULL DEFAULT '',
  germplasm_en_name varchar(50) NOT NULL DEFAULT '',
  germplasm_img varchar(100) NOT NULL DEFAULT '',
  germplasm_des varchar(500) NOT NULL DEFAULT '',
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);
COMMENT ON TABLE agri_germplasm IS '种质表';
COMMENT ON COLUMN agri_germplasm.germplasm_id IS '种质ID';
COMMENT ON COLUMN agri_germplasm.crop_name IS '作物名称';
COMMENT ON COLUMN agri_germplasm.crop_en_name IS '作物英文名称';
COMMENT ON COLUMN agri_germplasm.germplasm_name IS '种质名称';
COMMENT ON COLUMN agri_germplasm.germplasm_en_name IS '种质英文名称';
COMMENT ON COLUMN agri_germplasm.germplasm_img IS '种质图片';
COMMENT ON COLUMN agri_germplasm.germplasm_des IS '宣传语';
COMMENT ON COLUMN agri_germplasm.remark IS '备注';
COMMENT ON COLUMN agri_germplasm.status IS '状态';
COMMENT ON COLUMN agri_germplasm.order_num IS '排序';
COMMENT ON COLUMN agri_germplasm.create_by IS '创建者ID';
COMMENT ON COLUMN agri_germplasm.create_time IS '创建时间';
COMMENT ON COLUMN agri_germplasm.update_by IS '修改人ID';
COMMENT ON COLUMN agri_germplasm.update_time IS '修改时间';
COMMENT ON COLUMN agri_germplasm.del_flag IS '删除标志（0代表存在 2代表删除）';

INSERT INTO agri_germplasm (germplasm_id, crop_name, crop_en_name, germplasm_name, germplasm_en_name, germplasm_img, germplasm_des, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag)
VALUES 
(1, '西瓜', 'watermelon', '西瓜', 'watermelon', '/profile/iot/1/2023-0803-095310.png', '西瓜是一种夏季消暑的好选择，其含有丰富的水分和天然的清凉成分，能够有效地降低体温。除了降热解暑，西瓜皮还可以用来炒着吃或伴着吃，制成药材来治疗肾炎水肿、肝病黄疸等疾病，甚至可以作为美容材料用来敷脸。因此，夏季食用西瓜不仅能够解暑，还能保持身体健康。', '1', '0', 0, 1, '2023-05-18 09:35:37', 1, '2023-08-03 09:53:12', '0'),
(2, '番茄', 'tomato', '番茄', 'tomato', '/profile/iot/1/2023-0803-095406.png', '1212', '', '0', 0, 1, '2023-05-23 21:01:12', 1, '2023-08-03 09:54:08', '0'),
(3, '胡萝卜', 'carrot', '胡萝卜', 'carrot', '/profile/iot/1/2023-0803-095052.png', '胡萝卜（Daucus carota var. sativa Hoffm.），是伞形科胡萝卜属一年生或二年生草本植物。转基因胡萝卜：表面较光滑。胡萝卜根肉质，长圆锥形，呈橙红色或黄色；茎单生，全株被白色粗硬毛；叶片长圆形，先端尖锐；茎生叶近无柄，有叶鞘，末回裂片小或细长；花通常白色，有时带淡红色；花柄不等长；', '', '0', 0, 9, '2023-08-03 09:51:18', 9, NULL, '0'),
(4, '黄瓜', 'huanggua', '黄瓜', 'huanggua', '/profile/iot/10/2024-0819-113117.jpg', '好吃的黄瓜', '', '0', 0, 10, '2024-08-19 11:31:26', 0, NULL, '0'),
(5, '番茄', 'fanqie', '番茄', 'fanqie', '/profile/iot/9/2024-0918-114314.jpg', '123', '', '0', 0, 9, '2024-09-18 11:43:18', 0, NULL, '0'),
(6, '兰花', 'lanhua', '兰花根茎', 'lanhuagenjin', '/profile/iot/17/2024-0926-143656.jpg', '兰花', '', '0', 0, 17, '2024-09-26 14:37:00', 0, NULL, '0');

DROP TABLE IF EXISTS agri_germplasm_intro;
CREATE TABLE agri_germplasm_intro (
  intro_id bigserial PRIMARY KEY,
  germplasm_id bigint NOT NULL,
  intro_name varchar(50) NOT NULL DEFAULT '',
  intro_img varchar(100) NOT NULL DEFAULT '',
  intro_des varchar(1000) NOT NULL DEFAULT '',
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);
COMMENT ON TABLE agri_germplasm_intro IS '种质介绍表';
COMMENT ON COLUMN agri_germplasm_intro.intro_id IS '介绍ID';
COMMENT ON COLUMN agri_germplasm_intro.germplasm_id IS '种质ID';
COMMENT ON COLUMN agri_germplasm_intro.intro_name IS '名称';
COMMENT ON COLUMN agri_germplasm_intro.intro_img IS '图片';
COMMENT ON COLUMN agri_germplasm_intro.intro_des IS '描述';
COMMENT ON COLUMN agri_germplasm_intro.remark IS '备注';
COMMENT ON COLUMN agri_germplasm_intro.status IS '状态';
COMMENT ON COLUMN agri_germplasm_intro.order_num IS '排序';
COMMENT ON COLUMN agri_germplasm_intro.create_by IS '创建者ID';
COMMENT ON COLUMN agri_germplasm_intro.create_time IS '创建时间';
COMMENT ON COLUMN agri_germplasm_intro.update_by IS '修改人ID';
COMMENT ON COLUMN agri_germplasm_intro.update_time IS '修改时间';
COMMENT ON COLUMN agri_germplasm_intro.del_flag IS '删除标志（0代表存在 2代表删除）';

INSERT INTO agri_germplasm_intro (intro_id, germplasm_id, intro_name, intro_img, intro_des, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag)
VALUES 
(1, 2, '1', '/profile/iot/1/2023-0523-215817.png', '111', '', '0', 0, 0, '2023-05-23 21:58:19', 0, NULL, '0'),
(2, 2, '121', '/profile/iot/1/2023-0523-220029.png', '1121', '', '0', 0, 0, '2023-05-23 22:00:32', 0, NULL, '0'),
(3, 1, '介绍测试', '/profile/iot/1/2023-0524-135509.png', '111', '', '0', 0, 0, '2023-05-24 13:55:12', 0, NULL, '0');

DROP TABLE IF EXISTS agri_machine_info;
CREATE TABLE agri_machine_info (
  machine_id bigserial PRIMARY KEY,
  machine_code varchar(20) NOT NULL DEFAULT '',
  machine_name varchar(50) NOT NULL DEFAULT '',
  machine_type_id bigint NOT NULL DEFAULT 0,
  measure_unit varchar(10) NOT NULL DEFAULT '',
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);
COMMENT ON TABLE agri_machine_info IS '机械信息表';
COMMENT ON COLUMN agri_machine_info.machine_id IS '机械ID';
COMMENT ON COLUMN agri_machine_info.machine_code IS '机械编码';
COMMENT ON COLUMN agri_machine_info.machine_name IS '机械名称';
COMMENT ON COLUMN agri_machine_info.machine_type_id IS '机械类别';
COMMENT ON COLUMN agri_machine_info.measure_unit IS '计量单位';
COMMENT ON COLUMN agri_machine_info.remark IS '备注';
COMMENT ON COLUMN agri_machine_info.status IS '状态';
COMMENT ON COLUMN agri_machine_info.order_num IS '排序';
COMMENT ON COLUMN agri_machine_info.create_by IS '创建者ID';
COMMENT ON COLUMN agri_machine_info.create_time IS '创建时间';
COMMENT ON COLUMN agri_machine_info.update_by IS '修改人ID';
COMMENT ON COLUMN agri_machine_info.update_time IS '修改时间';
COMMENT ON COLUMN agri_machine_info.del_flag IS '删除标志（0代表存在 2代表删除）';

INSERT INTO agri_machine_info (machine_id, machine_code, machine_name, machine_type_id, measure_unit, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag)
VALUES 
(1, '001', '东方红拖拉机001', 1, '辆', '', '0', 0, 1, '2023-06-13 09:30:20', 0, NULL, '0'),
(2, '003', '测试农机', 3, '亮', '', '0', 0, 9, '2024-08-07 18:09:18', 0, NULL, '0'),
(3, '003', '33', 5, '33', '33', '0', 0, 10, '2024-08-19 13:38:53', 0, NULL, '0'),
(4, '001', '东方红拖拉机', 6, '台', '', '0', 0, 17, '2024-10-15 10:12:33', 0, NULL, '0');

DROP TABLE IF EXISTS agri_machine_type;
CREATE TABLE agri_machine_type (
  machine_type_id bigserial PRIMARY KEY,
  machine_type_name varchar(50) NOT NULL DEFAULT '',
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);
COMMENT ON TABLE agri_machine_type IS '机械类别表';
COMMENT ON COLUMN agri_machine_type.machine_type_id IS '机械类别ID';
COMMENT ON COLUMN agri_machine_type.machine_type_name IS '机械类别名称';
COMMENT ON COLUMN agri_machine_type.remark IS '备注';
COMMENT ON COLUMN agri_machine_type.status IS '状态';
COMMENT ON COLUMN agri_machine_type.order_num IS '排序';
COMMENT ON COLUMN agri_machine_type.create_by IS '创建者ID';
COMMENT ON COLUMN agri_machine_type.create_time IS '创建时间';
COMMENT ON COLUMN agri_machine_type.update_by IS '修改人ID';
COMMENT ON COLUMN agri_machine_type.update_time IS '修改时间';
COMMENT ON COLUMN agri_machine_type.del_flag IS '删除标志（0代表存在 2代表删除）';

INSERT INTO agri_machine_type (machine_type_id, machine_type_name, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag)
VALUES 
(1, '拖拉机', '2', '0', 3, 1, '2023-06-02 21:25:43', 1, '2023-06-13 09:29:33', '0'),
(2, '收割机', '', '0', 4, 1, '2023-06-13 09:29:38', 1, '2024-05-07 11:42:59', '0'),
(3, '插秧机', '', '0', 0, 9, '2023-06-13 09:29:43', 0, NULL, '0'),
(4, '无人机', '', '0', 0, 9, '2023-08-26 13:41:04', 0, NULL, '0'),
(5, '姚琼测试数据', '', '0', 0, 10, '2024-08-19 13:38:37', 0, NULL, '0'),
(6, '拖拉机', '', '0', 0, 17, '2024-10-15 10:12:09', 0, NULL, '0');

-- Table structure for agri_material_info
DROP TABLE IF EXISTS agri_material_info;
CREATE TABLE agri_material_info (
  material_id bigserial PRIMARY KEY,
  material_code varchar(20) NOT NULL DEFAULT '',
  material_name varchar(50) NOT NULL DEFAULT '',
  material_type_id bigint NOT NULL DEFAULT 0,
  measure_unit varchar(10) NOT NULL DEFAULT '',
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);

COMMENT ON TABLE agri_material_info IS '农资信息表';
COMMENT ON COLUMN agri_material_info.material_id IS '农资ID';
COMMENT ON COLUMN agri_material_info.material_code IS '农资编码';
COMMENT ON COLUMN agri_material_info.material_name IS '农资名称';
COMMENT ON COLUMN agri_material_info.material_type_id IS '农资类别';
COMMENT ON COLUMN agri_material_info.measure_unit IS '计量单位';
COMMENT ON COLUMN agri_material_info.remark IS '备注';
COMMENT ON COLUMN agri_material_info.status IS '状态';
COMMENT ON COLUMN agri_material_info.order_num IS '排序';
COMMENT ON COLUMN agri_material_info.create_by IS '创建者ID';
COMMENT ON COLUMN agri_material_info.create_time IS '创建时间';
COMMENT ON COLUMN agri_material_info.update_by IS '修改人ID';
COMMENT ON COLUMN agri_material_info.update_time IS '修改时间';
COMMENT ON COLUMN agri_material_info.del_flag IS '删除标志（0代表存在 2代表删除）';

-- Records of agri_material_info
INSERT INTO agri_material_info (material_id, material_code, material_name, material_type_id, measure_unit, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag) VALUES 
(1, '001', '钾肥', 1, '袋', '', '0', 0, 1, '2023-06-13 09:41:12', 0, NULL, '0'),
(2, '002', '磷肥', 1, '袋', '', '0', 0, 1, '2023-06-13 09:41:29', 0, NULL, '0'),
(3, '003', '镰刀', 2, '把', '', '0', 0, 1, '2023-06-13 09:41:45', 0, NULL, '0'),
(4, '004', '大镰刀', 2, '把', '', '0', 0, 9, '2023-06-13 09:46:52', 0, NULL, '0'),
(5, '9930', '姚琼测试', 3, '把', '', '0', 0, 10, '2024-08-19 11:46:51', 0, NULL, '0'),
(6, '001', '钾肥', 4, '袋', '', '0', 0, 17, '2024-10-15 10:13:23', 0, NULL, '0');

-- Table structure for agri_material_type
DROP TABLE IF EXISTS agri_material_type;
CREATE TABLE agri_material_type (
  material_type_id bigserial PRIMARY KEY,
  material_type_name varchar(50) NOT NULL DEFAULT '',
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);

COMMENT ON TABLE agri_material_type IS '农资类别表';
COMMENT ON COLUMN agri_material_type.material_type_id IS '农资类别ID';
COMMENT ON COLUMN agri_material_type.material_type_name IS '农资类别名称';
COMMENT ON COLUMN agri_material_type.remark IS '备注';
COMMENT ON COLUMN agri_material_type.status IS '状态';
COMMENT ON COLUMN agri_material_type.order_num IS '排序';
COMMENT ON COLUMN agri_material_type.create_by IS '创建者ID';
COMMENT ON COLUMN agri_material_type.create_time IS '创建时间';
COMMENT ON COLUMN agri_material_type.update_by IS '修改人ID';
COMMENT ON COLUMN agri_material_type.update_time IS '修改时间';
COMMENT ON COLUMN agri_material_type.del_flag IS '删除标志（0代表存在 2代表删除）';

-- Records of agri_material_type
INSERT INTO agri_material_type (material_type_id, material_type_name, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag) VALUES 
(1, '肥料', '', '0', 2, 1, '2023-06-13 09:40:21', 1, '2024-05-07 11:42:04', '0'),
(2, '镰刀', '', '0', 1, 9, '2023-06-13 09:40:46', 1, '2024-05-07 11:42:09', '0'),
(3, '姚琼测试类别', '', '0', 0, 10, '2024-08-19 11:43:33', 0, NULL, '0'),
(4, '化肥', '', '0', 0, 17, '2024-10-15 10:12:50', 0, NULL, '0');

-- Table structure for agri_plant_method
DROP TABLE IF EXISTS agri_plant_method;
CREATE TABLE agri_plant_method (
  method_id bigserial PRIMARY KEY,
  germplasm_id bigint NOT NULL,
  method_name varchar(50) NOT NULL DEFAULT '',
  method_img varchar(100) NOT NULL DEFAULT '',
  method_des varchar(1000) NOT NULL DEFAULT '',
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);

COMMENT ON TABLE agri_plant_method IS '种植方法表';
COMMENT ON COLUMN agri_plant_method.method_id IS '方法ID';
COMMENT ON COLUMN agri_plant_method.germplasm_id IS '种质ID';
COMMENT ON COLUMN agri_plant_method.method_name IS '名称';
COMMENT ON COLUMN agri_plant_method.method_img IS '图片';
COMMENT ON COLUMN agri_plant_method.method_des IS '描述';
COMMENT ON COLUMN agri_plant_method.remark IS '备注';
COMMENT ON COLUMN agri_plant_method.status IS '状态';
COMMENT ON COLUMN agri_plant_method.order_num IS '排序';
COMMENT ON COLUMN agri_plant_method.create_by IS '创建者ID';
COMMENT ON COLUMN agri_plant_method.create_time IS '创建时间';
COMMENT ON COLUMN agri_plant_method.update_by IS '修改人ID';
COMMENT ON COLUMN agri_plant_method.update_time IS '修改时间';
COMMENT ON COLUMN agri_plant_method.del_flag IS '删除标志（0代表存在 2代表删除）';

-- Records of agri_plant_method
INSERT INTO agri_plant_method (method_id, germplasm_id, method_name, method_img, method_des, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag) VALUES 
(1, 1, '方法2222', '/profile/iot/1/2023-0523-211723.png', '是的撒的撒的', '', '0', 0, 0, '2023-05-23 20:58:40', 0, '2023-05-24 13:55:47', '0'),
(2, 2, '方法', '/profile/iot/1/2023-0523-214207.png', '猜猜猜', '', '0', 0, 0, '2023-05-23 21:42:12', 0, '2023-05-24 13:55:34', '0');

-- Table structure for agri_standard_job
DROP TABLE IF EXISTS agri_standard_job;
CREATE TABLE agri_standard_job (
  job_id bigserial PRIMARY KEY,
  germplasm_id bigint NOT NULL DEFAULT 0,
  job_name varchar(50) NOT NULL DEFAULT '',
  cycle_unit char(1) NOT NULL DEFAULT '0',
  job_start integer NOT NULL DEFAULT 0,
  job_finish integer NOT NULL DEFAULT 0,
  remark varchar(2000) NOT NULL DEFAULT '',
  status char(1) NOT NULL DEFAULT '0',
  order_num integer NOT NULL DEFAULT 0,
  create_by bigint NOT NULL DEFAULT 0,
  create_time timestamp,
  update_by bigint DEFAULT 0,
  update_time timestamp,
  del_flag char(1) NOT NULL DEFAULT '0'
);

COMMENT ON TABLE agri_standard_job IS '标准作业任务表';
COMMENT ON COLUMN agri_standard_job.job_id IS '作业任务ID';
COMMENT ON COLUMN agri_standard_job.germplasm_id IS '种质ID';
COMMENT ON COLUMN agri_standard_job.job_name IS '作业任务名称';
COMMENT ON COLUMN agri_standard_job.cycle_unit IS '作业周期单位（0代表周 1代表天）';
COMMENT ON COLUMN agri_standard_job.job_start IS '起始周/天';
COMMENT ON COLUMN agri_standard_job.job_finish IS '结束周/天';
COMMENT ON COLUMN agri_standard_job.remark IS '备注';
COMMENT ON COLUMN agri_standard_job.status IS '状态';
COMMENT ON COLUMN agri_standard_job.order_num IS '排序';
COMMENT ON COLUMN agri_standard_job.create_by IS '创建者ID';
COMMENT ON COLUMN agri_standard_job.create_time IS '创建时间';
COMMENT ON COLUMN agri_standard_job.update_by IS '修改人ID';
COMMENT ON COLUMN agri_standard_job.update_time IS '修改时间';
COMMENT ON COLUMN agri_standard_job.del_flag IS '删除标志（0代表存在 2代表删除）';

-- Records of agri_standard_job
INSERT INTO agri_standard_job (job_id, germplasm_id, job_name, cycle_unit, job_start, job_finish, remark, status, order_num, create_by, create_time, update_by, update_time, del_flag) VALUES 
(1, 2, '播种', '1', 1, 3, '', '0', 0, 1, '2023-05-25 22:15:16', 1, '2023-05-30 09:37:59', '0'),
(2, 2, '浇水', '1', 2, 4, '', '0', 0, 1, '2023-05-30 09:38:25', 0, NULL, '0'),
(3, 2, '采摘', '1', 5, 6, '', '0', 0, 1, '2023-05-30 09:38:47', 0, NULL, '0'),
(4, 1, '育苗', '1', 1, 3, '', '0', 0, 1, '2023-05-30 17:40:31', 0, NULL, '0'),
(5, 1, '铺膜', '1', 3, 4, '', '0', 0, 1, '2023-05-30 17:41:18', 0, NULL, '0'),
(6, 1, '育苗完成', '1', 5, 6, '', '0', 0, 1, '2023-05-30 17:41:39', 0, NULL, '0'),
(7, 1, '定植', '1', 6, 7, '', '0', 0, 1, '2023-05-30 17:41:56', 0, NULL, '0'),
(8, 1, '植保防护', '1', 7, 8, '', '0', 0, 1, '2023-05-30 17:42:25', 0, NULL, '0'),
(9, 1, '授粉', '1', 9, 10, '', '0', 0, 1, '2023-05-30 17:42:49', 0, NULL, '0'),
(10, 1, '修剪侧蔓', '1', 11, 12, '', '0', 0, 1, '2023-05-30 17:45:54', 0, NULL, '0'),
(11, 1, '坐果期', '1', 12, 13, '', '0', 0, 1, '2023-05-30 17:46:13', 0, NULL, '0'),
(12, 1, '膨果期', '1', 13, 14, '', '0', 0, 1, '2023-05-30 17:46:37', 0, NULL, '0'),
(13, 1, '结果期', '1', 14, 15, '', '0', 0, 1, '2023-05-30 17:46:52', 0, NULL, '0');