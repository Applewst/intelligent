
<script setup>
import { ref, computed, watch, onMounted } from 'vue';
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
  console.log('获取论文数据文本处：',pageNum, pageSize, author, title);
  const response = await GetsearchPapers(pageNum, pageSize, author, title);
  if(response.code === 1){
    ElMessage.success('获取论文数据成功');
    papers.value = response.data.data;
    console.log('获取到的数据：',papers);
    
    total.value = response.data.total;
  }else{
    ElMessage.error('获取论文数据失败');
  }
}

onMounted(() => {
  // 页面加载时，获取数据
  GetAllSearchPapers(pageNum.value, pageSize.value, '', '');
})


// // 当前页的论文数据
// const currentPagePapers = computed(() => {
//   const start = (pageNum.value - 1) * pageSize.value;
//   const end = start + pageSize.value;
//   return papers.value.slice(start, end);
// });

watch(pageSize,()=>{
  pageNum.value = 1; // 切换每页展示数量时，重置页码
})
// 监听每页展示数量的变化
watch([pageSize,pageNum], () => {
  // 重新获取数据
  GetAllSearchPapers(pageNum.value, pageSize.value, '', '');
});



</script>


<template>
  <div class="search-paper">
    <div class="module">
      <h2 class="module-title">论文搜索结果</h2>
      <div class="paper-container"
        
      >
        <el-card
          class="paper-item"
          v-for="item in papers"
          :key="item.id"
        >
          <div class="paper-info">
            <div class="paper-image">
              <img v-if="item.file" :src="item.file" alt="Paper Image" />
            </div>
            <div class="paper-content">
              <div class="paper-title">{{ item.title }}</div>
              <div class="paper-detail">{{ item.detail }}</div>
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