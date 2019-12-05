# SDN Project Kafka Deployment

## Project Overview
* Deploy Kafka
* Application is exposing data using ReST services - for that any standard API available on open network can be used. weather data API is used in this project.
* Producer: Calls the REST api to read the data (in JSON format) and puts it on the kafka bus using Java api's.
* Consumers: Three consumers are there to consume the data and do specific tasks
  * Consumer1 : Prints data locally .
  * Consumer2 : Send data to REST application using Java api's.
  * Consumer3 : Stores the data in Elasticsearch DB.

## Getting Started


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

***Step 1 - Pre-requisite***

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
Created topic "weather".
```

See all the created topic on Kafka by running the list topic command
```bash
$ bin/kafka-topics.sh --list --zookeeper localhost:2181
```
***Step 5 - After installing kafka come out from this directory or open new terminal with this same repository folder***

```bash
$ cd ..
```

### Optional step 

If you want to check weather the data is getting uploaded to the kafka or not? Do the below two steps after **Step 4** 

***Step 1 - Send Messages to Kafka*** 

The **producer** is the process responsible for put data into our Kafka. The Kafka comes with a command line client that will take input from a file or from standard input and send it out as messages to the Kafka cluster. The default Kafka send each line as a separate message.

Let’s run the producer and then type a few messages into the console to send to the server.

```bash
$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic weather
```
You can exit this command or keep this terminal running for further testing. Now open a new terminal to the Kafka consumer process on next step.

***Step 2 – Using Kafka Consumer***
Kafka also has a command line consumer to read data from Kafka cluster and display messages to standard output.
```bash
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic weather --from-beginning
```



### Install Elasticsearch 

***Step 1 - Open Command Terminal***

Go to your **Linux and open command terminal.** If you are using Ubuntu then simple use keyboard shortcut **Ctrl+Alt+T** to get Terminal

***Step 2 - Install Java for ElasticSearch***

You can then check that Java is installed by running the command 
```bash
$ sudo apt-get install default-jdk
$ echo $JAVA_HOME /usr/lib/jvm/java-8-oracle
```
##### Check the current installed Java version:
```bash
$ java -version
```

***Step 3 - Install apt-transport-https***

To access Debian repository over secure HTTPs channel, the APT transport will help us in that by allowing repositories access via the HTTP Secure protocol (HTTPS) which also referred to as HTTP over TLS. The command to add it is:
```bash
$ sudo apt-get install apt-transport-https
```

***Step 4 - Download and install the Public Signing Key for Elasticsearch packages***

Use the below command to download and install the Public Signing Key that will help us to add the official repository to of ElasticSearch on Ubuntu.
```bash
$ wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -
```

***Step 5 -  Add Repository for ElasticSearch***

```bash
$ add-apt-repository "deb https://artifacts.elastic.co/packages/7.x/apt stable main"
```

***Step 6 - Download and Install ElasticSearch on Ubuntu Linux***

After adding the repo for ElasticSearch first update the system, so that it can recognize the added repo by flushing caches.

Here is the update command:

```bash
$ sudo apt-get update
```
Now, type the ElasticSearch installation command:

```bash
$ sudo apt-get install elasticsearch
```
***Step 7 - Configure Elasticsearch to start automatically***

After installing, if you don’t want to start the Elasticsearch service manually then simply add its services to systemctl, so that it could start every time automatically with system boots up.

```bash
$ sudo /bin/systemctl enable elasticsearch.service
```
##### Enable Elasticsearch service

```bash
$ sudo systemctl enable elasticsearch.service
```
##### Start the Elastic service using below command:

```bash
$ sudo systemctl start elasticsearch.service
```
##### In future to stop the same service you can use this:

```bash
$ sudo systemctl stop elasticsearch.service
```

***Step 8 - Verify Elasticsearch is running or not***

Now everything is up and running by now on your system for ElasticSearch, its time to check whether it is working fine or not. So, to test it we use CURL.

##### IF you don’t have curl installed on your system you can use this command to get it:

```bash
$ sudo apt-get install curl
```
##### Now test the Elasticsearch by sending an HTTP request with port number 9200

```bash
$ curl -X GET "localhost:9200/"
```
##### Output:
```bash
{
  "name" : "good-boy",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "7UGPD6IhROyTXwr0lBhRbQ",
  "version" : {
    "number" : "7.5.0",
    "build_flavor" : "default",
    "build_type" : "deb",
    "build_hash" : "e9ccaed468e2fac2275a3761849cbee64b39519f",
    "build_date" : "2019-11-26T01:06:52.518245Z",
    "build_snapshot" : false,
    "lucene_version" : "8.3.0",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}
```

You can also check whether it working or not using the browser. Open the browser and enter the localhost:9200 address.

***Step 9 - Configure Elasticsearch via YML file***

To access the ElasticSearch from any public IP, we have to do some changes in ElasticSearch configuration file. Open it using below command:

```bash
$ sudo nano /etc/elasticsearch/elasticsearch.yml
```
And comment the line **network.host**  and set IP address to **localhost** and save it using **Ctrl+X** and then type **Y** and press **Enter** button.

##### After changing restart the ElasticSearch service:

```bash
$ sudo systemctl restart elasticsearch.service
```



## Consumer application

***Step 1 - Go inside the Spring-Boot-Kafka-Consumer directory and build the application using maven and before building you need to change log file path to wherever you want***

```bash
$ cd Spring-Boot-Kafka-Consumer
$ vim src/main/resources/logback-spring.xml
```
***Step 2 - Change logfile path***

```bash
<file>your_log_file.log</file>
<fileNamePattern>your_log_file.%d{yyyy-MM-dd}.log</fileNamePattern>
```
***Step 3 - Build application***
```bash
$ mvm clean install
```
On succession of build, Kafka-Consumer-App.jar file gets generated inside the "target" folder and that **.jar** file is the final build for consumer application

***Step 4 - Run application***
```bash
$ java -jar Kafka-Consumer-App.jar
```
Opens the log of consumer

## Producer application

***Step 1 - Go inside the Spring-Boot-Kafka-Producer directory and build the application using maven and before building you need to change log file path to wherever you want***

```bash
$ cd Spring-Boot-Kafka-Producer
$ vim src/main/resources/logback-spring.xml
```
***Step 2 - Change logfile path***
```
<file>your_log_file.log</file>
<fileNamePattern>your_log_file.%d{yyyy-MM-dd}.log</fileNamePattern>
```

***Step 3 - Build application***
```bash
$ mvm clean install
```
On succession of build, Kafka-Producer-App.jar file gets generated inside the "target" folder and that **.jar** file is the final build for consumer application


***Step 4 - Run application***
```bash
$ java -jar Kafka-Producer-App.jar
```
Open the log of producer

When producer started scheduled task of getting data from the weather and push it to the kafka broker, then all the consumers listning to this broker will recieve data and started doing their specific tasks.


***Step 5 - To check that consumer3 has successfully inserted data to elasticsearch, go to consumer3 log and find the below log message***
```
Consumed JSON Message :{ elasticsearch URL :: http://localhost:9200/weather/databus/{id}/_create } | { id : 38 } Data Sent To Elasticsearch.
```
***Step 6 - Copy the above "id" and replace it with in below url***
```bash
http://localhost:9200/weather/databus/<id>
```
***Step 7 - Open the above url in the browser***

You can see that the data is succesully inserted !!!

## Thanks
**Prof. Samar Shailendra**, for allowing us to do this project.

