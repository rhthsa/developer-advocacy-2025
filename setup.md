6. Manual add account to argocd (in ACD CRD) before run update_argocd_password in lab-user-provisioner.sh
   
   ```
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

   ```
   rbac:
     defaultPolicy: 'role:admin'
   ```

7. Export lab user password and cluster admin password (the passwords should be there in the mail sent from RHDP). Then run [lab-user-provisioner.sh](scripts/lab-user-provisioner.sh) script with number of lab users as the script argument.

   For example, provisioning 5 lab users:

   ```sh
   export USER_PASSWORD=lzTGhfDLHifMVkfs
   export ADMIN_PASSWORD=KKnpB87F68F3PLiy
   export totalUsers=3
   ./lab-user-provisioner.sh 3
   ```

8. Check imagestream in openshift projecct, Add name tag in imagestream java

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

if not found crd, restart operator
create rolebinding for shipwright-build-aggregate-edit to userx  



argocd

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


deploy https://github.com/chatapazar/openshift-workshop.git
folder sample for replace https://httpbin.org/status/200

route=$(oc get route -l app.kubernetes.io/component=lokistack-gateway -n openshift-logging -o jsonpath={.items[0].spec.host})
echo $route
curl -vvv -k -G -H "Authorization: Bearer $(oc whoami -t)" "https://${route}/api/logs/v1/application/loki/api/v1/label" | jq
curl -k -H "Authorization: Bearer $(oc whoami -t)" "https://${route}/api/logs/v1/application/loki/api/v1/query" 

curl -G -s -H "Authorization: Bearer $(oc whoami -t)" "https://${route}/api/logs/v1/application/loki/api/v1/query_range" --data-urlencode 'query={ log_type="application", kubernetes_namespace_name="user1-observe",  kubernetes_container_name="backend" } ' | jq



