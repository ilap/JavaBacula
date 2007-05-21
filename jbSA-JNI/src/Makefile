# 
# 
#
CLASSDIR=${JBSADIR}/build/classes
PACKAGE=org.ilap.javabacula.network
PACKAGEDIR=org/ilap/javabacula/network
JARFILE=${JBSADIR}/dist/JavaBacula.jar
TARGETDIR=${PROJDIR}/Libraries/dlls
TARGET	= linux

# JAVALIB=/usr/java/jdk1.5.0_07/lib
# JAVAINC=/usr/java/jdk1.5.0_07/include

BACDIR=/Devel/Projects/JavaBacula/Sources/bacula/bacula/src
BACINC=$(BACDIR)

# *** FIXME *** OBJS = $(CLASSDIR)/$(PACKAGEDIR)/BaculaChannel.class libJavaBacula.so 
OBJS = $(CLASSDIR)/$(PACKAGEDIR)/BaculaConnection.class libJavaBacula.so 
# Main.class
JAVAC = ${JDKBIN}/javac
JAVAH = ${JDKBIN}/javah

CPU	= 386
CC	= gcc
CPP	= g++
# CPP	= gcc


#CPPFLAGS	= -g3 -shared -O2 -I./include -I. -lc -I$(JAVAINC) -I$(JAVAINC)/win32 -L$(JAVALIB) -lc -I. -I$(BACDIR) -I$(BACDIR)/lib
CPPFLAGS	= -g3 -shared -O2 -I./include -I. -lc -I$(JAVAINC) -I$(JAVAINC)/linux -L$(JAVALIB) -lc -I. -I$(BACDIR) -I$(BACDIR)/lib

#CFLAGS		= -g3 -O2 -I./include -I. -I$(JAVAINC) -I$(JAVAINC)/w32 -L$(JAVALIB) -lc  -I. -I$(BACDIR) -I$(BACDIR)/lib
CFLAGS		= -g3 -O2 -I./include -I. -I$(BACINC) -I$(JAVAINC) -I$(JAVAINC)/linux -L$(JAVALIB) -lc  -I. -I$(BACDIR) -I$(BACDIR)/lib

TFLAGS	= -O2 -I/usr/include -I./include -I. -lc -I$(JAVAINC) -I$(JAVAINC)/linux -L$(JAVALIB) -lc -I. -I$(BACDIR) -I$(BACDIR)/lib

LDFLAGS 	= -L./lib
LDFLAGS 	= -L./lib -L$(BACDIR)/lib
LIBS		= -lbac -lssl -lpthread


# gcc  -o libnativelib.so -shared -Wl,-soname,libnative.so  
#     -I/export/home/jdk1.2/include 
#     -I/export/home/jdk1.2/include/linux nativelib.c  
#     -static -lc

all:	$(OBJS) BaculaTest

clean:
	rm -f *.o *~ *.class *.log BaculaTest
	rm -f $(TARGETDIR)/libJavaBacula.so

BaculaSession.h: 
	#$(JAVAH) -o BaculaSession.h -classpath $(CLASSDIR) -jni $(PACKAGE).BaculaSession
	$(JAVAH) -o BaculaConnection.h -classpath $(JARFILE) -jni $(PACKAGE).BaculaConnection

libJavaBacula.so: BaculaConnection.cpp BaculaConnection.h
	$(CPP) $(CPPFLAGS) $(LDFLAGS) $(LIBS) -DNO_TEST -o $(TARGETDIR)/libJavaBacula.so NativeUtils.cpp BaculaConnection.cpp BaculaNativeConnection.cpp $(BACDIR)/lib/libbac.a 

BaculaTest:
	$(CPP) $(TFLAGS) $(LDFLAGS) $(LIBS) -c BaculaNativeConnection.cpp  
	$(CPP) $(TFLAGS) $(LDFLAGS) $(LIBS) -c NativeUtils.cpp  
	$(CPP) $(TFLAGS) $(LDFLAGS) $(LIBS) -o BaculaTest BaculaTest.cpp BaculaNativeConnection.o NativeUtils.o $(BACDIR)/lib/libbac.a 
