export project=${1-'wschart'}

echo "project ${project}"

cd /data/docker_swarm/${project}

base=$( ansible-vault view push_git_enc.sh)
echo "${base}"
eval "${base}"
