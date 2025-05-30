- Manual add account to argocd (in ACD CRD) before run update_argocd_password in lab-user-provisioner.sh
   
   ```yaml
   extraConfig:
     accounts.user1: apiKey, login
     accounts.user2: apiKey, login
     accounts.user3: apiKey, login
     accounts.user4: apiKey, login
     accounts.user5: apiKey, login
     accounts.user6: apiKey, login
     accounts.user7: apiKey, login
     accounts.user8: apiKey, login
     accounts.user9: apiKey, login
     accounts.user10: apiKey, login
     accounts.user11: apiKey, login
     accounts.user12: apiKey, login
     accounts.user13: apiKey, login
     accounts.user14: apiKey, login
     accounts.user15: apiKey, login
     accounts.user16: apiKey, login
     accounts.user17: apiKey, login
     accounts.user18: apiKey, login
     accounts.user19: apiKey, login
     accounts.user20: apiKey, login
     accounts.user21: apiKey, login
     accounts.user22: apiKey, login
     accounts.user23: apiKey, login
     accounts.user24: apiKey, login
     accounts.user25: apiKey, login
     accounts.user26: apiKey, login
     accounts.user27: apiKey, login
     accounts.user28: apiKey, login
     accounts.user29: apiKey, login
     accounts.user30: apiKey, login
     accounts.user31: apiKey, login
     accounts.user32: apiKey, login
     accounts.user33: apiKey, login
     accounts.user34: apiKey, login
     accounts.user35: apiKey, login
     accounts.user36: apiKey, login
     accounts.user37: apiKey, login
     accounts.user38: apiKey, login
     accounts.user39: apiKey, login
     accounts.user40: apiKey, login
     accounts.user41: apiKey, login
     accounts.user42: apiKey, login
     accounts.user43: apiKey, login
     accounts.user44: apiKey, login
     accounts.user45: apiKey, login
     accounts.user46: apiKey, login
     accounts.user47: apiKey, login
     accounts.user48: apiKey, login
     accounts.user49: apiKey, login
     accounts.user50: apiKey, login
    ```
   
   and add defaultpolicy to role:admin 

   ```yaml
   rbac:
     defaultPolicy: 'role:admin'
   ```

  - Export lab user password and cluster admin password (the passwords should be there in the mail sent from RHDP). Then run [lab-user-provisioner.sh](scripts/lab-user-provisioner.sh) script with number of lab users as the script argument.

   For example, provisioning 5 lab users:

   ```sh
   export USER_PASSWORD=XVziuhmw9ivsPVIm
   export ADMIN_PASSWORD=adm
   export totalUsers=10
   ./lab-user-provisioner.sh 3
   ```

- Check imagestream in openshift projecct, Add name tag in imagestream java

  ```yaml
    - name: openjdk-21-ubi9
      annotations:
        description: Build and run Java applications using Maven and OpenJDK 21.
        iconClass: icon-rh-openjdk
        openshift.io/display-name: Red Hat OpenJDK 21 (UBI 9)
        sampleContextDir: undertow-servlet
        sampleRepo: 'https://github.com/jboss-openshift/openshift-quickstarts'
        supports: 'java:21,java'
        tags: 'builder,java,openjdk'
        version: '21'
      from:
        kind: DockerImage
        name: 'registry.redhat.io/ubi9/openjdk-21:latest'
      generation: 2
      importPolicy:
        importMode: Legacy
      referencePolicy:
        type: Local
  ```

- For build of openshift if not found crd, restart operator

- ArgoCD

  ```ssh
  ARGOCD=$(oc get route/openshift-gitops-server -n openshift-gitops -o jsonpath='{.spec.host}')
  echo https://$ARGOCD

  PASSWORD=$(oc extract secret/openshift-gitops-cluster -n openshift-gitops --to=-) 2>/dev/null
  echo $PASSWORD

  argocd login $ARGOCD  --insecure \
  --username admin \
  --password $PASSWORD

  oc config rename-context $(oc config current-context) dev-cluster
  argocd cluster add dev-cluster
  oc adm policy add-cluster-role-to-user cluster-admin -z openshift-gitops-argocd-application-controller -n openshift-gitops
  
  for i in $( seq 1 $totalUsers )
    do
        username=user$i
        argocd account update-password --account $username --new-password $USER_PASSWORD --current-password $PASSWORD
    done
  ```


- Deploy https://github.com/chatapazar/openshift-workshop.git folder sample for replace https://httpbin.org/status/200, project test, add view to all user, http://test.test.svc.cluster.local:8080/status/200
  
  

- OTEL

  install tempo operator
  install clsuter observability
  install build of opentelemetry
  run config/otel/tempo-pre.yaml
  run config/otel/ui-plugin.yaml
  run config/otel/otel-go-instrument-scc.yaml
  run tempo-sa.yaml --> per user


- DeveloperHub
  
  git clone software-templates repository
  software-templates/scaffolder-templates/quarkus-web-template/
  
  remove src/test/java/${{values.java_package_name}}/*
  remove src/main/java/${{values.java_package_name}}/*
  remove src/main/resources/META-INF
  
  copy 
  manifests/helm/build/templates/pipeline-build.yaml
  skeleton/src/main/docker/Dockerfile.jvm
  skeleton/src/main/java/${{values.java_package_name}}/*.java
  skeleton/src/main/resources/*
  skeleton/pom.xml
  skeleton/Dockerfile


  