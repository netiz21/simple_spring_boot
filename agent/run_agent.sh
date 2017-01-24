#!/bin/bash
mvn clean install
echo "============================================================================="
echo "============================  Start run agent ==============================="
java -javaagent:target/agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar com.thanos.springboot.agent.MyMainClass
echo "============================  Stop run agent  ==============================="
echo "============================================================================="

