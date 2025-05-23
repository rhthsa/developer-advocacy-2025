#!/bin/bash

####################################################
# Functions
####################################################
repeat() {
    echo
    for i in {1..120}; do
        echo -n "$1"
    done
    echo
}

create_projects() {
    echo

    for i in $( seq 1 $totalUsers )
    do
        echo ""
        echo "Logging in as user$i user to create projects..."
        echo

        oc login -u user$i -p $USER_PASSWORD --insecure-skip-tls-verify
        oc new-project user$i-shipwright
        oc new-project user$i-tekton
        oc new-project user$i-gitops-dev
        oc new-project user$i-gitops-prod
        oc new-project user$i-observe

        oc adm policy add-role-to-user view user$i -n gitea
        oc adm policy add-role-to-user view user$i -n openshift-pipelines
        oc adm policy add-role-to-user shpiwright-build-aggregate-view user$i -n user$i-shipwright
        
        oc adm policy add-role-to-user monitoring-edit user1 -n user1-observe
        oc adm policy add-role-to-user monitoring-rules-edit user1 -n user1-observe
        
        # create crb user-cluster-monitoring-view for cluster-monitoring-view add user to this crb
        repeat '-'
    done
}

add_monitoring_edit_role_to_user()
{
    echo ""
    echo "Logging in as cluster administrator to add monitoring edit role to users..."
    echo

    oc login -u admin -p $ADMIN_PASSWORD --insecure-skip-tls-verify

    for i in $( seq 1 $totalUsers )
    do
        oc adm policy add-role-to-user monitoring-edit user$i -n user$i-observe
        oc adm policy add-role-to-user monitoring-edit user$i -n user$i-dev
        oc adm policy add-role-to-user monitoring-edit user$i -n user$i-sit
        oc adm policy add-role-to-user monitoring-edit user$i -n user$i-prod
    done
}

add_logging_view_role_to_user()
{

    echo ""
    echo "Add Logging View to user project ..."
    echo

    oc login -u admin -p $ADMIN_PASSWORD --insecure-skip-tls-verify

    for i in $( seq 1 $totalUsers )
    do
        # if test $i -gt 2;
        # then
        echo
        echo "Add cluster-logging-application-view to project $i ..."
        echo

        cat ../manifests/logging-view.yaml | sed "s#NAMESPACE#user$i-observe#g" | sed "s#USERNAME#user$i#g" | oc apply -n user$i-observe -f -

        # cat ../manifests/logging-view.yaml | sed "s#NAMESPACE#user$i-dev#g" | sed "s#USERNAME#user$i#g" | oc apply -n user$i-dev -f -
        # cat ../manifests/logging-view.yaml | sed "s#NAMESPACE#user$i-sit#g" | sed "s#USERNAME#user$i#g" | oc apply -n user$i-sit -f -
        # cat ../manifests/logging-view.yaml | sed "s#NAMESPACE#user$i-prod#g" | sed "s#USERNAME#user$i#g" | oc apply -n user$i-prod -f -
        
        # fi
    done

}


add_ui_serviceaccount() {

    echo ""
    echo "Logging in as cluster administrator to add ui serviceaccount to ..."
    echo

    oc login -u admin -p $ADMIN_PASSWORD --insecure-skip-tls-verify

    for i in $( seq 1 $totalUsers )
    do
        oc create serviceaccount ui -n user$i-super-heroes
        oc adm policy add-scc-to-user anyuid -z ui -n user$i-dev
        oc adm policy add-scc-to-user anyuid -z ui -n user$i-sit
        oc adm policy add-scc-to-user anyuid -z ui -n user$i-prod
    done
}

create_argocd_user() {

}

update_argocd_password(){

    oc adm policy add-cluster-role-to-user cluster-admin -z openshift-gitops-argocd-application-controller -n openshift-gitops
    ARGOCD=$(oc get route/openshift-gitops-server -n openshift-gitops -o jsonpath='{.spec.host}')
    echo https://$ARGOCD
    PASSWORD=$(oc extract secret/openshift-gitops-cluster -n openshift-gitops --to=-) 2>/dev/null
    echo $PASSWORD
    argocd login $ARGOCD  --insecure --username admin --password $PASSWORD
    for i in $( seq 1 $totalUsers )
    do
        username=user$i
        argocd account update-password --account $username --new-password $USER_PASSWORD --current-password $PASSWORD
    done
}

####################################################
# Main (Entry point)
####################################################
totalUsers=$1

create_projects
repeat '-'

#add_monitoring_edit_role_to_user
#repeat '-'

#add_ui_serviceaccount
#repeat '-'

#add_logging_view_role_to_user
#repeat '-'

#update_argocd_password
#repeat '-'

oc project default

echo "Done!!!"