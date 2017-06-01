#!/bin/sh
while ! nc -z config-server 3306 ; do
    echo "Waiting for upcoming Config Server"
    sleep 2
done
exec java -jar /elastest-torm-0.0.1-SNAPSHOT.jar