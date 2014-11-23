#!/bin/sh

if [ $# -gt 1 ]; then
  echo "Usage: start.sh"
  exit 1
fi

BASEDIR=$(dirname $0)/..

cd $BASEDIR
MAINJAR=`ls $BASEDIR/target/scala-2.10/bidder-assembly-*.jar 2>/dev/null | head -n1`
MAINCLASS=com.loveads.bidder.Boot

exec java $JAVA_OPTS -cp "$MAINJAR" "$MAINCLASS" "$@"