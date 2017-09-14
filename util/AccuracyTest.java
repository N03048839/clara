package spineReader;

import java.io.*;
import java.awt.Image;
import java.awt.image.*;
import java.util.*;
import javax.imageio.ImageIO;

class AccuracyTest 
{
	protected final String IMAGE_DIRECTORY;
	protected final String ANSWER_FILENAME;
	
	
	static boolean output_verbose = false;
	static boolean output_suppress = false;
	
	protected Image[] imageArray;
	protected String[] filenameArray;
	protected String[] answerArray;
	protected int[] charsCorrect;
	protected double[] score;
	
	protected int size;
	
	AccuracyTest(final String imageDir, final String ansFN)
	{
		this.IMAGE_DIRECTORY = imageDir;
		this.ANSWER_FILENAME = ansFN;
		
		try {  // --- Set up image array
			print("Populating image array...");
			populateImageArray(IMAGE_DIRECTORY);
			print("done\n");
		} catch (IOException e) {
			System.out.println("Error opening image directory \"" + IMAGE_DIRECTORY +"\"\n" + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		try {  // --- Set up answer array
			print("Populating answer array...");
			populateAnswerArray(ANSWER_FILENAME);
			print("done\n");
		} catch (IOException e) {
			System.out.println("Error opening answer file \"" + ANSWER_FILENAME +"\"\n" + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		
		size = answerArray.length;
		
		charsCorrect = new int[size];
		score = new double[size];
	}
	
	protected String ocr(Image image) {
		return null;
	}
	
	protected void stop() {
		
	}
	
	public void run()
	{
		print("Starting test...\n");
		
		for (int i = 0; i < this.size; i++) 
		{
			printVerbose("   IMG: " + filenameArray[i]);
			charsCorrect[i] = matchingChars(
							answerArray[i],
							ocr(imageArray[i])
							);
			int answerLength = answerArray[i].length();
			score[i] = charsCorrect[i] / answerLength;
			printVerbose("    " + charsCorrect[i] + "/" + answerLength 
										+ "    (" + score[i] + ")\n");
		}
		
		print("Test complete.  Computing results...\n");
		double sum = 0.0, avg;
		for (int i = 0; i < this.size; i++)
			sum += score[i];
		avg = sum / this.size;
		
		print(" Algorithm completed with accuracy of: " + avg + "\n");
	}
	
	
	protected int matchingChars(String a, String b)
	{
		int count = 0;
		String shorter = (a.length() >= b.length())? a : b;
		
		for (int i = 0; i < shorter.length(); i++) 
			if (a.charAt(i) == b.charAt(i))
				count++;
			
		return count;
	}
	
	protected void populateImageArray(final String IMG_DIR) throws IOException
	{
		printVerbose("\nOpening directory: " + IMG_DIR + "\n");
		File directory = new File(IMG_DIR);
		
		printVerbose("Directory contents:\n");
		File[] dirContents = directory.listFiles();
		
		Queue<Image> temp = new LinkedList<Image>();
		Queue<String> fntemp = new LinkedList<String>();
		 
		for (File file : dirContents) 
		{
			if (file.isFile()) {
				printVerbose("      " + file.getName() + "\n");
				temp.add(ImageIO.read(file));
				fntemp.add(file.getName());
			} 
			else if (file.isDirectory())
				printVerbose("[dir] " + file.getName() + "\n");
		}
		
		int img_cnt = temp.size();
		printVerbose("Scan complete; " + img_cnt + " valid images found\n");
		imageArray = new Image[img_cnt];
		
		int i = 0;
		while (!temp.isEmpty()) 
		{
			imageArray[i] = temp.removeFirst();
			filenameArray[i] = fntemp.poll();
			i++;
		}
	}
	
	protected void populateAnswerArray(final String ANS_FN) throws IOException
	{
		printVerbose("\nOpening answer file: " + ANS_FN + "\n");
		Scanner infile = new Scanner(ANS_FN);
		Queue<String> temp = new LinkedList<String>();
		while (infile.hasNextLine())
			temp.add(infile.nextLine());
		infile.close();
		
		int ans_cnt = temp.size();
		printVerbose("Scan complete; " + ans_cnt + " valid answers found\n");
		answerArray = new String[ans_cnt];
		
		int i = 0;
		while (!temp.isEmpty())
			answerArray[i++] = temp.poll();
	}
	
	private static void showHelpMessage()
	{
		System.out.println("HELP MESSAGE");
	}
	
	public void print(String s)
	{
		if (!output_suppress)
			System.out.print(s);
	}
	
	public void printVerbose(String s)
	{
		if (output_verbose)
			print(s);
	}
	
	public static void main(String[] args)
	{
		String IMG_DIR = null, ANS_FN = null;
		
		// --- Stores user's working directory
		final String RUN_DIR = System.getProperty("user.dir");
		final String ANS_FN_DEF = "answers.ans";
		
		if (args.length == 0) 
		{	// --- Run with default settings
			IMG_DIR = RUN_DIR;
			ANS_FN = ANS_FN_DEF;
		}
		else
		{	// --- Specify run settings
			for (int i = 1; i < args.length; i++)
			{
				// --- View list of commands
				if (args[i-1].matches("--help")) {
					showHelpMessage();
					System.exit(0);
				}
				
				// --- Specify new answer file
				if (args[i-1].matches("--ans"))
					ANS_FN = args[i];
				else
					ANS_FN = RUN_DIR + ANS_FN_DEF;
				
				// --- Specify verbose output
				if (args[i-1].matches("-v"))
					output_verbose = true;
				
				// --- Specify output suppression
				if (args[i-1].matches("-S"))
					output_suppress = true;
			}
		}
		
		AccuracyTest a = new AccuracyTest(IMG_DIR, ANS_FN);
		a.run();
	}
}
