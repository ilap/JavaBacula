#ifndef __NativeUtils_h__
#define __NativeUtils_h__
#include <jni.h>

JNIEnv *JNU_GetEnv();
jvalue JNU_CallMethodByName(JNIEnv *env,
                     jboolean *hasException,
                     jobject obj, 
                     const char *name,
                     const char *descriptor,
                     ...);


#endif
