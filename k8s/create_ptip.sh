#!/usr/bin/env bash

if ! hash kubectl 2>/dev/null
then
    echo "'kubectl' was not found in PATH"
    echo "Kindly ensure that you can acces an existing kubernetes cluster via kubectl"
    exit
fi

WORKING_DIR=`pwd`
TARGET_NS=ptip

kubectl get namespace $TARGET_NS > /dev/null 2>&1
if [ $? -ne 0 ]
then
    kubectl create namespace $TARGET_NS
fi

echo "Deploying Aggregate Service"
kubectl apply -n $TARGET_NS -f $WORKING_DIR/ptip_aggregate.yml

