<template>
  <div class="full-less-padding">
    <slot />
  </div>
</template>

<style scoped>
.full-less-padding {
  display: flex;
  flex-direction: column;
  justify-content: center;

  padding: 20px; /* Reduced padding compared to the full layout */
  box-sizing: border-box;
  font-size: 1.5rem; /* Adjust font size if needed */
  overflow: hidden; /* Prevent scrollbars */
}
</style>
