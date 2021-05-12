export SERVICE=wschart
export DOMAIN=${SERVICE}.ddns.net
export IMAGE=registry.ddns.net:5000/wschart:latest
#export NODE_ID=$(docker info -f '{{.Swarm.NodeID}}')
#path=${PWD}
#echo "path=${path}"
#cd "${PWD}/${SERVICE}"
echo "${PWD}"

./mvnw clean package -DskipTests

#cp ${PWD}/target/*.jar ${path}/${SERVICE}/app.jar

#cd ${path}
docker-compose -f ./${SERVICE}.yml build
docker-compose -f ./${SERVICE}.yml push

echo "[build & push success]------------------------------------"


for node in $(docker-machine ls -q --filter state=Running)
do
  echo "$node"
  echo "$(docker-machine ssh $node "docker pull ${IMAGE}")"
done

docker stack deploy -c ./${SERVICE}.yml ${SERVICE}