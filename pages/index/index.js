Page({
  data: {
    showWardrobeModal: false,
    showFridgeModal: false,
    wardrobeAnimation: {},
    fridgeAnimation: {},
    
    wardrobeItems: [
      { id: 1, icon: '👔', name: '衬衫', count: 5 },
      { id: 2, icon: '👕', name: 'T恤', count: 8 },
      { id: 3, icon: '🧥', name: '外套', count: 3 },
      { id: 4, icon: '👖', name: '牛仔裤', count: 4 },
      { id: 5, icon: '👗', name: '连衣裙', count: 6 },
      { id: 6, icon: '🧣', name: '围巾', count: 2 }
    ],
    
    fridgeItems: [
      { id: 1, icon: '🥛', name: '牛奶', count: 2 },
      { id: 2, icon: '🥚', name: '鸡蛋', count: 12 },
      { id: 3, icon: '🍎', name: '苹果', count: 5 },
      { id: 4, icon: '🥬', name: '蔬菜', count: 3 },
      { id: 5, icon: '🍖', name: '肉类', count: 1 },
      { id: 6, icon: '🧊', name: '冰块', count: 20 }
    ]
  },

  onLoad() {
    console.log('温馨小家页面加载');
  },

  openWardrobe() {
    this.createAnimation(() => {
      this.setData({
        showWardrobeModal: true
      });
    });
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
  },

  closeFridgeModal() {
    this.setData({
      showFridgeModal: false
    });
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