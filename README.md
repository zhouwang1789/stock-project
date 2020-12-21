# Stock project
Stock trending analysis and price prediction

# Pre-requisites
* JDK 11+
* Maven 3.6+
* Docker 20.10.0

# How to run
Clone the project and under the root directory, open a terminal/CMD window and run
1. ` mvn clean install -f ./stock-data`    # build stock-data project for retrieving data from [alphavantage](https://www.alphavantage.co/documentation/)
1. `docker-compose up`    # start the services