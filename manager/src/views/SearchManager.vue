<template>
  <div class="teacher-manager">
    <el-card>
      <template #header>
        <div class="header">
          <span>科研动态管理</span>
          <el-button type="primary" size="small" @click="openAddForm">新增</el-button>
        </div>
      </template>
      <el-table :data="filteredDynamicList" style="width: 100%" border>
        <el-table-column prop="title" label="标题" width="180" />
        <el-table-column prop="tag" label="主题" width="240" />
        <el-table-column prop="time" label="上传时间" />
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
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :total="SearchList.length"
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="pageOptions"
          @current-change="handleCurrentChange"
        />
      </div>

      <!-- 新增科研动态弹窗 -->
      <el-dialog v-model="addFormVisible" title="科研动态" width="70%">
        <el-form :model="addForm" ref="addFormRef" label-width="100px">
          <el-form-item label="标题" prop="title">
            <el-input v-model="addForm.title"></el-input>
          </el-form-item>
          <el-form-item label="主题" prop="tag">
            <el-input v-model="addForm.tag"></el-input>
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
      <el-dialog v-model="editFormVisible" title="编辑科研动态" width="70%">
        <el-form :model="editForm" ref="editFormRef" label-width="100px">
          <el-form-item label="标题" prop="title">
            <el-input v-model="editForm.title"></el-input>
          </el-form-item>
          <el-form-item label="主题" prop="tag">
            <el-input v-model="editForm.tag"></el-input>
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
import { ref, reactive, computed, watch } from 'vue';
import { QuillEditor } from '@vueup/vue-quill';
import { ElMessageBox, ElMessage } from 'element-plus';

const SearchList = ref([
  {
    id: 1,
    title: "神经网络",
    tag: "机器学习",
    time: "2023-10-01",
    content: "这是神经网络的内容。",
  },
  {
    id: 2,
    title: "卷积神经网络",
    tag: "机器学习",
    time: "2023-11-01",
    content: "这是卷积神经网络的内容。",
  },
  {
    id: 3,
    title: "神经网络",
    tag: "机器学习",
    time: "2023-10-01",
    content: "这是神经网络的内容。",
  },
]);

// 当前页数和分页设置
const currentPage = ref(1);
const pageSize = ref(3);
const pageOptions = [3, 6, 9, 12];

// 计算当前页的数据
const filteredDynamicList = computed(() => {
  const startIndex = (currentPage.value - 1) * pageSize.value;
  const endIndex = startIndex + pageSize.value;
  return SearchList.value.slice(startIndex, endIndex);
});

// 分页当前页切换
const handleCurrentChange = (val) => {
  currentPage.value = val;
};

// 新增表单相关逻辑
const addFormVisible = ref(false);
const addForm = reactive({
  title: '',
  tag: '',
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
  const newId = SearchList.value.length + 1;
  SearchList.value.push({
    id: newId,
    title: addForm.title,
    tag: addForm.tag,
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
  title: '',
  tag: '',
  time: '',
  content: '',
});

const openEditForm = (row) => {
  editFormVisible.value = true;
  editForm.title = row.title;
  editForm.tag = row.tag;
  editForm.time = row.time;
  editForm.content = row.content;
};

const updateDynamic = () => {
  const index = SearchList.value.findIndex(item => item.id === editForm.id);
  if (index !== -1) {
    SearchList.value[index] = {
      id: editForm.id,
      title: editForm.title,
      tag: editForm.tag,
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
  ElMessageBox.confirm('确定要删除该科研动态吗？', '提示', {
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
</script>

<style scoped>
.teacher-manager {
  padding: 20px;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
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