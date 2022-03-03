#include <jni.h>
#include <string>
#include <thread>

void testCrash() {
    printf("testCrash start");
    volatile int *a = reinterpret_cast<volatile int *>(NULL);
    *a = 1;
    printf("testCrash end");
}

void test1() {
    printf("test");
    testCrash();
}

void test2() {
    printf("test1");
    test1();
}

void test3() {
    printf("test2");
    test2();
}

void test4() {
    printf("test4");
    test3();
}

void thread_func(int num) {
    printf("thread_func start====%d", num);
    test1();
    return;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_suyf_androidbreakpad_MainActivity_nativeCrash(JNIEnv *env, jobject thiz) {
    test4();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_suyf_androidbreakpad_MainActivity_threadNativeCrash(JNIEnv *env, jobject thiz) {
    std::thread thread(thread_func, 100);
    thread.join();
}