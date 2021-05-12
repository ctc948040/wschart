export SERVICE=wschart
export DOMAIN=${SERVICE}.ddns.net
export IMAGE=registry.ddns.net:5000/wschart:latest
#export NODE_ID=$(docker info -f '{{.Swarm.NodeID}}')
path=${PWD}
echo "path=${path}"
cd "/data/websocket정리하기/spring-websockets_chart"
echo "${PWD}"

#./mvnw clean package -DskipTests

cp ${PWD}/target/*.jar ${path}/${SERVICE}/app.jar

cd ${path}
echo "${PWD}"
docker-compose -f ${PWD}/${SERVICE}/${SERVICE}.yml build
docker-compose -f ${PWD}/${SERVICE}/${SERVICE}.yml push

echo "[build & push success]------------------------------------"

for node in $(docker-machine ls -q --filter state=Running)
do
  echo "$node"
  echo "$(docker-machine ssh $node "docker pull ${IMAGE}")"
done

docker stack deploy -c ./${SERVICE}/${SERVICE}.yml ${SERVICE}