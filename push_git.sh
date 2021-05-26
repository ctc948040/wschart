export project=${1-'wschart'}

echo "project:${project}"

cd /data/docker-swarm/${project}

git remote set-url origin https://ctc948040:taechul9**@github.com/ctc948040/${project}.git

git add .

echo "date:$(date "+%Y-%m-%d %_H:%M:%S")"

git commit -a -m "$(date "+%Y-%m-%d %_H:%M:%S") modify files"

git status

git push origin