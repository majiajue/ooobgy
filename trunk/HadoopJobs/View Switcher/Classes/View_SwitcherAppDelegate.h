//
//  View_SwitcherAppDelegate.h
//  View Switcher
//
//  Created by Cui Luhai on 10-4-16.
//  Copyright China Academy of Art 2010. All rights reserved.
//

#import <UIKit/UIKit.h>
@class SwitchViewController;

@interface View_SwitcherAppDelegate : NSObject <UIApplicationDelegate> {
    IBOutlet UIWindow *window;
	IBOutlet SwitchViewController *switchViewController;
}

@property (nonatomic, retain) UIWindow *window;
@property (nonatomic, retain) SwitchViewController *switchViewController;
@end

