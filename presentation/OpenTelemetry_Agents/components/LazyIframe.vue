<template>
  <div class="iframe-frame" style="--gap: 6px; --radius: 0px; --frame-color: #334155;">
    <iframe
      :src="iframeSrc"
      class="iframe-content"
    />
  </div>
</template>

<style scoped>
.iframe-frame {
  position: absolute;
  inset: 0;                 /* fill the slide */
  box-sizing: border-box;
  padding: var(--gap);      /* space around the iframe */
  border: 0px solid var(--frame-color);
  border-radius: var(--radius);
}

.iframe-content {
  width: 100%;
  height: 100%;
  display: block;
  border: 0;
  /* Make iframe corners follow the frame. Keep radius >= gap to avoid negatives. */
  border-radius: calc(var(--radius) - var(--gap));
}
</style>


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
