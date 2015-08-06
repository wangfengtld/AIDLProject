#include <stdio.h>
#include "com_example_androidjni_MainActivity.h"
//JNIEXPORT jstring JNICALL Java_com_example_androidjni_MainActivity_getJniData(
//		JNIEnv *env, jclass obj) {
//	return (*env)->NewStringUTF(env, "Hello Jni!");
//}

//JNIEXPORT jint JNICALL Java_com_example_androidjni_MainActivity_getJniData(
//		JNIEnv *env, jclass obj) {
//	return 9;
//}

JNIEXPORT jobject JNICALL Java_com_example_androidjni_MainActivity_getJniData(
		JNIEnv *env, jclass obj) {
	//���ڰ������������������ com/feixun/jni/Student ������ Lcom/feixun/jni/Student;
	//   ���������� �����Ի��class����
	jclass stucls = (*env)->FindClass(env,"com/example/androidjni/Student"); //���Student������

	//��õø����͵Ĺ��캯��  ������Ϊ <init> �������ͱ���Ϊ void �� V
	jmethodID constrocMID = (*env)->GetMethodID(env ,stucls, "<init>",
			"(ILjava/lang/String;)V");

	jstring str = (*env)->NewStringUTF(env ," come from Native ");

	jobject stu_ojb = (*env)->NewObject(env ,stucls, constrocMID, 11, str); //����һ�����󣬵��ø���Ĺ��캯�������Ҵ��ݲ���

	return stu_ojb;
}
