// composables/useGlobalId.js
import { ref } from 'vue'

// Shared state for the ID
const globalId = ref(null) // Initialize with `null` or a default value

export function useGlobalId() {
  // Function to set the ID
  const setId = (id) => {
    globalId.value = id
  }

  // Function to clear the ID (optional)
  const clearId = () => {
    globalId.value = null
  }

  // Return the ID and functions to interact with it
  return {
    globalId,
    setId,
    clearId,
  }
}
