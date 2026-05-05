#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_webforge_app_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Motor C++ de Javier Activo";
    return env->NewStringUTF(hello.c_str());
}
