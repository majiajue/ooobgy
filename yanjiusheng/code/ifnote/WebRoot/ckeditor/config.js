CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	config.language = 'zh-cn'; // 配置语言
	config.uiColor = '#ACF'; // 背景颜色
	config.width = 800; // 宽度
	config.height = 300; // 高度
	config.font_names = '宋体;楷体_GB2312;新宋体;黑体;隶书;幼圆;微软雅黑;Arial;comic sans ms;lucida sans unicode;times new roman;trebuchet ms;verdana;tahoma'; // 字体
	config.skin = 'office2003';// 皮肤

	// 工具栏
	config.toolbar=
		[
		['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
		['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
		['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
		['Link','Unlink','Anchor'],
		['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
		'/',
		['Styles','Format','Font','FontSize','TextColor','BGColor'],
		['SelectAll','Copy','Cut','Paste','PasteFromWord','PasteText','-','RemoveFormat','Undo','Redo'],
		'/',
		['Maximize','ShowBlocks','-','Find','Replace'],
		['Source','-','Templates','-','Preview']
		];
};