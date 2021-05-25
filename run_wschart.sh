export PROFILE=${1-'dev'}
export SERVICE=wschart
export DOMAIN=${SERVICE}.ddns.net
export IMAGE=registry.ddns.net:5000/wschart:${PROFILE}.$(git rev-parse --short HEAD)

#export NODE_ID=$(docker info -f '{{.Swarm.NodeID}}')
path=${PWD}
#echo "path=${path}"
#cd "${PWD}/${SERVICE}"
echo "${PWD}"

export DOCKER_BUILDKIT=1 
docker build -f Dockerfile_v2.0  -t ${IMAGE} --target ${PROFILE} .
#docker-compose -f ./${SERVICE}.yml build
docker push ${IMAGE}

echo "[build & push success]------------------------------------!!!"

for node in $(docker-machine ls -q --filter state=Running)
do
  echo "$node"
  echo "$(docker-machine ssh $node "docker pull ${IMAGE}")"
done
#docker stack rm ${SERVICE}
docker stack deploy -c ./${SERVICE}.yml ${SERVICE}

#for node in $(docker-machine ls -q --filter state=Running)
#do
#  echo "$node"
#  echo "$(docker-machine ssh $node "docker container prune -f;docker image prune -a -f;")"
#done

docker service logs ${SERVICE}_${SERVICE} -f