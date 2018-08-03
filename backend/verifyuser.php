<?php
	$server = "localhost";
	$dbname = "hajjbank";
	$dbusername = "root";
	$dbpass = "";
	$phone = "12822";
	$pass = "";
	$id = 0;
	$phone = $_GET["phone"];
	$pass = $_GET["pass"];
	$status = -1;
	$conn = new mysqli($server, $dbusername,  $dbpass, $dbname);
	if ($conn-> connect_error){
		die("Connection failed" . $conn->connect_error);
	}
	$sql = "select * from client where phone = '$phone'";
	$result = $conn->query($sql);
	if($result->num_rows > 0){
		while($row = $result -> fetch_assoc()){
			//echo 1;
			if($row["password"] == $pass)
				$status = 1;
			else
				$status = 0;
			$id = $row["id"];
		}
	}
	echo "{\"status\":"; 
	echo $status . ",";
	echo "\"id\":" . $id; 
	echo "}";

?>