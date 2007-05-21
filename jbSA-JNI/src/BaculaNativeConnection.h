#ifndef __BaculaNativeConnection_h__
#define __BaculaNativeConnection_h__

#include <jni.h>

#include "bacula.h"
#include "jcr.h"
#include "BaculaConfig.h"
#include "NativeUtils.h"

#define SET_HELPER(source, dest) memset(&dest, 0, sizeof(dest)); \
	bstrncpy(dest, source, strlen(source)+1);

class BaculaNativeConnection {
public:
	jobject		_handlerID;     // Back pointer (GlobalRef) to the Java's peer instance

	/* Default Constructor & Destructor */
	BaculaNativeConnection();
	BaculaNativeConnection(const char* workingDir);
	BaculaNativeConnection(int debugLevel);
	BaculaNativeConnection(DaemonType daemonType);
	BaculaNativeConnection(DaemonType daemonType, const char* workingDir, int debugLevel);
	~BaculaNativeConnection();

	/* Connection methods. */
	jboolean	connect(const char *host, int port);
	void		disConnect();
	jboolean	reConnect();

	/* ***FIXME*** I need disconnect to login again to daemon. is it a bug or feature?*/
	jboolean	login();
	jboolean	login(const char *loginName, const char *password);

	void		sendCommand(const char *cmd);
	inline void	sendSignal(int signal)	{ if (isConnected()) bnet_sig(_bsock, signal); };
	inline int	receiveMessage()        { return isConnected()?bnet_recv(_bsock):BNET_HARDEOF; };
	inline char*    replyOfCommand()        { return   isConnected()?(char *)_bsock->msg:(char *)NULL; };

	/* Config methods. */
	inline jboolean	isConnected()	{ return (_bsock != NULL); };	
	inline jboolean	isLogined()	{ return (isConnected() && (_logined == TRUE)); };
	inline void     setLogined(jboolean logined) { _logined = logined; };
	
	/* bnet functions*/
	inline int      getReplyLength()    { return isConnected()?_bsock->msglen:BNET_HARDEOF; };
	inline char     *signalToAscii()    { return isConnected()?(char*) bnet_sig_to_ascii(_bsock):NULL; };
	inline jboolean isBnetStopped()     { return isConnected()?is_bnet_stop(_bsock):false; }; 

	/* Who I am */
	inline DaemonType	getDaemonType() { return _daemonType;};
	inline void		setDaemonType(DaemonType daemonType)	{ _daemonType = daemonType; };

	/* JNI's methods*/
	inline jobject		getHandlerID()	{ return _handlerID; };
	inline const char	*getLoginName() { return (const char*) _loginName; };
	inline const char	*getPassword()  { return (const char*) _password; };
	
	inline void setHostName(const char *hostName)		{ SET_HELPER(hostName, _hostName) } ;
	inline void setPort(int port)				{ _port = port; } ;
	inline void setLoginName(char *loginName)	{ SET_HELPER(loginName, _loginName) } ;


	inline int  getTimeOut()                    { return _timeOut; };
	inline void setTimeOut(int minute)          { _timeOut = 60 * minute; };
	inline void setHandlerID(jobject handlerID) { _handlerID = handlerID; };

	int	messageReceived(int key); // method to test the JNI events

private:
	/* Class variables */
	const static int _maxConnections = 128;	// ***FIXME*** Not used at the momment.
	static int	_connectionCount;	// ***FIXME*** Not used at the momment.
	
	/* Instance variables */
	BSOCK*	_bsock;
	int _compatible;

	/* 
	 * Connection parameters 
	 * I am not sure if those all need for to store the parameters.
	 */
	char	_loginName[MAX_NAME_LENGTH];    // *UserAgent*, Administrator, Operator, Guest etc.
	char	_password[MAX_NAME_LENGTH];     // This is the double md5 hashed password place holder
	char	_hostName[MAX_NAME_LENGTH];     // ***FIXME***, we need this value to be stored
	int	_port;                          // same as above

	/* Session config parameters */
	int		_timeOut;	// Timeout for connect and login process;
	DaemonType	_daemonType;	// The default type is DIRECTOR.
	jboolean	_logined;	// Set to true if authentication process was successfull.
	jstring		_errorMessage;  // ***FIXME***, not used at the moment.

	/*
	 * Private functions
	 */
	void initBaculaNativeConnection(DaemonType daemonType, const char* workingDir, int debugLevel);
	void storePassword(char *password);
	inline const char	*getHostName()  { return (const char*) _hostName; };
	inline int              getPort()       { return _port; };

};

#endif
