<template>
  <div class="student-management">
    <!-- 顶部标题和新增按钮 -->
    <div class="header">
      <h2 class="title">学生发展管理</h2>
      <el-button type="primary" @click="handleAdd">新增学生</el-button>
    </div>

    <!-- 搜索表单 -->
    <div class="search-section">
      <el-input
        v-model="searchForm.name"
        placeholder="请输入姓名"
        class="search-input"
        suffix-icon="Search"
      />
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button @click="handleReset">重置</el-button>
    </div>

    <!-- 数据表格 -->
    <el-table :data="DevelopData" border class="data-table">
      <el-table-column prop="id" label="ID" width="80" align="center" />
      <el-table-column prop="name" label="姓名" width="150" />
      <el-table-column prop="time" label="时间" width="150" />
      <el-table-column prop="detail" label="详细" width="700" />
      <el-table-column prop="file" label="照片" width="150">
        <template #default="{ row }">
          <el-image
            :src="row.file"
            fit="cover"
            style="width: 60px; height: 60px; border-radius: 4px"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" align="center">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleEdit(row)">
            编辑
          </el-button>
          <el-button type="danger" size="small" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
  <div class="pagination-wrapper">
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 18, 28]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
      />
    </div>

    <!-- 对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="40%"
      @close="handleDialogClose"
    >
      <el-form :model="formData" label-width="100px">
        <el-form-item label="姓名">
          <el-input
            v-model="formData.name"
            placeholder="请输入姓名"
            clearable
          />
        </el-form-item>
     
        <el-form-item label="详细">
          <el-input
            v-model="formData.detail"
            placeholder="请输入内容"
            clearable
          />
        </el-form-item>
        <el-form-item label="照片URL">
          <el-input v-model="formData.file" placeholder="请输入照片URL" />
        </el-form-item>
       <el-form-item label="预览">
          <el-image
            v-if="formData.file"
            :src="formData.file"
            fit="cover"
            style="width: 200px; height: 150px"
          >
            <template #error>
              <div class="image-error" style="width: 200px; height: 150px">
                <el-icon><Picture /></el-icon>
                <span>图片加载失败</span>
              </div>
            </template>
          </el-image>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted,watch } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Search } from '@element-plus/icons-vue';
import { GetDevelopList,AddDevelop,UpdateDevelop,DeleteDevelop } from '@/api/develop';
// 搜索表单
const searchForm = reactive({
  name: ''
});

// 学生数据
const students = ref([]);
const total = ref(0);
const pageNum = ref(1);
const pageSize = ref(5);

// 对话框
const dialogVisible = ref(false);
const dialogTitle = ref('新增学生');
const isEdit = ref(false);
const currentEditId = ref(null);

// 表单数据
const formData = reactive({
  id: '',
  name: '',
  time: '',
  detail: '',
  file: ''
});

// 模拟数据
const DevelopData = ref([]);

//获取学生发展列表
const getDevelopList = async (pageNum,pageSize,name) => {
  console.log('获取学生发展文本处',pageNum,pageSize,name)
  const response = await GetDevelopList(pageNum,pageSize,name)
  DevelopData.value = response.data.data;  
  total.value = response.data.total;
};
//新增学生发展
const AddDevelopData = async (name,detail,file) => {
  console.log('新增学生发展文本处',name,detail,file)
  const response = await AddDevelop(name,detail,file)
  getDevelopList(pageNum.value,pageSize.value,searchForm.name)
  if(response.code === 1){
    ElMessage.success('新增成功')
  }else{
    ElMessage.error('新增失败')
  }
}
//编辑学生发展
const UpdateDevelopData = async (id,name,detail,file) => {
  console.log('编辑学生发展文本处',id,name,detail,file)
  const response = await UpdateDevelop(id,name,detail,file)
  getDevelopList(pageNum.value,pageSize.value,searchForm.name)
  if(response.code === 1){
    ElMessage.success('编辑成功')
  }else{
    ElMessage.error('编辑失败')
  }
}
//删除学生发展
const DeleteDevelopData = async (id) => {
  console.log('删除学生发展文本处',id)
  const response = await DeleteDevelop(id)
  getDevelopList(pageNum.value,pageSize.value,searchForm.name)
  if(response.code === 1){
    ElMessage.success('删除成功')
  }else{
    ElMessage.error('删除失败')
  }
}

onMounted(()=>{
  getDevelopList(pageNum.value,pageSize.value,searchForm.name)
})
watch([pageNum,pageSize],()=>{
  getDevelopList(pageNum.value,pageSize.value,searchForm.name)
})
// // 过滤后的学生数据
// const filteredStudents = computed(() => {
//   return students.value;
// });


// 搜索
const handleSearch = () => {
  getDevelopList(pageNum.value,pageSize.value,searchForm.name)
  searchForm.name = '';
};

// 重置
const handleReset = () => {
  searchForm.name = '';
  getDevelopList(pageNum.value,pageSize.value,searchForm.name)
};

// 新增
const handleAdd = () => {
  dialogTitle.value = '新增学生';
  isEdit.value = false;
  resetForm();
  dialogVisible.value = true;
};

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = '编辑学生';
  isEdit.value = true;
  currentEditId.value = row.id;
  Object.assign(formData, row);
  dialogVisible.value = true;
};

// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该学生吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    DeleteDevelopData(row.id)
    
  }).catch(() => {
    ElMessage.info('已取消删除');
  });
};

// 提交表单
const handleSubmit = () => {
  if (!formData.name || !formData.detail) {
    ElMessage.warning('请填写必填项');
    return;
  }

  if (isEdit.value) {
    //编辑
    UpdateDevelopData(formData.id,formData.name,formData.detail,formData.file)
    dialogVisible.value = false;
  } else {
    AddDevelopData(formData.name,formData.detail,formData.file)
    dialogVisible.value = false;
    resetForm();
  };
}

// 重置表单
const resetForm = () => {
  formData.id = '';
  formData.name = '';
  formData.time = '';
  formData.detail = '';
  formData.file = '';
};


</script>

<style scoped lang="less">
.student-management {
  padding: 24px;
  background: #f5f7fa;
  min-height: 100vh;
}

/* New header layout with flex */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.title {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  margin: 0;
}

/* Updated search section styling */
.search-section {
  background: white;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  display: flex;
  gap: 12px;
  align-items: center;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.search-input {
  flex: 1;
  max-width: 400px;
}
.pagination-wrapper {
  display: flex;
  justify-content: center;
  padding: 24px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}
.image-error {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  background: #f5f7fa;
  color: #909399;
}

.image-error .el-icon {
  font-size: 48px;
  margin-bottom: 8px;
}


/* Enhanced table and button styling */
:deep(.el-table) {
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

:deep(.el-table__header) {
  background-color: #f5f7fa;
}

:deep(.el-table th) {
  background-color: #f5f7fa;
  color: #606266;
  font-weight: 600;
}

:deep(.el-button--primary) {
  background-color: #409eff;
  border-color: #409eff;
}

:deep(.el-button--danger) {
  background-color: #f56c6c;
  border-color: #f56c6c;
}
</style>
