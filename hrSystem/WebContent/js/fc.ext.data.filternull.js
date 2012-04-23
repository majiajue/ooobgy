function fcDataFilterNull(value){
	value = String(value);
	if (value == null || value.toLowerCase() == "null" || value.toLowerCase() == "undefined") {
		return "";
	} else {
		return value;
	}
}