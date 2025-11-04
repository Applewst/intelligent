
<script setup>
import { ref, computed, watch } from 'vue';

// 模拟数据
const module = {
  title: '论文搜索结果',
};

const papers = ref([
  {
    id:1,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 1',
    detail: '这是一篇关于机器学习算法优化的研究，详细探讨了深度学习模型的训练策略。',
    author: '作者 A',
    time: '2023-01-01',
  },
  {
    id:2,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 2',
    detail: '本文介绍了自然语言处理领域中的一种新型预训练模型，重点是多语言文本理解能力的提升。',
    author: '作者 B',
    time: '2023-02-01',
  },
  {
    id:3,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 3',
    detail: '研究了图像识别技术在医疗诊断中的应用，对比了卷积神经网络的不同架构对图像分类的影响。',
    author: '作者 C',
    time: '2023-03-01',
  },
  {
    id:4,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 4',
    detail: '本文探讨了区块链技术在供应链管理中的应用，分析了其在提高透明度和效率方面的潜力。',
    author: '作者 D',
    time: '2023-04-01',
  },
  {
    id:5,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 5',
    detail: '研究了人工智能在交通管理中的应用，提出了基于强化学习的智能交通信号控制系统设计。',
    author: '作者 E',
    time: '2023-05-01',
  },
  {
    id:6,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 6',
    detail: '该论文分析了生成对抗网络（GAN）在图像生成中的最新进展，提出了改进GAN稳定性的方法。',
    author: '作者 F',
    time: '2023-06-01',
  },
  {
    id:7,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 7',
    detail: '本文介绍了量子计算在密码学中的应用，探讨了量子密钥分发的安全性与效率。',
    author: '作者 G',
    time: '2023-07-01',
  },
  {
    id:8,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 8',
    detail: '研究了深度强化学习在游戏AI中的应用，分析了AlphaZero框架在策略优化中的作用。',
    author: '作者 H',
    time: '2023-08-01',
  },
  {
    id:9,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 9',
    detail: '该论文讨论了车联网（IoV）的概念，提出了基于5G技术的智能交通系统架构。',
    author: '作者 I',
    time: '2023-09-01',
  },
  {
    id:10,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 10',
    detail: '本文介绍了信息检索技术的最新进展，重点是基于深度学习的文本嵌入模型。',
    author: '作者 J',
    time: '2023-10-01',
  },
  {
    id:11,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 11',
    detail: '研究了云计算中的资源调度算法，提出了基于强化学习的动态资源分配策略。',
    author: '作者 K',
    time: '2023-11-01',
  },
  {
    id:12,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 12',
    detail: '该论文探讨了智能家居系统中的用户行为分析，提出了基于机器学习的个性化推荐算法。',
    author: '作者 L',
    time: '2023-12-01',
  },
  {
    id:13,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 13',
    detail: '本文介绍了大数据技术在金融风控中的应用，提出了基于图神经网络的风险评估模型。',
    author: '作者 M',
    time: '2023-12-15',
  },
  {
    id:14,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 14',
    detail: '研究了语音识别技术中的噪声消除算法，提出了基于深度学习的鲁棒性改进方法。',
    author: '作者 N',
    time: '2023-08-15',
  },
  {
    id:15,
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 15',
    detail: '该论文分析了无人机在物流配送中的应用，提出了基于路径规划算法的优化方案。',
    author: '作者 O',
    time: '2023-05-15',
  },
]);

const total = ref(papers.value.length); // 总数
// 分页相关数据
const pageSize = ref(3); // 每页默认展示的数量
const pageOptions = [3, 6, 9, 12]; // 可选的每页展示数量
const currentPage = ref(1); // 当前页码

// 当前页的论文数据
const currentPagePapers = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return papers.value.slice(start, end);
});

// 监听每页展示数量的变化
watch(pageSize, () => {
  currentPage.value = 1; // 每次改变每页展示数量时，重置当前页码
});

// 监听当前页码的变化
function handleCurrentChange(val) {
  currentPage.value = val;
}

// 初始化
const initialize = () => {
  // 如果需要从后端获取数据，可以在这里初始化
};

initialize();
</script>

<template>
  <div class="search-paper">
  
    <div class="module">
    
      <h2 class="module-title">{{ module.title }}</h2>
      <!-- 卡片展示区域 -->
      <div class="paper-container">
          <el-card
            class="paper-item"
            v-for="(paper, index) in currentPagePapers"
            :key="index"
          >
            <div class="paper-info">
              <!-- 卡片图片 -->
              <div class="paper-image">
                <img :src="paper.image" alt="Paper Image" />
              </div>

              <!-- 文字说明 -->
              <div class="paper-content">
                <!-- 标题 -->
                <div class="paper-title">{{ paper.title }}</div>
                <!-- 摘要 -->
                <div class="paper-detail">{{ paper.detail }}</div>
                <!-- 作者和时间 -->
                <div class="paper-footer">
                  <p class="author">{{ paper.author }}</p>
                  <p class="time">{{ paper.time }}</p>
                </div>
              </div>
            </div>
          </el-card>
        </div>
      <!-- 分页组件 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="pageOptions"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @current-change="handleCurrentChange"
        />
      </div>
  </div>
  </div>

</template>


<style scoped lang="less">
.search-paper {
  margin-top: 100px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.module {
  margin: 0 auto;
  width: 1200px;
  padding: 10px;
  margin-bottom: 50px;
  border-radius: 5px;
  position: relative;
}

.module-title {
  text-align: center;
  margin-bottom: 60px;
  font-weight: bold;
  font-size: 30px;
}

.pagination-controls {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.paper-container {
  display: flex; /* 使用 flex 布局 */
  flex-direction: column; /* 卡片竖着排列 */
  gap: 20px; /* 卡片之间的间距 */
}

.paper-item {
  height: 300px;
  overflow: hidden;
}

.paper-info {
  display: flex;
  height: 100%;

}

.paper-image {
  flex: 1;
}

.paper-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.paper-content {
  flex: 2;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 10px;
}

.paper-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.paper-detail {
  flex: 1;
  line-height: 1.5;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.paper-footer {
  display: flex;
  flex-direction: column;
  align-items: flex-end;

  
  
}

.author {
  font-size: 14px;
  margin-bottom: 5px;
}

.time {
  font-size: 14px;
}

.page-selection {
  display: flex;
  align-items: center;
  gap: 10px;
}

.label {
  font-size: 14px;
}
</style>
