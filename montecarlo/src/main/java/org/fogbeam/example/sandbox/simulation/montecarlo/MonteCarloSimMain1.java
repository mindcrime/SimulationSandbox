package org.fogbeam.example.sandbox.simulation.montecarlo;

import org.apache.commons.math4.distribution.EmpiricalDistribution;
import org.apache.commons.math4.stat.descriptive.SummaryStatistics;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.core.source32.JDKRandom;
import org.apache.commons.statistics.distribution.ContinuousDistribution.Sampler;
import org.apache.commons.statistics.distribution.NormalDistribution;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class MonteCarloSimMain1 extends ApplicationFrame
{
	private static final long serialVersionUID = 1L;

	public MonteCarloSimMain1( DefaultCategoryDataset dataSet )
	{
		super( "MonteCarloSimMain1" );
		JFreeChart barChart = ChartFactory.createBarChart( "Outputs", 
				"X-Axis", "Y-Axis", dataSet );

		ChartPanel chartPanel = new ChartPanel( barChart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		setContentPane( chartPanel );			
	}
	
	// set up an equation with some input vars
	private final static double calcValue( double x )
	{
		double y = (Math.abs(x) + Math.PI);
		return y;
	}
	
	public static void main(String[] args) 
	{
		// choose a distribution, and range for each input var
		UniformRandomProvider rnd = new JDKRandom(1L);
		
		NormalDistribution distNormal = new NormalDistribution(0, 1);
		Sampler distNormalSampler = distNormal.createSampler(rnd);
		
		// iterate  many times, calculating the output var

		double[] rawData = new double[1200];
		for( int i = 0; i < 1200; i++ )
		{
			double fromNormalDist = distNormalSampler.sample();
			double yVal = calcValue( fromNormalDist );
			rawData[i] = yVal;
		}
		
		final int BIN_COUNT = 150;
		double[] histogram = new double[BIN_COUNT];
		EmpiricalDistribution emp = new EmpiricalDistribution(BIN_COUNT);
		emp.load(rawData);
		
		int k = 0;
		for( SummaryStatistics stats: emp.getBinStats() )
		{
			histogram[k++] = stats.getN();
		}
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for( int i = 0; i < histogram.length; i++ )
		{
			dataSet.addValue( histogram[i], "Count", new Integer(i) );
		}
				
		// plot the output distribution
		MonteCarloSimMain1 mcs = new MonteCarloSimMain1( dataSet );
		
		mcs.pack( );
	    RefineryUtilities.centerFrameOnScreen( mcs );
	    mcs.setVisible( true );
		
		// give the answer in terms of probabilities...
		// eg, 30% of the probability mass is below A, 60% is
		// between A and B, and 10% is above B, or something like
		// that
		
	    
	    
		System.out.println( "done" );
	}

}
