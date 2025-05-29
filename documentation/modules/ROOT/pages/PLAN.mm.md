---
title: Tech Talk - Developer Series 2025
markmap:
  colorFreezeLevel: 4
---

## Developer Series 2025

- [Platform Engineering](https://tag-app-delivery.cncf.io/blog/proposal-platform-engineering-/)
  - [CNCF White Paper](https://tag-app-delivery.cncf.io/whitepapers/platforms/)
  - OpenShift Container "PLATFORM"
    - Build
      - Build with Buildpack
        - Shipwright
    - Deploy
        - CI with Tekton
        - CD with ArgoCD
    - Observability
      - Log
        - Loki
        - LogQL
      - Metrics
        - Developer Console
          - PromQL
        - Custom Metrics Autoscaler
        - [Custom Alert](https://github.com/voraviz/quarkus-todo-app/tree/otel?tab=readme-ov-file#auto-instrumentation)
        - Grafana Dashboard (SLI/SLO concept)
          - [Example from Service Mesh but concept is the same](https://github.com/voraviz/quarkus-todo-app/tree/otel?tab=readme-ov-file#auto-instrumentation)
      - Trace
        - [OTEL Auto-instrument](https://github.com/voraviz/quarkus-todo-app/tree/otel?tab=readme-ov-file#auto-instrumentation)
          - Java
          - Node
          - Go
    <!-- - Brand new features
      - OpenShift Lightspeed?
      - [Incident Dectection](https://developers.redhat.com/articles/2025/04/15/incident-detection-openshift-tech-preview-here?sc_cid=RHCTG0250000446542#)
      - [Observability Signal Correlation](https://developers.redhat.com/articles/2024/09/19/observability-signal-correlation-red-hat-openshift-technology-preview)
      - Deploy sample RAG app? -->
  - Developer Hub
    - Golden Path