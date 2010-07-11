//
//  View_SwitcherAppDelegate.m
//  View Switcher
//
//  Created by Cui Luhai on 10-4-16.
//  Copyright China Academy of Art 2010. All rights reserved.
//

#import "View_SwitcherAppDelegate.h"
#import "SwitchViewController.h"
@implementation View_SwitcherAppDelegate

@synthesize window;
@synthesize switchViewController;

- (void)applicationDidFinishLaunching:(UIApplication *)application{
    //sleep(3);
	// Override point for customization after application launch
	[window addSubview:switchViewController.view];
    [window makeKeyAndVisible];
}


- (void)dealloc {
    [window release];
    [super dealloc];
	[switchViewController release];
}


@end
