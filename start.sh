#!/bin/sh

# Create jar file
echo ""
echo ">+++++++++++++++++++++++<"
echo "roulette : Creating jar"
mvn package -DskipTests -Ppackage

# Build docker image
echo ""
echo ">+++++++++++++++++++++++<"
echo "roulette : Creating docker image"
cp target/roulette-0.0.1-SNAPSHOT.jar ./src/docker
cd ./src/docker
docker build --rm --force-rm --tag=roulette:1.0.0 -f Dockerfile .
rm roulette-0.0.1-SNAPSHOT.jar

# Run container
echo ""
echo ">+++++++++++++++++++++++<"
echo "roulette : Starting containers"
docker-compose -p roulette up -d

sleep 5

echo "roulette : App started"

