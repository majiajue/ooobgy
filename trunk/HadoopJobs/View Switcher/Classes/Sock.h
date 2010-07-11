//
//  Sock.h
//  View Switcher
//
//  Created by Mac on 10-4-24.
//  Copyright 2010 sock3. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Sock : NSObject {
@protected
	int soid;
	NSString * path;
	int cid;
}

-(void) setSoid : (int) ssid;
-(int) getSoid;
-(void) setPath : (NSString *) spath;
-(NSString *) getPath;
-(void) setCid : (int) scid;
-(int) getCid;
-(Sock *) initByPath : (NSString *) spath andColor: (int) color;
-(Sock *) initBySoid : (int) ssid andPath: (NSString *) spath andColor: (int) color;

@end
