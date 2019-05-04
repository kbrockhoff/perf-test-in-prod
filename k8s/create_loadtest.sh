#!/usr/bin/env bash

. ./kubectl_check.sh

WORKING_DIR=`pwd`
WORKING_DIR="$WORKING_DIR/../jmeter-kubernetes"
TARGET_NS=loadtest

kubectl get namespace $TARGET_NS > /dev/null 2>&1
if [ $? -ne 0 ]
then
    kubectl create namespace $TARGET_NS
fi

NODES=`kubectl get no | egrep -v "master|NAME" | wc -l`

echo "Number of worker nodes on this cluster is " $NODES

echo

#echo "Creating $NODES Jmeter slave replicas and service"

echo

kubectl apply -n $TARGET_NS -f $WORKING_DIR/jmeter_slaves_deploy.yaml

kubectl apply -n $TARGET_NS -f $WORKING_DIR/jmeter_slaves_svc.yaml

echo "Creating Jmeter Master"

kubectl apply -n $TARGET_NS -f $WORKING_DIR/jmeter_master_configmap.yaml

kubectl apply -n $TARGET_NS -f $WORKING_DIR/jmeter_master_deploy.yaml


echo "Creating Influxdb and the service"

kubectl apply -n $TARGET_NS -f $WORKING_DIR/jmeter_influxdb_configmap.yaml

kubectl apply -n $TARGET_NS -f $WORKING_DIR/jmeter_influxdb_deploy.yaml

kubectl apply -n $TARGET_NS -f $WORKING_DIR/jmeter_influxdb_svc.yaml

echo "Creating Grafana Deployment"

kubectl apply -n $TARGET_NS -f $WORKING_DIR/jmeter_grafana_deploy.yaml

kubectl apply -n $TARGET_NS -f $WORKING_DIR/jmeter_grafana_svc.yaml

echo "Printout Of the $TARGET_NS Objects"

echo

kubectl get -n $TARGET_NS all

echo namespace = $TARGET_NS > $WORKING_DIR/tenant_export
