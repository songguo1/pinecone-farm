CREATE TABLE agri_documents (
    document_id BIGSERIAL PRIMARY KEY,   -- 文档唯一标识
    document_type_id INT NOT NULL,      -- 文档类型ID
    title VARCHAR(255) ,         -- 文档标题
    content TEXT,                        -- 文档内容（可以存储Markdown格式）
    file_path VARCHAR(255),              -- 文件路径（如果存储PDF或Word文档）
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 创建时间
    update_time TIMESTAMP,               -- 修改时间
    create_by VARCHAR(50),               -- 创建人
    update_by VARCHAR(50),               -- 修改人
    remark TEXT                          -- 备注信息
);

COMMENT ON TABLE agri_documents IS '系统文档表';
COMMENT ON COLUMN agri_documents.document_id IS '文档唯一标识';
COMMENT ON COLUMN agri_documents.document_type_id IS '文档类型ID';
COMMENT ON COLUMN agri_documents.title IS '文档标题';
COMMENT ON COLUMN agri_documents.file_path IS '文档文件路径';
COMMENT ON COLUMN agri_documents.create_time IS '创建时间';
COMMENT ON COLUMN agri_documents.update_time IS '修改时间';
COMMENT ON COLUMN agri_documents.create_by IS '创建人';
COMMENT ON COLUMN agri_documents.update_by IS '修改人';
COMMENT ON COLUMN agri_documents.remark IS '备注信息';

-- 文档类型
CREATE TABLE agri_document_types (
    document_type_id SERIAL PRIMARY KEY,   -- 文档类型唯一标识
    document_type_name VARCHAR(50) NOT NULL,  -- 文档类型名称
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 创建时间
    update_time TIMESTAMP,               -- 修改时间
    create_by VARCHAR(50),               -- 创建人
    update_by VARCHAR(50),               -- 修改人
);

COMMENT ON TABLE agri_document_types IS '文档类型表';
COMMENT ON COLUMN agri_document_types.document_type_id IS '文档类型唯一标识';
COMMENT ON COLUMN agri_document_types.document_type_name IS '文档类型名称';
COMMENT ON COLUMN agri_document_types.create_time IS '创建时间';
COMMENT ON COLUMN agri_document_types.update_time IS '修改时间';
COMMENT ON COLUMN agri_document_types.create_by IS '创建人';
COMMENT ON COLUMN agri_document_types.update_by IS '修改人';

CREATE TABLE agri_drone_photos (
    photo_id BIGSERIAL PRIMARY KEY,      -- 图片唯一标识
    photo_path VARCHAR(255) NOT NULL,    -- 图片存储路径
    task_id VARCHAR(50),                -- 任务调度ID
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 创建时间
    update_time TIMESTAMP,               -- 修改时间
    create_by VARCHAR(50),               -- 创建人
    update_by VARCHAR(50),               -- 修改人
);

COMMENT ON TABLE agri_drone_photos IS '航拍图片表';
COMMENT ON COLUMN agri_drone_photos.photo_id IS '图片唯一标识';
COMMENT ON COLUMN agri_drone_photos.photo_path IS '图片存储路径';
COMMENT ON COLUMN agri_drone_photos.task_id IS '任务调度ID';
COMMENT ON COLUMN agri_drone_photos.create_time IS '创建时间';
COMMENT ON COLUMN agri_drone_photos.update_time IS '修改时间';
COMMENT ON COLUMN agri_drone_photos.create_by IS '创建人';
COMMENT ON COLUMN agri_drone_photos.update_by IS '修改人';

CREATE TABLE agri_pest_photos (
    photo_id BIGSERIAL PRIMARY KEY,      -- 照片唯一标识
    photo_path VARCHAR(255) NOT NULL,    -- 照片存储路径
    capture_time TIMESTAMP,              -- 照片拍摄时间
    status int,     -- 识别结果（系统识别后填写）
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 创建时间
    update_time TIMESTAMP,               -- 修改时间
    create_by VARCHAR(50),               -- 创建人
    update_by VARCHAR(50),               -- 修改人
);

COMMENT ON TABLE agri_pest_photos IS '病虫害照片表';
COMMENT ON COLUMN agri_pest_photos.photo_id IS '照片唯一标识';
COMMENT ON COLUMN agri_pest_photos.photo_path IS '照片存储路径';
COMMENT ON COLUMN agri_pest_photos.capture_time IS '照片拍摄时间';
COMMENT ON COLUMN agri_pest_photos.status IS '病虫害识别';
COMMENT ON COLUMN agri_pest_photos.create_time IS '创建时间';
COMMENT ON COLUMN agri_pest_photos.update_time IS '修改时间';
COMMENT ON COLUMN agri_pest_photos.create_by IS '创建人';
COMMENT ON COLUMN agri_pest_photos.update_by IS '修改人';

