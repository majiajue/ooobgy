//
//  Color.m
//  View Switcher
//
//  Created by Mac on 10-4-24.
//  Copyright 2010 sock3. All rights reserved.
//

#import "Color.h"


@implementation Color

-(void) setCid: (int) ccid{
	self.cid = cid;
}

-(void) setColor: (NSString *) cl{
	self.color = cl;
}

-(int) getCid{
	return cid;
}

-(NSString *) getColor{
	return color;
}

-(Color *) initByColorName:(NSString *)cl {
	self.color = cl;
	return self;
}

-(Color *) initById : (int)ccid AndName:(NSString *) name;{
	self.cid = ccid;
	self.color = name;
	return self;
}

@end
