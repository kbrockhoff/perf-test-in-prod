# Performance Testing in Production

Leveraging the Universal Scalability Law

## Purpose

This repository supports the talk of the same name. The talk explains and illustrates how system
performance can be tested and improved without having to stand up a test environment similar to production.

## Technologies

System under test is a Spring Boot app which returns a generated Atom feed of Lorem Ipsum with artifically
induced delays to simulate a microservices architecture. The system under test runs on a Kubernetes cluster
with Prometheus collecting metrics backed by long-term storage provided by InfluxDB. A namespace with a
Apache JMeter cluster with test results stored in InfluxDB is used to put load on the system.

R and its USL library are used to calculate the Universal Scalability Law equations from the
resulting metrics.

## Results

Prometheus Query: http_server_requests_seconds{kubernetes_name="aggregate-svc",uri="/feeds/{feedId}"}
