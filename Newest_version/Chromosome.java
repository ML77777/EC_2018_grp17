import java.util.Arrays;

public class Chromosome implements Comparable<Chromosome>{

	double[] individual = new double[10];
	double fitness;
	double singleStdevs;
	double[] coordinateStdevs= new double[10];
	
	Chromosome() {
		
	}
	Chromosome(double[] individualInfo) {
		setindividual(individualInfo);
	}
	Chromosome(double[] individualInfo,double stdev) {
		setindividual(individualInfo);
		setSingleSTDev(stdev);
		Arrays.fill(this.coordinateStdevs, stdev);
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public void setindividual(double[] individual) {
		this.individual = individual;
	}
	
	public void setSingleSTDev(double newStDev) {
		
		this.singleStdevs = newStDev;
		Arrays.fill(this.coordinateStdevs, newStDev);
	}
	
	public void setCoordinateSTDev(double newStDev,int position) {
		this.coordinateStdevs[position] = newStDev;
	}
	
	public double getSingleSTdev() {
		return singleStdevs;
	}
	
	public double getCoordinateSTdev(int position) {
		return coordinateStdevs[position];
	}
	
	public double[] getIndividual() {
		return individual;
	}
	
	public double getSingleCoordinate(int position) {
		return individual[position];
	}
	
	public double getFitness() {
		return fitness;
	}

	@Override
	public int compareTo(Chromosome compareFitness) {
		
		double compareFit=  ((Chromosome) compareFitness).getFitness(); 
		// TODO Auto-generated method stub
		return Double.compare(compareFit,this.fitness);
	}
}
