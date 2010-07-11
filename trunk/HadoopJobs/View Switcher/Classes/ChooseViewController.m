    //
//  ChooseViewController.m
//  sock02
//
//  Created by Cui Luhai on 10-4-19.
//  Copyright 2010 GoldenDigit. All rights reserved.
//

#import "ChooseViewController.h"



@implementation ChooseViewController
@synthesize gestureStartPoint;   
@synthesize image1;
@synthesize image2;
@synthesize image3;
@synthesize image4;
@synthesize dirString;
@synthesize i;
@synthesize j;
- (void)viewDidLoad {
    //[super viewDidLoad];
	[self.view setAlpha:0.0f];
	UIColor *clearColor = [[UIColor alloc]initWithWhite:0.0f alpha:0.0f];
	[image1 setImage:[UIImage imageNamed:@"shoe1.png"]];
	[image3 setImage:[UIImage imageNamed:@"sock1.png"]];
	[image1 setBackgroundColor:clearColor];
	[image2 setBackgroundColor:clearColor];
	[image1 setTag:0];
	[image2 setTag:1];
	i = 1;
	j = 1;
	[UIView beginAnimations:nil context:NULL];
	[UIView setAnimationDuration:4.0f];
	[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
	[self.view setAlpha:1.0f];
	[UIView commitAnimations]; 
}

/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (void)dealloc {
    [super dealloc];
}

#pragma mark -

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
	UITouch *touch = [touches anyObject];
	gestureStartPoint = [touch locationInView:self.view];
}
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
	UITouch *touch = [touches anyObject];
	CGPoint currentPosition = [touch locationInView:self.view];
	
	CGFloat deltay = gestureStartPoint.x - currentPosition.x;
	CGFloat deltaY = fabsf(gestureStartPoint.y - currentPosition.y);
	if (gestureStartPoint.y>240) {
		if (deltay >= minGestureLength && deltaY <= maxVariance) {
			dirString = @"shoeleft";
		}
		else if (-deltay >= minGestureLength && deltaY<= maxVariance) {
			dirString = @"shoeright";
		}
	}
	else{
		if (deltay >= minGestureLength && deltaY <= maxVariance) {
			dirString = @"sockleft";
		}
		else if (-deltay >= minGestureLength && deltaY<= maxVariance) {
			dirString = @"sockright";
		}
	}
	
	
	
	//[self.view insertSubview:comingImage atIndex:0];
	//[goingImage removeFromSuperview];
	
}

- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
	[self changePic:dirString];
}

- (void)changePic:(NSString *)direction
{
	UIImageView *comingImage = nil;
	UIImageView *goingImage = nil;
	
	if (direction == @"shoeleft") 
	{
		if (i < 3) {
			i++;
		}
		else i = 1;
		if(self.image1.tag == 1){
			comingImage = self.image1;
			goingImage = self.image2;
			[image1 setTag:0];
			[image2 setTag:1];
		}
		else{
			comingImage = self.image2;
			goingImage = self.image1;
			[image1 setTag:1];
			[image2 setTag:0];
			//transition = UIViewAnimationTransitionNone;
		}
		NSString *num = [[NSString alloc] initWithFormat:@"shoe%i.png",i];
		[comingImage setImage:[UIImage imageNamed:num]];
		comingImage.center = CGPointMake(480.0f, 230.0f);
		goingImage.center = CGPointMake(160.0f, 230.0f);
		[UIView beginAnimations:nil context:NULL];
		[UIView setAnimationDuration:0.5f];
		[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
		//[UIView setAnimationTransition:transition forView:self.view cache:YES];
		comingImage.center = CGPointMake(160.0f, 230.0f);
		goingImage.center = CGPointMake(-160.0f, 230.0f);
		
		[UIView commitAnimations];
	}
	else if(direction == @"shoeright")
	{
		if (i > 1) {
			i--;
		}
		else i = 3;
		if(self.image1.tag == 1){
			comingImage = self.image1;
			goingImage = self.image2;
			[image1 setTag:0];
			[image2 setTag:1];
		}
		else{
			comingImage = self.image2;
			goingImage = self.image1;
			[image1 setTag:1];
			[image2 setTag:0];
			//transition = UIViewAnimationTransitionNone;
		}
		NSString *num = [[NSString alloc] initWithFormat:@"shoe%i.png",i];
		[comingImage setImage:[UIImage imageNamed:num]];
		comingImage.center = CGPointMake(-160.0f, 230.0f);
		goingImage.center = CGPointMake(160.0f, 230.0f);
		[UIView beginAnimations:nil context:NULL];
		[UIView setAnimationDuration:0.5f];
		[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
		comingImage.center = CGPointMake(160.0f, 230.0f);
		goingImage.center = CGPointMake(480.0f, 230.0f);
		[UIView commitAnimations];
	}
	else if(direction == @"sockleft")
	{
		if (j <2) {
			j++;
		}
		else j = 1;
		if(self.image3.tag == 3){
			comingImage = self.image3;
			goingImage = self.image4;
			[image3 setTag:2];
			[image4 setTag:3];
		}
		else{
			comingImage = self.image4;
			goingImage = self.image3;
			[image3 setTag:3];
			[image4 setTag:2];
			//transition = UIViewAnimationTransitionNone;
		}
		NSString *num = [[NSString alloc] initWithFormat:@"sock%i.png",j];
		[comingImage setImage:[UIImage imageNamed:num]];
		comingImage.center = CGPointMake(480.0f, 230.0f);
		goingImage.center = CGPointMake(160.0f, 230.0f);
		[UIView beginAnimations:nil context:NULL];
		[UIView setAnimationDuration:0.5f];
		[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
		comingImage.center = CGPointMake(160.0f, 230.0f);
		goingImage.center = CGPointMake(-160.0f, 230.0f);
		[UIView commitAnimations];
	}
	else if(direction == @"sockright")
	{
		if (j > 1) {
			j--;
		}
		else j = 2;
		if(self.image3.tag == 3){
			comingImage = self.image3;
			goingImage = self.image4;
			[image3 setTag:2];
			[image4 setTag:3];
		}
		else{
			comingImage = self.image4;
			goingImage = self.image3;
			[image3 setTag:3];
			[image4 setTag:2];
			//transition = UIViewAnimationTransitionNone;
		}
		NSString *num = [[NSString alloc] initWithFormat:@"sock%i.png",j];
		[comingImage setImage:[UIImage imageNamed:num]];
		comingImage.center = CGPointMake(-160.0f, 230.0f);
		goingImage.center = CGPointMake(160.0f, 230.0f);
		[UIView beginAnimations:nil context:NULL];
		[UIView setAnimationDuration:0.5f];
		[UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
		comingImage.center = CGPointMake(160.0f, 230.0f);
		goingImage.center = CGPointMake(480.0f, 230.0f);
		[UIView commitAnimations];
	}
	
	dirString = @"";

}


@end
