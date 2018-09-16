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

		// Select the parents from the population of 100, take the top 26 parents from the ranking based on fitness values and 24 other random
			public double[][] SelectParents(ArrayList<Individual> genotype_population,int max_parents,int top_parents_split){
		     int index, max = 100;
				 int current_parents = 0;
				 double[][] parents = new double[max_parents][]; //An even number

		      //Take the top 26 fitness candidates to be parents
					for (int i = 0; current_parents < max_parents; i++){
						  if (i < top_parents_split){
							   index = i;
						  } else {
							   index = rnd_.nextInt(max-top_parents_split ) + top_parents_split; // Takes index between 26(top_parents) and 100 (max)
						  }
		          parents[current_parents] = genotype_population.get(index).get_genotype();
						  current_parents++;
					}
					return parents;
			}

		//Create 25 children, 4 possibilities to start, same as parent 1, parent 2, first half parent 1 + second half parent 2 and reverse. Each step has chance for mutation
			public double[][] CreateChildren(double[][] parents){
				   int max_children = parents.length/2, amount_children = 0;
		       double children[][] = new double[max_children][];
					 double random_value,first_parent[],second_parent[];

					 for (int i = 0; amount_children < max_children; i = i+2){ // Need to create so many children for every 2 parent
		            first_parent = parents[i];
							  second_parent = parents[i+1];
		            random_value = rnd_.nextDouble();
		            //System.out.print("Parent 1" + Arrays.toString(first_parent) + "\n");
								//System.out.print("Parent 2" + Arrays.toString(second_parent) + "\n");
							  if (random_value > 0.75 && random_value < 1.0){
		              children[amount_children] = mutation_generator(first_parent);
									//System.out.print(" Zelfde als parent 1 met index " + i + "\n");
							  } else if (random_value > 0.50){
									    double new_child[] = new double[10];
		                  for (int j = 0; j < 10; j++){
								  			   if (j < 5) {
								  					  new_child[j] = first_parent[j];
								  				 } else {
											  		  new_child[j] = second_parent[j];
								  				 }
										  }
									children[amount_children] = mutation_generator(new_child);;
											//System.out.print(" half 1 en half 2 met index  " + i + "\n");
							  }
							  else if (random_value > 0.25) {
		              children[amount_children] = mutation_generator(second_parent);;
									//System.out.print(" Zelfde als parent 2 met index " + i + "\n");
						  	}
							  else {
									  double new_child[] = new double[10];
								  	for (int j = 0; j < 10; j++){
								  			if (j < 5) {
								  				 new_child[j] = second_parent[j];
								  			} else {
									  			 new_child[j] = first_parent[j];
									  		}
								    }
										//System.out.print(" half 2 en half 1 met index  " + i + "\n");
									  children[amount_children] = mutation_generator(new_child);
						   }
							 amount_children++;
						}
						/*for (int i = 0; i < max_children; i++){
						System.out.print("Children array: " + i +" "+ Arrays.toString(children[i]) + "\n\n" );
					}*/
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
                    individual_genotype[j] = rnd_.nextDouble();
							      //System.out.print("Print random: " + individual[j] + "\n" );
						    }
					  }
            population_genotypes[i] = individual_genotype;
				}
				System.out.print("Returning population\n" );
		    System.out.print("Array size: " + population_genotypes.length + "\n");
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
        int population_size = 100, amount_parents = 50, amount_children = 25, top_parents_split = 26;
	      double initial_genotypes[][],parents[][],children[][];
        Individual[] temp_array_individual;
				//List<Individual> temp_population_list
				ArrayList<Individual> population_list,children_list;//= new ArrayList<>(Arrays.asList(population));

        // init population
				initial_genotypes = CreateGenotypes(population_size);
				System.out.print("Got the intial genotypes of population\n" );
        // calculate fitness (and store in individual objects and then convert into 1 big list to be sorted)
				temp_array_individual = create_individuals(initial_genotypes);
				System.out.print("Got the individuals\n" );

				population_list = new ArrayList<Individual>(Arrays.asList(temp_array_individual));
				Collections.sort(population_list,Collections.reverseOrder());

				System.out.print("Size" + population_list.size()  + "\n" );
				/*for (int i = 0; i < population_list.size()  ;i++ ){
					System.out.print("Fitness index " + i + " is " + population_list.get(i).get_fitness() + "\n" );
				}*/

       while(evals<evaluations_limit_){
            // Select parents
						parents = SelectParents(population_list,amount_parents, top_parents_split);
						//System.out.print("Selected parents\n");

						// Apply crossover / mutation operators
						children = CreateChildren(parents);
            //System.out.print("Children created\n");
            // Check fitness of unknown fuction (children)
						temp_array_individual = create_individuals(children);
						children_list = new ArrayList<Individual>(Arrays.asList(temp_array_individual));
						/*for (int i = 0; i < children_list.size()  ;i++ ){
							System.out.print("Fitness index " + i + " is " + children_list.get(i).get_fitness() + "\n" );
						}*/
						population_list.addAll(children_list);
						Collections.sort(population_list,Collections.reverseOrder());
						/*System.out.print("Size" + population_list.size()  + "\n" );
						for (int i = 0; i < population_list.size()  ;i++ ){
							System.out.print("Fitness index " + i + " is " + population_list.get(i).get_fitness() + "\n" );
						}*/
            // Select survivors (25 elimnations atm)
						population_list.subList(population_list.size()-amount_children, population_list.size()).clear();
						//System.out.print("Elimnated some individuals\n");
						/*System.out.print("Size" + population_list.size()  + "\n" );
						for (int i = 0; i < population_list.size()  ;i++ ){
							System.out.print("Fitness index " + i + " is " + population_list.get(i).get_fitness() + "\n" );
						}*/
						//System.out.print("-----------------Cycle completed ----------------\n");
        }
	}
}

/* Commands to run
javac	-cp	contest.jar	 player17.java Individual.java
jar	 cmf	 MainClass.txt	 submission.jar	 player17.class Individual.class
export	LD_LIBRARY_PATH=

java	 -jar 	testrun.jar	 -submission=player17  -evaluation=SphereEvaluation	-seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=BentCigarFunction -seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=BentCigarFunction -seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=SchaffersEvaluation -seed=1

java	–Dvar1=0.5	-jar	testrun.jar	-submission=player1		-	evaluation=BentCigarFunction -seed=1 and Double.parseDouble(System.getProperty(“var1”));	*/
