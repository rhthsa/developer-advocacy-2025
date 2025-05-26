# OpenShift Observability - Distributed Tracing (Technical Preview)
<!-- TOC -->

- [OpenShift Observability - Distributed Tracing (Technical Preview)](#openshift-observability---distributed-tracing-technical-preview)
  - [What is Distributed Tracing?](#what-is-distributed-tracing)
  - [Summary](#summary)
  - [Next Step](#next-step)

<!-- /TOC -->

## What is Distributed Tracing?

https://github.com/rhthsa/developer-advocacy-2025/blob/main/config/otel/tempoMonolithic.yaml



oc create -k todo-kustomize/overlays/otel -n $PROJECT
oc wait --for condition=ready --timeout=180s pod -l app=todo-db  -n $PROJECT 
oc wait --for condition=ready --timeout=180s pod -l app=todo  -n $PROJECT


OTEL Auto-Instrumentation
git clone https://github.com/voraviz/openshift-otel.git


## Summary



## Next Step
- [Boost developer Productivity with Developer Hub](developerhub.md)