//
//  Shoe.m
//  View Switcher
//
//  Created by Mac on 10-4-24.
//  Copyright 2010 sock3. All rights reserved.
//

#import "Shoe.h"


@implementation Shoe

-(void) setSid : (int) ssid{
	self.sid = ssid;
}

-(int) getSid{
	return sid;
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

-(Shoe *) initByPath: (NSString *) spath andColor:(int) color{
	self.path = spath;
	self.cid = color;
	return self;
}

-(Shoe *) initBySid : (int)ssid andPath:(NSString *) spath andColor:(int) color{
	self.sid = ssid;
	self.path = spath;
	self.cid = color;
	return self;
}

@end
