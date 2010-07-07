#include <jni.h>
#include <com_taobao_dw_comm_tws_AliWS101.h>
#include <assert.h>
#include <string.h>
#include <iostream>
#include <fstream>
#include "ali_tokenizer.h"

using namespace std;
using namespace ws;

#define TAGID_BRAND 9231
#define TAGID_STYLE 9244
#define TAGID_CATWORD 21506
#define TAGID_DESCWORD 30721
#define RES_BUFFSIZE 4096

AliTokenizerFactory factory;
AliTokenizer* pTokenizer = NULL;
/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_TwsTokenization
 * Method:    initialize
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_taobao_dw_comm_tws_AliWS101_initialize
(JNIEnv *env, jobject obj, jstring confPath) 
{     
        jboolean isCopy = false;
        const char* buf = env->GetStringUTFChars(confPath, &isCopy);        
        
//        AliTokenizerFactory factory;
        factory.Init(buf);               
        env->ReleaseStringUTFChars(confPath, buf);
        buf = NULL;

        pTokenizer = factory.GetAliTokenizer("TAOBAO_CHN");
        if ( !pTokenizer )
        {
               exit(1);
        }
}                  
   

/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_TwsTokenization
 * Method:    unitialize
 * Signature: ()V
 */
JNIEXPORT void Java_com_taobao_dw_comm_tws_AliWS101_unitialize
  (JNIEnv *env, jobject obj) 
{
	// Do nothing, release resources
/*        if (pTokenizer != NULL) 
        {
		delete pTokenizer;
		pTokenizer = NULL;
	}*/
}

/*
 * Class:     com_alimama_loganalyzer_jobs_daily_dataresearcher_TwsTokenization
 * Method:    tokenization
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_taobao_dw_comm_tws_AliWS101_tokenization
(JNIEnv *env, jobject obj, jstring retrievalSentence)
{	
	jboolean isCopy = false;
	const char* buf = env->GetStringUTFChars(retrievalSentence, &isCopy);
        uint32_t len = strlen(buf);

	if(len == 0 || len > 2048) 
	{
	        env->ReleaseStringUTFChars(retrievalSentence, buf);
	        buf = NULL;

        	jstring jresult = env->NewStringUTF("");
	        return jresult;
	}
        
        SegResult* pSegResult = pTokenizer->CreateSegResult();
        
        if ( !pSegResult )
        {        
                exit(1);
        }
     	
        uint32_t ret = pTokenizer->Segment(buf, len, UTF8, SEG_TOKEN_RETRIEVE, pSegResult);
        
        char* result = new char[RES_BUFFSIZE];
	memset(result, 0, RES_BUFFSIZE);
        
        if(ret == 0)
        {

            uint32_t seek = 0;
            result[0] = 0;
            
            SegToken* pToken = pSegResult->GetFirstToken(MAIN_LIST);
            while(pToken)
            {
                   strncpy(result + seek, pToken->pWord, pToken->length);
                   seek += pToken->length;
                   result[seek++] = 0x0002;
                   result[seek++] = 'A';
                                     
                   if(pToken->semanticTagNum > 0)
                   {
                         for(int i = 0; i < pToken->semanticTagNum; ++i)
                         {
                               if(pToken->pSemanticTag[i].id == TAGID_BRAND)
                               {
                                     result[seek++] = 'B';
                               }
                               if(pToken->pSemanticTag[i].id == TAGID_STYLE)
                               {
                                     result[seek++] = 'S';
                               }
                               if(pToken->pSemanticTag[i].id == TAGID_CATWORD)
                               {
                                     result[seek++] = 'C';
                               }
                               if(pToken->pSemanticTag[i].id == TAGID_DESCWORD)
                               {
                                     result[seek++] = 'D';
                               }
                         }
                   }
                   result[seek++] = 0x0002;                        
                   /*
                   if (pToken->subTokenNum > 0)
                   {
                          SegToken* p_sub_token = NULL;
                          for (int i = 0; i < pToken->subTokenNum; ++i)
                          {
                                p_sub_token = pToken->pSubToken + i;
                                strncpy(result + seek, p_sub_token->pWord, p_sub_token->length);
                                seek += p_sub_token->length;
                                result[seek++] = 0x0002;
                                result[seek++] = 'A';                 
                                               
                                if(p_sub_token->semanticTagNum > 0)
                                {
                                       for(int i = 0; i < p_sub_token->semanticTagNum; ++i)
                                       {
                                              if(p_sub_token->pSemanticTag[i].id == TAGID_BRAND)
                                              {
                                                    result[seek++] = 'B';
                                              }
                                              if(p_sub_token->pSemanticTag[i].id == TAGID_STYLE)
                                              {
                                                    result[seek++] = 'S';
                                              }
                                              if(p_sub_token->pSemanticTag[i].id == TAGID_CATWORD)
                                              {
                                                    result[seek++] = 'C';
                                              }
                                              if(p_sub_token->pSemanticTag[i].id == TAGID_DESCWORD)
                                              {
                                                    result[seek++] = 'D';
                                              }
                                        }
                                }
                                result[seek++] = 0x0002;           
                          }                   
                   } 
                                      
                   if (pToken->relatedTokenNum > 0)
                   {
                         SegToken* p_rel_token = NULL;
                         for (int i = 0; i < pToken->relatedTokenNum; ++i)
                         {
                                p_rel_token = pToken->pRelatedToken + i;
                                strncpy(result + seek, p_rel_token->pWord, p_rel_token->length);
                                seek += p_rel_token->length;
                                result[seek++] = 0x0002;
                                result[seek++] = 'A';                                
                                
                                if(p_rel_token->semanticTagNum > 0)
                                {
                                       for(int i = 0; i < p_rel_token->semanticTagNum; ++i)
                                       {
                                              if(p_rel_token->pSemanticTag[i].id == TAGID_BRAND)
                                              {
                                                    result[seek++] = 'B';
                                              }
                                              if(p_rel_token->pSemanticTag[i].id == TAGID_STYLE)
                                              {
                                                    result[seek++] = 'S';
                                              }
                                              if(p_rel_token->pSemanticTag[i].id == TAGID_CATWORD)
                                              {
                                                    result[seek++] = 'C';
                                              }
                                              if(p_rel_token->pSemanticTag[i].id == TAGID_DESCWORD)
                                              {
                                                    result[seek++] = 'D';
                                              }
                                        }
                                }
                                result[seek++] = 0x0002;
                         }
                   }*/

                  pToken = pToken->pRightSibling;                
            }
            
            result[seek] = '\0';
        }
        //cout<<result<<endl;         
        pTokenizer->ReleaseSegResult(pSegResult);
         
        env->ReleaseStringUTFChars(retrievalSentence, buf);
	buf = NULL;
	
	jstring jresult = env->NewStringUTF(result);

	delete[] result;
    	result = NULL;
        
	return jresult;
}
