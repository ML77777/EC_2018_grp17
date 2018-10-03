import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

import java.util.Arrays; // Added myself, so remove later probably if violate
import java.util.ArrayList; // Added myself, so remove later probably if violate
import java.util.Collections; // Added myself, so remove later probably if violate

public class player17 implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;
  private int evaluations_limit_,evals = 0;

	public player17()
	{
		rnd_ = new Random();
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
				System.out.print("Evaluation limit: " + evaluations_limit_ + "\n");
		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));
				System.out.print("Boolean isMultimodal: " + isMultimodal + "\n");
				System.out.print("Boolean hasStructure: " + hasStructure + "\n");
				System.out.print("Boolean isSeparable: " + isSeparable + "\n");

		// Do sth with property values, e.g. specify relevant settings of your algorithm
        if(isMultimodal){
            // Do sth
        }else{
            // Do sth else
        }
    }

//second version
			// Select the parents from the population of 100, take the top 26 parents from the ranking based on fitness values and 24 other random
				public double[][] SelectParents(ArrayList<Individual> genotype_population,int max_parents){
			     int index, max = 100;
					 int current_parents = 0;
					 double[][] parents = new double[max_parents-9][]; //An even number

			      //Take the top 26 fitness candidates to be parents
						for (int i = 0; current_parents < max_parents-9; i++){
							  //if (i % 2 == 0){
								if (i == 0){
								   index = 0;
							  } else {
								   index = rnd_.nextInt(max); // Takes a random index between 0 and max
							  }
			          parents[current_parents] = genotype_population.get(index).get_genotype();
							  current_parents++;
						}
						return parents;
				}

			// second version
			public double[][] CreateChildren(double[][] parents, int max_children){
					 int amount_children = 0, int_range;
					 double children[][] = new double[max_children][];
					 double first_parent[],second_parent[];

					 for (int i = 0; amount_children < max_children; i++){ // Need to create so many children for every 2 parent
								first_parent = parents[i];
								second_parent = parents[i+1];

								//System.out.print("Parent 1" + Arrays.toString(first_parent) + "\n");
								//System.out.print("Parent 2" + Arrays.toString(second_parent) + "\n");

                if (i == 0){

									 for (int k = 0; k < 10; k++){
										 double mutation_child[] = new double[10];
										 for (int l = 0; l < 10; l++){
											  mutation_child[l] = first_parent[l];
											 if ( k < 10 && k == l){
												 int_range = rnd_.nextInt(21000000) - 10000000; //Will get integer values between -10 and 10 if 21 and and 10, but placed 6 zeros after
												 mutation_child[l] += (double) int_range/100000000;  // to get range betwee -0.1 and 0.1 if was 100, but put 6 zeros behind
											 }
										 }
										 children[amount_children] = mutation_child;
										 amount_children++;
 									 }
								}    // Each two pair parents will have two children, the first one is mutation of the best fitness (quality)
 								else {//if (random_value > 0.50 ){
										double first_child[] = new double[10];
										double second_child[] = new double[10];
									  for (int j = 0; j < 10; j++){
												if (j < 5) {
													  first_child[j] = second_parent[j];
														second_child[j] = first_parent[j];
												} else {
													 first_child[j] = first_parent[j];
													 second_child[j] = second_parent[j];
												}
										}
										/*for (int j = 0; j < 10; j++){
											int random_value = rnd_.nextInt(2);
											if (random_value == 0)//(j % 2 == 0){
												first_child[j] = second_parent[j];
												second_child[j] = first_parent[j];
											} else {
												first_child[j] = first_parent[j];
												second_child[j] = second_parent[j];
											}
										}*/
									children[amount_children] = mutation_generator(first_child);;
									amount_children++;
									children[amount_children] = mutation_generator(second_child);;
									amount_children++;
									i++;
								}
						}
						return children;
			}

		//Takes a genotye sequence, and takes a chance if mutation occurs or not, otherwise check for mutation at random places with random values
		public double[] mutation_generator(double[] genotype_sequence){
			    double mutation_chance = rnd_.nextDouble(), random_value, genotype_value, final_genotype_sequence[] = new double[10];

					if (mutation_chance > 0.5){
		          for (int i = 0; i < 10; i++){
								 random_value = rnd_.nextDouble();
								 genotype_value = genotype_sequence[i];
								 if (random_value > 0.5){
		                 genotype_value = rnd_.nextDouble();
								 }
								 final_genotype_sequence[i] = genotype_value;
							}
					} else {final_genotype_sequence = genotype_sequence; };
					return final_genotype_sequence;
		}

  //Initiliaze the random genotypes (10 dimension values) of a population of a specified size and return it as an array
  public double[][] CreateGenotypes(double population_size){

		    double population_genotypes[][] = new double[ (int) population_size][];

		    for (int i = 0; i < population_size ; i++) {
					//double individual[] = {0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
					  double individual_genotype[] = {0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
						if (i > 0) {
					      for (int j = 0; j < 10 ; j++) {
                    individual_genotype[j] = rnd_.nextGaussian();//rnd_.nextDouble();
							      //System.out.print("Print random: " + individual[j] + "\n" );
						    }
					  }
            population_genotypes[i] = individual_genotype;
				}

		    return population_genotypes;
	}

//From a array of genotypes, calculates the fitness for each genotype array and creates an Individual object containing genotypes and fitness value, returns a array of individuals
  public Individual[] create_individuals(double[][] array_genotypes){


			int size = array_genotypes.length;
			Individual[] array_of_individuals = new Individual[size];

      for (int i = 0; i < size; i++){
				double[] individual_genotypes = array_genotypes[i];
				Double fitness = (double) evaluation_.evaluate(individual_genotypes);
				evals++;
				Individual an_individual = new Individual(individual_genotypes,fitness);
				array_of_individuals[i] = an_individual;
			}

			return array_of_individuals;
	}

	public void run()
	{
		// Run your algorithm here
        int population_size = 100, amount_parents = 50, amount_children = 50, top_parents_split = 26,removed;
	      double initial_genotypes[][],parents[][],children[][];
        Individual[] temp_array_individual;
				//List<Individual> temp_population_list
				ArrayList<Individual> population_list,children_list;//= new ArrayList<>(Arrays.asList(population));

        // init population
				initial_genotypes = CreateGenotypes(population_size);
				System.out.print("Got the intial genotypes of population of Array size: " + initial_genotypes.length + "\n" );
				temp_array_individual = create_individuals(initial_genotypes);
				System.out.print("Got the individuals\n" );

				population_list = new ArrayList<Individual>(Arrays.asList(temp_array_individual));
				Collections.sort(population_list,Collections.reverseOrder());

				System.out.print("Size of arraylist " + population_list.size()  + "\n" );
				/*for (int i = 0; i < population_list.size()  ;i++ ){
					System.out.print("Fitness index " + i + " is " + population_list.get(i).get_fitness() + "\n" );
				}*/

       while(evals<evaluations_limit_){
            // Select parents
						//parents = SelectParents(population_list,amount_parents, top_parents_split);
						parents = SelectParents(population_list,amount_parents);
						//System.out.print("Selected parents of size " + parents.length + "\n");

						// Apply crossover / mutation operators
						children = CreateChildren(parents,amount_children);
						/*for (int i = 0; i < children.length ;i++){
							 System.out.print("Genotype index " + i + " is " + Arrays.toString(children[i]) + "\n\n" );
						}*/
            //System.out.print("Children created of size " + children.length + "\n");
            // Check fitness of unknown fuction (children)

						temp_array_individual = create_individuals(children);
						children_list = new ArrayList<Individual>(Arrays.asList(temp_array_individual));
						/*for (int i = 0; i < children_list.size()  ;i++ ){
							System.out.print("Fitness index " + i + " is " + Arrays.toString(children_list.get(i).get_genotype()) + "\n" );
							System.out.print("Fitness index " + i + " is " + children_list.get(i).get_fitness() + "\n" );
						}*/
						//population_list.subList(population_list.size()-amount_children, population_list.size()).clear();         // Eliminate worst x lowest ranked individuals
           /* for (int i = 0; i < 50  ;i++ ){
							   population_list.remove(rnd_.nextInt(population_list.size()));
						}*/
						Collections.sort(population_list);
						population_list.subList(population_list.size()-10, population_list.size()).clear();
						//System.out.print("Size of arraylist 10 min" + population_list.size()  + "\n" );
						population_list.addAll(children_list);
						Collections.sort(population_list,Collections.reverseOrder());
					  population_list.subList(population_list.size()-40, population_list.size()).clear();
						//System.out.print("Size of arraylist na een cycle " + population_list.size()  + "\n" );
						//population_list.subList(population_list.size()-40, population_list.size()).clear();
			      //System.out.print("Size of arraylist after adding " + population_list.size()  + "\n" );
						//System.out.print("Size" + population_list.size()  + "\n" );
						/*for (int i = 0; i < population_list.size()  ;i++ ){
							//System.out.print("Fitness index " + i + " is " + population_list.get(i).get_fitness() + "\n" );
							System.out.print("Fitness index " + i + " is " + Arrays.toString(population_list.get(i).get_genotype()) + "\n" );
						}*/
            // Select survivors (25 elimnations atm)
						//System.out.print("Before elimination new population size: " + population_list.size() + "\n");
						//population_list.subList(population_list.size()-amount_children, population_list.size()).clear();         // Eliminate worst x lowest ranked individuals

					  /*removed = 0;
						for (int i = 0; i < parents.length; i++){
							System.out.print("Parent " + i + Arrays.toString(parents[i]) + "\n");
						}
            removed = 0;
						for (int i = 0; i < parents.length ;i++ ){

								for (int j = 0; j < population_list.size();j++) {
											if ( Arrays.equals(population_list.get(j).get_genotype(),parents[i])){
													population_list.remove(j);
													//System.out.print("Removed parent of index i " + i + " which was j " + j + "\n");
													removed++;
													System.out.print("Removed " + removed + " van i value " + i + " dat is index " + j + "\n");
													break;
											}
								}
							}
						System.out.print("Size of arraylist after removing " + population_list.size()  + "\n" );
						Collections.sort(population_list,Collections.reverseOrder());*/

						//System.out.print("Fitness index " + i + " is " + population_list.get(i).get_fitness() + "\n" );
						//System.out.print("Elimnated some individuals, new population size: " + population_list.size() + "\n");
						//System.out.print("Size" + population_list.size()  + "\n" );
						//System.out.print("-----------------Cycle completed ----------------\n");
        }
	}
}

/* Commands to run
javac	-cp	contest.jar	 player17.java Individual.java
jar	 cmf	 MainClass.txt	 submission.jar	 player17.class Individual.class
export	LD_LIBRARY_PATH=/home/marvin/Documenten/Evolutionary_computing_assignment/class

java	 -jar 	testrun.jar	 -submission=player17  -evaluation=SphereEvaluation	-seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=BentCigarFunction -seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=BentCigarFunction -seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=SchaffersEvaluation -seed=1

java	–Dvar1=0.5	-jar	testrun.jar	-submission=player1		-	evaluation=BentCigarFunction -seed=1 and Double.parseDouble(System.getProperty(“var1”));	*/
