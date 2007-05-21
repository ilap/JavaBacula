#include "BaculaNativeConnection.h"

/*
 * Default constructors
 */
BaculaNativeConnection::BaculaNativeConnection() {
    initBaculaNativeConnection(DIRECTOR, ".", 0);
}

BaculaNativeConnection::BaculaNativeConnection(const char *workingDir) {
    initBaculaNativeConnection(DIRECTOR, workingDir, 0);
}

BaculaNativeConnection::BaculaNativeConnection(int debugLevel) {
    initBaculaNativeConnection(DIRECTOR, ".", debugLevel);
}

BaculaNativeConnection::BaculaNativeConnection(DaemonType daemonType) {
    initBaculaNativeConnection(daemonType, ".", 0);
}
BaculaNativeConnection::BaculaNativeConnection(DaemonType daemonType, const char* workingDir, 
							 int debugLevel) {
    initBaculaNativeConnection(daemonType, workingDir, debugLevel);
}

/*
 * Instance initializator
 */
void BaculaNativeConnection::initBaculaNativeConnection(DaemonType daemonType, 
                                    const char *workingDir, int debugLevel) {

    _bsock		= NULL;
    _compatible		= true;
    _handlerID		= NULL;       
    _timeOut		= 30;       // Timeout (seconds) for connect and login process;
    _daemonType		= daemonType;	// The default is Director	   

    if (WSA_Init() != 0) {
	Dmsg0(150, "ERROR: while initializing WSA...\n");
    }
    
    // Bacula settings, not really clear what they do
    init_stack_dump();
    init_msg(NULL, NULL);
    textdomain("bacula");

    debug_level = debugLevel; 
    my_name_is(0, NULL, "JavaBacula");
    working_directory = workingDir;

    /* ***FIXME*** Check if we need this */
    struct sigaction sigignore;
    sigignore.sa_flags = 0;
    sigignore.sa_handler = SIG_IGN;
    sigfillset(&sigignore.sa_mask);
    sigaction(SIGPIPE, &sigignore, NULL);
}


/*
 * Default destructor
 */
BaculaNativeConnection::~BaculaNativeConnection() {
    disConnect();
    if (WSACleanup() != 0) {
    	Dmsg0(150, "ERROR: while cleaning up WSA...\n");
    }
}

/*
 * Connect to the specified Bacula Daemon.
 */
jboolean BaculaNativeConnection::connect(const char *host, int port) {
	  
    if (isConnected()) {
    	return true;
    }

    /*
     * Parameters are:
     * JCR *jcr			: Here is not used
     * int retry_intervall		:
     * int max_retry_time		: 
     * const char *name		: Director name?,
     * const *host			: Hostname or IP,
     * char *service		: Not Used,
     * int port			: service port (9101, 9102, 9103),
     * int verbose			: verbose.
     */
    _bsock = bnet_connect(NULL, 6, 6, _whoAmI[_daemonType], (char*) host, 
						NULL, port, 0 /*** FIXME ***/);
    if (_bsock == NULL) {
    	Dmsg3(0, "ERROR: Failed to connect to '%s' (%s) on port %i\n", 
			_whoAmI[_daemonType], host, port);
    	return false;
    } else { 
	/* Save parameters for the reConnect() */
	setHostName(host);
	setPort(port);
    }

    Dmsg0(0, "DEBUG: Bacula connected\n");
    return true;
}

/*
 * disConnect the;
 */
void BaculaNativeConnection::disConnect() {
	   
    if (!isConnected()) {
        return;
    }
        
    /*
     * If we use ACL and "quit" is not defined in commandACL, we can 
     * not use "quit", so we using ".quit" instead.
     */
    sendCommand(".quit\n");	
    setLogined(false);
    bnet_sig(_bsock, BNET_TERMINATE);	// Terminate session by send EOF.
    bnet_close(_bsock);
    _bsock = NULL;
}

/*
 * Reconnect he;
 */
jboolean BaculaNativeConnection::reConnect() {
    disConnect();
    return (connect(getHostName(), getPort()) && login());
}

/*
 * If parameters is not set, we using the default login and pwd 
 * Use setLoginName(char *loginName) and storePassword(char *password)
 * in the sender. 
 */
jboolean BaculaNativeConnection::login() {
    return login(getLoginName(), getPassword());
}

