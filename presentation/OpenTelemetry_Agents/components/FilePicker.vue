<template>
  <div>
    <input type="file" multiple @change="onPick">
    <ul v-if="files.length">
      <li v-for="f in files" :key="f.name">{{ f.name }} ({{ f.size }} bytes)</li>
    </ul>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const files = ref([])

function onPick(e) {
  const list = e.target.files
  files.value = []
  for (let i = 0; i < list.length; i++) {
    files.value.push({
      name: list[i].name,
      size: list[i].size,
      type: list[i].type,
    })
  }
}
</script>
