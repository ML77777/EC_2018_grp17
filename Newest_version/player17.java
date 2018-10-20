import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Arrays;
import java.util.Properties;
import java.util.Collections; // Added myself, so remove later probably if violate




public class player17 implements ContestSubmission
{

	Random rnd_;
	ContestEvaluation evaluation_;
    private int evaluations_limit_;

    public static final double STANDARDDEVSETUP = 1;
    public static final int NUMBEROFPOP = 25;
    int evals = 0;

	public player17()
	{
		rnd_ = new Random();

	}

	public static void main(String[] args) {


	}

	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation)
	{
		// Set evaluation problem used in the run
		evaluation_ = evaluation;

		// Get evaluation properties
		Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

		// Do sth with property values, e.g. specify relevant settings of your algorithm
        if(isMultimodal){
            // Do sth
        }else{
            // Do sth else
        }
    }

	public void printDoubleArray(double[] printedArray) {
		for (int i =0; i< printedArray.length; i++) {
			System.out.print((printedArray[i]+" "));
		}
		System.out.println();
	}


	public double[] generateFirstChild() {
		Random r = new Random();

		double[] newChild = new double[10];
		for (int i=0;i < 10;i++) {
			//newChild[i] = r.nextGaussian()*STANDARDDEVSETUP; //Mean 0 and the constant stdev
			newChild[i] = rnd_.nextGaussian()*STANDARDDEVSETUP; //Using the random rnd_ with seed
		}
		return newChild;
	}

	Chromosome[] createInitialPopulation(int numberOfPops) {

		Chromosome[] kids = new Chromosome[NUMBEROFPOP];
		for (int i =0;i < numberOfPops;i++) {

			kids[i] = new Chromosome(generateFirstChild(),1);
		}

		return kids;
	}

	Island InitializeIsland(int size) {
		Island newIsland = new Island(size);
		Chromosome[] parents = createInitialPopulation(size);

		for (int i = 0;i<parents.length ;i++) {
			parents[i].setFitness((double) evaluation_.evaluate(parents[i].getIndividual()));
			evals ++;
        }

		for (int i = 0 ;i < size ; i++) {
			newIsland.addParentChromosome(parents[i]);
		}
		return newIsland;
	}
