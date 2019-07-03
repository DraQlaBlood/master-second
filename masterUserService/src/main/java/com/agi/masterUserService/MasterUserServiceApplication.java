package com.agi.masterUserService;;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

@EnableSwagger2
@EnableFeignClients("com.agi.masterUserService")
@SpringBootApplication
public class MasterUserServiceApplication {

	public static void main(String[] args) {


		SpringApplication.run(MasterUserServiceApplication.class, args);

		run(args);
	}

	public static void run(String[] args) {

		try{
			PrintStream console = new PrintStream(new File("C:\\json\\console.txt"));
			System.setOut(console);

			console.print("Populate both Micro-services database (MongoBD) with Groups data with POST Method\n\n");
			console.print("localhost:8081/master/v1/addGroup \n\n");
			console.print("CREATE <username> \n\n");
			console.print("localhost:8081/master/v1/addUser \n\n");
			console.print("ADD <username> TO <group-name> \n\n");
			console.print("localhost:8081/master/v1/addUserToGroup/{$group_id}?email='$UserEmail' \n\n");
			console.print("REMOVE <username> FROM <group-name> \n\n");
			console.print("localhost:8081/master/v1/removeUserToGroup/{$group_id}?email='$UserEmail' \n\n");
			console.print("DELETE <username> \n\n");
			console.print("localhost:8081/master/v1/deleteUser/{user_id} \n\n");
			console.print("for more documentation on masterUserService \n\n");
			console.print(" http://localhost:8081/swagger-ui.html \n\n");
			console.print("For CRUD purpose I have run the microservices with MongoDB, Please make sur you have " +
					"Mongo running before tests \n\n");


		}catch (FileNotFoundException e){
			System.out.println(e);
		}


	}

}
