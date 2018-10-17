tuningData <- read.csv("C:\\Users\\Rens\\Dropbox\\School\\Evolutionary Computing\\report\\dataTuning\\katsuura.csv",header=TRUE)
tuningData = tuningData[-(1:30),] ##KATSUURA
# Compute the analysis of variance
res.aov <- aov(KatsuuraEvaluation ~ Parameter, data = tuningData)##KATSUURA
# Summary of the analysis
summary(res.aov)

tuningData <- read.csv("C:\\Users\\Rens\\Dropbox\\School\\Evolutionary Computing\\report\\dataTuning\\bent.csv",header=TRUE)
tuningData = tuningData[-(1:10),] ##BENT
# Compute the analysis of variance
res.aov <- aov(BentCigar ~ Parameter, data = tuningData)##BENT
# Summary of the analysis
summary(res.aov)

tuningData <- read.csv("C:\\Users\\Rens\\Dropbox\\School\\Evolutionary Computing\\report\\dataTuning\\schaffer.csv",header=TRUE)
tuningData = tuningData[-(1:10),] ##SCHAFFER
# Compute the analysis of variance
res.aov <- aov(SchaffersEvaluation ~ Parameter, data = tuningData)##SCHAFFER
# Summary of the analysis
summary(res.aov)