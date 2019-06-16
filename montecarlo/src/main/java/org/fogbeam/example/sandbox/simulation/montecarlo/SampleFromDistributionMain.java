package org.fogbeam.example.sandbox.simulation.montecarlo;

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

public class SampleFromDistributionMain extends ApplicationFrame
{

	public SampleFromDistributionMain( DefaultCategoryDataset dataSet ) 
	{
		super( "SampleFromNormalDistribution" );
		
		JFreeChart lineChart = ChartFactory.createLineChart(
	         "Random Numbers",
	         "X-Axis","Y-Axis",
	         dataSet,
	         PlotOrientation.VERTICAL,
	         true,true,false);
	         
	      ChartPanel chartPanel = new ChartPanel( lineChart );
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

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		
		for( int i = 0; i < 100; i++ )
		{
			double fromNormalDist = distNormalSampler.sample();
			dataSet.addValue( fromNormalDist, "Value", new Integer(i) );
			System.out.println( "fromNormalDist: " + fromNormalDist );
		}
		
		// plot the numbers...

		SampleFromDistributionMain sfd = new SampleFromDistributionMain( dataSet );
		
		sfd.pack( );
	    RefineryUtilities.centerFrameOnScreen( sfd );
	    sfd.setVisible( true );
	}

}
