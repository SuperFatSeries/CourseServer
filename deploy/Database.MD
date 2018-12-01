#MySQL
``` bash
docker run --name sfs-mysql -e MYSQL_ROOT_PASSWORD=roo#0 -d mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```
#Mongo
``` bash
docker run -p 27017:27017 -d --name sfs-mongo -e MONGO_INITDB_ROOT_USERNAME=user -e MONGO_INITDB_ROOT_PASSWORD=user#0 mongo
```
#Redis
``` bash
docker run -p 6379:6379 --name sfs-redis -d redis
```
