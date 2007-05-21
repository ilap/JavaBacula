#include "NativeUtils.h"
#include "bacula.h"

/* The global Java Virtual Machine */
static JavaVM *_jvm;


JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved) {
    _jvm = jvm;
    return JNI_VERSION_1_4;
}

JNIEXPORT void JNICALL JNI_OnUnLoad(JavaVM *jvm, void *reserved) {
	return;
}

JNIEnv* JNU_GetEnv(){
	JNIEnv* env;
	if (_jvm != NULL) {
  		_jvm->GetEnv((void**)&env, JNI_VERSION_1_4);
	}
	return env;
}
 
jvalue JNU_CallMethodByName(JNIEnv *env,
                     jboolean *hasException,
                     jobject obj, 
                     const char *name,
                     const char *descriptor,
                     ...) {

    va_list args;
    jclass clazz;
    jmethodID mid;
    jvalue result;

    if (env->EnsureLocalCapacity(2) == JNI_OK) {
        clazz = env->GetObjectClass(obj);

	mid = env->GetMethodID(clazz, name, descriptor);
        if (mid) {
           
            const char *p = descriptor;
            /* skip over argument types to find out the 
             * return type 
			 */
            while (*p != ')') p++;
            /* skip ')' */
            p++;
            va_start(args, descriptor);
            switch (*p) {
            case 'V':
                env->CallVoidMethodV(obj, mid, args);
                break;
            case '[':
            case 'L':
                result.l = env->CallObjectMethodV(
                                       obj, mid, args);
                break;
            case 'Z':
                result.z = env->CallBooleanMethodV(
                                       obj, mid, args);
                break;
            case 'B':
                result.b = env->CallByteMethodV(
                                       obj, mid, args);
                break;
            case 'C':
                result.c = env->CallCharMethodV(
                                       obj, mid, args);
                break;
            case 'S':
                result.s = env->CallShortMethodV(
                                       obj, mid, args);
                break;
            case 'I':
                result.i = env->CallIntMethodV(
                                       obj, mid, args);
                break;
            case 'J':
                result.j = env->CallLongMethodV(
                                       obj, mid, args);
                break;
            case 'F':
                result.f = env->CallFloatMethodV(
                                       obj, mid, args);
                break;
            case 'D':
                result.d = env->CallDoubleMethodV(
                                       obj, mid, args);
                break;
            default:
                env->FatalError("illegal descriptor");
            }
            va_end(args);
        }
        env->DeleteLocalRef(clazz);
    }
    if (hasException) {
        *hasException = env->ExceptionCheck();
    }
    return result;
}
