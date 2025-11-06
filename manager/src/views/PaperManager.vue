<template>
  <div class="teacher-manager">
    <el-card>
      <template #header>
        <div class="header">
          <!-- <span>论文管理</span>
            -->
          <div class="titleTop">
            <span>论文管理</span>
            <el-button type="primary" size="small" @click="openAddForm">新增</el-button>

          </div>
          <div class="InputSearch">
            <div class="Search1">
              <span>名称</span>
                <el-input
                v-model="searchTitle"
                placeholder="请输入论文名称"
                style="width: 200px; margin-right: 10px;"
                suffix-icon="Search"
              />
            </div>
           
            <div class="Search2">
              <span>作者</span>
              <el-input
              v-model="searchAuthor"
              placeholder="请输入作者名称"
              style="width: 200px; margin-right: 10px;"
              suffix-icon="Search"
            />
            </div>
            <div>
              <el-button
                type="primary"
                size="large"
                round
              >搜索</el-button>
            </div>
            
          </div>
          <!-- <el-button type="primary" size="small" @click="openAddForm">新增</el-button> -->
        </div>
      </template>
      <el-table :data="SearchList" style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="论文名称" width="200" />
        <el-table-column prop="author" label="论文作者" width="200" />
        <el-table-column prop="time" label="上传时间" sortable width="150" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="openEditForm(row)">
              编辑
            </el-button>
            <el-button size="small" type="danger" @click="deleteDynamic(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页功能 -->
      <div class="pagination">
        <el-pagination
         
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="pageOptions"
          @current-change="handleCurrentChange"
        />
      </div>

      <!-- 新增科研动态弹窗 -->
      <el-dialog v-model="addFormVisible" title="新增论文" width="70%">
        <el-form :model="addForm" ref="addFormRef" label-width="100px">
          <el-form-item label="论文名称" prop="title">
            <el-input v-model="addForm.title"></el-input>
          </el-form-item>
          <el-form-item label="论文作者" prop="author">
            <el-input v-model="addForm.author"></el-input>
          </el-form-item>
          <el-form-item label="上传时间" prop="time">
            <el-input v-model="addForm.time"></el-input>
          </el-form-item>
          <el-form-item label="内容" prop="content">
            <!-- 富文本编辑器 -->
            <QuillEditor
              v-model:content="addForm.content"
              :options="editorOptions"
              style="min-height: 200px"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="addFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveDynamic">确 定</el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 编辑科研动态弹窗 -->
      <el-dialog v-model="editFormVisible" title="编辑论文" width="70%">
        <el-form :model="editForm" ref="editFormRef" label-width="100px">
          <el-form-item label="论文名称" prop="title">
            <el-input v-model="editForm.title"></el-input>
          </el-form-item>
          <el-form-item label="论文作者" prop="author">
            <el-input v-model="editForm.author"></el-input>
          </el-form-item>
          <el-form-item label="上传时间" prop="time">
            <el-input v-model="editForm.time"></el-input>
          </el-form-item>
          <el-form-item label="内容" prop="content">
            <!-- 富文本编辑器 -->
            <QuillEditor
              v-model:content="editForm.content"
              :options="editorOptions"
              style="min-height: 200px"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="editFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateDynamic">确 定</el-button>
          </span>
        </template>
      </el-dialog>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, watch, onMounted } from 'vue';
import { QuillEditor } from '@vueup/vue-quill';
import { ElMessageBox, ElMessage } from 'element-plus';
import { GetPaperList } from '../api/paperApi.js'
const SearchList = ref([

]);

// 调用接口获取数据
onMounted(async () => {
  try {
    const response = await GetPaperList(1,3,"张三");
    total.value = response.data.total;
    SearchList.value = response.data.row;
    console.log(SearchList);
    
  } catch (error) {
    console.error('获取论文列表失败:', error);
  }
});

// 查询条件
const searchTitle = ref('');
const searchAuthor = ref('');

// 当前页数和分页设置
const pageNum = ref(1);  //当前页面
const pageSize = ref(3);
const pageOptions = [3, 6, 9, 12];
const total = ref();


