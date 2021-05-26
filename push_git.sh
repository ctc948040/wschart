base=$( ansible-vault view push_git_enc.sh)
echo "${base}"
eval "${base}"
