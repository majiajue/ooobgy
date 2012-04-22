function fcLayoutExtCompCenter(id) {
	// banding body resize
	$(window).resize(function(){
		reLayout();
	});
	reLayout();
	
	function reLayout() {
		var xl = 0;
		var screenWidth = $(window).width();
		var extCompWidth = $("#" + id).width();
		xl = Math.floor((screenWidth - extCompWidth) / 2);
		$("#" + id).css({
			"margin-left" : xl + "px"
		});
	}
}
