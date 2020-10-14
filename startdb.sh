CONTAINER_NAME=proda-tmp-db
docker stop $CONTAINER_NAME
docker rm $CONTAINER_NAME
docker run -d \
    -p 127.0.0.1:15438:5432 \
    --name $CONTAINER_NAME \
    -e POSTGRES_PASSWORD=8629f4395d1926b35e6cb9ef \
    -e POSTGRES_DB=prodactivity \
    -e POSTGRES_USER=prodactivity \
    postgres:13
