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

enable_user_workload_monitoring()
{
    echo
    echo "Enabling monitoring for user-defined projects..."
    echo

    oc apply -f ../manifests/cluster-monitoring-config.yml -n openshift-monitoring
}

install_operator() {
    operatorNameParam=$1
    operatorDescParam=$2
    ymlFilePathParam=$3
    project=$4

    echo
    echo "Installing $operatorDescParam to $project project..."
    echo

    oc apply -f $ymlFilePathParam -n $project

    echo
    echo "Waiting for $operatorDescParam to be available..."
    echo

    available="False"

    while [[ $available != "True" ]]; do
        sleep 5
        available=$(oc get -n $project operators.operators.coreos.com \
            $operatorNameParam.$project \
            -o jsonpath='{.status.components.refs[?(@.apiVersion=="apps/v1")].conditions[?(@.type=="Available")].status}')
    done

    echo "$operatorDescParam is now available!"
}

install_developer_hub() {
    operatorName=rhdh
    operatorDesc="Red Hat Developer Hub"
    ymlFilePath=../manifests/developerhub-operator.yml
    project=rhdh-operator

    install_operator $operatorName "$operatorDesc" $ymlFilePath $project
}

install_tempo() {
    project=openshift-operators
    operatorName=tempo-product
    operatorDesc="Tempo Operator"
    ymlFilePath=../manifests/tempo-subscription.yml

    install_operator $operatorName "$operatorDesc" $ymlFilePath $project
}

install_web_terminal() {
    operatorName=web-terminal
    operatorDesc="Web Terminal"
    ymlFilePath=../manifests/web-terminal-subscription.yml
    project=openshift-operators

    install_operator $operatorName "$operatorDesc" $ymlFilePath $project
}

install_loki() {
    
    oc create -f ../manifests/logging-operator.yaml
    oc create -f ../manifests/loki-operator.yaml
    sleep 60
    oc wait --for condition=established --timeout=180s \
    crd/lokistacks.loki.grafana.com \
    crd/clusterloggings.logging.openshift.io
    oc get csv -n openshift-logging
    
    S3_BUCKET=$(oc get configs.imageregistry.operator.openshift.io/cluster -o jsonpath='{.spec.storage.s3.bucket}' -n openshift-image-registry)
    REGION=$(oc get configs.imageregistry.operator.openshift.io/cluster -o jsonpath='{.spec.storage.s3.region}' -n openshift-image-registry)
    ACCESS_KEY_ID=$(oc get secret image-registry-private-configuration -o jsonpath='{.data.credentials}' -n openshift-image-registry|base64 -d|grep aws_access_key_id|awk -F'=' '{print $2}'|sed 's/^[ ]*//')
    SECRET_ACCESS_KEY=$(oc get secret image-registry-private-configuration -o jsonpath='{.data.credentials}' -n openshift-image-registry|base64 -d|grep aws_secret_access_key|awk -F'=' '{print $2}'|sed 's/^[ ]*//')
    ENDPOINT=$(echo "https://s3.$REGION.amazonaws.com")
    DEFAULT_STORAGE_CLASS=$(oc get sc -A -o jsonpath='{.items[?(@.metadata.annotations.storageclass\.kubernetes\.io/is-default-class=="true")].metadata.name}')

    cat ../manifests/logging-loki-instance.yaml \
    |sed 's/S3_BUCKET/'$S3_BUCKET'/' \
    |sed 's/REGION/'$REGION'/' \
    |sed 's|ACCESS_KEY_ID|'$ACCESS_KEY_ID'|' \
    |sed 's|SECRET_ACCESS_KEY|'$SECRET_ACCESS_KEY'|' \
    |sed 's|ENDPOINT|'$ENDPOINT'|'\
    |sed 's|DEFAULT_STORAGE_CLASS|'$DEFAULT_STORAGE_CLASS'|' \
    |oc apply -f -
    
    sleep 60
    
    oc get po -n openshift-logging
}

install_gitea() {

echo
    echo "Installing Gitea to gitea project..."
    echo

    oc apply -k ../manifests/gitea/OLMDeploy

    echo
    echo "Waiting for Gitea to be available..."
    echo

    available="False"

    while [[ $available != "True" ]]; do
        sleep 5
        available=$(oc get -n gitea-operator operators.operators.coreos.com \
            gitea-operator.gitea-operator \
            -o jsonpath='{.status.components.refs[?(@.apiVersion=="apps/v1")].conditions[?(@.type=="Available")].status}')
    done

    echo "Gitea Operator is now available!"
    
    oc new-project gitea
    cat ../manifests/gitea/gitea-instance.yml | sed "s#TOTAL_USER#$totalUsers#g" | oc apply -n gitea -f -
    
    echo "Gitea Instance is now available!"
}

####################################################
# Main (Entry point)
####################################################
echo
echo "Red Hat Developer Workshop Provisioner"
repeat '-'

oc project default

#install_web_terminal
#repeat '-'

#enable_user_workload_monitoring
#repeat '-'

#install_loki
#repeat '-'

#install_tempo
#repeat '-'

#install_developer_hub
#repeat '-'

install_gitea
repeat '-'

oc project default

echo "Done!!!"