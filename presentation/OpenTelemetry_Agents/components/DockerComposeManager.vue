<template>
  <div id="docker-compose-manager">
    <!-- Header Section -->
    <header>
      <h1>Docker Compose Manager</h1>
    </header>

    <!-- Container Section -->
    <div class="container">
      <ul class="compose-list">
        <li
          v-for="compose in dockerComposes"
          :key="compose.id"
          class="compose-item"
        >
          <p>
            {{ compose.name }} -
            <span :class="['status', compose.status.toLowerCase()]">
              {{ compose.status }}
            </span>
          </p>
          <button
            class="stop-button"
            @click="stopDockerCompose(compose.id)"
          >
            Stop
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "DockerComposeManager",
  data() {
    return {
      dockerComposes: [], // List of Docker Compose instances
      refreshInterval: null, // Interval ID for refreshing the list
    };
  },
  methods: {
    // Fetch and display the list of Docker Compose instances
    async fetchDockerComposes() {
      try {
        const response = await fetch("http://localhost:3030/docker-compose/list");
        this.dockerComposes = await response.json();
      } catch (error) {
        console.error("Error fetching Docker Compose list:", error);
      }
    },

    // Stop a Docker Compose instance
    async stopDockerCompose(id) {
      try {
        const response = await fetch(
          `http://localhost:3030/docker-compose/stop/${id}`,
          { method: "POST" }
        );
        if (response.ok) {
          this.fetchDockerComposes(); // Refresh the list
        } else {
          alert("Failed to stop Docker Compose.");
        }
      } catch (error) {
        console.error("Error stopping Docker Compose:", error);
      }
    },
  },
  mounted() {
    // Fetch the list of Docker Compose instances on component mount
    this.fetchDockerComposes();

    // Set up an interval to refresh the list every 5 seconds
    this.refreshInterval = setInterval(this.fetchDockerComposes, 5000);
  },
  beforeUnmount() {
    // Clear the interval when the component is unmounted
    if (this.refreshInterval) {
      clearInterval(this.refreshInterval);
    }
  },
};
</script>

<style scoped>
/* Styling for the Docker Compose Manager */
body {
  font-family: Arial, sans-serif;
  background-color: #121212;
  color: #e0e0e0;
  margin: 0;
  padding: 0;
}

header {
  background-color: #1e1e1e;
  padding: 20px;
  text-align: center;
}

header h1 {
  margin: 0;
  color: #ffffff;
}

.container {
  padding: 20px;
}

.compose-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.compose-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #1e1e1e;
  margin-bottom: 10px;
  padding: 10px 20px;
  border-radius: 5px;
}

.compose-item p {
  margin: 0;
}

.status {
  font-weight: bold;
  text-transform: uppercase;
}

.status.running {
  color: #4caf50;
}

.status.stopped {
  color: #f44336;
}

.status.unknown,
.status.created {
  color: #ff9800;
}

.stop-button {
  background-color: #f44336;
  color: #ffffff;
  border: none;
  padding: 5px 10px;
  border-radius: 3px;
  cursor: pointer;
}

.stop-button:hover {
  background-color: #d32f2f;
}
</style>
