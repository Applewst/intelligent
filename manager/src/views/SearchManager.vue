
<script setup>
import { ref, reactive, computed,watch } from 'vue';


  
    const SearchList = ref([
      {
        id: 1,
        title:"神经网络",
        tag:"机器学习",
        time:"2023-10-01",
      },
      {
        id: 2,
        title:"卷积神经网络",
        tag:"机器学习",
        time:"2023-11-01",
      },
      {
        id: 3,
        title:"神经网络",
        tag:"机器学习",
        time:"2023-10-01",
      },
      {
        id: 4,
        title:"神经网络",
        tag:"机器学习",
        time:"2023-10-01",
      },
      {
        id: 5,
        title:"神经网络",
        tag:"机器学习",
        time:"2023-10-01",
      },
      {
        id: 6,
        title:"神经网络",
        tag:"机器学习",
        time:"2023-10-01",
      },
      {
        id: 7,
        title:"神经网络",
        tag:"机器学习",
        time:"2023-10-01",
      },
    ]);
    const currentPage = ref(1);    // 当前页数
    const pageSize = ref(3);  // 每页显示条数
    const pageOptions = [3, 6, 9, 12]; // 可选的每页展示数量
    // 计算当前页的数据
    console.log(pageSize.value)
    
    const filteredDynamicList = computed(() => {
      const startIndex = (currentPage.value - 1) * pageSize.value;
      const endIndex = startIndex + pageSize.value;
      return SearchList.value.slice(startIndex, endIndex);
    });
    watch(pageSize, () => {
      currentPage.value = 1; // 每次改变每页展示数量时，重置当前页码
    })
    // 分页当前页切换
    const handleCurrentChange = (val) => {
      currentPage.value = val;
    };


    const addFormVisible = ref(false);   // 新增表单是否可见
    const editFormVisible = ref(false);  // 编辑表单是否可见
    const addForm = reactive({
      title: '',
      tag: '',
      time: '',
    });
    const editForm = reactive({
      title: '',
      tag: '',
      time: '',
    });

    // 打开新增表单
    const openAddForm = () => {
      addFormVisible.value = true;
    }

    // 打开编辑表单
    const openEditForm = (row) => {
      editFormVisible.value = true;
      editForm.title = row.title;
      editForm.tag = row.tag;
      editForm.time = row.time;
      console.log(row);
      
    }
    
    // 点击删除
    const deleteDynamic = (row) => {
      //提示是否要删除
      ElMessageBox.confirm('确定要删除该科研动态吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        //点击确定 删除该行数据
        removeDynamic(row);
        ElMessage({
          type: 'success',
          message: '删除成功!',
        });
      }).catch(() => {
        //点击取消 不做任何操作
      });
      const index = SearchList.value.findIndex(item => item.id === row.id);
      if (index !== -1) {
        SearchList.value.splice(index, 1);
      }
    }

</script>


<template>
  <div class="teacher-manager">
    <el-card>
      <template #header>
        <div class="header">
          <span>科研动态管理</span>
          <el-button type="primary" size="small" @click="openAddForm">新增</el-button>
        </div>
      </template>
      <el-table :data="filteredDynamicList" style="width: 100%" border >
        <el-table-column prop="title" label="标题" width="180" />
        <el-table-column prop="tag" label="主题" width="240" />
        <el-table-column prop="time" label="时间" />
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
          :disabled="disabled"
           layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="pageOptions"
          @current-change="handleCurrentChange"
        />
      </div>
      <!-- 新增科研动态弹窗 -->
      <el-dialog v-model="addFormVisible" title="科研动态" width="40%">
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
         
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="addFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveDynamic">确 定</el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 编辑科研动态弹窗 -->
      <el-dialog v-model="editFormVisible" title="编辑科研动态" width="40%">
        <el-form :model="editForm" ref="editFormRef" label-width="100px">
          <el-form-item label="标题" prop="title">
            <el-input v-model="editForm.title"></el-input>
          </el-form-item>
          <el-form-item label="主题" prop="tag">
            <el-input v-model="editForm.tag"></el-input>
          </el-form-item>
          <el-form-item label="时间" prop="time">
            <el-input v-model="editForm.email"></el-input>
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

img {
  display: block;
}
</style>
