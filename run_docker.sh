docker rm wschart -f && \
docker run  -d -it \
-p 8081:8081 -p 8083:8083 \
--name wschart \
ctc94/ws_chart

docker logs ws_chart -f