/*
 * If parameters is not set, we using the default login and pwd 
 */
jboolean BaculaNativeConnection::login(const char *loginName, const char *password) {

    int tls_local_need;		
    int tls_remote_need;
    btimer_t *tick;
    tls_local_need  = BNET_TLS_NONE; 
    tls_remote_need = BNET_TLS_NONE;
	
    if (!isConnected()) {
    	return false;
    }
    setLoginName((char*) loginName);
    storePassword((char*) password);
    loginName = getLoginName();
    password  = getPassword();
    bash_spaces((char*) loginName);
    Dmsg2(150,"loginname %s password %s\n\n", loginName, password);
    
    /* We set the timeout for the login procedure */
    Dmsg0(1300,"INFO : bsock timer entering\n");
    tick = start_bsock_timer(_bsock, _timeOut);

    if (!bnet_fsend(_bsock, _helloMessages[_daemonType], (char*)loginName)) {
    	stop_bsock_timer(tick);
	Dmsg2(150, "DEBUG: Error sending Hello to %s. ERR=%s\n", 
	_whoAmI[_daemonType], bnet_strerror(_bsock));
	return 0;
    }

    if (!cram_md5_respond(_bsock, (char*)password, &tls_remote_need, &_compatible) ||
    	!cram_md5_challenge(_bsock, (char*)password, tls_local_need, _compatible)) {
		
	stop_bsock_timer(tick);
	Dmsg3(150, "ERROR: Authetication problem REASON=%s, PWD(%s), DAEMONTYPE(%d)\n", 
		replyOfCommand(), password, _daemonType);
	return 0;
    }
    Dmsg1(150, "INFO :>client: %s", _bsock->msg);
   
    if (bnet_recv(_bsock) <= 0) {
	stop_bsock_timer(tick);
	Dmsg2(200, "DEBUG: Bad response from %s to Hello command: ERR=%s\n",
		_whoAmI[_daemonType], bnet_strerror(_bsock));
         return 0;
    }
    Dmsg1(150, "INFO :<server: %s", _bsock->msg);
    stop_bsock_timer(tick);
    if (strncmp(_bsock->msg, _helloResponses[_daemonType], 
        sizeof(_helloResponses[_daemonType])) != 0) {
        Dmsg1(200, "DEBUG: File daemon rejected Hello command. ERR=%s\n", 
    		   bnet_strerror(_bsock));
        return 0;
    }
    setLogined(true);

    return 1;
} // login(const char *loginName, const char *password)


/*
 * callback function to the Java
 */
void BaculaNativeConnection::sendCommand(const char * command) {
    if (isConnected()) {
    	_bsock->msglen = strlen(command);
    	 pm_strcpy(&_bsock->msg, command);
    	bnet_send(_bsock);
    }
}

/*
 * Reply messages
 * ***FIXME** we need check the message length?
 */
// inline char* BaculaNativeConnection::replyOfCommand() { 
//		return (char *) _bsock->msg; 
//};


/*
 * Set md5 hashed password sent by Java, again md5 hash,
 * because we want store password in Java with as md5 hashed password. 
 */
void BaculaNativeConnection::storePassword(char *password) { 
    struct MD5Context context;
    unsigned char signature[16];
    unsigned int i, j;

    MD5Init(&context);
    MD5Update(&context, (unsigned char *) password, strlen(password));
    MD5Final(signature, &context);

    memset(_password, 0, strlen(_password));

    for (i = 0, j = 0; i < sizeof(signature); i++) {
	sprintf(&_password[j], "%02x", signature[i]);
        j += 2;
    }
}


/*
 * This just a test function for testing the Events callback to the Java
 */
int BaculaNativeConnection::messageReceived(int msg) {
	
    jboolean has_exception = JNI_FALSE;
    printf("messageReceived() called...\n");

    JNIEnv *env = JNU_GetEnv();
    JNU_CallMethodByName(env,
                      	&has_exception,
                      	_handlerID,
                       	"messageReceived", // caller function
                       	"(I)V",
                       	msg);
   	// printf("In C++: %d\n", this);

    if (has_exception) {
	printf("messageReceived has exception...\n");
	env->ExceptionClear();
	return -1;
    } else {
	return false;
    }
}
