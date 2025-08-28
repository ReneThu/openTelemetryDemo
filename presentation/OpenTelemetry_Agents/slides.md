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
layout: full
---

<iframe src="htmldocs/log_output.html" width="100%" height="500px" style="border: none;"></iframe>

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

<img v-click
class="fit-picture-mercyotel"
src="./pictures/mercyotel.jpg"
/>

<style>
  .fit-picture-mercyotel {
    width: 450px;
    height: auto; /* Maintains aspect ratio */
  }
</style>


---
layout: center
---

# What is OpenTelemetry?

<ul>
  <li v-click>Vendor-neutral standard for telemetry</li>
  <li v-click>Composed of APIs, SDKs, and instrumentation</li>
  <li v-click>Standardized semantic conventions</li>
  <li v-click>Exports to many backends</li>
</ul>


---
layout: center
---

# OTel Components

<ul>
  <li v-click>API</li>
  <li v-click>SDK</li>
  <li v-click>
    <span >Zero-code</span>
  </li>
  <li v-click>Collector</li>
</ul>

<!-- Presenter Notes -->
<!-- 
- API: The interface developers use to instrument their code and define telemetry data structures.
- SDK: The implementation that processes telemetry data and sends it to exporters.
- Zero-code: Automatic instrumentation requiring no changes to application code.
- Collector: A service that receives telemetry data, processes it, and exports it to monitoring backends like Dynatrace or Prometheus.
-->

---
layout: center
---

<h1>How Can OpenTelemetry Be Used?</h1>

<ul>
  <li v-click><strong>SDK/API:</strong> Directly instrument your code using OpenTelemetry libraries.</li>
  <li v-click v-mark="{ at: 3, color: '#404c8f', type: 'highlight' }"><strong>Otel Agent:</strong> Attach an OpenTelemetry agent to your application for automatic instrumentation.</li>
</ul>


---
layout: center
---

<h1>Adding the OpenTelemetry Agent</h1>

<ol>
  <li v-click>Download the OpenTelemetry Java agent JAR file.</li>
  <li v-click>Start your application with the agent attached:
    <pre><code>java -javaagent:path/to/opentelemetry-javaagent.jar -jar your-app.jar</code></pre>
  </li>
</ol>


---
layout: center
---

<v-clicks at="1">
<div>

````md magic-move
```docker{all|all}
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/mainService-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
```
```docker
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY otel/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

COPY build/libs/mainService-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
```
````

</div>
</v-clicks>

---
layout: center
---

<v-clicks at="1">
<div>

````md magic-move
```docker{all|all}
services:
  mainService:
    image: mainservice:latest
    ports:
      - "8080:8080"
    container_name: main_service_container
    depends_on:
      guestBookService:
        condition: service_healthy
      dataBaseService:
        condition: service_healthy
    networks:
      - app-network
    environment:
      RABBITMQ_HOST: rabbitmq
      RABBITMQ_PORT: 5672
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/health"]
      interval: 2s
      timeout: 10s
      retries: 10
```
```docker
services:
  mainService:
    ....
    environment:
      RABBITMQ_HOST: rabbitmq
      RABBITMQ_PORT: 5672
      ....
    ....
```
```docker{all|7|8|9|all}
services:
  mainService:
    ....
    environment:
      RABBITMQ_HOST: rabbitmq
      RABBITMQ_PORT: 5672
      JAVA_TOOL_OPTIONS: "-javaagent:/otel/opentelemetry-javaagent.jar"
      OTEL_EXPORTER_OTLP_PROTOCOL: grpc
      OTEL_EXPORTER_OTLP_ENDPOINT: "http://jaeger:4317"
    ....
```
````

</div>
</v-clicks>

---
layout: center
---

<v-clicks at="1">
<div>

```docker{all|all|2|4-7|5|6|7|all}
  jaeger:
    image: jaegertracing/all-in-one:1.46
    container_name: jaeger
    ports:
      - "16686:16686"
      - "4317:4317"
      - "4318:4318"
    networks:
      - app-network
```

</div>
</v-clicks>

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

<h1>How Does OpenTelemetry Achieve This?</h1>

<p>OpenTelemetry leverages advanced techniques like bytecode instrumentation and the Java Virtual Machine Tool Interface (JVMTI) to collect telemetry data seamlessly.</p>

---
layout: center
---

<h1>Understanding JVMTI</h1>

<p>The Java Virtual Machine Tool Interface (JVMTI) is a native programming interface for the JVM. It allows tools to inspect and modify the state and behavior of Java applications at runtime.</p>
<ul>
  <li>Provides hooks for lifecycle events like class loading, thread start/stop.</li>
  <li>Used for profiling, debugging, and monitoring.</li>
</ul>

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