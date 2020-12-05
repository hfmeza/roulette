#!/bin/sh

cd ./src/docker
docker-compose -p roulette stop
docker-compose -p roulette rm -f
