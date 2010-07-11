//
//  Sock.m
//  View Switcher
//
//  Created by Mac on 10-4-24.
//  Copyright 2010 sock3. All rights reserved.
//

#import "Sock.h"


@implementation Sock
-(void) setSoid : (int) ssid{
	self.soid = ssid;
}

-(int) getSoid{
	return soid;
}

-(void) setPath : (NSString *) spath{
	self.path = spath;
}

-(NSString *) getPath{
	return path;
}

-(void) setCid : (int) scid{
	self.cid = scid;
}

-(int) getCid{
	return cid;
}

-(Sock *) initByPath : (NSString *) spath andColor: (int) color{
	self.path = spath;
	self.cid = color;
	return self;
}

-(Sock *) initBySoid : (int) ssid andPath: (NSString *) spath andColor: (int) color{
	self.soid = ssid;
	self.path = spath;
	self.cid = color;
	return self;
}
@end
