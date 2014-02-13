# elasticsearch-mesos

This Apache Mesos framework allows you to utilize your Mesos cluster to run Elasticsearch.

The framework is composed of a Driver node and a set of slaves.

The driver will do all the heavy lifting like downloading Elasticsearch to the worker nodes, making the cluster configuration available to the slaves via HTTP, and monitoring the instances. It will automatically modify the configuration files to include the known Elasticsearch instances to be used for unicast peer discovery via a template variable.

## Prerequisites

- An Apache Mesos cluster running version 0.15.0+
- Java
- Maven
- wget

## Tutorial

1. Download the distribution from the Mesosphere [download server](http://downloads.mesosphere.io/elasticsearch/elasticsearch-mesos-0.90.10-1.tgz).

1. Untar it onto the driver machine   
   ```tar xzf es-mesos-*.tgz```

1. Edit ```config/mesos.yaml``` and replace it with your Mesos settings.

1. Edit ```config/elasticsearch.yaml``` and replace it with your Elasticsearch settings.

1. Start the driver to initiate launching Elasticsearch on Mesos    

        bin/elasticsearch-mesos

## Configuration Values

##### mesos.executor.uri
Adjust this if you want the nodes to retrieve the distribution from somewhere else

Default: ```http://downloads.mesosphere.io/elasticsearch/elasticsearch-mesos-0.90.10-1.tgz```

##### mesos.master.url

Change this setting to point to your Mesos Master. The default works for a local Mesos install.

Default: ```zk://localhost:2181/mesos```

##### java.library.path

Change this to the path to where the mesos native library is installed.

Default: ```/usr/local/lib```

##### elasticsearch.noOfHwNodes

How many hardware nodes we want to run this cluster on.  This prevents multiple nodes from the same cluster to run on a single physical node.

Default: ```1```

##### resource.*
The specified resources will be relayed to Mesos to find suitable machines. The configuration file lists ```cpu```, ```mem``` and ```disk```, but really anything you specify will be relayed to Mesos as a scalar value when requesting resources.

Defaults:  ```resource.cpu:1.0```, ```resource.mem: 2048```, ```resource.disk: 1000```

## Building

Execute ```./build.sh``` to download all dependencies including Elasticsearch, compile the code and make the distribution.

## Known Limitations

Currently the driver does not deal with cluster failure in an intelligent manner. These features will be added shortly once we gain some initial feedback.

## Versioning

elasticsearch-mesos uses the version of the embedded Elasticsearch as the first 3 version numbers. The last and 4th version number is the version of elasticsearch-mesos.
