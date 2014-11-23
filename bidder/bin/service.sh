#!/bin/sh
#
# spray
#
# processname: spray

BASEDIR=$(dirname $0)/..
cd $BASEDIR
BASEDIR=$(pwd)

NAME=spray
SCRIPT=$BASEDIR/bin/start.sh

PIDFILE=$BASEDIR/var/$NAME.pid
LOGFILE=$BASEDIR/log/$NAME.log

# See how we were called.
case "$1" in
  start)
        # Start service.
        [ -x ${SCRIPT} ] || (exit 1)
        if [ -f $PIDFILE ] && kill -0 $(cat $PIDFILE); then
            echo 'Service already running' >&2
            exit 1
        fi
        echo "Starting service: "
        ${SCRIPT} >${LOGFILE} 2>&1 &
        RETVAL=$?
        PID=$!
        [ $RETVAL -eq 0 ] && echo $PID > $PIDFILE
        ;;
  stop)
        # Stop service.
        echo "Stoping service: "
        kill -15 $(cat $PIDFILE)
        RETVAL=$?
        [ $RETVAL -eq 0 ] && rm -f $PIDFILE
        ;;
  restart|reload)
        $0 stop
        $0 start
        ;;
  *)
        echo $"Usage: $0 {start|stop|restart}"
        RETVAL=3
        ;;
esac

exit $RETVAL