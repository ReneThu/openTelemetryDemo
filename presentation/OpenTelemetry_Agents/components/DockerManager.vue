<template>
  <div id="docker-manager">
    <!-- Controls Section -->
    <div id="controls">
      <button @click="createDocker">Prepare Containers</button>
      <button @click="startDocker">Docker Up</button>
      <button @click="stopDocker">Docker Down</button>
    </div>

    <!-- Iframe Section -->
    <iframe id="docker-watch" :src="iframeSrc" />
  </div>
</template>

<script>
export default {
  props: {
    dockerComposePath: {
      type: String,
      required: true,
    },
    name: {
      type: String,
      required: false, // Optional prop for additional functionality
    },
  },
  data() {
    return {
      dockerId: null,
      iframeSrc: "",
    };
  },
  methods: {
    async createDocker() {
      try {
        const response = await fetch("http://localhost:3030/docker-compose/create", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            path: this.dockerComposePath, // Use the prop for the Docker Compose file path
            name: this.name
          }),
        });
        const data = await response.json();
        this.dockerId = data.id;

        // Update iframe source
        this.iframeSrc = `http://localhost:3030/docker/watch/${this.dockerId}`;
      } catch (error) {
        console.error("Error creating Docker instance:", error);
      }
    },
    async startDocker() {
      if (!this.dockerId) {
        alert("Please create a Docker instance first.");
        return;
      }
      try {
        await fetch(`http://localhost:3030/docker-compose/start/${this.dockerId}`, {
          method: "POST",
        });
      } catch (error) {
        console.error("Error starting Docker instance:", error);
      }
    },
    async stopDocker() {
      if (!this.dockerId) {
        alert("Please create a Docker instance first.");
        return;
      }
      try {
        await fetch(`http://localhost:3030/docker-compose/stop/${this.dockerId}`, {
          method: "POST",
        });
      } catch (error) {
        console.error("Error stopping Docker instance:", error);
      }
    },
  },
};
</script>

<style scoped>
/* Styling for the Docker Manager */
#docker-manager {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
  background-color: #121212; /* Very dark gray background */
  color: #ffffff; /* White text for contrast */
}

#controls {
  display: flex;
  justify-content: space-around;
  padding: 15px;
  background-color: #1e1e1e; /* Dark gray for the controls section */
  border-bottom: 1px solid #444; /* Subtle border for separation */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.5); /* Modern shadow effect */
  z-index: 1;
}

button {
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  background-color: #333; /* Dark gray for buttons */
  color: #ffffff; /* White text on buttons */
  border: 1px solid #444; /* Subtle border */
  border-radius: 5px;
  transition: background-color 0.3s ease, transform 0.2s ease; /* Smooth hover effect */
}

button:hover {
  background-color: #444; /* Slightly lighter on hover */
  transform: scale(1.05); /* Slight zoom effect */
}

iframe {
  flex: 1;
  width: 100%;
  border: none;
  background-color: #1e1e1e; /* Matches the dark theme */
  overflow: hidden; /* Prevent scrollbars */
}
</style>
