export SERVICE=wschart
export DOMAIN=${SERVICE}.ddns.net
export IMAGE=registry.ddns.net:5000/wschart:$(git rev-parse --short HEAD)
#export NODE_ID=$(docker info -f '{{.Swarm.NodeID}}')
path=${PWD}
#echo "path=${path}"
#cd "${PWD}/${SERVICE}"
echo "${PWD}"

#./mvnw clean package -DskipTests

DOCKER_BUILDKIT=1 docker build -t ${IMAGE} .
#docker-compose -f ./${SERVICE}.yml build
docker-compose -f ./${SERVICE}.yml push
#
echo "[build & push success]------------------------------------!!!"
#
for node in $(docker-machine ls -q --filter state=Running)
do
  echo "$node"
  echo "$(docker-machine ssh $node "docker pull ${IMAGE}")"
done
#docker stack rm ${SERVICE}
docker stack deploy -c ./${SERVICE}.yml ${SERVICE}

sleep 30

for node in $(docker-machine ls -q --filter state=Running)
do
  echo "$node"
  echo "$(docker-machine ssh $node "docker container prune -f;docker image prune -a -f;")"
done

#cd ${path}