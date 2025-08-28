<template>
  <iframe
    :src="iframeSrc"
    style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: 0;"
  />
</template>

<script setup>
import { ref } from 'vue'
import { onSlideEnter,  onSlideLeave } from '@slidev/client'

const props = defineProps({
  url: {
    type: String,
    required: true,
  },
})

// Reactive state for iframe source
const iframeSrc = ref('')

// Load the iframe only when the slide becomes active
onSlideEnter(() => {
  iframeSrc.value = props.url
})

onSlideLeave(() => {
  iframeSrc.value = ''
})
</script>
