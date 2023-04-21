package police;

import java.io.File;
import java.io.IOException;
/**
 * Class whose main method will follow the steps needed for arresting
 * members of criminal organizations. The step should be followed as established
 * in the
 * project's document.
 * 
 * @author Gretchen
 *
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