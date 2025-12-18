<template>
  <div class="rich-text-editor-wrapper">
    <div ref="editorContainer" class="quill-editor"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, watch, nextTick } from 'vue'
import Quill from 'quill'
import 'quill/dist/quill.snow.css'

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  placeholder: {
    type: String,
    default: '请输入内容...'
  },
  height: {
    type: String,
    default: '300px'
  },
  disabled: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:modelValue', 'change', 'ready'])

const editorContainer = ref(null)
let quillInstance = null
let isInternalChange = false

const toolbarOptions = [
  [{ header: [1, 2, 3, 4, 5, 6, false] }],
  [{ font: [] }],
  [{ size: ['small', false, 'large', 'huge'] }],
  ['bold', 'italic', 'underline', 'strike'],
  [{ color: [] }, { background: [] }],
  [{ script: 'sub' }, { script: 'super' }],
  [{ list: 'ordered' }, { list: 'bullet' }],
  [{ indent: '-1' }, { indent: '+1' }],
  [{ direction: 'rtl' }],
  [{ align: [] }],
  ['blockquote', 'code-block'],
  ['link', 'image'],
  ['clean']
]

// 初始化编辑器
const initEditor = () => {
  if (!editorContainer.value || quillInstance) return

  quillInstance = new Quill(editorContainer.value, {
    theme: 'snow',
    modules: {
      toolbar: toolbarOptions
    },
    placeholder: props.placeholder
  })

  // 设置初始内容
  if (props.modelValue) {
    quillInstance.clipboard.dangerouslyPasteHTML(props.modelValue)
  }

  // 监听内容变化
  quillInstance.on('text-change', () => {
    if (isInternalChange) return
    const html = quillInstance.root.innerHTML
    emit('update:modelValue', html)
    emit('change', html)
  })

  // 设置禁用状态
  if (props.disabled) {
    quillInstance.enable(false)
  }

  emit('ready', quillInstance)
}

// 监听 modelValue 变化
watch(
  () => props.modelValue,
  (newVal) => {
    if (!quillInstance) return
    const currentHtml = quillInstance.root.innerHTML
    if (newVal !== currentHtml) {
      isInternalChange = true
      if (newVal) {
        quillInstance.clipboard.dangerouslyPasteHTML(newVal)
      } else {
        quillInstance.setContents([])
      }
      nextTick(() => {
        isInternalChange = false
      })
    }
  }
)

// 监听禁用状态
watch(
  () => props.disabled,
  (newVal) => {
    if (quillInstance) {
      quillInstance.enable(!newVal)
    }
  }
)

onMounted(() => {
  nextTick(() => {
    initEditor()
  })
})

onBeforeUnmount(() => {
  if (quillInstance) {
    quillInstance.off('text-change')
    quillInstance = null
  }
})

// 暴露方法给父组件
defineExpose({
  getContent: () => quillInstance?.root.innerHTML || '',
  setContent: (html) => {
    if (quillInstance) {
      quillInstance.clipboard.dangerouslyPasteHTML(html || '')
    }
  },
  clearContent: () => {
    if (quillInstance) {
      quillInstance.setContents([])
    }
  },
  getQuillInstance: () => quillInstance
})
</script>

<style scoped>
.rich-text-editor-wrapper {
  width: 100%;
}

.quill-editor {
  height: v-bind(height);
  width: 100%;
}

/* 富文本内图片支持拖动和缩放句柄显示友好 */
::deep(.ql-editor img) {
  max-width: 100%;
  cursor: move;
}

/* 确保编辑器容器高度正确 */
:deep(.ql-container) {
  height: calc(v-bind(height) - 42px);
  font-size: 14px;
}

:deep(.ql-toolbar) {
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
  background: #f5f5f5;
}

:deep(.ql-container) {
  border-bottom-left-radius: 4px;
  border-bottom-right-radius: 4px;
}

/* 确保工具栏按钮可点击，移除可能影响点击的样式 */
:deep(.ql-toolbar .ql-formats) {
  margin-right: 10px;
}

/* 重置全局 button 样式对 Quill 工具栏按钮的影响 */
:deep(.ql-toolbar button) {
  background: transparent !important;
  background-color: transparent !important;
  border: none !important;
  border-radius: 0 !important;
  padding: 3px 5px !important;
  margin: 0 !important;
  width: 28px !important;
  height: 24px !important;
  display: inline-flex !important;
  align-items: center !important;
  justify-content: center !important;
  cursor: pointer !important;
  float: left !important;
  font-size: inherit !important;
  font-weight: inherit !important;
  font-family: inherit !important;
  transition: none !important;
}

:deep(.ql-toolbar button:hover),
:deep(.ql-toolbar button:focus),
:deep(.ql-toolbar button:focus-visible) {
  background-color: transparent !important;
  border: none !important;
  border-color: transparent !important;
  outline: none !important;
  color: #409eff !important;
}

:deep(.ql-toolbar button.ql-active) {
  background-color: transparent !important;
  color: #409eff !important;
}

:deep(.ql-toolbar button:hover .ql-stroke),
:deep(.ql-toolbar button.ql-active .ql-stroke),
:deep(.ql-toolbar .ql-picker-label:hover .ql-stroke) {
  stroke: #409eff !important;
}

:deep(.ql-toolbar button:hover .ql-fill),
:deep(.ql-toolbar button.ql-active .ql-fill) {
  fill: #409eff !important;
}

/* 确保 SVG 图标正确显示 */
:deep(.ql-toolbar button svg) {
  width: 18px;
  height: 18px;
  float: left;
}

:deep(.ql-snow .ql-stroke) {
  stroke: #444 !important;
  fill: none !important;
}

:deep(.ql-snow .ql-fill),
:deep(.ql-snow .ql-stroke.ql-fill) {
  fill: #444 !important;
}

:deep(.ql-snow .ql-picker) {
  color: #444;
}

:deep(.ql-snow .ql-picker-options) {
  background-color: #fff;
  border: 1px solid #ccc;
  box-shadow: 0 2px 8px rgba(0,0,0,0.15);
}

/* 重置 picker 下拉按钮样式 */
:deep(.ql-toolbar .ql-picker-label) {
  background: transparent !important;
  border: 1px solid transparent !important;
  border-radius: 0 !important;
  padding: 2px 5px !important;
}

:deep(.ql-toolbar .ql-picker-label:hover),
:deep(.ql-toolbar .ql-picker-label.ql-active) {
  color: #409eff !important;
  border-color: transparent !important;
}
</style>
