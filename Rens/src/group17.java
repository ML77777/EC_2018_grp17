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
    public static final int NUMBEROFPOP = 25;
    int evals = 0;
	
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
	
	
	public void run()
	{
		int numberOfIslands = 3;
		
		Island[] islandArray = new Island[numberOfIslands];
		Random r =new Random();
		
		
		
		double[] meansParameters = new double[10];
		double[] stDevParas = new double[10];

		
		
		//Init
		for (int i = 0;i<numberOfIslands;i++) {
			islandArray[i] = InitializeIsland(NUMBEROFPOP);
		}
		
		while(evals<evaluations_limit_){
			
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
						parametersIndividual[j] = r.nextGaussian() * islandArray[k].getSDDev() + meansParameters[j];
					}
					
					kids[i] = new Chromosome(parametersIndividual);
				}
				//Evaluation
				
				for (int i =0 ;i<kids.length;i++) {
	
					kids[i].setFitness((double) evaluation_.evaluate(kids[i].getIndividual()));
					//System.out.println(kids[i].getFitness()+ " " +islandArray[k].getSDDev() + " " +islandArray[k].unchanged+ " " +islandArray[k].topFitness + " " + k);
					System.out.println(kids[i].getFitness() + "|"+k);
					//System.out.println(kids[i].getFitness());
					
					evals ++;
				}
				
				//Add them to the Island
				for (int i =0 ;i<kids.length;i++) {
					islandArray[k].addKidChromosome(kids[i]);
				}
				islandArray[k].kidsBecomeParents();
			}
			
		}
		
		//First mean calc 
		
//		int first25Percent = (int) Math.round((NUMBEROFPOP/4)+0.5); //0.5 TO ALWAYS ROUND UP
//		
//		double[] meansParameters = new double[10];
//		double[] stDevParas = new double[10];
//		
//		double stDev = STANDARDDEVSETUP;
//		double topFit = 0;
//		double unchanged = 0;
//		
//		while(evals<evaluations_limit_){
//        	
//			
//			//Selection
//			
//			
//			Arrays.sort(parents);
//			first25Percent = (int) Math.round((NUMBEROFPOP/4)+0.5); //0.5 TO ALWAYS ROUND UP
//			
//			meansParameters = new double[10];
//			stDevParas = new double[10];
//			for (int i=0;i<10;i++) {
//				double tempMean = 0;
//				double tempStdev = 0;
//				for (int j=0;j<first25Percent;j++) {
//					tempMean = tempMean + parents[j].getSingleCoordinate(i);
//					tempStdev = tempStdev + parents[j].getCoordinateSTdev(i);
//				}
//				meansParameters[i] = (tempMean/first25Percent);
//				stDevParas[i] = (tempStdev/first25Percent);
//			}
//			
//
//			//Crossover
//        	//Mutation
//        	
//			for (int i=0;i<NUMBEROFPOP;i++) {
//				
//				
//				double[] parametersIndividual = new double[10];
//				for (int j = 0;j<10;j++) {
//					parametersIndividual[j] = r.nextGaussian()*stDev + meansParameters[j];
//				}
//				kids[i] = new Chromosome(parametersIndividual);
//				
//			}
//			
//			
//
//			
//			//Evaluate kids
//			for (int i =0 ; i < NUMBEROFPOP ; i++) {
//				double tempFitness = (double) evaluation_.evaluate(kids[i].getIndividual());
//				kids[i].setFitness(tempFitness);
//				//System.out.println(kids[i].getFitness());
//				if (topFit < tempFitness) {
//					topFit = tempFitness;
//					stDev = stDev - stDev/10;
//					unchanged = 0;
//					
//					//System.out.println(kids[i].getFitness()+","+evals+","+stDev);
//					//System.out.println(kids[i].getFitness());
//				} else if(unchanged >= NUMBEROFPOP*3) {
//					
//					stDev = STANDARDDEVSETUP;
//					topFit = 0;
//					unchanged = 0;
//					
//				} else {
//					unchanged ++;
//				}
//				evals++;
//				//System.out.printf("%.2f,", kids[i].getFitness()); 
//				//System.out.print(kids[i].getFitness()+ ",");
//				//printDoubleArray(kids[i].getIndividual());
//			}
//            
//			
//			
//			
//			parents = kids;
//        }
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
