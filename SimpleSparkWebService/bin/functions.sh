#!/bin/bash

PRG_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )/.." && pwd )"

function clean_db() {
  for table in $(aws dynamodb list-tables --output text | awk '{print $2}')
  do
    aws dynamodb delete-table --table-name $table
  done
}

function build() {
  mvn clean install dependency:copy-dependencies
  rm -rf $PRG_HOME/target/run
  mkdir $PRG_HOME/target/run
  cp -rf $PRG_HOME/target/dependency/* $PRG_HOME/target/run
  cp $PRG_HOME/target/SimpleSparkWebService-1.0-SNAPSHOT.jar $PRG_HOME/target/run
}

function run() {
  (
    cd $PRG_HOME/target/run/;
    java -jar SimpleSparkWebService-1.0-SNAPSHOT.jar -cp .
  )
}

# build && run \!
