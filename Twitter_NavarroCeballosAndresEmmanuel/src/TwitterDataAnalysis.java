import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class TwitterDataAnalysis extends JFrame{
	private JTextArea yesTextArea;
    private JTextArea noTextArea;
    private JProgressBar progressBar;
    private List<String> dataCollection;
	public TwitterDataAnalysis() {
		setTitle("An�lisis de Datos en Twitter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridBagLayout());

        yesTextArea = new JTextArea();
        noTextArea = new JTextArea();
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        add(new JScrollPane(yesTextArea), gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        add(new JScrollPane(noTextArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        add(progressBar, gbc);

        setVisible(true);
        dataCollection = new ArrayList<>();
        
        Thread dataThread = new Thread(this::fillData);
        Thread histogramThread = new Thread(this::generateHistogram);

        dataThread.start();
        histogramThread.start();
	}
	private void fillData() {
        Random random = new Random();
        int totalData = 10_000_000;
        int currentData = 0;

        while (currentData < totalData) {
            String result = random.nextBoolean() ? "SI" : "NO";
            dataCollection.add(result);
            SwingUtilities.invokeLater(() -> {
            if (result.equals("SI")) {
                yesTextArea.append(result + "\n");
            } else {
                noTextArea.append(result + "\n");
            }
            });
            currentData++;

            int progress = (int) (((double) currentData / totalData) * 100);
            progressBar.setValue(progress);
        }
    }
	private void generateHistogram() {
		int yesCount = 0;
        int noCount = 0;
        for (String result : dataCollection) {
            if (result.equals("SI")) {
                yesCount++;
            } else {
                noCount++;
            }
        }
		try {
            Thread.sleep(5000); 
            System.out.print("Histograma generado:\nSI: " + yesCount + "\nNO: " + noCount);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	 }
	public static void main(String[] args) {
        SwingUtilities.invokeLater(TwitterDataAnalysis::new);
    }
}