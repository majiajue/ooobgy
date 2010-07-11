//
//  Color.h
//  View Switcher
//
//  Created by Mac on 10-4-24.
//  Copyright 2010 sock3. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Color : NSObject {
@protected
	int cid;
	NSString *color;
}

-(void) setCid : (int) ccid;
-(void) setColor : (NSString *) cl;
-(int) getCid;
-(NSString *) getColor;
-(Color *) initByColorName : (NSString *) cl;
-(Color *) initById : (int)ccid AndName:(NSString *) name;
@end
