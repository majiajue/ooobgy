#include <jni.h>
#include <com_alimama_loganalyzer_jobs_mrs_algo_TwsTokenization.h>
#include <assert.h>
#include <iostream>
#include <vector>
#include <wsInterface.h>
#include <fstream>

using namespace std;
using namespace ws;

WsSegmenter* pSegmenter = NULL;

/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_TwsTokenization
 * Method:    initialize
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_alimama_loganalyzer_jobs_mrs_algo_TwsTokenization_initialize
(JNIEnv *env, jobject obj, jstring confPath) {
	// Step 1) Clean old object and create new object	
	if (pSegmenter != NULL) {
		delete pSegmenter;
		pSegmenter = NULL;
	}
	pSegmenter = new WsSegmenter();

	// Step 2) Load the input library
	jboolean isCopy = false;
	const char* buf = env->GetStringUTFChars(confPath, &isCopy);
	pSegmenter->init(buf);
	env->ReleaseStringUTFChars(confPath, buf);
        buf = NULL;
}

/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_TwsTokenization
 * Method:    unitialize
 * Signature: ()V
 */
JNIEXPORT void Java_com_alimama_loganalyzer_jobs_mrs_algo_TwsTokenization_unitialize
  (JNIEnv *env, jobject obj) {
	// Do nothing, release resources
	if (pSegmenter != NULL) {
		delete pSegmenter;
		pSegmenter = NULL;
	}
}

/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_TwsTokenization
 * Method:    tokenization
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_alimama_loganalyzer_jobs_mrs_algo_TwsTokenization_tokenization  
(JNIEnv *env, jobject o, jstring retrievalSentence)
{
	WsSegmenter& segmenter = *pSegmenter;

	// Step 1) Load the input words
	jboolean isCopy = false;
	const char* buf = env->GetStringUTFChars(retrievalSentence, &isCopy);
	uint32_t len = strlen(buf);
	if(len == 0 || len > 2048) {
	        env->ReleaseStringUTFChars(retrievalSentence, buf);
	        buf = NULL;

        	jstring jresult = env->NewStringUTF("");
	        return jresult;
	}

	// Step 2) Allocate the result buffer
	char* result = new char[4096];
	memset(result, 0, 4096);

	// Step 3) Segment input string
	uint16_t *u16buf = NULL;
        u16buf = new uint16_t[len];
        uint32_t u16len = segmenter.utf8ToUtf16(buf, len, u16buf, len);
        std::vector<WORD_T> words;
        bool ret = segmenter.segmentMax(u16buf, u16len, words);
        if(ret)
        {
            uint32_t seek = 0;
            result[0] = 0;
		
            for(uint32_t i = 0; i < words.size(); i++)
            {
                const WORD_T &word_i = words[i];
                int32_t u8InitLen = word_i.length * 3;
                int32_t u8len = segmenter.utf16ToUtf8(word_i.pBuf, word_i.length, result + seek, u8InitLen + 1);

		/* delete space in the word
		for (int32_t j = 0; j < u8len; j ++ )
		{
			result[seek + j] 
			if (result[seek + j] != ' ') {
				if ( real_length != j)
				{
					result[seek + real_length] = result[seek + j];
				}
				real_length ++;
			}
		}
		u8len = real_length;
		*/

		if (u8len == 0)
		{
			continue;
		}
		
                if(u8len > 0)
                {
                    seek += u8len;
                }
		result[seek++] = 0x0002;
 
		bool isBrand = false;
		bool isStyle = false;
		bool isCat = false;
		bool isDesc = false;
		int checks = 4;
                for(uint32_t k = 0; k < word_i.wordTypes.size() && checks > 0; k++)
                {
			//cout << word_i.wordTypes[k].typeID << "\t";
			if (word_i.wordTypes[k].typeID == 3361)
			{	
				isBrand = true;
				checks --;
			}		
			
			if (word_i.wordTypes[k].typeID == 3362)
			{
				isStyle = true;
				checks --;
			}
			
			if (word_i.wordTypes[k].typeID == 2846)
			{
				isCat = true;
				checks --;
			}

			if (word_i.wordTypes[k].typeID == 3584)
			{
				isDesc = true;
				checks --;
			}
                }
		result[seek++] = 'A';

		if (isBrand)
		{
			result[seek++] = 'B';
		}
		if (isStyle)
		{
			result[seek++] = 'S';
		}
		if (isCat)
		{
			result[seek++] = 'C';
		}
		if (isDesc)
		{
			result[seek++] = 'D';
		}
                result[seek++] = 0x0002;
            }

            result[seek] = '\0';
        }

	// Step 4) Delete
        delete[] u16buf;
        u16buf = NULL;

	env->ReleaseStringUTFChars(retrievalSentence, buf);
	buf = NULL;
	
	jstring jresult = env->NewStringUTF(result);

	delete[] result;
    	result = NULL;
	return jresult;
}

