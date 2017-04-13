#!/bin/bash
profile=${1}
#export SPRING_PROFILES_ACTIVE=${1-dev}
echo "========================"
echo "using profile: " ${profile}
echo "========================"
mvn help:active-profiles
mvn spring-boot:run -Drun.jvmArguments="-Xms2048m -Xmx2048m -Dprofile=${profile}"