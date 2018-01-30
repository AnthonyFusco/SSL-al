#!/usr/bin/env bash
curl -G http://localhost:8086/query --data-urlencode "q=CREATE DATABASE influxdb"

curl -i -XPOST 'http://localhost:8086/write?db=influxdb' --data-binary 'cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000'
