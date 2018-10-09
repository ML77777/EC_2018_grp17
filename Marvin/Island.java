import java.util.Arrays;

public class Island {

	Chromosome[] populationIsland;
	Chromosome[] kidsIsland;
	int populationCount = 0;
	int kidsCount = 0;
	int popSize;
	double currentSDDev = 1;
	double topFitness = 0;
	int unchanged = 0;
	int maxUnchanged = 100;
	boolean newTopFit = false;
	double beginSDDEV = 1;

	Island(int popSize) {
		populationIsland = new Chromosome[popSize];
		kidsIsland = new Chromosome[popSize];
		this.popSize = popSize;
	}

	Chromosome populationIsland(int indexNum) {
		return populationIsland[indexNum];
	}

	void addParentChromosome(Chromosome newIndividual) {
		populationIsland[populationCount] = newIndividual;
		populationCount ++;
	}

	void addKidChromosome(Chromosome newIndividual) {
		kidsIsland[kidsCount] = newIndividual;
		if (newIndividual.getFitness() > topFitness) {
			topFitness= newIndividual.getFitness();
			newTopFit = true;
		}
		kidsCount ++;
	}
	Chromosome[] topXpercent(double percentage) {

		Arrays.sort(populationIsland);
		Chromosome[] returnArray = new Chromosome[(int) Math.round((popSize*percentage)+0.5)];
		for (int i = 0 ; i<(populationCount*percentage);i++) {
			returnArray[i] = populationIsland[i];
		}
		return returnArray;
	}

	Chromosome[] topXindividuals(int numIndividuals) {

		Arrays.sort(populationIsland);
		Chromosome[] returnArray = new Chromosome[numIndividuals];
		for (int i = 0 ; i<(numIndividuals);i++) {
			returnArray[i] = populationIsland[i];
		}
		return returnArray;
	}

	int getPopSize() {
		return this.popSize;
	}
	int getNumKids() {
		return this.kidsCount;
	}
	void kidsBecomeParents() {
		if (newTopFit) {
			unchanged = 0;
			this.setSDDEV(this.getSDDev() - (this.getSDDev()/10));
			newTopFit = false;
		} else {
			this.setSDDEV(this.getSDDev() - (this.getSDDev()/20));
			unchanged ++;
		}
		if (unchanged == maxUnchanged) {
			topFitness = 0;
			unchanged = 0;
			this.setSDDEV(beginSDDEV);
		}
		populationIsland = kidsIsland;
		kidsCount = 0;
	}
	double getSDDev() {
		return this.currentSDDev;
	}

	void setSDDEV(double newSd) {
		this.currentSDDev = newSd;
	}

	double getSumFitnessPopulation(){
		double sum = 0;
		for (int i = 0; i < populationIsland.length;i++){
        sum += populationIsland[i].getFitness();
		}
		return sum;
	}

}
