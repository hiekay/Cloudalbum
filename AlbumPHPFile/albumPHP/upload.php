<?php date_default_timezone_set('prc');

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$image = $_POST['image'];
		
		$db_server = " ";
		$db_user = " ";
		$db_pwd = " ";
		$link = mysql_connect($db_server, $db_user, $db_pwd) or die(mysql_error()); 
		$db_selected = mysql_select_db($db_user, $link);
		
		$sql ="SELECT id FROM photos ORDER BY id ASC;";
		
		$res = mysql_query($sql) or die(mysql_error());
		
		$id = 0;
		
		while($row = mysql_fetch_array($res)){
				$id = $row['id'];
		}
		
		$time = date("Y.m.d-H:i:s");

		$path = "uploads/$time.png";
		
		$actualpath = "http://i.cs.hku.hk/~xbzeng/album/$path";
		
		$sql = "INSERT INTO photos (image) VALUES ('$actualpath');";
		
		if(mysql_query($sql)){
			file_put_contents($path,base64_decode($image));
			chmod("uploads/$time.png", 0644);
			echo "Successfully Uploaded";
		}
		
		mysql_close($link);
	}else{
		echo "Error";
	}

	printf("New Photo: %s.png\n", $time);


	

