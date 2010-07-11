//
//  SwitchViewController.h
//  View Switcher
//
//  Created by Cui Luhai on 10-4-16.
//  Copyright 2010 China Academy of Art. All rights reserved.
//

#import <UIKit/UIKit.h>
@class StartViewController;
@class ChooseViewController;

@interface SwitchViewController : UIViewController {
	StartViewController *startViewController;
	ChooseViewController *chooseViewController;

}
@property (retain,nonatomic) StartViewController *startViewController;
@property (retain,nonatomic) ChooseViewController *chooseViewController;

@end
