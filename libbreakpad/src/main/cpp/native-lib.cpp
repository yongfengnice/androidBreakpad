//
// Created by wps on 2022/1/10.
//
#include <stdio.h>
#include <stdlib.h>
#include "client/linux/handler/exception_handler.h"
#include "client/linux/handler/minidump_descriptor.h"
#include <jni.h>
#include "utils/log_utils.h"
#include "android/log.h"

bool DumpFilterCallback(void *context) {
    LOGE("DumpFilterCallback...")
    return true;
}

bool DumpCallback(const google_breakpad::MinidumpDescriptor &descriptor,
                  void *context,
                  bool succeeded) {
    printf("Dump path: %s\n", descriptor.path());
    return succeeded;
}

void Crash() {
    volatile int *a = reinterpret_cast<volatile int *>(NULL);
    *a = 1;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_suyf_libbreakpad_TestBreakPad_nativeInit(JNIEnv *env, jclass clazz,
                                                  jstring dirPath) {

    const char *charPath = env->GetStringUTFChars(dirPath, JNI_FALSE);

    string dumpPath = string(charPath);
    static google_breakpad::MinidumpDescriptor descriptor(dumpPath);
    static google_breakpad::ExceptionHandler eh(descriptor, DumpFilterCallback, DumpCallback,
                                                NULL, true, -1);

    __android_log_print(ANDROID_LOG_ERROR, "Suyf", "dumpPath4==%s", dumpPath.c_str());
    LOGE("Java_com_suyf_libbreakpad_TestBreakPad_nativeInit")
    env->ReleaseStringUTFChars(dirPath, charPath);
}