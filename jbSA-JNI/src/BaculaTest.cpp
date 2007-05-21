#include <stdlib.h> 
#include <stdio.h>

#include "BaculaNativeConnection.h"
#include "BaculaConfig.h"

char *hashPassword(const char *password);

void usage(char *argv[]) {
	printf("usage: %s <Daemon Types> <host or IP> <port number> <login name> <password>\n", 
			argv[0]);
	printf("or \"%s <password to md5 hash>\" to display hashed password \n", 
			argv[0]);
	printf("Daemon Types are: 0=\"Director\", 1=\"Client\", 2=\"Storage\"\n");
}

/*
 * test for a F*ing vsnprintf, because does not work in W32
 */ 
void error(const char *format, ...) {
                 
    va_list ap;
    char errstr[1000];
    va_start(ap, format);

#ifdef WIN32
	// vsprintf(errstr, format, ap);
	vsnprintf(errstr, 1000, format, ap);
#else
	vsnprintf(errstr, 1000, format, ap);
#endif
	va_end(ap);
}

int main(int argc, char *argv[]) 
{ 

	char *host = "192.168.0.101", *login, *passwd;
	int type, port;

	if (!((argc == 2) || (argc == 6))) {
		usage( argv);
		exit(1);
	}

	if (argc == 2) {
            hashPassword((const char*) argv[1]);
            exit(0);
	}

	/*
	 * Please set the correct values because this an early state class,
	 * so the error handling is not perfect:)
	 */
	type	= atoi(argv[1]); 
	host	= argv[2];
	port	= atol(argv[3]);
	login   = argv[4];
	passwd  = argv[5];
        
	BaculaNativeConnection *bc = new BaculaNativeConnection((DaemonType) type, ".", 1400);

	Dmsg2(0, "Connecting to %s:%d\n", host, port);
	if (!bc->connect((char*) host, port)) {
		Dmsg2(0, "ERROR: on connect to %s:%d\n", host, port);	
		exit(1);
	}

	Dmsg1(0, "Login as %s\n", login);
//	if(bc->login(login, hashPassword(passwd))) {
	if(bc->login(login, passwd)) {
		Dmsg2(0, "The '%s' user is logged in '%s' host!\n", login, host);
	} else {
		Dmsg2(0, "ERROR: The '%s' user can not login to the '%s' host!\n", login, host);
		bc->disConnect();
		exit(1);
	}

	int status;
	char input[64];

	
	while (!bc->isBnetStopped()) {

            /* Please do not use reconnect as the first command. */
            printf("%s# ", host);
            gets(input);
            
            if(strcasecmp(input, "reconnect") == 0) {
            
                printf("Reconnecting...");
                if (bc->reConnect()) {
                    printf("Reconnec done");
		} else {
                    printf("ERROR: Failed on reconnect...");
                    exit(1);
		}
	        continue;
            }

            bc->sendCommand(input);
            
            while (true) {
                if ((status = bc->receiveMessage()) >= 0) {
                    printf("%s", bc->replyOfCommand());
		} else if (status == BNET_SIGNAL) {
                    if (bc->getReplyLength() == BNET_PROMPT) {
			printf("%s", bc->replyOfCommand());
			break;
                    } else if (bc->getReplyLength() == BNET_EOD) {
			printf("%s", bc->replyOfCommand());
			break;
                    } else if (bc->getReplyLength() == BNET_HEARTBEAT) {
			bc->sendSignal(BNET_HB_RESPONSE);
			printf("%s", bc->replyOfCommand());
			printf("INFO: Heartbeat signal received, answered.\n");
                    } else {
			printf("ERROR: Unexpected signal received :");
			printf("%s\n", bc->signalToAscii());
                    }
		} else if (bc->isBnetStopped()) {
                    printf("ERROR: Bacula is stopped\n");
                    break;         
                } else {
                    printf("ERROR: Error signal received\n");
                    break;
		}
            } // While
	}
	bc->disConnect();
}


/*
 * Hashing plain text password because we will store in Java as 
 * hashed password, and the JNI API use this format.
 */ 
char *hashPassword(const char *password) {
	struct MD5Context context;
    unsigned char	signature[16];

	static char	buf[64];               
	memset(buf, 0, 64);

	MD5Init(&context);
	MD5Update(&context, (unsigned char *)password, strlen(password));
	MD5Final((unsigned char *)signature, &context);

	printf("Plain text password:\t'%s'\n", password);
	printf("MD5:\t\t\t'");
	for (int i=0; i < 16; i++) {
		printf("%02x", signature[i]& 0xFF); 
	}
        printf("\n");
	bin_to_base64((char*)buf, 64, (char *)signature, 16, true);
	printf("BASE64(MD5):\t\t'%s'\n", buf);
	
	return buf;
}

