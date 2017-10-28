## Start MongoDB
```
docker run --name SimRacingSeriesMongoDb -v /Users/dennis/Documents/Development/Docker/mongodb:/data/db -p 27017:27017 -d mongo:3.4
docker stop SimRacingSeriesMongoDb
docker rm SimRacingSeriesMongoDb
```