package edu.db.tool.deid.training;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.document.IToken;

import edu.db.tool.deid.utils.FileUtil;
import edu.db.tool.deid.utils.ResourceUtil;
import edu.db.tool.deid.utils.StringFormat;
/**
 *
 * @author Duy Bui
 */
public class CRFConfig {
	public String rootDir;

	public String experimentName;

	public CRFConfig(String rootDir,String experimentName) {
		super();
                this.rootDir=rootDir;
		this.experimentName = experimentName;

	}

	public String getRunDir() {
		return rootDir + "/" + experimentName;
	}

	public String getOutputdir() {
		return getRunDir() + "/outputs";
	}

	public String getTrainingDataDir() {
		return getRunDir() + "/" + "TrainingData";
	}

	public String getTrainingPropFile() {
		return getRunDir() + "/" + "standford.ner.prop";
	}

	public String getTrainingModelFile() {
		return getRunDir() + "/" + "standford.ser.gz";
	}

	public void prepareDirBinary(List<IDocument> documents, String configModelFile, String gazetteFile) {
		System.out.println(FileUtil.getAbsolutePath(getRunDir()));
                System.out.println("documents:"+documents.size());
		File dir = new File(rootDir);
		dir.mkdirs();

		dir = new File(getTrainingDataDir());
		dir.mkdirs();

		List<String> trainFileList = new ArrayList<>();
		for (IDocument doc : documents) {
			List<IToken> tokens = doc.getTokens();
			StringBuffer buf = new StringBuffer();
			for (IToken token : tokens) {

				String label = token.goldLabel;
				// if(!label.equals("O"))
				// label="PHI";

				buf.append(token.normText + "\t" + label + "\n");
			}

			String outputFilePAth = getTrainingDataDir() + "/" + doc.fileName + ".tsv";
			FileUtil.writeFile(buf.toString(), outputFilePAth, false);

			trainFileList.add(outputFilePAth);

		}

		System.out.println("trainFileList:" + trainFileList.size());

		StringBuffer buf = new StringBuffer();
		buf.append("trainFileList = " + StringFormat.listToString(trainFileList, ",") + "\n");
		buf.append("serializeTo = " + getTrainingModelFile() + "\n");
		if (gazetteFile != null) {
			buf.append("cleanGazette=true\n");
			File gazFile = new File(gazetteFile);
			buf.append("gazette=" + gazFile.getAbsolutePath().replaceAll("\\\\", "/") + "\n");

		}
                buf.append(ResourceUtil.getText(configModelFile));
//		buf.append(FileUtil.readFile(configModelFile));
		FileUtil.writeFile(buf.toString(), getTrainingPropFile(), false);
	}
}
