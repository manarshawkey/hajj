<?php
	$server = "localhost";
	$dbname = "hajjbank";
	$dbusername = "root";
	$dbpass = "";
	$mid = 0;
	$clientid = 0; 
	$amount = 0;
	$storename = "";
	$balance = 0;
	$flg = -1; 
	$flg1 = -1;
	$type = "purchase";
	$mid = $_GET["mid"];
	$clientid = $_GET["clientid"];
	$amount = $_GET["amount"];
	$conn = new mysqli($server, $dbusername,  $dbpass, $dbname);
	if ($conn-> connect_error){
		die("Connection failed" . $conn->connect_error);
	}
	
	$s = "select * from client where id = '$clientid'";
	$res = $conn->query($s);
	if($res->num_rows > 0){
		while($row1 = $res->fetch_assoc()){
			$balance = $row1["balance"];
		}
	}
	if($balance < $amount or $balance === 0){
		echo "{\"status\":" . -1 . "}";
	}
	else{
	
		$sql = "select * from merchant where id = '$mid'";
		$result = $conn->query($sql);
		if($result->num_rows > 0){
			while($row = $result->fetch_assoc())
			{
				$storename = $row["storename"];
			}
		}
		$sqll = "insert into transaction (merchantid, amount, type, clientid, tr_time, storename) values ('$mid', '$amount', '$type', '$clientid', sysdate(), '$storename')";
		if( $conn->query($sqll) === true){
			$flg = 1;
		}
		else
		{
			$flg = 0;
		}
	
		$sq = "update client set balance = balance - $amount where id = '$clientid'";
		if ($conn->query($sq)){
			$flg1 = 1;
		}
		else {
			$flg1 = 0;
		}
		
		if(($flg === 1) and ($flg1 === 1)){
			echo "{\"status\":" . 1 . "}";
		}
		else {
			echo "{\"status\":" . 0 . "}";
		}
		
	}
	

?>











