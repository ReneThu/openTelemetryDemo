<template>
  <div class="ft-root">
    <div class="ft-header">
      <div class="ft-path">{{ path }}</div>
      <div class="ft-actions">
        <button class="ft-btn" @click="refresh" :disabled="loading">
          {{ loading ? 'Loading…' : 'Refresh' }}
        </button>
        <label class="ft-auto">
          <input type="checkbox" v-model="auto" />
          Auto-refresh
        </label>
      </div>
    </div>

    <div v-if="error" class="ft-error">{{ error }}</div>

    <ul v-if="tree" class="ft-tree">
      <TreeNode :node="tree" :expanded="expanded" @toggle="toggle" />
    </ul>

    <div v-else-if="!loading" class="ft-empty">No data</div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, onBeforeUnmount, defineProps, defineEmits } from 'vue'

const props = defineProps({
  path: { type: String, required: true },
  api: { type: String, default: '/api/file-tree' },
  pollMs: { type: Number, default: 2000 }, // auto-refresh interval
  startExpanded: { type: Boolean, default: true },
})

const tree = ref(null)
const loading = ref(false)
const error = ref('')
const expanded = ref(new Set()) // Set of directory absolute paths that are expanded
const auto = ref(true)
let timer = null

async function fetchTree() {
  loading.value = true
  error.value = ''
  try {
    const res = await fetch(`${props.api}?path=${encodeURIComponent(props.path)}`)
    if (!res.ok) throw new Error(`HTTP ${res.status}`)
    const data = await res.json()
    tree.value = data
    // Expand root the first time
    if (props.startExpanded && tree.value && tree.value.path && expanded.value.size === 0) {
      expanded.value.add(tree.value.path)
    }
  } catch (e) {
    error.value = String(e?.message || e)
  } finally {
    loading.value = false
  }
}

function refresh() {
  fetchTree()
}

function toggle(dirPath) {
  if (expanded.value.has(dirPath)) expanded.value.delete(dirPath)
  else expanded.value.add(dirPath)
  // force update by reassigning the Set (Vue reactivity for Set content)
  expanded.value = new Set(expanded.value)
}

watch(() => props.path, fetchTree, { immediate: true })

onMounted(() => {
  timer = setInterval(() => {
    if (auto.value && !loading.value) fetchTree()
  }, props.pollMs)
})

onBeforeUnmount(() => {
  if (timer) clearInterval(timer)
})

/* ----- Recursive Tree Node ----- */
const emits = defineEmits(['toggle'])
const TreeNode = {
  name: 'TreeNode',
  props: {
    node: { type: Object, required: true }, // { name, path, type: 'dir'|'file', children? }
    expanded: { type: Object, required: true },
  },
  emits: ['toggle'],
  setup(p, { emit }) {
    const onToggle = () => emit('toggle', p.node.path)
    return { onToggle }
  },
  template: `
    <li>
      <div v-if="node.type === 'dir'" class="ft-row">
        <span class="ft-caret" @click="onToggle">
          <span v-if="expanded.has(node.path)">▾</span>
          <span v-else>▸</span>
        </span>
        <span class="ft-icon ft-dir">📁</span>
        <span class="ft-name ft-dir-name" @click="onToggle">{{ node.name }}</span>
      </div>
      <ul v-if="node.type === 'dir' && expanded.has(node.path)" class="ft-children">
        <template v-if="node.children && node.children.length">
          <TreeNode v-for="child in node.children"
                    :key="child.path"
                    :node="child"
                    :expanded="expanded"
                    @toggle="$emit('toggle', $event)" />
        </template>
        <li v-else class="ft-dim">Empty</li>
      </ul>

      <div v-else-if="node.type === 'file'" class="ft-row">
        <span class="ft-caret ft-caret-placeholder"></span>
        <span class="ft-icon ft-file">📄</span>
        <span class="ft-name">{{ node.name }}</span>
        <span class="ft-meta" v-if="node.size != null">{{ prettySize(node.size) }}</span>
      </div>
    </li>
  `,
  methods: {
    prettySize(n) {
      if (n == null || isNaN(n)) return ''
      const units = ['B', 'KB', 'MB', 'GB']
      let i = 0
      let v = n
      while (v >= 1024 && i < units.length - 1) {
        v /= 1024
        i++
      }
      return `${v.toFixed(v < 10 && i > 0 ? 1 : 0)} ${units[i]}`
    },
  },
}
</script>

<style scoped>
/* Dark-theme friendly styles */
.ft-root {
  font-family: ui-sans-serif, system-ui, -apple-system, Segoe UI, Roboto, Inter, Helvetica, Arial, "Apple Color Emoji", "Segoe UI Emoji";
  color: #e5e7eb; /* slate-200 */
}

.ft-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 6px;
}

.ft-path {
  font-size: 0.9rem;
  color: #94a3b8; /* slate-400 */
  word-break: break-all;
}

.ft-actions {
  display: flex;
  gap: 10px;
  align-items: center;
}

.ft-btn {
  background: #1f2937; /* gray-800 */
  color: #e5e7eb;
  border: 1px solid #374151; /* gray-700 */
  border-radius: 6px;
  padding: 4px 10px;
  cursor: pointer;
}
.ft-btn:disabled {
  opacity: 0.6;
  cursor: default;
}
.ft-auto {
  font-size: 0.85rem;
  color: #a3a3a3;
  user-select: none;
}

.ft-error {
  color: #f87171; /* red-400 */
  font-size: 0.9rem;
  margin: 8px 0;
}

.ft-empty, .ft-dim {
  color: #9ca3af; /* gray-400 */
}

.ft-tree, .ft-children {
  list-style: none;
  padding-left: 0;
  margin: 0;
}

.ft-children {
  padding-left: 18px;
  border-left: 1px dashed #334155; /* slate-700 */
  margin-left: 7px;
}

.ft-row {
  display: flex;
  align-items: center;
  line-height: 1.7;
}

.ft-caret {
  width: 1.1em;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  margin-right: 2px;
  color: #cbd5e1; /* slate-300 */
  cursor: pointer;
}
.ft-caret-placeholder {
  visibility: hidden;
}

.ft-icon {
  margin-right: 6px;
}
.ft-dir {
  color: #60a5fa; /* blue-400 */
}
.ft-file {
  color: #cbd5e1; /* slate-300 */
}

.ft-name {
  white-space: nowrap;
}
.ft-dir-name {
  color: #93c5fd; /* blue-300 */
  cursor: pointer;
}

.ft-meta {
  margin-left: 8px;
  color: #64748b; /* slate-500 */
  font-size: 0.8rem;
}
</style>
