Page({
  data: {
    serverResponse: ''
  },
  
  onLoad() {
    console.log('首页加载');
  },
  
  goToAbout() {
    wx.navigateTo({
      url: '../about/about'
    });
  },
  
  testLocalServer() {
    wx.request({
      url: 'http://localhost:3000/api/test',
      method: 'GET',
      success: (res) => {
        console.log('服务器响应:', res.data);
        this.setData({
          serverResponse: res.data.message
        });
      },
      fail: (err) => {
        console.error('请求失败:', err);
        this.setData({
          serverResponse: '服务器连接失败，请确保本地服务器已启动'
        });
      }
    });
  }
})