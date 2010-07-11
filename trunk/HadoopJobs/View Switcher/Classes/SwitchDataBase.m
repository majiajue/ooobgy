//
//  SwitchDataBase.m
//  View Switcher
//
//  Created by Mac on 10-4-24.
//  Copyright 2010 sock3. All rights reserved.
//

#import "SwitchDataBase.h"
#import "Color.h"
#import "Shoe.h"
#import "Sock.h"

@implementation SwitchDataBase

-(void) checkAndInitDatabase{
	// Check if the SQL database has already been saved to the users phone, if not then copy it over
	BOOL success;
	
	// Create a FileManager object, we will use this to check the status
	// of the database and to copy it over if required
	NSFileManager *fileManager = [NSFileManager defaultManager];
	
	// Check if the database has already been created in the users filesystem
	success = [fileManager fileExistsAtPath:databasePath];
	
	// If the database already exists then return without doing anything
	if(success) return;
	
	// If not then proceed to copy the database from the application to the users filesystem
	
	// Get the path to the database in the application package
	NSString *databasePathFromApp = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:databaseName];
	
	// Copy the database from the package to the users filesystem
	[fileManager copyItemAtPath:databasePathFromApp toPath:databasePath error:nil];
	
	[fileManager release];
}

-(void) initDB{
	// init databaseName
	databaseName = @"ShoeSockDatabase_ver010101.sql";
	// Get the path to the documents directory and append the databaseName
	NSArray *documentPaths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
	NSString *documentsDir = [documentPaths objectAtIndex:0];
	databasePath = [documentsDir stringByAppendingPathComponent:databaseName];
	
	[self checkAndInitDatabase];
}

-(NSMutableArray *) findAllColor{
	// must init before use the method find***
	sqlite3 *database;
	
	NSMutableArray *colors;
	
	colors = [[NSMutableArray alloc] init];
	
	//Open Database
	if (sqlite3_open([databasePath UTF8String], &database) == SQLITE_OK) {
		const char *sqlStatement = "select * from color";
		sqlite3_stmt *compiledStatement;
		if(sqlite3_prepare_v2(database, sqlStatement, -1, &compiledStatement, NULL) == SQLITE_OK) {
			// Loop through the results and add them to the feeds array
			while(sqlite3_step(compiledStatement) == SQLITE_ROW) {
				//Read data
				int cid = (int)sqlite3_column_text(compiledStatement, 1);
				NSString *colorName = [NSString stringWithUTF8String:(char *)sqlite3_column_text(compiledStatement, 2)];
				//create Color
				Color *color = [[Color alloc] initById:cid AndName:colorName];
				[colors addObject:color];
				[color release];
			}
		}
		// Release the compiled statement from memory
		sqlite3_finalize(compiledStatement);
	}
	sqlite3_close(database);
	
	return colors;
}

-(NSMutableArray *)	findAllShoes{
	sqlite3 *database;
	NSMutableArray *shoes;
	shoes = [[NSMutableArray alloc] init];
	if (sqlite3_open([databasePath UTF8String], &database) == SQLITE_OK) {
		const char *sqlStatement = "slect * from shoe";
		sqlite3_stmt *compiledStatement;
		if(sqlite3_prepare_v2(database, sqlStatement, -1, &compiledStatement, NULL) == SQLITE_OK) {
			// Loop through the results and add them to the feeds array
			while(sqlite3_step(compiledStatement) == SQLITE_ROW) {
				//Read data
				int sid = (int)sqlite3_column_text(compiledStatement, 1);
				NSString *imgPath = [NSString stringWithUTF8String:(char *)sqlite3_column_text(compiledStatement, 2)];
				int cid = (int)sqlite3_column_text(compiledStatement, 3);
				//create Shoe
				Shoe *shoe = [[Shoe alloc] initBySid:sid andPath:imgPath andColor:cid];
				[shoes addObject:shoe];
				[shoe release];
			}
		}
		// Release the compiled statement from memory
		sqlite3_finalize(compiledStatement);
	}
	sqlite3_close(database);
	
	return shoes;
}

