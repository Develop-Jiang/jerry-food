Page({
  onLoad() {
    console.log('关于页面加载');
  },
  
  goBack() {
    wx.navigateBack();
  }
})