#!/bin/bash -x

# Our elasticsearch-mesos project version follows the Cassandra version number
PROJVERSION=$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v '\[')
CASSVERSION=$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=cassandra.version | grep -v '\[')

echo Building Cassandra $CASSVERSION for Mesos

# Create our jar so we can package it up as well. Do this first, so we can fail fast
mvn clean package

rm -r elasticsearch-mesos-*
wget https://download.elasticsearch.org/elasticsearch/elasticsearch/elasticsearch-${CASSVERSION}.tar.gz

tar xzf elasticsearch-*.tar.gz
rm elasticsearch-*tar.gz

mv elasticsearch-* elasticsearch-mesos-${PROJVERSION}

cp bin/* elasticsearch-mesos-${PROJVERSION}/bin
chmod u+x elasticsearch-mesos-${PROJVERSION}/bin/*

cp conf/* elasticsearch-mesos-${PROJVERSION}/conf
cp target/*.jar elasticsearch-mesos*/lib

tar czf elasticsearch-mesos-${PROJVERSION}.tgz elasticsearch-mesos-${PROJVERSION}


