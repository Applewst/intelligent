
<script setup>
import { ref, reactive, computed, watch, onMounted } from 'vue';
import { QuillEditor } from '@vueup/vue-quill';
import { ElMessageBox, ElMessage } from 'element-plus';
import { GetPaperList } from '../api/paperApi.js';

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




const SearchList = ref([]);

// 调用接口获取数据
onMounted(async () => {
  try {
    const response = await GetPaperList(1, 3, "张三");
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
const pageNum = ref(1); // 当前页面
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





// 搜索功能
const handleSearch = () => {
  // 这里可以添加搜索逻辑，例如调用接口获取搜索结果
};
</script>


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
                @click="handleSearch"
              >
                搜索
              </el-button>
            </div>
          </div>
          <!-- <el-button type="primary" size="small" @click="openAddForm">新增</el-button> -->
        </div>
      </template>
      <el-table :data="SearchList" style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="150" />
        <el-table-column prop="title" label="论文名称" width="450" />
        <el-table-column prop="author" label="论文作者" width="300" />
        <el-table-column prop="time" label="上传时间" sortable width="200" />
        <el-table-column label="操作" width="300">
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
      <el-dialog v-model="addFormVisible" title="新增论文" width="70%" :top="0">
        <el-form :model="addForm" ref="addFormRef" label-width="100px">
          <el-form-item label="论文名称" prop="title">
            <el-input v-model="addForm.title"></el-input>
          </el-form-item>
          <el-form-item label="论文作者" prop="author">
            <el-input v-model="addForm.author"></el-input>
          </el-form-item>
          <!-- <el-form-item label="上传时间" prop="time">
            <el-input v-model="addForm.time"></el-input>
          </el-form-item> -->
          <el-form-item label="内容" prop="content"></el-form-item>
            <!-- 富文本编辑器 -->
            <QuillEditor
              v-model:content="addForm.content"
              :options="editorOptions"
              style="min-height: 200px"
            />
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="addFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveDynamic">确 定</el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 编辑科研动态弹窗 -->
      <el-dialog v-model="editFormVisible" title="编辑论文" width="80%">
        <el-form :model="editForm" ref="editFormRef" label-width="100px">
          <el-form-item label="论文名称" prop="title">
            <el-input style="width: 300px" v-model="editForm.title"
            ></el-input>
          </el-form-item>
          <el-form-item style="width: 300px" label="论文作者" prop="author">
            <el-input v-model="editForm.author"></el-input>
          </el-form-item>
          <!-- <el-form-item style="width: 300px" label="上传时间" prop="time">
            <el-input v-model="editForm.time"></el-input>
          </el-form-item> -->
          <el-form-item label="内容" prop="content"></el-form-item>
            <!-- 富文本编辑器 -->
            <QuillEditor
              v-model:content="editForm.content"
              :options="editorOptions"
              style="min-height: 20px"
            />
          
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


<style scoped lang="less">
.teacher-manager {
  padding: 20px;
}
.header {
  .titleTop {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
    border-bottom: 1px solid #7c7575;
  }
  .InputSearch {
    display: flex;
    align-items: center;
    // justify-content: space-around;
    span {
      font-size: 12px;
      color: #b7b2b2;
      margin-right: 10px;
    }
    .Search1 {
      margin-left: 300px;
    }
    .Search2 {
      margin-left: 160px;
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

/* 富文本编辑器容器样式 */
:deep(.ql-container) {
  border: 2px solid #e4e7ed;
  border-top: none;
  border-radius: 0 0 8px 8px;
  background-color: #ffffff;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.02);
}

:deep(.ql-container:hover) {
  border-color: #b3d8ff;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.1);
}

:deep(.ql-container.ql-focus) {
  border-color: #409eff;
  box-shadow: 0 0 0 3px rgba(64, 158, 255, 0.1);
}

/* 编辑器内容区域 */
:deep(.ql-editor) {
  min-height: 320px;
  max-height: 500px;
  padding: 20px 24px;
  font-size: 15px;
  line-height: 1.8;
  color: #303133;
  overflow-y: auto;
}

:deep(.ql-editor:focus) {
  outline: none;
}

:deep(.ql-editor.ql-blank::before) {
  color: #c0c4cc;
  font-style: normal;
  left: 24px;
  font-size: 15px;
}

/* 工具栏样式 */
:deep(.ql-toolbar.ql-snow) {
  border: 2px solid #e4e7ed;
  border-bottom: 1px solid #e4e7ed;
  border-radius: 8px 8px 0 0;
  background: linear-gradient(180deg, #ffffff 0%, #f8f9fa 100%);
  padding: 12px 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.04);
}

/* 工具栏按钮组间距 */
:deep(.ql-toolbar.ql-snow .ql-formats) {
  margin-right: 16px;
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

:deep(.ql-toolbar.ql-snow .ql-formats:last-child) {
  margin-right: 0;
}

/* 工具栏按钮样式 */
:deep(.ql-toolbar.ql-snow button) {
  width: 36px;
  height: 36px;
  padding: 6px;
  margin: 0;
  border-radius: 6px;
  transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
  border: 1px solid transparent;
}

:deep(.ql-toolbar.ql-snow button:hover) {
  background-color: #ecf5ff;
  border-color: #d9ecff;
  transform: translateY(-1px);
}

:deep(.ql-toolbar.ql-snow button:active) {
  transform: translateY(0);
}

:deep(.ql-toolbar.ql-snow button.ql-active) {
  background: linear-gradient(135deg, #409eff 0%, #66b1ff 100%);
  border-color: #409eff;
  box-shadow: 0 2px 6px rgba(64, 158, 255, 0.3);
}

:deep(.ql-toolbar.ql-snow button.ql-active .ql-stroke) {
  stroke: #ffffff;
}

:deep(.ql-toolbar.ql-snow button.ql-active .ql-fill) {
  fill: #ffffff;
}

/* 工具栏图标颜色 */
:deep(.ql-toolbar.ql-snow .ql-stroke) {
  stroke: #606266;
  stroke-width: 2;
  transition: stroke 0.2s ease;
}

:deep(.ql-toolbar.ql-snow .ql-fill) {
  fill: #606266;
  transition: fill 0.2s ease;
}

:deep(.ql-toolbar.ql-snow button:hover .ql-stroke) {
  stroke: #409eff;
}

:deep(.ql-toolbar.ql-snow button:hover .ql-fill) {
  fill: #409eff;
}

/* 下拉选择器样式 */
:deep(.ql-toolbar.ql-snow .ql-picker) {
  height: 36px;
  border-radius: 6px;
  transition: all 0.2s ease;
  border: 1px solid transparent;
}

:deep(.ql-toolbar.ql-snow .ql-picker:hover) {
  background-color: #ecf5ff;
  border-color: #d9ecff;
}

:deep(.ql-toolbar.ql-snow .ql-picker.ql-expanded) {
  background-color: #ecf5ff;
  border-color: #409eff;
}

:deep(.ql-toolbar.ql-snow .ql-picker-label) {
  padding: 6px 12px;
  border: none;
  color: #606266;
  font-weight: 500;
}

:deep(.ql-toolbar.ql-snow .ql-picker-label:hover) {
  color: #409eff;
}

:deep(.ql-toolbar.ql-snow .ql-picker-options) {
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  background-color: #ffffff;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
  padding: 8px 0;
  margin-top: 4px;
}

:deep(.ql-toolbar.ql-snow .ql-picker-item) {
  padding: 8px 16px;
  transition: all 0.2s ease;
}

:deep(.ql-toolbar.ql-snow .ql-picker-item:hover) {
  background-color: #ecf5ff;
  color: #409eff;
}

:deep(.ql-toolbar.ql-snow .ql-picker-item.ql-selected) {
  background-color: #409eff;
  color: #ffffff;
}

/* 编辑器内容样式优化 */
:deep(.ql-editor p) {
  margin: 0 0 14px 0;
}

:deep(.ql-editor h1) {
  font-size: 32px;
  font-weight: 700;
  margin: 24px 0 16px 0;
  color: #303133;
  line-height: 1.4;
  border-bottom: 2px solid #e4e7ed;
  padding-bottom: 12px;
}

:deep(.ql-editor h2) {
  font-size: 26px;
  font-weight: 600;
  margin: 20px 0 14px 0;
  color: #303133;
  line-height: 1.4;
}

:deep(.ql-editor ul, .ql-editor ol) {
  padding-left: 28px;
  margin: 12px 0;
}

:deep(.ql-editor li) {
  margin: 6px 0;
  line-height: 1.8;
}

:deep(.ql-editor li::marker) {
  color: #409eff;
  font-weight: 600;
}

:deep(.ql-editor a) {
  color: #409eff;
  text-decoration: none;
  border-bottom: 1px solid transparent;
  transition: all 0.2s ease;
}

:deep(.ql-editor a:hover) {
  border-bottom-color: #409eff;
}

:deep(.ql-editor img) {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
  margin: 16px 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease;
}

:deep(.ql-editor img:hover) {
  transform: scale(1.02);
}

:deep(.ql-editor blockquote) {
  border-left: 4px solid #409eff;
  padding-left: 16px;
  margin: 16px 0;
  color: #606266;
  font-style: italic;
  background-color: #f5f7fa;
  padding: 12px 16px;
  border-radius: 0 4px 4px 0;
}

:deep(.ql-editor code) {
  background-color: #f5f7fa;
  padding: 2px 6px;
  border-radius: 4px;
  font-family: 'Courier New', monospace;
  color: #e83e8c;
  font-size: 0.9em;
}

:deep(.ql-editor pre) {
  background-color: #282c34;
  color: #abb2bf;
  padding: 16px;
  border-radius: 8px;
  overflow-x: auto;
  margin: 16px 0;
}

/* 滚动条样式 */
:deep(.ql-editor::-webkit-scrollbar) {
  width: 10px;
}

:deep(.ql-editor::-webkit-scrollbar-track) {
  background: #f5f7fa;
  border-radius: 5px;
}

:deep(.ql-editor::-webkit-scrollbar-thumb) {
  background: linear-gradient(180deg, #c0c4cc 0%, #909399 100%);
  border-radius: 5px;
  border: 2px solid #f5f7fa;
}

:deep(.ql-editor::-webkit-scrollbar-thumb:hover) {
  background: linear-gradient(180deg, #909399 0%, #606266 100%);
}

/* 分隔线样式 */
:deep(.ql-toolbar.ql-snow .ql-formats) {
  position: relative;
}

:deep(.ql-toolbar.ql-snow .ql-formats:not(:last-child)::after) {
  content: '';
  position: absolute;
  right: -8px;
  top: 50%;
  transform: translateY(-50%);
  width: 1px;
  height: 24px;
  background-color: #e4e7ed;
}
</style>
