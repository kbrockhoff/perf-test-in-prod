#!/usr/bin/env bash

. ./kubectl_check.sh

WORKING_DIR=`pwd`
TARGET_NS=loadtest

kubectl get namespace $TARGET_NS > /dev/null 2>&1
if [ $? -eq 0 ]
then
    kubectl delete namespace $TARGET_NS
fi

