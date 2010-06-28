#include <com_alimama_loganalyzer_jobs_mrs_algo_AliWS086.h>
#include <assert.h>
#include <iostream>
#include <vector>
#include <fstream>
#include <string.h>
#include "AliWSInterface.h"

using namespace std;
using namespace ws;

#define TAOBAO 2
#define TAGID_BRAND 3361
#define TAGID_STYLE 3362
#define TAGID_CATWORD 2846
#define TAGID_DESCWORD 3584

AliWS* pSegmenter = NULL;

/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_AliWSTokenization
 * Method:    initialize
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_alimama_loganalyzer_jobs_mrs_algo_AliWS086_initialize
(JNIEnv *env, jobject obj, jstring confPath) {
	// Step 1) Clean old object and create new object	
	if (pSegmenter != NULL) {
		delete pSegmenter;
		pSegmenter = NULL;
	}
	pSegmenter = new AliWS();

	// Step 2) Load the input library
	jboolean isCopy = false;
	const char* buf = env->GetStringUTFChars(confPath, &isCopy);

        pSegmenter->init(buf); 

        pSegmenter->selectSegmenter(TAOBAO); 

	env->ReleaseStringUTFChars(confPath, buf);
        buf = NULL;
}

/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_AliWSTokenization
 * Method:    unitialize
 * Signature: ()V
 */
JNIEXPORT void Java_com_alimama_loganalyzer_jobs_mrs_algo_AliWS086_unitialize
  (JNIEnv *env, jobject obj) {
	// Do nothing, release resources
	if (pSegmenter != NULL) {
		delete pSegmenter;
		pSegmenter = NULL;
	}
}

/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_AliWSTokenization
 * Method:    tokenization
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_alimama_loganalyzer_jobs_mrs_algo_AliWS086_tokenization  
(JNIEnv *env, jobject o, jstring retrievalSentence)
{
	AliWS& segmenter = *pSegmenter;

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
	char* result = new char[8192];
	memset(result, 0, 8192);

	// Step 3) Segment input string
        std::vector<WORD_UTF8_T> words;
        bool ret = false;
        
//        cout<< "start segment" <<endl;
        ret = segmenter.segmentMax(buf, len, words);
        if(ret)
        {
//            cout<< "segment success" <<endl;
            uint32_t seek = 0;
            result[0] = 0;
		
            for(uint32_t i = 0; i < words.size(); i++)
            {
                const WORD_UTF8_T &word_i = words[i];

                int32_t u8len = word_i.length;
                strncpy(result + seek, word_i.pBuf, u8len);

                if(u8len > 0)
                {
                    seek += u8len;
                }
		result[seek++] = 0x0002;
                
                //属性信�?
		bool isBrand = false;
		bool isStyle = false;
		bool isCat = false;
		bool isDesc = false;
		int checks = 4;
                for(uint32_t k = 0; k < word_i.wordTypes.size() && checks > 0; k++)
                {
			//cout << word_i.wordTypes[k].typeID << "\t";
			if (word_i.wordTypes[k].typeID == TAGID_BRAND)
			{	
				isBrand = true;
				checks --;
			}		
			
			if (word_i.wordTypes[k].typeID == TAGID_STYLE)
			{
				isStyle = true;
				checks --;
			}
			
			if (word_i.wordTypes[k].typeID == TAGID_CATWORD)
			{
				isCat = true;
				checks --;
			}

			if (word_i.wordTypes[k].typeID == TAGID_DESCWORD)
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
                
                //语义信息
/*                if( word_i.semItems.size() > 0)
                {
                    for(uint32_t k = 0; k < word_i.semItems.size(); k++)
                    {                        
                        strncpy(result + seek, word_i.pBuf + word_i.semItems[k].offset, word_i.semItems[k].len);
                        seek += word_i.semItems[k].len;
                        result[seek++] = 0x0002;
                    }
                }*/
                //cout<< result <<endl;
            }

            result[seek] = '\0';
        }

	// Step 4) Delete
	env->ReleaseStringUTFChars(retrievalSentence, buf);
	buf = NULL;
	
	jstring jresult = env->NewStringUTF(result);

	delete[] result;
    	result = NULL;
	return jresult;
}
