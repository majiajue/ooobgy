//
//  Shoe.h
//  View Switcher
//
//  Created by Mac on 10-4-24.
//  Copyright 2010 sock3. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Shoe : NSObject {
@protected
	int sid;
	NSString * path;
	int cid;
}

-(void) setSid : (int) ssid;
-(int) getSid;
-(void) setPath : (NSString *) spath;
-(NSString *) getPath;
-(void) setCid : (int) scid;
-(int) getCid;
-(Shoe *) initByPath: (NSString *) spath andColor:(int) color;
-(Shoe *) initBySid : (int)ssid andPath:(NSString *) spath andColor:(int) color;

@end
