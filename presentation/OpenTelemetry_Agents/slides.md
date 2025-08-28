---
# You can also start simply with 'default'
#theme: seriph
# random image from a curated Unsplash collection by Anthony
# like them? see https://unsplash.com/collections/94734566/slidev
#background: https://cover.sli.dev
# some information about your slides (markdown enabled)
title: Demystifying OpenTelemetry, Java Agents Unveiled
info: |
  ## Slidev Starter Template
  Presentation slides for developers.

  Learn more at [Sli.dev](https://sli.dev)
# apply unocss classes to the current slide

# https://sli.dev/features/drawing
drawings:
  persist: false
# slide transition: https://sli.dev/guide/animations.html#slide-transitions
transition: slide-up
# enable MDC Syntax: https://sli.dev/features/mdc
mdc: true
# <!-- Empty slide -->
layout: center
---

<style>
.headline {
    font-weight: 1000;
    text-align: center;
    font-size: 50px;
}

.headline-smol {
    font-weight: bold;
    text-align: center;
}

.addonestuff {
    font-weight: bold;
    text-align: center;
}
.centerLogo {
    justify-items: anchor-center;
    transform: scale(0.3);
}
.centerQrCOde {
    justify-items: anchor-center;
    transform: scale(0.3);
}
</style>


<h1 class="headline">Demystifying OpenTelemetry: Java Agents Unveiled</h1><br />
<h2 class="headline-smol">Marco Sussitz</h2>

<div class="addonestuff">Senior Software Developer at Dynatrace</div>
<br /><br />
<div class="centerLogo">
    <img src="./pictures/Dynatrace_Logo.png" alt=""/>
</div>

---
title: Exited To Release Meme
layout: center
---

<img v-click
class="fit-picture-fuckyeah"
src="./pictures/fuckYeah.webp"
/>

<style>
  .fit-picture-fuckyeah {
    width: 300px;
    height: auto; /* Maintains aspect ratio */
  }
</style>


---
title: ContainerInfra
layout: full
---

<style>
/* Updated styles for black theme and larger elements */
.svc rect {
  fill: #333333; /* Dark gray for nodes */
  stroke: #ffffff; /* White borders */
  stroke-width: 1.5;
  rx: 8;
  ry: 8;
  filter: drop-shadow(0 1px 2px rgba(255, 255, 255, 0.2)); /* Light shadow */
}
.svc text {
  fill: #ffffff; /* White text for contrast */
  font-size: 40px; /* Scaled font size */
  pointer-events: none;
  text-anchor: middle;
  dominant-baseline: middle;
}
.http {
  stroke: #1f77b4; /* Blue for HTTP connections */
}
.mq {
  stroke: #ff7f0e; /* Orange for MQ connections */
}
.link {
  fill: none;
  stroke-width: 7.5; /* Scaled line thickness */
  marker-end: url(#arrow);
}
</style>

<svg viewBox="0 0 2500 1500" preserveAspectRatio="xMidYMid meet" width="100%">
  <!-- Arrowhead definition -->
  <defs>
    <marker id="arrow" viewBox="0 0 10 10" refX="8" refY="5" markerWidth="15" markerHeight="15" orient="auto" markerUnits="strokeWidth">
      <path d="M 0 0 L 10 5 L 0 10 Z" fill="#1f77b4" />
    </marker>
  </defs>

  <!-- STEP 1 -------------------------------------------------------------- -->
  <g v-click class="svc" id="mainService">
    <rect x="175" y="500" width="400" height="200" />
    <text x="375" y="600">mainService</text>
  </g>

  <!-- STEP 2 -------------------------------------------------------------- -->
  <g v-click>
    <!-- guestBookService node -->
    <g class="svc" id="guestBookService">
      <rect x="700" y="500" width="450" height="200" />
      <text x="925" y="600">guestBookService</text>
    </g>
    <!-- main → guestBook arrow -->
    <path class="link http" d="M 575 600 L 700 600" />
  </g>

  <!-- STEP 3 -------------------------------------------------------------- -->
  <g v-click>
    <!-- dataBaseService node -->
    <g class="svc" id="dataBaseService">
      <rect x="1300" y="500" width="500" height="200" />
      <text x="1550" y="600">dataBaseService</text>
    </g>
    <!-- guestBook → dataBase arrow -->
    <path class="link http" d="M 1150 600 L 1300 600" />
  </g>

  <!-- STEP 4 -------------------------------------------------------------- -->
  <g v-click>
    <!-- mysql node -->
    <g class="svc" id="mysql">
      <rect x="1950" y="500" width="400" height="200" />
      <text x="2150" y="600">mysql</text>
    </g>
    <!-- dataBase → mysql arrow -->
    <path class="link http" d="M 1800 600 L 1950 600" />
  </g>

  <!-- STEP 5 -------------------------------------------------------------- -->
  <g v-click>
    <!-- rabbitmq node -->
    <g class="svc" id="rabbitmq">
      <rect x="700" y="1000" width="450" height="200" />
      <text x="925" y="1100">rabbitmq</text>
    </g>
    <!-- main → rabbitmq arrow -->
    <path class="link mq" d="M 375 700 L 375 1000 L 700 1100" />
  </g>

  <!-- STEP 6 -------------------------------------------------------------- -->
  <g v-click>
    <!-- emailService node -->
    <g class="svc" id="emailService">
      <rect x="1300" y="1000" width="500" height="200" />
      <text x="1550" y="1100">emailService</text>
    </g>
    <!-- rabbitmq → emailService arrow -->
    <path class="link mq" d="M 1150 1100 L 1300 1100" />
  </g>
</svg>

---
layout: FullLessPadding
---

<DockerManager
dockerComposePath="/home/marco/Documents/Development/techEvangelistGeneric/otelDemo/docker-compose.yaml"
name="simple-deploy"
/>


---
layout: FullLessPadding
---
<LazyIframe url="http://localhost:8080/" />

---
layout: center
---

<h1>TODO not for AI add slide with log output</h1>

---
layout: center
---

<img v-click
class="fit-picture-tooMaynLogs"
src="./pictures/tooManyLogs.jpeg"
/>

<style>
  .fit-picture-tooMaynLogs {
    width: 450px;
    height: auto; /* Maintains aspect ratio */
  }
</style>

---
layout: FullLessPadding
---

<DockerComposeManager />

---
layout: center
---

<h1>TODO not for AI add mercy meme with otel</h1>

---
layout: center
---

<h1>TODO add slide explaining what otel is</h1>

---
layout: center
---

<h1>TODO add slide explaining what are the other monitoring tools that we are going to use</h1>

---
layout: center
---

<h1>TODO add slide explaining how otel can be used e.g. so sdk api and the otel agent</h1>

---
layout: center
---

<h1>TODO add slide showing how the otel agent has to be added to your application</h1>

---
layout: center
---

<h1>TODO not for AI add a code example how I have added otel to my docker compose. This will need to be split up into multible slides</h1>


---
layout: center
---

<h1>TODO not for AI add a new deplyoment slides for the deploy with otel</h1>

---
layout: center
---

<h1>TODO not for AI add Iframes of the varius tools that I am going to show</h1>

---
layout: center
---

<h1>TODO add slide asking the question how is otel able to do that</h1>

---
layout: center
---

<h1>TODO long story short with the JVMTI. add slide explaining how the JVMTI works and what it does</h1>

---
layout: center
---

<h1>TODO JVMTI is not create what else can we use, java agents!</h1>

---
layout: center
---

<h1>TODO not for AI show basic example on how java agents would work. take this from other presentations</h1>

---
layout: center
---

<h1>TODO not for AI use the agent agent project to show what otel does do your applications</h1>

---
layout: center
---

<h1>TODO not for AI otel is using some otel classes where are thy comming from what classloader is loading those</h1>

---
layout: center
---

<h1>TODO not for AI explain the otel infrastrcutre</h1>