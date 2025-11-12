<template>
  <div class="teacher-management">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="教师姓名">
          <el-input
            v-model="searchForm.name"
            placeholder="请输入教师姓名"
            clearable
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作栏 -->
    <el-card class="toolbar-card">
      <el-button type="primary" @click="handleAdd">新增教师</el-button>
    </el-card>

    <!-- 表格 -->
    <el-card>
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="title" label="研究方向" min-width="150" />
        <el-table-column
          prop="description"
          label="教师详情"
          min-width="200"
          show-overflow-tooltip
        />
        <el-table-column prop="email" label="邮箱" min-width="180" />
        <el-table-column label="照片" width="100">
          <template #default="{ row }">
            <el-avatar :src="row.avatar" :size="50" />
          </template>
        </el-table-column>
        <el-table-column prop="gender" label="性别" width="80">
          <template #default="{ row }">
            <el-tag :type="row.gender === 1 ? 'primary' : 'success'">
              {{ row.gender === 1 ? "男" : "女" }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)"
              >编辑</el-button
            >
            <el-button type="danger" size="small" @click="handleDelete(row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      @close="handleDialogClose"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formData.name" placeholder="请输入教师姓名" />
        </el-form-item>
        <el-form-item label="研究方向" prop="title">
          <el-input v-model="formData.title" placeholder="请输入研究方向" />
        </el-form-item>
        <el-form-item label="教师详情" prop="description">
          <el-input
            v-model="formData.description"
            type="textarea"
            :rows="4"
            placeholder="请输入教师详细介绍"
          />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="照片URL" prop="avatar">
          <el-input v-model="formData.avatar" placeholder="请输入照片URL" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="formData.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">女</el-radio>
          </el-radio-group>
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
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  getTeacherList as realGetTeacherList,
  addTeacher as realAddTeacher,
  updateTeacher as realUpdateTeacher,
  deleteTeacher as realDeleteTeacher,
} from "@/api/teacher";

// 模拟数据开关：true=使用模拟数据，false=使用真实API
const USE_MOCK_DATA = ref(true);

const mockTeachers = ref([
  {
    id: 1,
    name: "张伟",
    title: "机器学习与深度学习",
    description:
      "主要研究深度神经网络、强化学习和计算机视觉领域，发表SCI论文20余篇。",
    email: "zhangwei@example.com",
    avatar: "/placeholder.svg?height=50&width=50",
    gender: 1,
  },
  {
    id: 2,
    name: "李娜",
    title: "计算机视觉",
    description:
      "专注于图像识别、目标检测和视频分析技术研究，主持国家自然科学基金项目2项。",
    email: "lina@example.com",
    avatar: "/placeholder.svg?height=50&width=50",
    gender: 2,
  },
  {
    id: 3,
    name: "王强",
    title: "自然语言处理",
    description:
      "从事文本挖掘、情感分析和机器翻译研究，获得省部级科技进步奖3项。",
    email: "wangqiang@example.com",
    avatar: "/placeholder.svg?height=50&width=50",
    gender: 1,
  },
  {
    id: 4,
    name: "赵敏",
    title: "数据挖掘与分析",
    description:
      "擅长大数据分析、数据可视化和商业智能，指导研究生获得多项竞赛奖项。",
    email: "zhaomin@example.com",
    avatar: "/placeholder.svg?height=50&width=50",
    gender: 2,
  },
  {
    id: 5,
    name: "刘洋",
    title: "分布式系统",
    description:
      "研究云计算架构、微服务技术和容器编排，有丰富的工业界项目经验。",
    email: "liuyang@example.com",
    avatar: "/placeholder.svg?height=50&width=50",
    gender: 1,
  },
  {
    id: 6,
    name: "陈静",
    title: "人工智能伦理",
    description:
      "关注AI技术的社会影响、算法公平性和隐私保护问题，出版专著2部。",
    email: "chenjing@example.com",
    avatar: "/placeholder.svg?height=50&width=50",
    gender: 2,
  },
  {
    id: 7,
    name: "杨军",
    title: "云计算与大数据",
    description:
      "主导多个企业级云平台建设项目，擅长Hadoop、Spark等大数据技术栈。",
    email: "yangjun@example.com",
    avatar: "/placeholder.svg?height=50&width=50",
    gender: 1,
  },
  {
    id: 8,
    name: "周芳",
    title: "软件工程",
    description:
      "专注于软件设计模式、敏捷开发和DevOps实践，培养学生就业率100%。",
    email: "zhoufang@example.com",
    avatar: "/placeholder.svg?height=50&width=50",
    gender: 2,
  },
]);

// 下一个ID（用于新增时生成ID）
const nextId = ref(9);

// 模拟API延迟
const mockDelay = (ms = 500) =>
  new Promise((resolve) => setTimeout(resolve, ms));

// 搜索表单
const searchForm = reactive({
  name: "",
});

// 表格数据
const tableData = ref([]);
const loading = ref(false);

// 分页
const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0,
});

