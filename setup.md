# Workshop Setup Guide

1. Request OpenShift cluster e.g. **OpenShift Cluster Workshop** with a number of users as needed from RHPDS.

2. Open a terminal on your computer then login to cluster with cluster admin user i.e. **admin** user.

3. Clone this repository to your computer then go to [script](scripts/) directory.

4. Run [workshop-provisioner.sh](scripts/workshop-provisioner.sh) script to setup all required operators and software instances.

   ```sh
    ./workshop-provisioner.sh
   ```

5. X

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
   export USER_PASSWORD=N44PdL9hZ4LrzJaY
   export ADMIN_PASSWORD=FuOPVkXuQYLwD8G9
   export totalUsers=3
   ./lab-user-provisioner.sh 3
   ```

