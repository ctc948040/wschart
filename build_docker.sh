#!/bin/sh

./mvnw clean package -DskipTests

echo $1
img_name=$1

if [ "$img_name" = "" ]; then
  img_name=ctc94/wschart
fi

echo $img_name

docker build -t $img_name --build-arg PORT=80 .

echo $img_name