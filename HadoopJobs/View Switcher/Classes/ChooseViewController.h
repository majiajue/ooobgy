//
//  ChooseViewController.h
//  sock02
//
//  Created by Cui Luhai on 10-4-19.
//  Copyright 2010 GoldenDigit. All rights reserved.
//
#define minGestureLength 10
#define maxVariance 5
#define maxshoes 3
#define maxsocks 2

#import <UIKit/UIKit.h>


@interface ChooseViewController : UIViewController {
	IBOutlet UIImageView *image1,*image2,*image3,*image4;
	CGPoint gestureStartPoint;
	NSString *dirString;
	int i,j;	
}

@property (nonatomic ,retain) UIImageView *image1;
@property (nonatomic ,retain) UIImageView *image2;
@property (nonatomic ,retain) UIImageView *image3;
@property (nonatomic ,retain) UIImageView *image4;
@property (nonatomic ,retain) NSString *dirString;
@property int i;
@property int j;
@property CGPoint gestureStartPoint;

- (void)changePic:(NSString *)direction;
@end
