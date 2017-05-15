#!/bin/bash
mvn clean install
echo "============================================================================="
echo "============================  Enable COops start ==============================="
java -XX:+UseCompressedOops -javaagent:target/agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar com.thanos.springboot.agent.MyMainClass
echo "============================  Enable COops end ==============================="
echo "============================================================================="
echo "============================  Disable COops start ==============================="
java -XX:-UseCompressedOops -javaagent:target/agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar com.thanos.springboot.agent.MyMainClass
echo "============================  Disable COops end ==============================="

