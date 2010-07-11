    //
//  SwitchViewController.m
//  View Switcher
//
//  Created by Cui Luhai on 10-4-16.
//  Copyright 2010 China Academy of Art. All rights reserved.
//

#import "SwitchViewController.h"
#import "StartViewController.h"
#import "ChooseViewController.h"


@implementation SwitchViewController
@synthesize startViewController;
@synthesize chooseViewController;

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


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    StartViewController *startController = [[StartViewController alloc]initWithNibName:@"StartViewController" bundle:nil];
	self.startViewController = startController;
	[self.view insertSubview:startController.view atIndex:1];
	ChooseViewController *chooseController = [[ChooseViewController alloc]initWithNibName:@"ChooseViewController" bundle:nil];
	self.chooseViewController = chooseController;
	[self.view insertSubview:chooseController.view atIndex:0];
	[startController release];
	[chooseController release];
}

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
	[startViewController release];
    [super dealloc];
}


@end
