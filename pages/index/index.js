Page({
  data: {
    showWardrobeModal: false,
    showFridgeModal: false,
    wardrobeAnimation: {},
    fridgeAnimation: {},
    
    // 后端API配置
    API_BASE_URL: 'http://localhost:8080/api',
    homeId: 1, // 默认家庭ID
    
    wardrobeItems: [],
    fridgeItems: [],
    
    // 家庭信息
    homeName: '我的温馨小家'
  },

  onLoad() {
    console.log('温馨小家页面加载');
    this.initPage();
  },
  
  // 初始化页面
  initPage() {
    console.log('🏠 开始初始化页面...');
    this.loadHomeInfo();
  },
  
  // 加载家庭信息
  loadHomeInfo() {
    wx.request({
      url: `${this.data.API_BASE_URL}/home/info`,
      method: 'GET',
      success: (res) => {
        console.log('📋 家庭信息:', res.data);
        if (res.data.code === 200 && res.data.data) {
          const homeInfo = res.data.data;
          this.setData({
            homeId: homeInfo.id,
            homeName: homeInfo.homeName || '我的温馨小家'
          });
          console.log(`✅ 家庭名称: ${this.data.homeName}`);
        }
      },
      fail: (err) => {
        console.error('❌ 获取家庭信息失败:', err);
        wx.showToast({
          title: '后端服务连接失败',
          icon: 'none',
          duration: 2000
        });
      }
    });
  },

  openWardrobe() {
    this.createAnimation(() => {
      this.setData({
        showWardrobeModal: true
      });
      
      // 从后端加载衣物数据
      this.loadClothingFromBackend();
    });
  },
  
  // 从后端加载衣物列表
  loadClothingFromBackend() {
    wx.showLoading({ title: '加载中...' });
    
    wx.request({
      url: `${this.data.API_BASE_URL}/clothing/list`,
      method: 'GET',
      data: { homeId: this.data.homeId },
      success: (res) => {
        wx.hideLoading();
        
        if (res.data.code === 200 && res.data.data) {
          const clothingList = res.data.data;
          
          // 转换数据格式以适配前端显示
          const formattedItems = clothingList.map(item => ({
            id: item.id,
            icon: this.getCategoryIcon(item.category),
            name: item.name || '未命名',
            count: item.category || '未分类',
            imageUrl: item.imageUrl
          }));
          
          this.setData({
            wardrobeItems: formattedItems.length > 0 ? formattedItems : this.getDefaultWardrobeItems()
          });
          
          console.log(`✅ 衣柜数据加载成功: ${formattedItems.length}件`);
        } else {
          console.warn('⚠️ 使用默认数据');
          this.setData({
            wardrobeItems: this.getDefaultWardrobeItems()
          });
        }
      },
      fail: (err) => {
        wx.hideLoading();
        console.error('❌ 加载衣柜数据失败:', err);
        
        // 降级：使用默认数据
        this.setData({
          wardrobeItems: this.getDefaultWardrobeItems()
        });
        
        wx.showToast({
          title: '使用演示数据',
          icon: 'none',
          duration: 1500
        });
      }
    });
  },
  
  // 根据分类返回图标
  getCategoryIcon(category) {
    const iconMap = {
      '衬衫': '👔',
      'T恤': '👕',
      '外套': '🧥',
      '裤子': '👖',
      '连衣裙': '👗',
      '裙子': '👗',
      '配饰': '🧣',
      '围巾': '🧣',
      '鞋子': '👟',
      '内衣': '🩲',
      '泳衣': '👙'
    };
    return iconMap[category] || '👕';
  },
  
  // 获取默认衣柜数据（降级方案）
  getDefaultWardrobeItems() {
    return [
      { id: 0, icon: '👔', name: '白色衬衫', count: '衬衫' },
      { id: 0, icon: '👕', name: '蓝色T恤', count: 'T恤' },
      { id: 0, icon: '🧥', name: '黑色外套', count: '外套' },
      { id: 0, icon: '👖', name: '牛仔裤', count: '裤子' },
      { id: 0, icon: '👗', name: '碎花连衣裙', count: '连衣裙' },
      { id: 0, icon: '🧣', name: '羊毛围巾', count: '配饰' }
    ];
  },

  closeWardrobeModal() {
    this.setData({
      showWardrobeModal: false
    });
  },

  openFridge() {
    this.createAnimation(() => {
      this.setData({
        showFridgeModal: true
      });
    });
    console.log('🧊 冰箱功能开发中...');
  },

  closeFridgeModal() {
    this.setData({
      showFridgeModal: false
    });
  },
  
  // 预览图片
  previewImage(e) {
    const url = e.currentTarget.dataset.url;
    if (url) {
      wx.previewImage({
        urls: [url],
        current: url
      });
    }
  },

  createAnimation(callback) {
    const animation = wx.createAnimation({
      duration: 300,
      timingFunction: 'ease-in-out'
    });
    
    animation.scale(0.95).step();
    animation.scale(1).step();
    
    setTimeout(callback, 300);
  }
})