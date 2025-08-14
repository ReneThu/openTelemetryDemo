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
layout: center
transition: slide-up
---

# Start Deployment

### Start the deployment

<button @click="runCommand('./start.sh no-tracing')">Start Without Tracing</button>
<button @click="runCommand('./start.sh with-tracing')">Start With Tracing</button>

