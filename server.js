const express = require('express');
const cors = require('cors');
const path = require('path');
const app = express();
const port = 3000;

// 配置CORS
app.use(cors({
  origin: '*',
  methods: ['GET', 'POST'],
  allowedHeaders: ['Content-Type']
}));

// 静态文件服务
app.use(express.static(path.join(__dirname, 'public')));

// 根路径 - 返回温馨小家页面
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

// 测试接口
app.get('/api/test', (req, res) => {
  res.json({
    message: '本地服务器连接成功！',
    timestamp: new Date().toISOString()
  });
});

// 健康检查接口
app.get('/api/health', (req, res) => {
  res.json({
    status: 'ok',
    message: '服务器运行正常'
  });
});

// 衣柜物品接口
app.get('/api/wardrobe', (req, res) => {
  const wardrobeItems = [
    { id: 1, icon: '👔', name: '衬衫', count: 5 },
    { id: 2, icon: '👕', name: 'T恤', count: 8 },
    { id: 3, icon: '🧥', name: '外套', count: 3 },
    { id: 4, icon: '👖', name: '牛仔裤', count: 4 },
    { id: 5, icon: '👗', name: '连衣裙', count: 6 },
    { id: 6, icon: '🧣', name: '围巾', count: 2 }
  ];
  
  res.json({
    success: true,
    data: wardrobeItems,
    message: '衣柜物品获取成功'
  });
});

// 冰箱物品接口
app.get('/api/fridge', (req, res) => {
  const fridgeItems = [
    { id: 1, icon: '🥛', name: '牛奶', count: 2 },
    { id: 2, icon: '🥚', name: '鸡蛋', count: 12 },
    { id: 3, icon: '🍎', name: '苹果', count: 5 },
    { id: 4, icon: '🥬', name: '蔬菜', count: 3 },
    { id: 5, icon: '🍖', name: '肉类', count: 1 },
    { id: 6, icon: '🧊', name: '冰块', count: 20 }
  ];
  
  res.json({
    success: true,
    data: fridgeItems,
    message: '冰箱物品获取成功'
  });
});

// 启动服务器
app.listen(port, () => {
  console.log(`\n🏠 本地服务器运行在 http://localhost:${port}`);
  console.log(`\n📱 可用页面:`);
  console.log(`   主页: http://localhost:${port}/`);
  console.log(`   API测试: http://localhost:${port}/api/test`);
  console.log(`   健康检查: http://localhost:${port}/api/health`);
  console.log(`   衣柜接口: http://localhost:${port}/api/wardrobe`);
  console.log(`   冰箱接口: http://localhost:${port}/api/fridge\n`);
});