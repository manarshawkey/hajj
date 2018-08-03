<?php
	$server = "localhost";
	$dbname = "hajjbank";
	$dbusername = "root";
	$dbpass = "";
		
	$conn = new mysqli($server, $dbusername,  $dbpass, $dbname);
	if ($conn-> connect_error){
		die("Connection failed" . $conn->connect_error);
	}
	
	$id1 = 0;
	$id2 = 0;
	$type1 = "transfer money";
	$type2 = "receive money";
	$phone1 = "";
	$phone2 = "";
	$amount = 0;
	$balance = 0;
	$id1 = $_GET["id1"];
	$phone2 = $_GET["phone2"];
	$amount = $_GET["amount"];
	
	// balance 1 >= amount
	
	$sql1 = "select * from client where id = '$id1'";
	$result1 = $conn->query($sql1);
	if($result1 -> num_rows > 0)
	{
		while($row1 = $result1->fetch_assoc()){
			$balance = $row1["balance"];
			$phone1 = $row1["phone"];
		}
	}
	
	
	$sql8 = "select * from client where phone = '$phone2'";
	$result8 = $conn->query($sql8);
	if($result8 -> num_rows > 0)
	{
		while($row8 = $result8->fetch_assoc()){
			$id2 = $row8["id"];
			//echo "hi";
		}
	}
	
	
	
	if($balance >= $amount) {
		//echo "kolo tmam";
		
		
		//transaction 1 3and client 1
		
		$sql2 = "insert into transaction (clientid, type, tr_time, client2phone, amount) values ('$id1', '$type1', sysdate(), '$phone1', '$amount')";
		if($conn->query($sql2) === true){
			//echo "zai elfol";
			//updating client 1 balance
			$sql3 = "update client set balance = balance - '$amount' where id = '$id1'";
			if($conn->query($sql3) === true){
				//echo "kda reda";
				//Transaction 2 3and client 2
				$sql4 = "select * from client where phone = '$phone2'";
				$result4 = $conn->query($sql4);
				if($result4->num_rows > 0){
					while($row4 = $result4->fetch_assoc()){
						$id4 = $row4["id"];
						//echo "3ash ya basha  ". $id4; 
						
						//transaction2
						$sql5 = "insert into transaction (clientid, type, tr_time, client1phone, amount) values ('$id2', '$type2', sysdate(), '$phone2', '$amount')";
						if($conn->query($sql5) === true){
							//echo "n7mdo w nshkor fadlo";
							$sql6 = "update client set balance = balance + '$amount' where id = '$id2'";
							if($conn->query($sql6) === true)
							{
								//echo "7amdella 3al salama";
								echo "{\"status\":" . 1 . "}";
							}
							else echo "{\"status\":" . 0 . "}";
						}else echo "{\"status\":" . 0 . "}";
					}
				} else echo "{\"status\":" . 0 . "}";
			}else echo "{\"status\":" . 0 . "}";
		}else echo "{\"status\":" . 0 . "}";
		
		
		
	}
	else {   //balance < amount
		echo "{\"status\":" . 0 . "}";
	}
	
	
	
	
	
	
	
	
	
?>