/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Fall 2017 
// PROJECT:          Train Hub
// FILE:             TrainGenerator.java
//
// TEAM:    individual
// Author:  Zhenyu Zou
// email: zzou24@wisc.edu
// netID: 907 593 6980
// Lecture number: 001
//
//
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons: Identify persons by name, relationship to you, and email. 
// Describe in detail the the ideas and help they provided. 
// 
// Online sources: Piazza Discussions
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class provide methods for generating a Train.
 * 
 * COMPLETE THIS CLASS and HAND IN THIS FILE
 * 
 * @see Config
 */
public class TrainGenerator {
	
	/**
	 * Get a new train generated randomly.
	 * The constant variables for determining how many cargo, 
	 * what cargo and how heavy are in {@link Config}.
	 * 
	 * @return a train generated randomly
	 */
	public static Train getIncomingTrain(){
		Train incomingTrain = new Train("TrainHub");
		
		Random rand = new Random(System.currentTimeMillis());

		// How many freight cars
		int cartNum = rand.nextInt(Config.MAX_CART_NUM - Config.MIN_CART_NUM + 1) + Config.MIN_CART_NUM;

		for(int i=0;i<cartNum;i++){
			// What kind of cargo
			int loadIndex = rand.nextInt(Config.CARGO_ARRAY.length);
			String load = Config.CARGO_ARRAY[loadIndex];

			// How heavy
			int weight = rand.nextInt(Config.MAX_WEIGHT - Config.MIN_WEIGHT + 1) + Config.MIN_WEIGHT;

			// Where to
			int destIndex = rand.nextInt(Config.DEST_ARRAY.length);
			String dest = Config.DEST_ARRAY[destIndex];
			
			incomingTrain.add(new CargoCar(load, weight, dest));
		}
		
		return incomingTrain;
	}
	
	/**
	 * Get a new train generated from a file.
	 * Files for generating a train must have the format as follow
	 * <p>
	 * {destination},{cargo},{weight}<br>
	 * {destination},{cargo},{weight}<br>
	 * ...
	 * <p>
	 * where {destination} is a string among Config.DEST_ARRAY,
	 * {cargo} is a string among Config.CARGO_ARRAY,
	 * and {weight} is a string for any positive integer.
	 * <p>
	 * Ignore the line if it is not matched in this format. 
	 * See the sample in/outputs provided in the assignment description to get more details.
	 * 
	 * @param filename train input file name
	 * @return a train generated from a file
	 */
	public static Train getIncomingTrainFromFile(String filename){
		
		// construct a try-catch block to handle exception
		try{
			// initialize the file from the incoming file
			File file = new File(filename);
			
			// use scanner to read from the incoming file
			Scanner input = new Scanner(file);
			
			// set a new train object to null and construct a new train
			Train aTrain = null;
			aTrain = new Train("-1");
			
			// keep looping as long as there exist a next line in the file
			while(input.hasNextLine())
			{
				// store all the information to a new string, trim all the spaces
				String TempLine = input.nextLine().trim();
				
				// if the line does not contain char ",", then proceed to the next line
				while(!TempLine.contains(","))
					// update the line to the next one
					TempLine = input.nextLine();
				
				// if the format is valid, then split the line into parts
				String[] TempParts = TempLine.split(",");
				
				// if the line contains more parts than three, which means invalid line, then proceed to the next line
				if(TempParts.length != 3)
					// if not valid, then continue the loop
					continue;
				
				// grab for valid destination.
				String dest = TempParts[0].trim();
		        
				// get cargo product
				String product = TempParts[1].trim();
				
				// get the weight of cargo material
				String tempWeight = TempParts[2].trim();
				
				// initialize a new weight integer
				int weight = 0;
				
				// use the try catch block to avoid any bad numbers such as double
				try{
					weight = Integer.parseInt(tempWeight);
				}catch(NumberFormatException e)
				{
					// if exception is caught, then it is a bad line, then proceed the while loop
					continue;
				}
				
				// form a brand new car
				// attach the new car to the train
				aTrain.add(new CargoCar(product, weight, dest));
			}
	
			// close the input stream and return the trains
			input.close();
			return aTrain;
			
		}catch(FileNotFoundException e)
		{
			// the input file is bad, then display the error message and return null
			System.out.println(Config.ERROR_FILE_READ);
			return null;
		}
	}
}
