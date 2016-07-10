<?php
	$db_server = " ";
	$db_user = " ";
	$db_pwd = " ";
	$link = mysql_connect($db_server, $db_user, $db_pwd) or die(mysql_error()); 
	$db_selected = mysql_select_db($db_user, $link);	
	
	$sql = "SELECT image FROM photos";
	 
	$res = mysql_query($sql);
	 
	$result = array();
	 
	while($row = mysql_fetch_array($res)){
 		array_push($result,array('url'=>$row['image']));
	}
	 
	echo json_encode(array("result"=>$result));
	 
	mysql_close($link);