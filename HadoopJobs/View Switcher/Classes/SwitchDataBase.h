//
//  SwitchDataBase.h
//  View Switcher
//
//  Created by Mac on 10-4-24.
//  Copyright 2010 sock3. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>


@interface SwitchDataBase : NSObject {
	NSString *databaseName;
	NSString *databasePath;
}

-(void) checkAndInitDatabase;
-(void) initDB;
-(NSMutableArray *)	findAllColor;
-(NSMutableArray *)	findAllShoes;
-(NSMutableArray *)	findAllSocks;
-(NSMutableArray *)	findShoesByColorId:(int) cid;
-(NSMutableArray *)	findSocksByColorId:(int) cid;

@end
