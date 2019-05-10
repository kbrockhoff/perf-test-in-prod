#!/usr/bin/env bash

. ./kubectl_check.sh

WORKING_DIR=`pwd`
TARGET_NS=monitoring

kubectl get namespace $TARGET_NS > /dev/null 2>&1
if [ $? -ne 0 ]
then
    kubectl create namespace $TARGET_NS
fi

echo "Deploying Cluster Prometheus"
kubectl apply -n $TARGET_NS -f $WORKING_DIR/prometheus_cluster_role.yml
kubectl apply -n $TARGET_NS -f $WORKING_DIR/prometheus_configmap.yml
kubectl apply -n $TARGET_NS -f $WORKING_DIR/prometheus_storage.yml
kubectl apply -n $TARGET_NS -f $WORKING_DIR/prometheus_deploy.yml
kubectl apply -n $TARGET_NS -f $WORKING_DIR/prometheus_svc.yml

kubectl apply -n $TARGET_NS -f $WORKING_DIR/influxdb_configmap.yml
kubectl apply -n $TARGET_NS -f $WORKING_DIR/influxdb_deploy.yml
kubectl apply -n $TARGET_NS -f $WORKING_DIR/influxdb_svc.yml
