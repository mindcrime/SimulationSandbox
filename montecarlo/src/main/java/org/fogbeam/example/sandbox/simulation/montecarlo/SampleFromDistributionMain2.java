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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class SampleFromDistributionMain2 extends ApplicationFrame
{

	public SampleFromDistributionMain2( DefaultCategoryDataset dataSet ) 
	{
		super( "SampleFromNormalDistribution" );
		
		JFreeChart barChart = ChartFactory.createBarChart( "Outputs", 
							"X-Axis", "Y-Axis", dataSet );
		
		ChartPanel chartPanel = new ChartPanel( barChart );
	    chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	    setContentPane( chartPanel );		
	}
	
	public static void main(String[] args) 
	{
		// just to play around with using the Distributions from
		// Apache Commons Math, and doing some plotting..
		
		UniformRandomProvider rnd = new JDKRandom(1L);
				
		NormalDistribution distNormal = new NormalDistribution(0, 1);
		Sampler distNormalSampler = distNormal.createSampler(rnd);
		
		double[] rawData = new double[2200];
		for( int i = 0; i < 2200; i++ )
		{
			double fromNormalDist = distNormalSampler.sample();
			rawData[i] = fromNormalDist;
			System.out.println( "fromNormalDist: " + fromNormalDist );
		}
		
		// plot the numbers...
		// bin the outputs and plot a bar chart of the bin value against count of
		// answers that fall in the bin
				
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
		
		SampleFromDistributionMain2 sfd = new SampleFromDistributionMain2( dataSet );
		
		sfd.pack( );
	    RefineryUtilities.centerFrameOnScreen( sfd );
	    sfd.setVisible( true );
	}
}
