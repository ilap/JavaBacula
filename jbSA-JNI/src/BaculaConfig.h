#ifndef __BaculaConfig_h__
#define __BaculaConfig_h__

#define	HELLO_MSG	"Hello %s calling\n"
#define	HELLO_MSG_FD	"Hello Director %s calling\n"
	
#define	HELLO_RESP	"1000 OK:"
#define	HELLO_RESP_FD	"2000 OK Hello\n"
#define	HELLO_RESP_SD	"3000 OK Hello\n"

#define IAM_DIR "Director Daemon"
#define IAM_FD  "File Daemon"
#define IAM_SD  "Storage Daemon"

static const char *_helloMessages[]	= {HELLO_MSG,  HELLO_MSG_FD,  HELLO_MSG_FD};
static const char *_helloResponses[]= {HELLO_RESP, HELLO_RESP_FD, HELLO_RESP_SD};
static const char *_whoAmI[]		= {IAM_DIR, IAM_FD, IAM_SD};

typedef enum {DIRECTOR = 0, FILEDAEMON, STORAGEDAEMON} DaemonType;

/*
 * ***FIXME*** I don not know why but the vnsprintf does not work on W32
 */
#ifdef HAVE_WIN32
#undef Dmsg0
#undef Dmsg1
#undef Dmsg2
#undef Dmsg3
#undef Dmsg4


#define Dmsg0(lvl, msg)             if ((lvl)<=debug_level) printf(msg) 
#define Dmsg1(lvl, msg, a1)         if ((lvl)<=debug_level) printf(msg, a1)
#define Dmsg2(lvl, msg, a1, a2)     if ((lvl)<=debug_level) printf(msg, a1, a2)
#define Dmsg3(lvl, msg, a1, a2, a3) if ((lvl)<=debug_level) printf(msg, a1, a2, a3)
#define Dmsg4(lvl, msg, arg1, arg2, arg3, arg4) if ((lvl)<=debug_level) printf(msg, arg1, arg2, arg3, arg4) 
#endif

#endif

