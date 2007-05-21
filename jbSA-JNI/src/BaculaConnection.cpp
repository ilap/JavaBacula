#include <jni.h>
#include <stdio.h>
#include "BaculaConnection.h"  
#include "BaculaNativeConnection.h"  

// Derived from NativeUtils.cpp
extern JavaVM *_jvm;

#define GETPEER(native, peer) BaculaNativeConnection *native = (BaculaNativeConnection*)peer

/* ***FIXME *** 
JNIEXPORT void JNICALL Java_BaculaConnection_jniSendMessage
  (JNIEnv *env, jobject object, jlong peer, jint key) {
 
	GETPEER(nativeInstance, peer);
	if (nativeInstance) {
		nativeInstance->messageReceived(key);
	}
}
*/

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniCreateChannel
 * Signature: (ILjava/lang/String;I)J
 * 
 * First of all, because this is a platform independent code, I do not want use
 * the platform specific thread solutions. So, I use thread in JAVA.
 *
 * If we create a java thread, it create a C++ instance to work each other.
 * When I creating an C++ class instance, then I will send back this reference 
 * to the real thread in java. So, for a communication a java thread working together
 * with single C++ instance (one-to-one relation).
 */

JNIEXPORT jlong JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniCreateChannel
(JNIEnv *env, jobject self, jint daemonType, jstring workingDirectory, jint debugLevel) {

	BaculaNativeConnection *nativeInstance = new BaculaNativeConnection();	
	if (nativeInstance != NULL) {
		/*
		 * I am not sure if NewGlobalRef is the good solution, must learn deeper in
		 * JNI.
		 */
		nativeInstance->_handlerID = env->NewGlobalRef(self);
		Dmsg0(1500, "create Called...");
	} 

	/* 
	 * Send back the BaculaNativeConnection instance to the Java, for later use.
	 */
	return (jlong) nativeInstance;
 }
 
/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniDestroyChannel
 * Signature: (J)V
 * 
 * From this function, we use the stored C++ object reference
 * to work with the caller java thread. Method is:
 * a) get the reference from java which is stored in a java threaded class instance
 */
JNIEXPORT void JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniDestroyChannel
(JNIEnv *env, jobject self, jlong peer) {
	
	GETPEER(nativeInstance, peer);
	if (nativeInstance != NULL) {
		env->DeleteGlobalRef(nativeInstance->_handlerID);
		nativeInstance->disConnect();
		delete nativeInstance;
                nativeInstance = NULL;
	}
	Dmsg0(1500, "destroy Called...");
    return;
 }

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniConnect
 * Signature: (JLjava/lang/String;I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniConnect
(JNIEnv *env, jobject self, jlong peer, jstring host, jint port) {
	char *s;
    jboolean isCopy;
	jboolean result = false;
	
	GETPEER(nativeInstance, peer);
	if (nativeInstance == NULL) {
		return false;
	} 

    s = (char*) env->GetStringUTFChars(host, &isCopy);
	if (s != NULL) {
		result = nativeInstance->connect(s, port);
	} else {
		result = false;
	}

    if (isCopy == JNI_TRUE)
		env->ReleaseStringUTFChars(host, s);
	return result;
}

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniDisConnect
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniDisConnect
(JNIEnv *env, jobject self, jlong peer) {
      GETPEER(nativeInstance, peer);
	  if (nativeInstance != NULL) {
		  nativeInstance->disConnect();
	  }
}

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniLogin
 * Signature: (JLjava/lang/String;Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniLogin
(JNIEnv *env, jobject self, jlong peer, jstring login, jstring password) {
	char *l, *p;
    jboolean isCopy, isCopy2;
	jboolean result = false;
	
	GETPEER(nativeInstance, peer);
	if (nativeInstance == NULL) {
		return false;
	} 

    l = (char*) env->GetStringUTFChars(login, &isCopy);
    p = (char*) env->GetStringUTFChars(password, &isCopy2);
	if ((l != NULL) && (p != NULL)) {
		result = nativeInstance->login(l, p);
	} else {
		result = false;
	}

    if (isCopy == JNI_TRUE)
		env->ReleaseStringUTFChars(login, l);
    if (isCopy2 == JNI_TRUE)
		env->ReleaseStringUTFChars(password, p);
	return result;
}

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniReConnect
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniReConnect
(JNIEnv *env, jobject self, jlong peer) {
	GETPEER(nativeInstance, peer);
	if (nativeInstance != NULL) {
		return nativeInstance->reConnect();
	} else {
		return false;
	}
}

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniSendCommand
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniSendCommand
(JNIEnv *env, jobject self, jlong peer, jstring cmd) {
      char *s;
      jboolean isCopy;

      s = (char*) env->GetStringUTFChars(cmd, &isCopy);
      GETPEER(nativeInstance, peer);
	  if ((nativeInstance != NULL) && (s != NULL)) {
		  nativeInstance->sendCommand(s);
	  }
      if (isCopy == JNI_TRUE)
         env->ReleaseStringUTFChars(cmd, s);
}

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniSendSignal
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniSendSignal
(JNIEnv *env, jobject self, jlong peer, jint signal) {
      GETPEER(nativeInstance, peer);
	  if (nativeInstance != NULL) {
		  nativeInstance->sendSignal(signal);
	  }
}

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniReceiveMessage
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniReceiveMessage
(JNIEnv *env, jobject self, jlong peer) {
	GETPEER(nativeInstance, peer);
	if (nativeInstance != NULL) {
		return nativeInstance->receiveMessage();
	} else {
		return 0;
	}
}

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniReplyOfCommand
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniReplyOfCommand
(JNIEnv *env, jobject self, jlong peer) {
	GETPEER(nativeInstance, peer);
	if (nativeInstance == NULL) {
		return NULL;
	}
	return env->NewStringUTF(nativeInstance->replyOfCommand()); 
}

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniGetReplyLength
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniGetReplyLength
(JNIEnv *env, jobject self, jlong peer) {
	GETPEER(nativeInstance, peer);
	if (nativeInstance != NULL) {
		return nativeInstance->getReplyLength();
	} else {
		return -1;
	}
}

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniSignalToString
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniSignalToString
(JNIEnv *env, jobject self, jlong peer) {
	GETPEER(nativeInstance, peer);
	if (nativeInstance == NULL) {
		return NULL;
	}
	return env->NewStringUTF(nativeInstance->signalToAscii()); 
}

/*
 * Class:     org_ilap_javabacula_network_BaculaConnection
 * Method:    jniIsBnetStopped
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_org_ilap_javabacula_network_BaculaConnection_jniIsBnetStopped
(JNIEnv *env, jobject self, jlong peer) {
	GETPEER(nativeInstance, peer);
	if (nativeInstance != NULL) {
		return nativeInstance->isBnetStopped();
	} else {
		return true;
	}
}


/*********************************************************************************
	jfieldID fid;
    jstring jstr;
    const char *str;

    jclass cls = (*env)->GetObjectClass(env, obj);
    fid = (*env)->GetFieldID(env, cls, "s",
                             "Ljava/lang/String;");
    if (fid == NULL) {
        return;
    }

    jstr = (*env)->GetObjectField(env, obj, fid);
    str = (*env)->GetStringUTFChars(env, jstr, 0);
    if (str == NULL) {
        return;
    }
    printf("  c.s = \"%s\"\n", str);
    (*env)->ReleaseStringUTFChars(env, jstr, str);

    // Create a new string and overwrite the instance field 
    jstr = (*env)->NewStringUTF(env, "123");
    (*env)->SetObjectField(env, obj, fid, jstr);
} 
*********************************************************************************/
