import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

import java.util.Arrays; // Added myself, so remove later probably

public class player17 implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;
    private int evaluations_limit_;

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

	/*public void SelectParents(){
	}

	public void CreateChildren(){
	}

	public void VariationOffspring(){
	}*/

  public double[][] CreatePopulation(double population_size, double child_offset){
    int final_population_size = (int) (population_size * child_offset);

		    double population[][] = new double[final_population_size][];


		    for (int i = 0; i < population_size ; i++) {
					//double individual[] = {0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
					  double individual[] = {0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
						if (i > 0) {
					      for (int j = 0; j < 10 ; j++) {
                    individual[j] = rnd_.nextDouble();
							      //System.out.print("Print random: " + individual[j] + "\n" );
						    }
					  }
            population[i] = individual;
				}
				System.out.print("Returning population\n" );
		    System.out.print("Array size: " + population.length + "\n");
		return population;
	}

	public void run()
	{
		// Run your algorithm here

        int evals = 0;
	      double population[][], population_size = 100.0, child_offset = 1.5;

        // init population
				population = CreatePopulation(population_size,child_offset);
				System.out.print("Got the population\n" );
        // calculate fitness
				for (int i = 0; i < (population_size); i++) {
					  double child[] = population[i];
					  Double fitness = (double) evaluation_.evaluate(child);
					  evals++;
				}
        while(evals<evaluations_limit_){
            // Select parents
            // Apply crossover / mutation operators

            // Check fitness of unknown fuction
					 for (int i = 0; i < (population_size); i++) {
						 		//System.out.print("Evaluatie nummer " + evals + "\n\n" );
					      //System.out.print("Iteratie: " + i + "\n" );
					      double child[] = population[i];
						    //System.out.print("The child: " + Arrays.toString(child)+ "\n" );
                Double fitness = (double) evaluation_.evaluate(child);
								evals++;
					 }
            //evals++;
            // Select survivors
        }
				//System.out.print("Fitness value " + fitness + "\n");
	}
}

/* Commands to run
javac	-cp	contest.jar	 player17.java
jar	 cmf	 MainClass.txt	 submission.jar	 player17.class
export	LD_LIBRARY_PATH=

java	 -jar 	testrun.jar	 -submission=player17  -evaluation=SphereEvaluation	-seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=BentCigarFunction -seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=BentCigarFunction -seed=1
java	 -jar 	testrun.jar	 -submission=player17  -evaluation=SchaffersEvaluation -seed=1

java	–Dvar1=0.5	-jar	testrun.jar	-submission=player1		-	evaluation=BentCigarFunction -seed=1 and Double.parseDouble(System.getProperty(“var1”));	*/
