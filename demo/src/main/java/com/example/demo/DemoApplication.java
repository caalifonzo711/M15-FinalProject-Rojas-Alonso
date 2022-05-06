package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.util.Scanner;


public class DemoApplication{
	public static void main(String[]args){
		WebClient client;
		System.out.println("please type in one of the following choices exactly as shown below and press ENTER: ");
		System.out.println("currency, currentWeather, currentIssLocation");
		Scanner scan = new Scanner(System.in);
		String s = scan.next();
		if (s.equals("currency")) {

			System.out.println("Please input a crypto below (example: ETH, BTC, DOGE)");
			String coinInput = scan.next();
			client = WebClient.create("https://rest.coinapi.io/v1/exchangerate/"+coinInput+"/USD/?apikey=2BA8A64C-30B5-460C-BF30-30A65FE48152");
			CoinResponse coinResponse = null;///////////////////////////////////
			try{
				Mono<CoinResponse> responseJson = client
						.get()
						.retrieve()
						.bodyToMono(CoinResponse.class);
				System.out.println("testing");
				CoinResponse coinresponse = responseJson.share().block();
				System.out.println(coinresponse.asset_id_base);
				double dollars = Double.parseDouble(coinresponse.rate);
				System.out.printf("$%.2f", dollars);
			}
			catch(Exception e){
				System.out.println("An error occurred: " + e.getMessage());

			}}

		else if (s.equals("currentWeather")){
			client = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat=33.6846&lon=-117.8265&appid=faf8c5b333741f28eedb7787ae134b8e");
			WeatherResponse weatherResponse = null;//////////////////////////////
			try{
				Mono<WeatherResponse> responseJson = client
						.get()
						.retrieve()
						.bodyToMono(WeatherResponse.class);
				System.out.println("testing");
				WeatherResponse weatherresponse = responseJson.share().block();
				System.out.println(weatherresponse.coord);
				System.out.println(weatherresponse.weather);
				System.out.println(weatherresponse.name);
			}
			catch(Exception e){
				System.out.println("An error occurred: " + e.getMessage());

			}}

		else if (s.equals("currentIssLocation")) {
			client = WebClient.create("http://api.open-notify.org/iss-now.json");
			IssResponse issResponse = null;/////////////////////////////////////
			try{
				Mono<IssResponse> responseJson = client
						.get()
						.retrieve()
						.bodyToMono(IssResponse.class);
				System.out.println("testingyeaaah");
				IssResponse issresponse = responseJson.share().block();
				System.out.println(issresponse.iss_position);
				//use iss_position to call weather API
				String issLat = issresponse.iss_position.get("latitude");
				String issLon = issresponse.iss_position.get("longitude");
				WebClient issClient;
				issClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat="+issLat+"&lon="+issLon+"&appid=faf8c5b333741f28eedb7787ae134b8e");
				WeatherResponse weatherResponse = null;//////////////////////////////
					Mono<WeatherResponse> IssResponseJson = issClient
							.get()
							.retrieve()
							.bodyToMono(WeatherResponse.class);
					WeatherResponse weatherresponse = IssResponseJson.share().block();
					System.out.println(weatherresponse.coord);
					System.out.println(weatherresponse.weather);
					System.out.println(weatherresponse.name);
					System.out.println(weatherresponse.sys.get("country"));

			}
			catch(Exception e){
				System.out.println("An error occurred: yeaa YAAAAH" + e.getMessage());


		}}
else {
		System.out.println("invalid");
		return;


	}
}}
//add while loop at start
//while (user input != end){
//let user pick lat & long too
	//make sure lat & long are valid
	//make sure user picks a valid coin
	//you can add a while loop until user input returns a valid website
//the code you have}
//delete scanner obj from memory