<script setup>
import { ref, watch, onMounted } from 'vue';
import { GetsearchPapers } from '@/api/search'
import { ElMessage } from 'element-plus';
// 模拟数据

const papers = ref([
]);
const total = ref(0); // 总数
// 分页相关数据
const pageSize = ref(3); // 每页默认展示的数量
const pageOptions = [3, 6, 9, 12]; // 可选的每页展示数量
const pageNum = ref(1); // 当前页码

//获取论文数据
const GetAllSearchPapers = async (pageNum, pageSize, author, title) => {
  console.log('获取论文数据文本处：', pageNum, pageSize, author, title);
  const response = await GetsearchPapers(pageNum, pageSize, author, title);
  console.log('response:', response);

  if (response.code === 1) {
    ElMessage.success('获取论文数据成功');
    papers.value = response.data.data;
    console.log('获取到的数据：', papers);

    total.value = response.data.total;
  } else {
    ElMessage.error('获取论文数据失败');
  }
}

onMounted(() => {
  // 页面加载时，获取数据
  GetAllSearchPapers(pageNum.value, pageSize.value, '', '');
})

watch(pageSize, () => {
  pageNum.value = 1; // 切换每页展示数量时，重置页码
})

watch([pageSize, pageNum], () => {
  // 重新获取数据
  GetAllSearchPapers(pageNum.value, pageSize.value, '', '');
});

const handleCurrentChange = (newPage) => {
  pageNum.value = newPage;
}

</script>

<template>
  <div class="search-paper">
    <div class="module">
      <h2 class="module-title">论文搜索结果</h2>
      <div class="paper-container">
        <el-card
          class="paper-item"
          v-for="item in papers"
          :key="item.id"
        >
          <div class="paper-info">
            <div class="paper-content">
              <div class="paper-title">{{ item.title }}</div>
              <div class="paper-detail">
                <p style="font-size: 16px; font-weight: bold">摘要：</p>
                <div class="paper-detail-content" v-html="item.detail"></div>
              </div>
              <div class="paper-footer">
                <p class="author">{{ item.author }}</p>
                <p class="time">{{ item.time }}</p>
              </div>
            </div>
          </div>
        </el-card>
      </div>
      <div class="pagination">
        <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="pageOptions"
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
  min-height: 300px;
  overflow: hidden;
  position: relative;
}

.paper-info {
  display: flex;
  height: 100%;
}

.paper-image {
  flex: 1;
}

.paper-image img {
  height: 260px;
  width: auto;
  max-width: 100%;
  object-fit: contain;
  object-position: center;
}

.paper-content {
  flex: 2;
  display: flex;
  height: 100%;
  flex-direction: column;
  justify-content: space-between;
  padding: 10px;
}

.paper-title {
  font-size: 18px;
  font-weight: bold;
  margin: 0 auto;
  margin-bottom: 10px;
}

.paper-detail {
  flex: 1;
  line-height: 1.5;
  min-height: 300px;
}

.paper-detail-content {
  padding-left: 2em;

  :deep(h1), :deep(h2), :deep(h3), :deep(h4), :deep(h5), :deep(h6) {
     margin: 0 0 8px 0;
    font-size: 14px;
    font-weight: 600;
  }

  :deep(h1) {
    font-size: 2em;
  }

  :deep(h2) {
    font-size: 1.5em;
  }

  :deep(h3) {
    font-size: 1.17em;
  }

  :deep(p) {
    margin: 0.5em 0;
  }

  :deep(ul), :deep(ol) {
    padding-left: 1.5em;
    margin: 0.5em 0;
  }

  :deep(blockquote) {
    border-left: 4px solid #ccc;
    margin: 0.5em 0;
    padding-left: 1em;
    color: #666;
  }

  :deep(pre) {
    background: #f5f5f5;
    padding: 1em;
    border-radius: 4px;
    overflow-x: auto;
  }

  :deep(a) {
    color: #409eff;
    text-decoration: none;

    &:hover {
      text-decoration: underline;
    }
  }

  :deep(img) {
    max-width: 100%;
    height: auto;
  }

  :deep(.ql-align-center) {
    text-align: center;
  }

  :deep(.ql-align-right) {
    text-align: right;
  }

  :deep(.ql-align-justify) {
    text-align: justify;
  }
  :deep(p){
    margin: 0 0 8px 0;
  }
  :deep(strong) {
  font-weight: bold !important;
  }
 :deep(em) {
  font-style: italic !important;
  }
  :deep(u) {
  text-decoration: underline !important;
}
:deep(s),:deep(strike) {
  text-decoration: line-through !important;
}
:deep(img) {
  max-width: 100%;
  max-height: 80px;
  object-fit: contain;
  border-radius: 4px;
}
:deep(ul),:deep(ol) {
  margin: 0 0 8px 0;
  padding-left: 20px;
}
 :deep(blockquote) {
  margin: 0 0 8px 0;
  padding-left: 10px;
  border-left: 3px solid #ddd;
  color: #666;
}
:deep(pre) {
  background: #f5f5f5;
  padding: 8px;
  border-radius: 4px;
  font-size: 12px;
  overflow: auto;
}
:deep(code) {
  background: #f5f5f5;
  padding: 2px 4px;
  border-radius: 3px;
  font-family: monospace;
}
:deep(a) {
  color: #409eff;
  text-decoration: underline;
}

}


.paper-footer {
  display: flex;
  position: absolute;
  flex-direction: column;
  align-items: flex-end;
  bottom: 8px;
  right: 8px;
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
