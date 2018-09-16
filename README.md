# EC_2018_grp17

#To create the class
javac	-cp	contest.jar	 player17.java      // If using other class include the classname.java after player17.java


#To create the submission
jar	 cmf	 MainClass.txt	 submission.jar	 player17.class  // If using other class include the classname.class after player17.class

#Add path to avoid error 
export	LD_LIBRARY_PATH= <your folder location>

#To run functions
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=SphereEvaluation	-seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=BentCigarFunction -seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=BentCigarFunction -seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=SchaffersEvaluation -seed=1

#To run with change in parameters 
java	–Dvar1=0.5	-jar	testrun.jar	-submission=player1		-	evaluation=BentCigarFunction -seed=1 and 

#To retrieve that parameter
Double.parseDouble(System.getProperty(“var1”));	

#To write to file
> ..
