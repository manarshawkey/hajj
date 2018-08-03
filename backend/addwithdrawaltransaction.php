<?php
	$server = "localhost";
	$dbname = "hajjbank";
	$dbusername = "root";
	$dbpass = "";
	$id = 0; 
	$amount = 0;
	$type = "withdrawal";
	$atmno = 0;
	$flg = -1;
	$flg1 = -1;
	$id = $_GET["id"];
	$amount = $_GET["amount"];
	$atmno = $_GET["atmno"];
	$conn = new mysqli($server, $dbusername,  $dbpass, $dbname);
	if ($conn-> connect_error){
		die("Connection failed" . $conn->connect_error);
	}
	
	$sq = "select * from client where id = '$id'";
	$r = $conn->query($sq);
	if($r -> num_rows > 0){
		while($ro = $r->fetch_assoc()){
			$balance = $ro["balance"];
		}
	}
	if($balance >= $amount) {
		$sql = "insert into transaction (clientid, amount, atmno, type, tr_time) values ('$id', '$amount', '$atmno', '$type', sysdate())";
		if($conn->query($sql)){
			$flg = 1;
		}
		else
			$flg = 0;	
		$sqll = "update client set balance = balance - $amount where id = '$id'";
		if($conn->query($sqll) and $flg === 1){
			echo "{\"status\":" . 1 . "}";
		}
		else {
			echo "{\"status\":" . 0 . "}";
		}
	}
	else
		echo "{\"status\":" . 0 . "}";

?>