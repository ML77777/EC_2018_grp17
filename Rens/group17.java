import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Arrays;
import java.util.Properties;




public class group17 implements ContestSubmission
{
	
	Random rnd_;
	ContestEvaluation evaluation_;
    private int evaluations_limit_;
    
    public static final double STANDARDDEVSETUP = 1;
    public static final int NUMBEROFPOP = 100;
    
	
	public group17()
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
	
	

	
	public double[] returnRandomMutation(double[] currentChild) {
		
		Random r = new Random();
		int randomNum =  r.nextInt((currentChild.length-1) + 1);
		int randomMut = r.nextInt(2);
		double[] randomMutArray;
		randomMutArray = currentChild;
		
		
		randomMutArray[randomNum] = randomMut;
		
		return randomMutArray;
		
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
			newChild[i] = r.nextGaussian()*STANDARDDEVSETUP; //Mean 0 and the constant stdev
		}
		return newChild;
	}
	
	public double[] combineParents(double[] parent1,double[] parent2) {
		Random r = new Random();
		double[] newChild = new double[10]; 
		
		int randomSplitPosition = r.nextInt(parent1.length-1);
		for (int i = 0 ;i <=randomSplitPosition;i++) {
			newChild[i] = parent1[i];
		}
		for (int j = randomSplitPosition ;j <parent2.length;j++) {
			newChild[j] = parent2[j];
		}
		
		return newChild;
	}
	
	public double generateNewStdev(double oldStdevMutate,int evalNum) {
		Random r = new Random();
		double learning = 1/(Math.pow(evalNum, 0.5));
		double newStdevMutate = oldStdevMutate * Math.exp(learning * r.nextGaussian());
		return newStdevMutate;
	}
	

	Chromosome[] createInitialPopulation(int numberOfPops) {
		
		
		
		Chromosome[] kids = new Chromosome[NUMBEROFPOP];
		for (int i =0;i < numberOfPops;i++) {
			
			kids[i] = new Chromosome(generateFirstChild(),1);
		}
		
		return kids;
	}

	 


	
	
	public void run()
	{
		Random r =new Random();
		
		System.out.println(evaluation_.getProperties());
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
		
		double stDev = 1;
		
		
		
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
			
			
			
			
			for (int i=0;i<NUMBEROFPOP;i++) {
				
				
				double[] parametersIndividual = new double[10];
				for (int j = 0;j<10;j++) {
					parametersIndividual[j] = r.nextGaussian() + meansParameters[j];
				}
				kids[i] = new Chromosome(parametersIndividual);
				
			}
			
			//Crossover
        	
        	
        	
        	//Mutation
        	
    
			
			//Evaluate kids
			for (int i =0 ; i < NUMBEROFPOP ; i++) {
				kids[i].setFitness((double) evaluation_.evaluate(kids[i].getIndividual()));
				evals++;
			}
            
			
			parents = kids;
        }
	}
	
	
	
	

	
}