/*
  Chromosome[][] CrossoverIslands(Island[] islandArray, int amount_new_children){
			Island first_island, second_island;
			Chromosome[] first_population_island,second_population_island;
			Chromosome[] first_crossover_population = new Chromosome[amount_new_children],second_crossover_population = new Chromosome[amount_new_children];
			double[] first_island_parent,second_island_parent;
			Chromosome[][] crossover_populations = new Chromosome[islandArray.length][];

      //Island level
      for (int i = 0; i < islandArray.length; i = i+2){
           first_island = islandArray[i];
					 second_island = islandArray[i+1];
           first_population_island = first_island.getPopulation();
					 second_population_island = second_island.getPopulation();
           //Population/Individual level
					 for (int j = 0; j < amount_new_children;j++){
               first_island_parent = first_population_island[j].getIndividual();
							 second_island_parent = second_population_island[j].getIndividual();

               //Chromosome level
							 double first_child[] = new double[10];
							 double second_child[] = new double[10];
							 for (int k = 0; k < 10; k++){
									 if (k < 5) {
											 first_child[k] = second_island_parent[k];
											 second_child[k] = first_island_parent[k];
									 } else {
											 first_child[k] = first_island_parent[k];
											 second_child[k] = second_island_parent[k];
									 }
							 }
							 first_crossover_population[j] = new Chromosome(first_child);
							 second_crossover_population[j] = new Chromosome(second_child);
					 }

					 for (int x =0 ;x< first_crossover_population.length;x++) {
						 first_crossover_population[x].setFitness((double) evaluation_.evaluate(first_crossover_population[x].getIndividual()));
						 evals ++;
						 second_crossover_population[x].setFitness((double) evaluation_.evaluate(second_crossover_population[x].getIndividual()));
						 evals ++;
					 }
           crossover_populations[i] = first_crossover_population;
					 crossover_populations[i+1] = second_crossover_population;
			}
			return crossover_populations;
	}*/

	Island[] Migrationislands(Island[] islandArray, int amount_to_swap){
			Island first_island, second_island;
			Chromosome[] first_population_island,second_population_island;
			Chromosome first_island_individual,second_island_individual;
			//Chromosome[][] crossover_populations = new Chromosome[islandArray.length][];

      //Island level
      for (int i = 0; i < islandArray.length; i = i+2){
           first_island = islandArray[i];
					 second_island = islandArray[i+1];

           first_population_island = first_island.getPopulation();
					 second_population_island = second_island.getPopulation();

           //Population/Individual level
					 for (int j = 0; j < amount_to_swap;j++){
               first_island_individual = first_population_island[j];
							 second_island_individual = second_population_island[j];

							 first_population_island[j] = second_island_individual;
							 second_population_island[j] = first_island_individual;
					 }
			}
			return islandArray;
	}

	public void run()
	{
		int numberOfIslands = 10;
		int current_generation = 0,generation_cycle = 0, lowest_fitnessIsland_index = 0;
		double total_generation = evaluations_limit_ / (NUMBEROFPOP * numberOfIslands);

     //For Migrationislands
    Island[] tocrossoverislands;
		Island[] islandArray = new Island[numberOfIslands];
		int cross_interval ;
		//cross_interval = (int) Double.parseDouble(System.getProperty("cross_interval"));
		cross_interval = 80;
		//cross_interval = (int)((total_generation-current_generation)/numberOfIslands);
		//cross_interval = (int)((total_generation-current_generation)/2);
    //System.out.println("cross interval: " + cross_interval);
    int amount_to_swap = 12;
		//amount_to_swap = (int) Double.parseDouble(System.getProperty("swap_size"));


		double[] meansParameters = new double[10];
		double[] stDevParas = new double[10];

		//Init
		for (int i = 0;i<numberOfIslands;i++) {
			islandArray[i] = InitializeIsland(NUMBEROFPOP);
		}

		while(evals<evaluations_limit_){
      int number_better = 0;
			for (int k = 0; k < numberOfIslands;k++) {
				//Selection
				meansParameters = new double[10];
				stDevParas = new double[10];

				for (int i=0;i<10;i++) {

					double tempMean = 0;
					double tempStdev = 0;
					Chromosome[] top25percent = islandArray[k].topXpercent(0.25);

					for (int j=0;j<(top25percent.length);j++) {

						tempMean = tempMean + top25percent[j].getSingleCoordinate(i);
						tempStdev = tempStdev + top25percent[j].getCoordinateSTdev(i);
					}

					meansParameters[i] = (tempMean/top25percent.length);
					stDevParas[i] = (tempStdev/top25percent.length);
				}
				//Mutation
				Chromosome[] kids = new Chromosome[islandArray[k].getPopSize()];

				if (evals + islandArray[k].getPopSize() > evaluations_limit_) {
					kids = new Chromosome[evaluations_limit_-evals];
				}
				for (int i=0;i<kids.length;i++) {

					double[] parametersIndividual = new double[10];
					for (int j = 0;j<10;j++) {
						parametersIndividual[j] = rnd_.nextGaussian() * islandArray[k].getSDDev() + meansParameters[j]; ////Using the random rnd_ with seed
					}

					kids[i] = new Chromosome(parametersIndividual);
				}
				//Evaluation

				for (int i =0 ;i< kids.length;i++) {

					kids[i].setFitness((double) evaluation_.evaluate(kids[i].getIndividual()));
					//System.out.println(kids[i].getFitness()+ " " +islandArray[k].getSDDev() + " " +islandArray[k].unchanged+ " " +islandArray[k].topFitness + " " + k);
				  //System.out.println(kids[i].getFitness() + "|"+k);
					//System.out.println(kids[i].getFitness());

					evals ++;
				}

				//Add them to the Island
				double fitness_islandk_old = islandArray[k].topFitness; // original topFitness for island k
				for (int i =0 ;i<kids.length;i++) {
					islandArray[k].addKidChromosome(kids[i]);
				}
				islandArray[k].kidsBecomeParents();

				// count number of island with better fitness for adaptive migration size
				//if (islandArray[k].topFitness > fitness_islandk_old){
				//	number_better++; // number of island with better fitness
				//}
			}

			//Migration interval adaptive
		  /*boolean cross_island = false;
			for (int i = 0; i < numberOfIslands;i++) {
					//if (islandArray[i].getPrep_elim() == cross_interval){
					if (islandArray[i].getElim_this_island()){
						//cross_interval = (int)((total_generation-current_generation)/numberOfIslands);
						//cross_interval = (int)((total_generation-current_generation)/2);
						cross_island = true;
						break;
					}
			}
			if (cross_island){
				for (int i = 0; i< numberOfIslands;i++){
							//islandArray[i].setPrep_elim(0);
							islandArray[i].setElim_this_island(false);
				}
				islandArray = Migrationislands(islandArray, amount_to_swap);
			}*/

			//Migration size adaptive
			/*if (number_better>=8){
				if (amount_to_swap >= 3){
					amount_to_swap--;
				}
			}
			if (number_better<=3){
				if (amount_to_swap <= 12 ){
					amount_to_swap++;
				}
			}*/

      //Tune and Determinstic
			generation_cycle++;
			current_generation++;
			if (generation_cycle == cross_interval){
         generation_cycle = 0;
				 //cross_interval = (int)((total_generation-current_generation)/numberOfIslands);
				 //cross_interval = (int)((total_generation-current_generation)/2);

				 //System.out.println("Cross interval " + cross_interval);

         islandArray = Migrationislands(islandArray, amount_to_swap);

        //Determinstic migration size
				//if (amount_to_swap>=3){
				//	 amount_to_swap--;
				 //}
			}

		}
	}



	public void run2()
	{
		Random r =new Random();

		//System.out.println(evaluation_.getProperties());
		int evals = 0;


		//Init
		Chromosome[] parents = createInitialPopulation(NUMBEROFPOP);
		Chromosome[] kids = new Chromosome[NUMBEROFPOP];


		//Establish first Fitness
		for (int i = 0;i<parents.length ;i++) {
			parents[i].setFitness((double) evaluation_.evaluate(parents[i].getIndividual()));
        	evals ++;
        }

		//First mean calc
		Arrays.sort(parents);
		int first25Percent = (int) Math.round((NUMBEROFPOP/4)+0.5); //0.5 TO ALWAYS ROUND UP

		double[] meansParameters = new double[10];
		double[] stDevParas = new double[10];

		double stDev = STANDARDDEVSETUP;
		double topFit = 0;
		double unchanged = 0;

		while(evals<evaluations_limit_){


			//Selection


			Arrays.sort(parents);
			first25Percent = (int) Math.round((NUMBEROFPOP/4)+0.5); //0.5 TO ALWAYS ROUND UP

			meansParameters = new double[10];
			stDevParas = new double[10];
			for (int i=0;i<10;i++) {
				double tempMean = 0;
				double tempStdev = 0;
				for (int j=0;j<first25Percent;j++) {
					tempMean = tempMean + parents[j].getSingleCoordinate(i);
					tempStdev = tempStdev + parents[j].getCoordinateSTdev(i);
				}
				meansParameters[i] = (tempMean/first25Percent);
				stDevParas[i] = (tempStdev/first25Percent);
			}


			//Crossover
        	//Mutation

			for (int i=0;i<NUMBEROFPOP;i++) {


				double[] parametersIndividual = new double[10];
				for (int j = 0;j<10;j++) {
					parametersIndividual[j] = r.nextGaussian()*stDev + meansParameters[j];
				}
				kids[i] = new Chromosome(parametersIndividual);

			}




			//Evaluate kids
			for (int i =0 ; i < NUMBEROFPOP ; i++) {
				double tempFitness = (double) evaluation_.evaluate(kids[i].getIndividual());
				kids[i].setFitness(tempFitness);
				//System.out.println(kids[i].getFitness());
				if (topFit < tempFitness) {
					topFit = tempFitness;
					stDev = stDev - stDev/10;
					unchanged = 0;

					//System.out.println(kids[i].getFitness()+","+evals+","+stDev);
					//System.out.println(kids[i].getFitness());
				} else if(unchanged >= NUMBEROFPOP*3) {

					stDev = STANDARDDEVSETUP;
					topFit = 0;
					unchanged = 0;

				} else {
					unchanged ++;
				}
				evals++;
				//System.out.printf("%.2f,", kids[i].getFitness());
				//System.out.print(kids[i].getFitness()+ ",");
				//printDoubleArray(kids[i].getIndividual());
			}




			parents = kids;
        }
	}






}
