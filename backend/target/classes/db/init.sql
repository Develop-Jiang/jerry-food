-- 创建数据库
CREATE DATABASE IF NOT EXISTS jerry_food DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE jerry_food;

-- 家庭信息表
CREATE TABLE IF NOT EXISTS home_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    home_name VARCHAR(100) NOT NULL COMMENT '家庭名称',
    wardrobe_style_id BIGINT COMMENT '衣柜样式ID',
    fridge_style_id BIGINT COMMENT '冰箱样式ID',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除 0-否 1-是'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家庭信息表';

-- 衣柜样式表
CREATE TABLE IF NOT EXISTS wardrobe_style (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    style_name VARCHAR(50) NOT NULL COMMENT '样式名称',
    style_code VARCHAR(20) NOT NULL UNIQUE COMMENT '样式代码',
    description VARCHAR(200) COMMENT '样式描述',
    color VARCHAR(20) COMMENT '主色调',
    image_url VARCHAR(500) COMMENT '样式图片URL',
    is_default TINYINT(1) DEFAULT 0 COMMENT '是否默认样式',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    status TINYINT(1) DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='衣柜样式表';

-- 冰箱样式表
CREATE TABLE IF NOT EXISTS fridge_style (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    style_name VARCHAR(50) NOT NULL COMMENT '样式名称',
    style_code VARCHAR(20) NOT NULL UNIQUE COMMENT '样式代码',
    description VARCHAR(200) COMMENT '样式描述',
    color VARCHAR(20) COMMENT '主色调',
    image_url VARCHAR(500) COMMENT '样式图片URL',
    is_default TINYINT(1) DEFAULT 0 COMMENT '是否默认样式',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    status TINYINT(1) DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冰箱样式表';

-- 衣物表
CREATE TABLE IF NOT EXISTS clothing (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '衣物名称',
    category VARCHAR(50) COMMENT '分类（上衣、裤子、裙子、外套等）',
    brand VARCHAR(100) COMMENT '品牌',
    size VARCHAR(20) COMMENT '尺码',
    color VARCHAR(30) COMMENT '颜色',
    season VARCHAR(20) COMMENT '季节（春、夏、秋、冬）',
    material VARCHAR(100) COMMENT '材质',
    price DECIMAL(10,2) COMMENT '价格',
    purchase_date DATE COMMENT '购买日期',
    image_url VARCHAR(500) COMMENT '衣物图片URL',
    thumbnail_url VARCHAR(500) COMMENT '缩略图URL',
    notes TEXT COMMENT '备注',
    home_id BIGINT COMMENT '所属家庭ID',
    wardrobe_position INT COMMENT '衣柜位置（1-左上，2-右上，3-左下，4-右下）',
    favorite TINYINT(1) DEFAULT 0 COMMENT '是否收藏',
    wear_count INT DEFAULT 0 COMMENT '穿着次数',
    status TINYINT(1) DEFAULT 1 COMMENT '状态 0-已删除 1-正常',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (home_id) REFERENCES home_info(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='衣物表';

-- 插入默认衣柜样式数据
INSERT INTO wardrobe_style (style_name, style_code, description, color, is_default, sort_order) VALUES
('木质经典款', 'WOODEN_CLASSIC', '温暖木质纹理，适合各种装修风格', '#8B4513', 1, 1),
('现代简约款', 'MODERN_SIMPLE', '简洁线条设计，现代感十足', '#2F4F4F', 0, 2),
('欧式奢华款', 'EUROPEAN_LUXURY', '雕花装饰，彰显高贵气质', '#DAA520', 0, 3),
('北欧清新款', 'NORDIC_FRESH', '浅色系设计，清新自然', '#F5DEB3', 0, 4);

-- 插入默认冰箱样式数据
INSERT INTO fridge_style (style_name, style_code, description, color, is_default, sort_order) VALUES
('双门经典款', 'DOUBLE_DOOR_CLASSIC', '经典上下双门设计', '#87CEEB', 1, 1),
('对开门豪华款', 'FRENCH_DOOR', '法式对开门，大容量存储', '#4682B4', 0, 2),
('单门简约款', 'SINGLE_DOOR', '小巧精致，节省空间', '#ADD8E6', 0, 3),
('复古怀旧款', 'VINTAGE_RETRO', '复古造型，独特风格', '#708090', 0, 4);

-- 插入默认家庭信息
INSERT INTO home_info (home_name, wardrobe_style_id, fridge_style_id) VALUES
('我的温馨小家', 1, 1);

-- 插入示例衣物数据
INSERT INTO clothing (name, category, brand, size, color, season, material, price, home_id, wardrobe_position) VALUES
('白色衬衫', '衬衫', 'UNIQLO', 'M', '白色', '春夏', '棉质', 199.00, 1, 1),
('蓝色T恤', 'T恤', 'H&M', 'L', '蓝色', '夏季', '纯棉', 99.00, 1, 2),
('黑色外套', '外套', 'ZARA', 'M', '黑色', '秋冬', '聚酯纤维', 599.00, 1, 3),
('牛仔裤', '裤子', 'Levis', '32', '蓝色', '四季', '牛仔布', 699.00, 1, 4),
('碎花连衣裙', '连衣裙', 'ONLY', 'S', '粉色', '夏季', '雪纺', 299.00, 1, 1),
('羊毛围巾', '配饰', 'BURBERRY', '', '米色', '冬季', '羊毛', 1299.00, 1, 2);