PROJECT_ROOT="/home/ubuntu/app2"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"
APP_LOG="$PROJECT_ROOT/application.log"

echo "stop & remove previous containers and images : ubuntu"  >> $DEPLOY_LOG
sudo docker stop $(docker ps -a -q)
sudo docker rm $(docker ps -a -q)
sudo docker rmi $(docker images -q)

echo "start docker-compose up: ubuntu"  >> $DEPLOY_LOG
sudo docker-compose -f /home/ubuntu/app2/docker-compose.yml up --build -d

docker logs spring >> $APP_LOG