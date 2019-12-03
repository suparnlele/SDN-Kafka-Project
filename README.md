# SDN Project Kafka Deployment

## Repository Setup Instructions

### Clone this repository
```bash
$ git clone https://github.com/suparnlele/SDN-Kafka-Project.git
```
### Go inside the project directory
```bash
$ cd SDN-Kafka-Project
```

## Prerequisites
1. Apche Kafka
2. Elasticsearch

## Prerequisites Installation

### Install Apache kafka on ubuntu

***Step 1:Pre-requisite***

Apache Kafka required Java to run. You must have java installed on your system. If java is not installed, execute below command to install default OpenJDK on your system.

```sudo apt update
$ sudo apt install default-jdk
```

***Step 2 – Download Apache Kafka***

Download the Apache Kafka binary files from its official download website. You can also select any nearby mirror to download.
```bash
$ wget http://www-us.apache.org/dist/kafka/2.2.1/kafka_2.12-2.2.1.tgz
```
Extract the archive file and move it to the ***usr/local/kafka*** directory
```bash
$ tar xzf kafka_2.12-2.2.1.tgz
$ mv kafka_2.12-2.2.1 /usr/local/kafka
```

***Step 3 – Start Kafka Server***

Kafka uses ZooKeeper, so first, start a ZooKeeper server on your system. You can use the script available with Kafka to get start single-node ZooKeeper instance.

```bash 
$ cd /usr/local/kafka
$ bin/zookeeper-server-start.sh config/zookeeper.properties
```

Start the Kafka server:
```bash
$ bin/kafka-server-start.sh config/server.properties
```

***Step 4 – Create a Topic in Kafka***
```bash
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic weather
```
The replication-factor describes how many copies of data will be created. As we are running with single instance keep this value 1.

Set the partitions options as the number of brokers you want your data to be split between. As we are running with a single broker keep this value 1.

You will get output like this if the above command executes successfully.
```
Created topic "Weather".
```

See all the created topic on Kafka by running the list topic command
```bash
$ bin/kafka-topics.sh --list --zookeeper localhost:2181
```
### Optional step

##### If you want to check weather the data is getting uploaded to the kafka or not ?
***Step 1 - Send Messages to Kafka*** 
The **producer** is the process responsible for put data into our Kafka. The Kafka comes with a command line client that will take input from a file or from standard input and send it out as messages to the Kafka cluster. The default Kafka send each line as a separate message.

Let’s run the producer and then type a few messages into the console to send to the server. 
```bash
$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic testTopic
```
You can exit this command or keep this terminal running for further testing. Now open a new terminal to the Kafka consumer process on next step.

***Step 2 – Using Kafka Consumer***
Kafka also has a command line consumer to read data from Kafka cluster and display messages to standard output.
```bash
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic testTopic --from-beginning
```

***Step 7 - After installing kafka come out from this directory or open new terminal with this same repository folder***

```bash
$ cd ..
```


### Install Elastic search on ubuntu

***1.Dependencies***
First, update the list of available packages by running  the given below command 
```bash
$ apt-get update.
```

***1.1 Test your installed java version***
You can then check that Java is installed by running the command 
```bash
$ java -version.
```

That’s all the dependencies we need for now, so let’s get started with obtaining and installing Elasticsearch.

***2.Download and Install***
Elasticsearch can be downloaded directly from their site in zip, tar.gz, deb, or rpm packages. 


### Download the archive
```bash
$ wget https://download.elasticsearch.org/elasticsearch/elasticsearch/elasticsearch-0.90.7.zip
```
```bash
$ unzip elasticsearch-0.90.7.zip
```
***3. Edit elasticsearch.yml file***
Go to the config folder in elasticsearch-0.90.7 . Edit the file elasticsearch.yml

```bash
$ sudo vi /etc/elasticsearch/elasticsearch.yml
```

Then find the line that specifies network.bind_host, then uncomment it and change the value to localhost so it looks like the following:

```bash
network.bind_host: localhost
```
Then insert the following line somewhere in the file, to disable dynamic scripts:
```bash
script.disable_dynamic: true
```
Save and exit. Now restart Elasticsearch to put the changes into effect:
```bash
$ sudo service elasticsearch restart
```

## 4. Test your Elasticsearch install
You have now extracted the zip to a directory and have the Elasticsearch binaries available, and can start the server.Make sure you’re in the resulting directory. 

Let’s ensure that everything is working. Move out of the config directory and run
```bash
$ ./bin/elasticsearch
 ```
Elasticsearch should now be running on port 9200. Do note that Elasticsearch takes some time to fully start, so running the curl command below immediately might fail. It shouldn’t take longer than ten seconds to start responding, so if the below command fails, something else is likely wrong.

Ensure the server is started by running
```bash
$ curl -X GET 'http://localhost:9200'
```
You should see the following response
```
{
  "ok" : true,
  "status" : 200,
  "name" : "Xavin",
  "version" : {
    "number" : "0.90.7",
    "build_hash" : "36897d07dadcb70886db7f149e645ed3d44eb5f2",
    "build_timestamp" : "2013-11-13T12:06:54Z",
    "build_snapshot" : false,
    "lucene_version" : "4.5.1"
  },
  "tagline" : "You Know, for Search"
}
```
If you see a response similar to the one above, Elasticsearch is working properly. Alternatively, you can query your install of Elasticsearch from a browser by visiting [Link](http://localhost:9200/). You should see the same JSON as you saw when using curl above.

The server can be stopped using the RESTful API
```bash
$curl -X POST 'http://localhost:9200/_cluster/nodes/_local/_shutdown'
```
You can restart the server with the corresponding service elasticsearch start.


### Conclusion
We have now installed, configured and begun using Elasticsearch. Since it responds to a basic RESTful API. It is now easy to begin adding to and querying data using Elasticsearch from your application.

