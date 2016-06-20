# DevOps - Sample Application

This is a sample application used for Neueda homework.
The homework itself is
[here](https://github.com/neueda/homework/tree/master/devops).

## Build

    ./gradlew build

## Run

All services require a running [etcd](https://coreos.com/etcd/) service
to function properly.

Service artifacts are runnable JARs with embedded Tomcat.

    java -jar consumer/build/libs/consumer-1.0.jar
    java -jar producer/build/libs/producer-1.0.jar

They accept some configuration options via command-line flags.

* `etcd` - etcd endpoint (default: `http://127.0.0.1:2379`)
* `server.address` - the address to publish for service discovery
  (default: `127.0.0.1`)
* `server.port` - the port to run on and publish for service discovery
