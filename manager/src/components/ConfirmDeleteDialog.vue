<template>
  <el-dialog
    v-model="visible"
    :title="title"
    :width="width"
    :before-close="handleCancel"
  >
    <div class="confirm-content">
      <el-icon class="warning-icon" :size="48" color="#faad14">
        <WarningFilled />
      </el-icon>
      <div class="message">{{ message }}</div>
    </div>

    <template #footer>
      <el-button @click="handleCancel" :disabled="loading">
        {{ cancelText }}
      </el-button>
      <el-button type="danger" @click="handleConfirm" :loading="loading">
        {{ confirmText }}
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, watch } from "vue";
import { WarningFilled } from "@element-plus/icons-vue";

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: "提示",
  },
  message: {
    type: String,
    default: "确定要删除吗？",
  },
  confirmText: {
    type: String,
    default: "确定",
  },
  cancelText: {
    type: String,
    default: "取消",
  },
  width: {
    type: String,
    default: "400px",
  },
});

const emit = defineEmits(["update:modelValue", "confirm", "cancel"]);

const visible = ref(props.modelValue);
const loading = ref(false);

watch(
  () => props.modelValue,
  (newVal) => {
    visible.value = newVal;
  }
);

watch(visible, (newVal) => {
  emit("update:modelValue", newVal);
});

const handleConfirm = async () => {
  loading.value = true;
  try {
    await emit("confirm");
  } finally {
    loading.value = false;
    visible.value = false;
  }
};

const handleCancel = () => {
  emit("cancel");
  visible.value = false;
};
</script>

<style scoped>
.confirm-content {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px 0;
}

.warning-icon {
  flex-shrink: 0;
}

.message {
  font-size: 14px;
  color: #333;
  line-height: 1.6;
}
</style>