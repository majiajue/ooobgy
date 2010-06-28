#include <jni.h>
#include <com_alimama_loganalyzer_jobs_mrs_algo_Normalizer.h>
#include <assert.h>
#include <fstream>
#include <string>
#include <iostream>
#include "NormalizeIntf.h"

using namespace std;
using namespace normalize;
NormalizeIntf* norm = NULL;

/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_Normalizer
 * Method:    normalize
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_alimama_loganalyzer_jobs_mrs_algo_Normalizer_normalize
  (JNIEnv *env, jobject obj, jstring jwords, jint options)
{
	char* bufNormalized = new char[2048];
	memset(bufNormalized, 0, 2048);

	if (norm != NULL)
	{
		jboolean isCopy = false;
		const char* buf = env->GetStringUTFChars(jwords, &isCopy);
		if (strlen(buf) < 512)
		{
			string wordsNormalized;
			string words = buf;
			norm->doNormalize(words, options, wordsNormalized, false, false);
			strcpy(bufNormalized, wordsNormalized.c_str());
		}

		env->ReleaseStringUTFChars(jwords, buf);
	}

	jstring jresult = env->NewStringUTF(bufNormalized);
        delete[] bufNormalized;
	
	return jresult;
}

/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_Normalizer
 * Method:    initialize
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_alimama_loganalyzer_jobs_mrs_algo_Normalizer_initialize
  (JNIEnv *env, jobject obj, jstring confPath)  
{
	if (norm == NULL)
	{
		jboolean isCopy = false;
		const char* buf = env->GetStringUTFChars(confPath, &isCopy);

		norm = NormalizeIntf::getInstance();
		if (norm->init(buf))
		{
		}
		env->ReleaseStringUTFChars(confPath, buf);
	}
}

/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_Normalizer
 * Method:    unitialize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_alimama_loganalyzer_jobs_mrs_algo_Normalizer_unitialize
  (JNIEnv *, jobject)
{

}
