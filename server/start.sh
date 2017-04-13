#!/bin/bash
export profile=${1}
#export SPRING_PROFILES_ACTIVE=${1-dev}
echo "========================"
echo "using profile: " ${profile}
echo "========================"
mvn help:active-profiles
mvn -Dprofile=${profile} -o spring-boot:run ${3}
