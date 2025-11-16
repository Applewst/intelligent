<template>
  <div class="research-news-manager">
    <!-- 操作栏 -->
    <div class="toolbar">
      <div class="flex items-center gap-4">
        <el-input
          v-model="searchName"
          placeholder="请输入标题关键词搜索"
          clearable
          style="width: 300px"
          @clear="handleSearch"
        >
          <template #append>
            <el-button :icon="Search" @click="handleSearch" />
          </template>
        </el-input>
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          添加科研动态
        </el-button>
      </div>
    </div>

    <!-- 数据表格 -->
    <el-table
      :data="tableData"
      v-loading="loading"
      border
      style="width: 100%"
      class="data-table"
    >
      <el-table-column prop="id" label="ID" width="80" align="center" />
      <el-table-column label="封面图" width="120" align="center">
        <template #default="{ row }">
          <el-image
            :src="row.image"
            fit="cover"
            style="width: 80px; height: 60px; border-radius: 4px"
            :preview-src-list="[row.image]"
          />
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" min-width="200" />
      <el-table-column
        prop="detail"
        label="详情"
        min-width="300"
        show-overflow-tooltip
      />
      <el-table-column
        prop="time"
        label="发布时间"
        width="180"
        align="center"
      />
      <el-table-column label="操作" width="180" align="center" fixed="right">
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
    <div class="pagination">
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
      />
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      @close="resetForm"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="封面图" prop="image">
          <el-input v-model="formData.image" placeholder="请输入图片URL" />
        </el-form-item>
        <el-form-item label="详情" prop="detail">
          <el-input
            v-model="formData.detail"
            type="textarea"
            :rows="4"
            placeholder="请输入详细内容"
          />
        </el-form-item>
        <el-form-item label="发布时间" prop="time">
          <el-date-picker
            v-model="formData.time"
            type="datetime"
            placeholder="选择日期时间"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  getResearchList,
  addResearch,
  updateResearch,
  deleteResearch,
} from "../api/research";

// 表格数据
const tableData = ref([]);
const loading = ref(false);

// 分页数据
const pageNum = ref(1);
const pageSize = ref(10);
const total = ref(0);

const searchName = ref("");

// 对话框相关
const dialogVisible = ref(false);
const dialogTitle = ref("添加科研动态");
const isEdit = ref(false);

// 表单数据
const formRef = ref();
const formData = reactive({
  id: undefined,
  title: "",
  image: "",
  detail: "",
  time: "",
});

// 表单验证规则
const formRules = {
  title: [{ required: true, message: "请输入标题", trigger: "blur" }],
  image: [{ required: true, message: "请输入图片URL", trigger: "blur" }],
  detail: [{ required: true, message: "请输入详细内容", trigger: "blur" }],
  time: [{ required: true, message: "请选择发布时间", trigger: "change" }],
};

const loadData = async () => {
  loading.value = true;
  try {
    const response = await getResearchList(
      pageNum.value,
      pageSize.value,
      searchName.value
    );
    tableData.value = response.data.data;
    total.value = response.data.total;
  } catch (error) {
    ElMessage.error("加载数据失败");
    console.error(error);
  } finally {
    loading.value = false;
  }
};

const handleSearch = () => {
  pageNum.value = 1;
  loadData();
};

// 添加
const handleAdd = () => {
  dialogTitle.value = "添加科研动态";
  isEdit.value = false;
  dialogVisible.value = true;
};

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = "编辑科研动态";
  isEdit.value = true;
  Object.assign(formData, row);
  dialogVisible.value = true;
};

const handleDelete = (row) => {
  ElMessageBox.confirm("确定要删除这条科研动态吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await deleteResearch(row.id);
        ElMessage.success("删除成功");
        loadData();
      } catch (error) {
        ElMessage.error("删除失败");
        console.error(error);
      }
    })
    .catch(() => {
      // 取消删除
    });
};

const submitForm = async () => {
  if (!formRef.value) return;

  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          await updateResearch(formData);
          ElMessage.success("更新成功");
        } else {
          await addResearch({ formData: title });
          ElMessage.success("添加成功");
        }
        dialogVisible.value = false;
        loadData();
      } catch (error) {
        ElMessage.error(isEdit.value ? "更新失败" : "添加失败");
        console.error(error);
      }
    }
  });
};

// 重置表单
const resetForm = () => {
  formRef.value?.resetFields();
  Object.assign(formData, {
    id: undefined,
    title: "",
    image: "",
    detail: "",
    time: "",
  });
};

// 初始化加载
onMounted(() => {
  loadData();
});
</script>

<style scoped>
.research-news-manager {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
}

.toolbar {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.data-table {
  margin-bottom: 20px;
}

.pagination {
  display: flex;
  justify-content: flex-end;
}
</style>