// 分页当前页切换
const handleCurrentChange = (val) => {
  pageNum.value = val;
};

// 新增表单相关逻辑
const addFormVisible = ref(false);
const addForm = reactive({
  id: SearchList.value.length + 1,
  title: '',
  author: '',
  time: '',
  content: '',
});

const editorOptions = {
  // 自定义 Quill 编辑器的工具栏
  modules: {
    toolbar: [
      [{ header: [1, 2, false] }, 'bold', 'italic', 'underline', 'strike'],
      [{ list: 'ordered' }, { list: 'bullet' }, { indent: '-1' }, { indent: '+1' }],
      ['link', 'image'],
      ['clean'],
      ['undo', 'redo'],
    ],
  },
};

const openAddForm = () => {
  addFormVisible.value = true;
};

const saveDynamic = () => {
  SearchList.value.push({
    id: SearchList.value.length + 1,
    title: addForm.title,
    author: addForm.author,
    time: addForm.time,
    content: addForm.content,
  });
  addFormVisible.value = false;
  ElMessage({
    type: 'success',
    message: '新增成功！',
  });
};

// 编辑表单相关逻辑
const editFormVisible = ref(false);
const editForm = reactive({
  id: null,
  title: '',
  author: '',
  time: '',
  content: '',
});

const openEditForm = (row) => {
  editFormVisible.value = true;
  editForm.id = row.id;
  editForm.title = row.title;
  editForm.author = row.author;
  editForm.time = row.time;
  editForm.content = row.content;
};

const updateDynamic = () => {
  const index = SearchList.value.findIndex(item => item.id === editForm.id);
  if (index !== -1) {
    SearchList.value[index] = {
      id: editForm.id,
      title: editForm.title,
      author: editForm.author,
      time: editForm.time,
      content: editForm.content,
    };
  }
  editFormVisible.value = false;
  ElMessage({
    type: 'success',
    message: '更新成功！',
  });
};

// 删除功能
const deleteDynamic = (row) => {
  ElMessageBox.confirm('确定要删除该论文吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      const index = SearchList.value.findIndex(item => item.id === row.id);
      if (index !== -1) {
        SearchList.value.splice(index, 1);
        ElMessage({
          type: 'success',
          message: '删除成功！',
        });
      }
    })
    .catch(() => {});
};

// 监听查询条件变化
watch([searchTitle, searchAuthor], () => {
  currentPage.value = 1; // 查询条件变化时重置当前页
});
</script>

<style scoped lang="less">
.teacher-manager {
  padding: 20px;
}
.header {
  /* display: flex; */
  .titleTop {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
  }
  .InputSearch{
    display: flex;
    align-items: center;
    justify-content: space-around;
    span{
      font-size:12px;
      color: #b7b2b2;
      margin-right: 10px;
    }
    .Search1{
      margin-left: 80px;
    }
    .Search2{
      margin-left: 80px;
      margin-right: 100px;
    }
  }
}
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.dialog-footer button:first-child {
  margin-right: 10px;
}
/* 美化富文本编辑器样式 */
.ql-container {
  border: 1px solid #ebebeb; /* 轻微灰色边框 */
  border-radius: 4px;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05); /* 轻微阴影 */
  overflow-y: auto;
}
.ql-editor {
  min-height: 200px; /* 保证编辑区域有一定的高度 */
  padding: 10px; /* 内间距，提升用户体验 */
  font-size: 14px; /* 统一字体大小 */
  line-height: 1.6; /* 行高，提升可读性 */
}
.ql-toolbar.ql-snow {
  border-bottom: 1px solid #ebebeb; /* 工具栏与编辑器之间的分割线 */
}
.ql-toolbar.ql-snow .ql-stroke {
  color: #000; /* 工具栏图标颜色 */
}
.ql-toolbar.ql-snow .ql-fill {
  color: #000; /* 工具栏填充图标颜色 */
}
.ql-editor .ql-editor p {
  margin: 0;
}
</style>