// 对话框
const dialogVisible = ref(false);
const dialogTitle = ref("新增教师");
const formRef = ref(null);
const formData = reactive({
  id: undefined,
  name: "",
  title: "",
  description: "",
  email: "",
  avatar: "",
  gender: 1,
});

const formRules = {
  name: [{ required: true, message: "请输入教师姓名", trigger: "blur" }],
  title: [{ required: true, message: "请输入研究方向", trigger: "blur" }],
  description: [{ required: true, message: "请输入教师详情", trigger: "blur" }],
  email: [
    { required: true, message: "请输入邮箱", trigger: "blur" },
    { type: "email", message: "请输入正确的邮箱格式", trigger: "blur" },
  ],
  avatar: [{ required: true, message: "请输入照片URL", trigger: "blur" }],
  gender: [{ required: true, message: "请选择性别", trigger: "change" }],
};

const fetchTeacherList = async () => {
  loading.value = true;
  try {
    if (USE_MOCK_DATA.value) {
      // 使用模拟数据
      await mockDelay(300);

      // 筛选数据
      let filteredData = mockTeachers.value;
      if (searchForm.name) {
        filteredData = filteredData.filter((item) =>
          item.name.includes(searchForm.name)
        );
      }

      // 分页
      const start = (pagination.page - 1) * pagination.pageSize;
      const end = start + pagination.pageSize;
      tableData.value = filteredData.slice(start, end);
      pagination.total = filteredData.length;
    } else {
      // 使用真实API
      const res = await realGetTeacherList({
        page: pagination.page,
        pageSize: pagination.pageSize,
        name: searchForm.name,
      });
      tableData.value = res.data.records || res.data.list || [];
      pagination.total = res.data.total || 0;
    }
  } catch (error) {
    console.error("获取教师列表失败:", error);
    ElMessage.error("获取教师列表失败");
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  pagination.page = 1;
  fetchTeacherList();
};

// 重置
const handleReset = () => {
  searchForm.name = "";
  pagination.page = 1;
  fetchTeacherList();
};

// 新增
const handleAdd = () => {
  dialogTitle.value = "新增教师";
  dialogVisible.value = true;
};

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = "编辑教师";
  Object.assign(formData, row);
  dialogVisible.value = true;
};

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除教师 "${row.name}" 吗？删除后将无法恢复！`,
      "删除确认",
      {
        confirmButtonText: "确认删除",
        cancelButtonText: "取消",
        type: "warning",
        center: true,
      }
    );

    if (USE_MOCK_DATA.value) {
      // 模拟删除
      await mockDelay(300);
      const index = mockTeachers.value.findIndex((item) => item.id === row.id);
      if (index > -1) {
        mockTeachers.value.splice(index, 1);
      }
    } else {
      // 真实API删除
      await realDeleteTeacher(row.id);
    }

    ElMessage.success("删除成功");
    fetchTeacherList();
  } catch (error) {
    if (error !== "cancel") {
      console.error("删除失败:", error);
      ElMessage.error("删除失败");
    }
  }
};

const handleSubmit = async () => {
  if (!formRef.value) return;

  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (USE_MOCK_DATA.value) {
          // 模拟提交
          await mockDelay(300);

          if (formData.id) {
            // 更新
            const index = mockTeachers.value.findIndex(
              (item) => item.id === formData.id
            );
            if (index > -1) {
              mockTeachers.value[index] = {
                ...mockTeachers.value[index],
                ...formData,
              };
            }
            ElMessage.success("更新成功");
          } else {
            // 新增
            const newTeacher = {
              ...formData,
              id: nextId.value++,
              createTime: new Date()
                .toLocaleString("zh-CN", {
                  year: "numeric",
                  month: "2-digit",
                  day: "2-digit",
                  hour: "2-digit",
                  minute: "2-digit",
                  second: "2-digit",
                  hour12: false,
                })
                .replace(/\//g, "-"),
            };
            mockTeachers.value.push(newTeacher);
            ElMessage.success("添加成功");
          }
        } else {
          // 使用真实API
          if (formData.id) {
            await realUpdateTeacher(formData);
            ElMessage.success("更新成功");
          } else {
            await realAddTeacher(formData);
            ElMessage.success("添加成功");
          }
        }

        dialogVisible.value = false;
        fetchTeacherList();
      } catch (error) {
        console.error("提交失败:", error);
        ElMessage.error("提交失败");
      }
    }
  });
};

// 对话框关闭
const handleDialogClose = () => {
  formRef.value?.resetFields();
  Object.assign(formData, {
    id: undefined,
    name: "",
    title: "",
    description: "",
    email: "",
    avatar: "",
    gender: 1,
  });
};

// 分页
const handleSizeChange = () => {
  fetchTeacherList();
};

const handleCurrentChange = () => {
  fetchTeacherList();
};

// 初始化
onMounted(() => {
  fetchTeacherList();
});
</script>

<style scoped>
.teacher-management {
  padding: 20px;
}

.search-card,
.toolbar-card {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
