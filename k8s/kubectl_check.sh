#!/usr/bin/env bash

if ! hash kubectl 2>/dev/null
then
    echo "'kubectl' was not found in PATH"
    echo "Kindly ensure that you can access an existing kubernetes cluster via kubectl"
    exit 1
fi

