<?php
	$server = "localhost";
	$dbname = "hajjbank";
	$dbusername = "root";
	$dbpass = "";
	$id = 0;
	$id = $_GET["id"];
	$conn = new mysqli($server, $dbusername,  $dbpass, $dbname);
	if ($conn-> connect_error){
		die("Connection failed" . $conn->connect_error);
	}
	$sql = "select * from merchant where id = '$id'";
	$result = $conn->query($sql);
	//echo "["; 
	$c = 0;  $limit = 0;
	if($result->num_rows > 0){
		$limit = $result->num_rows;
		while($row = $result -> fetch_assoc()){
			print json_encode($row);
			$c = $c + 1;
			if( $c < $limit){
				echo ",";
			}
		}
	}
	//echo "]";

?>