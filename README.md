# Data Structures Project 1: CrackTheCrimeCode
This is a project from the class Data Structures and Algorithms (CIIC4020) that is about arresting criminal organizations and deciphering their messages to find the leader.
# Running the project
To run the project, simply go to the `ArrestOperation` Java file and run the "main" method. If you want to use another case folder, simply change the `casePath` variable to the case folder you want, or have added.
Below is the main method:
```java
/**
 * Class whose main method will follow the steps needed for arresting
 * members of criminal organizations. The step should be followed as established
 * in the project's document.
 */
public class ArrestOperation {

	public static void main(String[] args) throws IOException {
		String casePath = "case1";
		PoliceDepartment PD = new PoliceDepartment("Captain Morgan");
		String caseFolder = "inputFiles/" + casePath;
		File flyersFile = new File(caseFolder + "/Flyers");
		PD.setUpOrganizations(caseFolder);
		for (File f : flyersFile.listFiles()) {
			String leader = PD.decipherMessage(f.getAbsolutePath());
			PD.arrest(leader);
		}
		PD.policeReport("results/" + casePath + "Report");
	}
}
```
## Images
![image](https://user-images.githubusercontent.com/92653848/233871078-4ab89f71-43df-4e1d-bcf8-21a3f124e332.png)

![image](https://user-images.githubusercontent.com/92653848/233871155-7e2329d9-ec21-419b-ac24-3b6aae852f92.png)

## Hope you enjoy :)
