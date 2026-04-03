const express = require('express');
const cors = require('cors');
const app = express();
const port = 3000;

// 配置CORS
app.use(cors({
  origin: '*',
  methods: ['GET', 'POST'],
  allowedHeaders: ['Content-Type']
}));

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

// 启动服务器
app.listen(port, () => {
  console.log(`本地服务器运行在 http://localhost:${port}`);
  console.log(`测试接口: http://localhost:${port}/api/test`);
  console.log(`健康检查: http://localhost:${port}/api/health`);
});