-(NSMutableArray *)	findAllSocks{
	sqlite3 *database;
	NSMutableArray *socks;
	socks = [[NSMutableArray alloc] init];
	if (sqlite3_open([databasePath UTF8String], &database) == SQLITE_OK) {
		const char *sqlStatement = "slect * from sock";
		sqlite3_stmt *compiledStatement;
		if(sqlite3_prepare_v2(database, sqlStatement, -1, &compiledStatement, NULL) == SQLITE_OK) {
			// Loop through the results and add them to the feeds array
			while(sqlite3_step(compiledStatement) == SQLITE_ROW) {
				//Read data
				int soid = (int)sqlite3_column_text(compiledStatement, 1);
				NSString *imgPath = [NSString stringWithUTF8String:(char *)sqlite3_column_text(compiledStatement, 2)];
				int cid = (int)sqlite3_column_text(compiledStatement, 3);
				//create Shoe
				Sock *sock = [[Sock alloc] initBySoid:soid andPath:imgPath andColor:cid];
				[socks addObject:sock];
				[sock release];
			}
		}
		// Release the compiled statement from memory
		sqlite3_finalize(compiledStatement);
	}
	sqlite3_close(database);
	
	return socks;
}

-(NSMutableArray *)	findShoesByColorId:(int) cid{
	sqlite3 *database;
	NSMutableArray *shoes;
	shoes = [[NSMutableArray alloc] init];
	if (sqlite3_open([databasePath UTF8String], &database) == SQLITE_OK) {
		NSString *sql = [[NSString alloc] initWithFormat:@"slect * from shoe where cid=%i",cid];
		const char *sqlStatement = [sql UTF8String];
		sqlite3_stmt *compiledStatement;
		if(sqlite3_prepare_v2(database, sqlStatement, -1, &compiledStatement, NULL) == SQLITE_OK) {
			// Loop through the results and add them to the feeds array
			while(sqlite3_step(compiledStatement) == SQLITE_ROW) {
				//Read data
				int sid = (int)sqlite3_column_text(compiledStatement, 1);
				NSString *imgPath = [NSString stringWithUTF8String:(char *)sqlite3_column_text(compiledStatement, 2)];
				int cid = (int)sqlite3_column_text(compiledStatement, 3);
				//create Shoe
				Shoe *shoe = [[Shoe alloc] initBySid:sid andPath:imgPath andColor:cid];
				[shoes addObject:shoe];
				[shoe release];
			}
		}
		// Release the compiled statement from memory
		sqlite3_finalize(compiledStatement);
		[sql release];
	}
	sqlite3_close(database);
	
	return shoes;
}

-(NSMutableArray *)	findSocksByColorId:(int) cid{
	sqlite3 *database;
	NSMutableArray *socks;
	socks = [[NSMutableArray alloc] init];
	if (sqlite3_open([databasePath UTF8String], &database) == SQLITE_OK) {
		NSString *sql = [[NSString alloc] initWithFormat:@"slect * from sock where cid=%i",cid];
		const char *sqlStatement = [sql UTF8String];
		sqlite3_stmt *compiledStatement;
		if(sqlite3_prepare_v2(database, sqlStatement, -1, &compiledStatement, NULL) == SQLITE_OK) {
			// Loop through the results and add them to the feeds array
			while(sqlite3_step(compiledStatement) == SQLITE_ROW) {
				//Read data
				int soid = (int)sqlite3_column_text(compiledStatement, 1);
				NSString *imgPath = [NSString stringWithUTF8String:(char *)sqlite3_column_text(compiledStatement, 2)];
				int cid = (int)sqlite3_column_text(compiledStatement, 3);
				//create Shoe
				Sock *sock = [[Sock alloc] initBySoid:soid andPath:imgPath andColor:cid];
				[socks addObject:sock];
				[sock release];
			}
		}
		// Release the compiled statement from memory
		sqlite3_finalize(compiledStatement);
		[sql release];
	}
	sqlite3_close(database);
	
	return socks;
}

@end

















